package model;

import gui.ReceiverCoefData;
import javafx.collections.FXCollections;
import javafx.stage.Stage;
import radiation.Diagram;
import radiation.Radiant;
import radiation.Receiver;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Model{
    private ArrayList<RadiantModel> Radiants = new ArrayList<>();
    private ArrayList<ReceiverModel> Receivers = new ArrayList<>();
    public transient ArrayList<Diagram> radiantDiagrams = new ArrayList<>();
    public transient ArrayList<Diagram> receiverDiagrams = new ArrayList<>();
    private transient Diagram eyeSensitivity;

    public Model(String name, String fileName) {
        Receivers = ModelLoader.getReceivers(fileName);
        for (int i=0;i<Receivers.size();i++) {
            Receivers.get(i).setSensitivity();
        }
        Radiants = ModelLoader.getRadiants(fileName);
        for (int i=0;i<Radiants.size();i++) {
            Radiants.get(i).setSpectrum();
        }
        //Чтение библиотеки спектров источников
        File directory = new File("src/main/resources/Library/RadiantDiagram");
        File[] arrayFiles = directory.listFiles();
        for (int i=0;i<=arrayFiles.length-1;i++){
            radiantDiagrams.add(new Diagram(arrayFiles[i].getName().substring(0,arrayFiles[i].getName().length()-4),arrayFiles[i]));
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
    public void run() {
        ResultDataTable[][] resultData = new ResultDataTable[Receivers.size()][Radiants.size()];
        Diagram[][] IpL = new Diagram[Receivers.size()][Radiants.size()];
        double Ip;
        for (int i=0; i<Receivers.size();i++){
            for (int j=0; j<Radiants.size();j++) {
                resultData[i][j] = new ResultDataTable(Receivers.get(i).getIp(Radiants.get(j).getFe(), Radiants.get(j).getSpectrumData(), eyeSensitivity));
            }
        }

        /*
        //Добавление всех объектов в массив для проверки пересечений
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
}