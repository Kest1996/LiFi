package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gui.RadiantGUI;
import gui.ReceiverCoefData;
import gui.ReceiverGUI;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import radiation.Radiant;
import radiation.Receiver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.lang.Math.abs;
import static java.lang.Math.acos;
import static java.lang.Math.sqrt;

public class ReceiverModel extends GuiModel {
    private double sensetivity;
    private double responseRate;
    private String name;
    private transient ImageView imgv;
    private transient Map<String, Double> coefficients = new HashMap<>();
    ReceiverModel() {
        img = "resources\\img\\receiver.jpg";
        sizeX = 0;
        sizeY = 0;
        sizeZ = 0;
    }
    ReceiverModel(ReceiverGUI receiverGUI) {
        this();
        Image imgo = new Image(img);
        imgv = new ImageView(imgo);
        this.sensetivity = receiverGUI.getSensitivity();
        this.responseRate = receiverGUI.getResponseRate();
        this.name = receiverGUI.getName();
    }

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
    private double getR(GuiModel guiModel1, GuiModel guiModel2) {
        return sqrt(sqr(guiModel1.getX()-guiModel2.getX())+sqr(guiModel1.getY()-guiModel2.getY())+sqr(guiModel1.getZ()-guiModel2.getZ()));
    }

    public String toString(){
        return ("Receiver "+getX()+"_"+getY()+"_"+getZ());
    }
    /* МБ потом понадобится
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
     */

    private double sqr(double n) {return n*n;}

}