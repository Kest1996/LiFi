package model;

import gui.ReceiverCoefData;
import javafx.collections.FXCollections;
import javafx.stage.Stage;
import radiation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Model{

    private ArrayList<RadiantModel> Radiants = new ArrayList<>();
    private ArrayList<ReceiverModel> Receivers = new ArrayList<>();

    public transient ArrayList<Diagram> radiantDiagrams = new ArrayList<>();
    public transient ArrayList<Diagram> receiverDiagrams = new ArrayList<>();
    public transient ArrayList<Directivity> radiantDirectivities = new ArrayList<>();

    private transient Diagram eyeSensitivity;

    /**
     * Конструктор
     * @param name
     * @param fileName
     */

    public Model(String name, String fileName) {

        Receivers = ModelLoader.getReceivers(fileName);
        for (int i=0;i<Receivers.size();i++) {
            Receivers.get(i).setSensitivity();
        }

        Radiants = ModelLoader.getRadiants(fileName);
        for (int i=0;i<Radiants.size();i++) {
            Radiants.get(i).setSpectrum();
            Radiants.get(i).setDirectivity();
        }

        //Чтение библиотеки спектров источников
        File directory = new File("src/main/resources/Library/RadiantDiagram");
        File[] arrayFiles = directory.listFiles();
        for (int i=0;i<=arrayFiles.length-1;i++){
            radiantDiagrams.add(new Diagram(arrayFiles[i].getName().substring(0,arrayFiles[i].getName().length()-4),arrayFiles[i]));
        }

        //Чтение библиотеки направленности приемников
        directory = new File("src/main/resources/Library/Directivity");
        arrayFiles = directory.listFiles();
        for (int i=0;i<=arrayFiles.length-1;i++){
            radiantDirectivities.add(new Directivity(arrayFiles[i].getName().substring(0,arrayFiles[i].getName().length()-4),arrayFiles[i]));
        }

        //Чтение библиотеки кривых чувствительности приемников
        directory = new File("src/main/resources/Library/ReceiverDiagram");
        arrayFiles = directory.listFiles();
        for (int i=0;i<=arrayFiles.length-1;i++){
            receiverDiagrams.add(new Diagram(arrayFiles[i].getName().substring(0,arrayFiles[i].getName().length()-4),arrayFiles[i]));
        }

        //Чтение кривой чувствительности глаза
        File file = new File("src/main/resources/Library/eyeSensitivity.txt");
        eyeSensitivity = new Diagram(file.getName().substring(0,file.getName().length()-4),file);

        run();
    }

    /**
     * Метод запуска модели
     */

    public void run() {

        //Настройки модели и трассировки
        //Точность по углу фи (ось X)
        double dphi = 1000.0;
        //Предельная дальность отслеживания
        double maxDistance = 100.0;

        //Добавление всех объектов в массив для проверки пересечений
        ArrayList<GuiModel> objs = new ArrayList<>();
        objs.addAll(Radiants);
        objs.addAll(Receivers);

        //Трассировка лучей
        ResultDataTable[] resultData = new ResultDataTable[Radiants.size()];
        for (int i=0; i<Radiants.size(); i++) {
            for (double phi=0.0; phi<Math.PI/2; phi=phi+Math.PI/dphi) {
                for (int tetaInd=0; tetaInd<Radiants.get(i).getDirectivityData().getSize();tetaInd++) {
                    //double teta = Radiants.get(i).getDirectivityData().getPoint(tetaInd).getIndex();
                    //traceRay(Radiants.get(i), phi, teta, maxDistance, objs, dphi);
                    traceRay(Radiants.get(i), phi, Radiants.get(i).getDirectivityData().getPoint(tetaInd), maxDistance, objs, dphi);
                }
            }
            resultData[i] = new ResultDataTable();
            for (int j=0; j<Receivers.size(); j++) {
                resultData[i].add(new ResultData(Receivers.get(j), Receivers.get(j).getIp(Radiants.get(i).getFe(), Radiants.get(i).getSpectrumData(), eyeSensitivity)));
                //System.out.println(i+"   "+j+"   "+Receivers.get(j).getEnergy());
                Receivers.get(j).setEnergy(0.0);
            }
            resultData[i].setList();
        }

        /*


        //Временно не используемый метод
        /*
        ArrayList<GuiModel> objs = new ArrayList<>();
        objs.addAll(Radiants);
        //getE
        ArrayList<HashMap<String, ReceiverCoefData>> coefMap = new ArrayList<>();
        for (int i = 0; i<Receivers.size();i++) {
            coefMap.add(Receivers.get(i).getE(objs));
        }

        //Перевод источников и приемников в массив для отображения
        ArrayList<GuiModel> objects = new ArrayList<>();
        for (int i = 0; i<Radiants.size();i++) {
            objects.add(Radiants.get(i));
        }
        for (int i = 0; i<Receivers.size();i++) {
            objects.add(Receivers.get(i));
        }

        */


        //Создание базы окна
        Stage ModelGUI = new Stage();
        ModelGUI.setTitle("Модель");
        ModelGUI.setX(0);
        ModelGUI.setY(0);

        //Установка расположения
        ModelScene modelScene = new ModelScene(ModelGUI, 1280, (720 - 50),Radiants,Receivers,resultData);
        ModelGUI.setScene(modelScene.getScene());

        ModelGUI.show();
        ModelGUI.setMaximized(true);
    }

    private void traceRay(RadiantModel radiant, double phi, double teta, double maxDistance, ArrayList<GuiModel> objs, double dphi) {
        double currentX = radiant.getX();
        double currentY = radiant.getY();
        double currentZ = radiant.getZ();
        for (double r = 0; r<=maxDistance; r++) {
            currentX = currentX + Math.sin(teta-Math.PI/2)*Math.cos(phi);
            currentY = currentY + Math.sin(teta-Math.PI/2)*Math.sin(phi);
            currentZ = currentZ + Math.cos(teta-Math.PI/2);
            for (int i=0; i<objs.size(); i++) {
                if (checkIntersection(currentX, currentY, currentZ, objs.get(i))) {
                    if (i>=Radiants.size()) {
                        Receivers.get(i-Radiants.size()).addEnergy(radiant.getDirectivityData().getByAngle(teta).getMeaning()/dphi);
                        //System.out.println(currentX+"  "+currentY+"  "+currentZ);
                        //System.out.println(i+" "+phi+"  "+teta+"  "+(radiant.getDirectivityData().getByAngle(teta).getMeaning()/dphi));
                        return;
                    }
                }
            }
        }
    }

    private void traceRay(RadiantModel radiant, double phi, DiagramPoint tetaP, double maxDistance, ArrayList<GuiModel> objs, double dphi) {
        double currentX = radiant.getX();
        double currentY = radiant.getY();
        double currentZ = radiant.getZ();
        double teta = tetaP.getIndex();
        for (double r = 0; r<=maxDistance; r++) {
            currentX = currentX + Math.sin(teta-Math.PI/2)*Math.cos(phi);
            currentY = currentY + Math.sin(teta-Math.PI/2)*Math.sin(phi);
            currentZ = currentZ + Math.cos(teta-Math.PI/2);
            for (int i=0; i<objs.size(); i++) {
                if (checkIntersection(currentX, currentY, currentZ, objs.get(i))) {
                    if (i>=Radiants.size()) {
                        Receivers.get(i-Radiants.size()).addEnergy(tetaP.getMeaning()/dphi);
                        return;
                    }
                }
            }
        }
    }

    private boolean checkIntersection(double x, double y, double z, GuiModel obj) {
        if (Math.floor(x) != obj.getX()) {
            return false;
        }
        if (Math.floor(y) != obj.getY()) {
            return false;
        }
        if (Math.floor(z) != obj.getZ()) {
            return false;
        }
        return true;
    }
}