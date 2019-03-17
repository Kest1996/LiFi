package model;

import gui.ReceiverCoefData;
import javafx.collections.FXCollections;
import javafx.stage.Stage;
import radiation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Model{

    private CopyOnWriteArrayList<RadiantModel> Radiants = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<ReceiverModel> Receivers = new CopyOnWriteArrayList<>();

    public transient ArrayList<Diagram> radiantDiagrams = new ArrayList<>();
    public transient ArrayList<Diagram> receiverDiagrams = new ArrayList<>();
    public transient ArrayList<Directivity> radiantDirectivities = new ArrayList<>();

    int threads = 8;

    private ArrayBlockingQueue<Ray> queueForRead = new ArrayBlockingQueue<>(threads, true);

    private transient Diagram eyeSensitivity;


    /**
     * Конструктор
     * @param name
     * @param fileName
     */

    public Model(String name, String fileName) {

        Receivers = new CopyOnWriteArrayList<>(ModelLoader.getReceivers(fileName));
        for (int i = 0; i < Receivers.size(); i++) {
            Receivers.get(i).setSensitivity();
        }

        Radiants = new CopyOnWriteArrayList<>(ModelLoader.getRadiants(fileName));
        for (int i=0; i<Radiants.size(); i++) {
            Radiants.get(i).setSpectrum();
            Radiants.get(i).setDirectivity();
        }

        //Чтение библиотеки спектров источников
        File directory = new File("src/main/resources/Library/RadiantDiagram");
        File[] arrayFiles = directory.listFiles();
        for (int i=0; i<=arrayFiles.length-1; i++){
            radiantDiagrams.add(new Diagram(arrayFiles[i].getName().substring(0,arrayFiles[i].getName().length()-4),arrayFiles[i]));
        }

        //Чтение библиотеки направленности приемников
        directory = new File("src/main/resources/Library/Directivity");
        arrayFiles = directory.listFiles();
        for (int i=0; i<=arrayFiles.length-1; i++){
            radiantDirectivities.add(new Directivity(arrayFiles[i].getName().substring(0,arrayFiles[i].getName().length()-4),arrayFiles[i]));
        }

        //Чтение библиотеки кривых чувствительности приемников
        directory = new File("src/main/resources/Library/ReceiverDiagram");
        arrayFiles = directory.listFiles();
        for (int i=0; i<=arrayFiles.length-1; i++){
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
        double maxDistance = 2000.0;

        //Добавление всех объектов в массив для проверки пересечений
        ArrayList<GuiModel> objs = new ArrayList<>();
        objs.addAll(Radiants);
        objs.addAll(Receivers);

        ExecutorService service = Executors.newCachedThreadPool();

        for (int i = 0; i < threads; i++) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        Ray ray = queueForRead.poll();
                        if (ray != null) {
                            if (ray.getDphi() == -1)
                                return;
                            traceRay(ray.radiant, ray.phi, ray.tetaInd, ray.maxDistance, ray.objs, ray.dphi);
                        }
                    }
                }
            });
        }

        //Трассировка лучей
        ResultDataTable[] resultData = new ResultDataTable[Radiants.size()];
        for (int i=0; i<Radiants.size(); i++) {
            for (double phi=0.0; phi<Math.PI/2; phi=phi+Math.PI/dphi) {
                for (int tetaInd=0; tetaInd<Radiants.get(i).getDirectivityData().getSize(); tetaInd++) {
                    while (true) {
                        if (queueForRead.size() < threads) {
                            queueForRead.add(new Ray(Radiants.get(i), phi, tetaInd, maxDistance, objs, dphi));
                            break;
                        }
                    }
                }
            }
            resultData[i] = new ResultDataTable();
            for (int j=0; j<Receivers.size(); j++) {
                resultData[i].add(new ResultData(Receivers.get(j), Receivers.get(j).getIp(Radiants.get(i).getFe(), Radiants.get(i).getSpectrumData(), eyeSensitivity)));
                Receivers.get(j).setEnergy(0.0);
            }
            resultData[i].setList();
        }
        System.out.println(0);
        for (int i = 0; i < threads; i++) {
            queueForRead.add(new Ray(Radiants.get(0), -1, -1, maxDistance, objs, -1));
        }
        System.out.println(1);

        service.shutdown();

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
        ModelScene modelScene = new ModelScene(ModelGUI, 1280, (720 - 50),new ArrayList<>(Radiants.stream().collect(Collectors.toList())),new ArrayList<>(Receivers.stream().collect(Collectors.toList())),resultData);
        ModelGUI.setScene(modelScene.getScene());

        ModelGUI.show();
        ModelGUI.setMaximized(true);
    }

    private void traceRay(RadiantModel radiant, double phi, int tetaInd, double maxDistance, ArrayList<GuiModel> objs, double dphi) {
        double currentX = radiant.getX();
        double currentY = radiant.getY();
        double currentZ = radiant.getZ();
        DiagramPoint tetaP = radiant.getDirectivityData().getPoint(tetaInd);
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
        if (roundcord(x,0.0000000001) != obj.getX()) {
            return false;
        }
        if (roundcord(y,0.0000000001) != obj.getY()) {
            return false;
        }
        if (roundcord(z,0.0000000001) != obj.getZ()) {
            return false;
        }
        return true;
    }

    private double roundcord(double x, double dx) {
        if (Math.abs(Math.ceil(x))-Math.abs(x)<=dx) {
            return Math.ceil(x);
        }
        else {
            return Math.floor(x);
        }
    }

    private class Ray {
        RadiantModel radiant;
        double phi;
        int tetaInd;
        double maxDistance;
        ArrayList<GuiModel> objs;
        double dphi;

        public Ray(RadiantModel radiant, double phi, int tetaInd, double maxDistance, ArrayList<GuiModel> objs, double dphi) {
            this.radiant = radiant;
            this.phi = phi;
            this.tetaInd = tetaInd;
            this.maxDistance = maxDistance;
            this.objs = objs;
            this.dphi = dphi;
        }

        public RadiantModel getRadiant() {
            return radiant;
        }

        public double getPhi() {
            return phi;
        }

        public int getTetaInd() {
            return tetaInd;
        }

        public double getMaxDistance() {
            return maxDistance;
        }

        public ArrayList<GuiModel> getObjs() {
            return objs;
        }

        public double getDphi() {
            return dphi;
        }
    }
}