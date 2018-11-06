package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gui.RadiantGUI;
import gui.ReceiverCoefData;
import gui.ReceiverGUI;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import radiation.Diagram;
import radiation.Radiant;
import radiation.Receiver;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.lang.Math.abs;
import static java.lang.Math.acos;
import static java.lang.Math.sqrt;

public class ReceiverModel extends GuiModel {

    private String sensitivity;
    private String name;

    private transient ImageView imgv;
    private transient Map<String, Double> coefficients = new HashMap<>();
    private transient Diagram sensitivityData;

    /**
     * Конструткор по умолчанию
     */

    ReceiverModel() {
        img = "resources\\img\\receiver.jpg";
        sizeX = 0;
        sizeY = 0;
        sizeZ = 0;
    }

    /**
     * Конструктор с параметрами
     * @param receiverGUI
     */

    ReceiverModel(ReceiverGUI receiverGUI) {
        this();
        Image imgo = new Image(img);
        imgv = new ImageView(imgo);
        this.sensitivity = receiverGUI.getSensitivity();
        this.name = receiverGUI.getName();
    }

    /**
     * Мапа для последующей передачи данных в гуи
     * @param objs
     * @return
     */

    public HashMap<String, ReceiverCoefData> getE(ArrayList<GuiModel> objs) {

        //Мапа для последующей передачи данных в гуи
        HashMap<String, ReceiverCoefData> result = new HashMap<>();
        coefficients = new HashMap<>();
        double k = Math.PI/180; //коэффициент преобразования в градусы

        for (int i=0; i<objs.size(); i++) {
            //расстояние между левыми точками
            double r = getR(this, objs.get(i));
            double phi = Math.atan2((objs.get(i).getY()-y),(objs.get(i).getX()-x))/k;
            double teta = acos((objs.get(i).getZ()-z)/r)/k-90;
            if (objs.get(i) instanceof RadiantModel) {
                String str = (objs.get(i).getX()+"_"+objs.get(i).getY()+"_"+objs.get(i).getZ());
                coefficients.put(str, ((RadiantModel) objs.get(i)).getE(phi,teta));
                //System.out.println(str+"\tr = "+r+"\tphi = "+phi+"\tteta = "+teta);
                //Мапа для последующей передачи данных в гуи
                result.put(str,new ReceiverCoefData(r, phi, teta, k));
            }
        }

        /*
        System.out.println("Коэффициенты:");
        try {
            Set<Map.Entry<String, Double>> set = coefficients.entrySet();
            for (Map.Entry<String, Double> me : set) {
                System.out.print(me.getKey() + ": ");
                System.out.println(me.getValue());
            }
        }
        catch (NullPointerException exc) {
            System.out.println("Нет пересечений");
        }
        */
        return result;
    }

    /**
     * Получение коэффициента приемника
     * @param guiModel1
     * @param guiModel2
     * @return
     */

    private double getR(GuiModel guiModel1, GuiModel guiModel2) {
        return sqrt(sqr(guiModel1.getX()-guiModel2.getX())+sqr(guiModel1.getY()-guiModel2.getY())+sqr(guiModel1.getZ()-guiModel2.getZ()));
    }

    public String toString(){
        return ("Receiver "+getX()+"_"+getY()+"_"+getZ());
    }

    /**
     * Получение коэфициента приемника в зависимости от наличия питания
     */

    private double getR(GuiModel guiModel1, GuiModel guiModel2, boolean r2){
        return sqrt(getRx(guiModel1,guiModel2)+getRy(guiModel1,guiModel2)+getRz(guiModel1,guiModel2));
    }
    private double getRx(GuiModel guiModel1, GuiModel guiModel2){
        return sqr(guiModel1.getX()+guiModel1.getSizeX()-guiModel2.getX()+guiModel2.getSizeX());
    }
    private double getRy(GuiModel guiModel1, GuiModel guiModel2){
        return sqr(guiModel1.getY()+guiModel1.getSizeY()-guiModel2.getY()+guiModel2.getSizeY());
    }
    private double getRz(GuiModel guiModel1, GuiModel guiModel2){
        return sqr(guiModel1.getZ()+guiModel1.getSizeZ()-guiModel2.getZ()+guiModel2.getSizeZ());
    }


    /**
     * Получение диаграммы приемника
     * @param Fe
     * @param spectrum
     * @param eyeSensitivity
     * @return
     */

    public Diagram getIp(double Fe,Diagram spectrum, Diagram eyeSensitivity){

        //Коэффциенты получаемого сигнала
        double[] optEff = {0.0000152,0.00000366,0.00000174,0.00000107,0.00000064,0.0000005,0.000000318,0.00000025};
        double[] L = {0.5,1.0,1.5,2.0,2.5,3.0,3.5,4.0};

        //Пересчет световых параметров в энергетические
        Diagram Fe_x_V = new Diagram("Fe*V");
        double start = getStartIndex(spectrum,eyeSensitivity);
        double end = getLastIndex(spectrum,eyeSensitivity);
        for (double i = start;i<=end;i++){
            Fe_x_V.add(i,spectrum.getByX(i).getMeaning()*eyeSensitivity.getByX(i).getMeaning());
        }

        Diagram FeLED = new Diagram("FeLED");
        double FeLEDn = 0;
        for (double i = start;i<=end-1;i++){
            FeLED.add(i,(Fe_x_V.getByX(i+1).getMeaning()+Fe_x_V.getByX(i).getMeaning())*(i+1-i)/2);
            FeLEDn = FeLEDn + FeLED.getByX(i).getMeaning();
        }
        FeLEDn = FeLEDn*683;

        /*Нормировка значений через коэффициент распределения энергетической спектральной плотности к
        абсолютной величине, с учетом полной оптической мощности, излучаемой источником.
         */

        double Kn = Fe/FeLEDn;
        Diagram spectrumn = new Diagram("spectrumn");
        for (double i=spectrum.getBegin();i<=spectrum.getEnd();i++){
            spectrumn.add(i,spectrum.getByX(i).getMeaning()*Kn);
        }

        /*Учет потерь потока излучения при преобразовании излучения от источника до приемника с учетом геометрических
        ограничений, связанных с размером приёмного устройства и удаленностью от источника излучения. Преобразование
        спектральных кривых (спектральной плотности излучения источника света и спектральной чувствительности фотодиода)
        */

        ArrayList<Diagram> FeL = new ArrayList<>();
        for (int i=0;i<L.length;i++) {
            FeL.add(new Diagram("Fe"+L[i]));
            start = getStartIndex(spectrumn,sensitivityData);
            end = getLastIndex(spectrumn,sensitivityData);
            for (double j=start;j<=end;j++) {
                FeL.get(i).add(j,spectrumn.getByX(j).getMeaning()*sensitivityData.getByX(j).getMeaning()*optEff[i]);
            }
        }

        /*Расчет величины фототока Ip [A], получаемого в результате спектральных преобразований
        с учетом геометрических потерь на фотоприёмнике.*/

        Diagram Ip = new Diagram("Ip");
        double IpL;
        for (int i=0;i<L.length;i++) {
            IpL = 0;
            start = FeL.get(i).getBegin();
            end = FeL.get(i).getEnd();
            for (double j=start;j<=end-1;j++) {
                IpL = IpL + (FeL.get(i).getByX(j+1).getMeaning()+FeL.get(i).getByX(j).getMeaning())*(j+1-j)/2;
            }
            Ip.add(L[i],IpL);
        }
        return Ip;
    }

    /**
     * Получение начального индекса
     * @param diagram1
     * @param diagram2
     * @return
     */

    private double getStartIndex(Diagram diagram1, Diagram diagram2) {
        if (diagram1.getBegin()>diagram2.getBegin()) {
            return diagram1.getBegin();
        }
        else {
            return diagram2.getBegin();
        }
    }

    /**
     * Получение конечного индекса
     * @param diagram1
     * @param diagram2
     * @return
     */

    private double getLastIndex(Diagram diagram1, Diagram diagram2) {
        if (diagram2.getEnd()>diagram1.getEnd()) {
            return diagram1.getEnd();
        }
        else {
            return diagram2.getEnd();
        }
    }

    public void setSensitivity() {
        String libPath = "src/main/resources/Library/ReceiverDiagram/";
        File file = new File(libPath+name+".txt");
        sensitivityData = new Diagram(file.getName().substring(0,file.getName().length()-4),file);
    }

    public Diagram getSensitivityData(){
        return sensitivityData;
    }

    private static double sqr(double x){
        return x*x;
    }

    /**
     * Расчет SNR
     * @param Ip
     * @return
     */

    public static double countSNR(double Ip) {
        double n = 1.1; //Глубина модуляции переменного СВЧ излучения
        double q = 1.6*Math.pow(10,-19); //Элементарный заряд
        double KbT = 2.59*Math.pow(10,-2); //Тепловая энергия
        double Rl = 50.0; //Сопротвдение нагрузки приемника
        double B = 1.5*Math.pow(10,8); //Ширина полосы пропускания
        double Id = 20*Math.pow(10,-9);
        double SNR1 = (sqr(n)*sqr(Ip)*Rl)/(8*q*KbT*B); //Термический

        System.out.println((8*q*KbT*B));

        double SNR2 = (n*Ip)/(2*sqrt(2)*q*B); //Фотонный
        double SNR3 = (sqr(n)*sqr(Ip))/(2*sqrt(2)*q*B*(n*Ip+sqrt(2)*Id)); //Теневого тока
        double SNR = 1/(1/SNR1+1/SNR2+1/SNR3);
        return SNR;
    }

    public String getName() {
        return name;
    }
}