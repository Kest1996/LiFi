package model;

import gui.ReceiverCoefData;
import javafx.stage.Stage;
import radiation.Radiant;

import javax.sound.midi.Receiver;
import java.util.ArrayList;
import java.util.HashMap;

public class Model{
    private ArrayList<RadiantModel> Radiants = new ArrayList<>();
    private ArrayList<ReceiverModel> Receivers = new ArrayList<>();

    public Model(String name, String fileName) {
        Receivers = ModelLoader.getReceivers(fileName);
        Radiants = ModelLoader.getRadiants(fileName);
        run();
    }
    public void run() {

        //Добавление всех объектов в массив для проверки пересечений
        ArrayList<GuiModel> objs = new ArrayList<>();
        objs.addAll(Radiants);

        //getE
        ArrayList<HashMap<String, ReceiverCoefData>> coefMap = new ArrayList<>();
        for (int i = 0; i<Receivers.size();i++) {
            coefMap.add(Receivers.get(i).getE(objs));
        }

        System.out.println("\n\n\n"+coefMap);
        //Перевод источников и приемников в массив для отображения
        ArrayList<GuiModel> objects = new ArrayList<>();
        for (int i = 0; i<Radiants.size();i++) {
            objects.add(Radiants.get(i));
        }
        for (int i = 0; i<Receivers.size();i++) {
            objects.add(Receivers.get(i));
        }

        //Создание базы окна
        Stage ModelGUI = new Stage();
        ModelGUI.setTitle("Модель: Ось XZ");
        ModelGUI.setX(0);
        ModelGUI.setY(0);
        //Установка расположения
        ModelScene sceneXZ = new ModelScene(ModelGUI, 1280, (720 - 50), objects, "XZ");
        ModelScene sceneYZ = new ModelScene(ModelGUI, 1280, (720 - 50), objects, "YZ");
        ModelScene sceneK = new ModelScene(ModelGUI, 1280, (720 - 50),Receivers, coefMap);
        //Установка сцен переключения
        sceneXZ.setScenes(sceneXZ.getScene(), sceneYZ.getScene(), sceneK.getScene());
        sceneYZ.setScenes(sceneXZ.getScene(), sceneYZ.getScene(), sceneK.getScene());
        sceneK.setScenes(sceneXZ.getScene(), sceneYZ.getScene(), sceneK.getScene());
        //Вывод коэффициентов
        ModelGUI.setScene(sceneXZ.getScene());

        ModelGUI.show();
    }
}