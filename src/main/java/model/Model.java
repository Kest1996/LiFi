package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gui.MainGUI;
import gui.RadiantGUI;
import gui.ReceiverGUI;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import radiation.Radiant;
import radiation.Receiver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Model{
    public static HashMap<String, RadiantModel> Radiants = new HashMap<>();
    public static HashMap<String, ReceiverModel> Receivers = new HashMap<>();

    //Задать предельные размеры помещения (надо же как-то ограничить лучи, чтобы дальше они не отслеживались)
    public int minX = 0;
    public int maxX = 1200;
    public int minY = 0;
    public int maxY = 1200;
    public int minZ = 0;
    public int maxZ = 600;


    public Model(String name, ArrayList<RadiantGUI> radiantsIn, ArrayList<ReceiverGUI> receiversIn) {
        //Конвертирование в Hashmap
        Radiants = new HashMap<>();
        Receivers = new HashMap<>();
        for (int i=0; i<radiantsIn.size();i++) {
            Radiants.put(radiantsIn.get(i).getCoords(), createRadiantModel(radiantsIn.get(i)));
        }
        for (int i=0; i<receiversIn.size();i++) {
            Receivers.put(receiversIn.get(i).getCoords(), createReceiverModel(receiversIn.get(i)));
        }
        run();

    }
    public void run() {
/*
        System.out.println("Источники:");
        Set<Map.Entry<String, RadiantModel>> setRad = Radiants.entrySet();
        for (Map.Entry<String, RadiantModel> me : setRad) {
            System.out.print(me.getKey() + ": ");
            System.out.println(me.getValue());
        }

        System.out.println("Приемники:");
        Set<Map.Entry<String, ReceiverModel>> setRec = Receivers.entrySet();
        for (Map.Entry<String, ReceiverModel> me : setRec) {
            System.out.print(me.getKey() + ": ");
            System.out.println(me.getValue());
        }
*/
        //double startT = System.nanoTime();
        Set<Map.Entry<String, ReceiverModel>> set = Receivers.entrySet();
        for (Map.Entry<String, ReceiverModel> me : set) {
            me.getValue().getE(minX, maxX, minY, maxY, minZ, maxZ);
            System.out.println("ВСЁ");
        }
        //double finishT = System.nanoTime();
        //System.out.println("Время выполнения: "+(finishT-startT)+" нс");
        //System.out.println("Время выполнения: "+String.format("%,12d",(long) (finishT-startT))+" нс");

        //Перевод источников и приемников в массив для отображения
        ArrayList<GuiModel> objects = new ArrayList<>();
        for (Map.Entry<String, RadiantModel> me : Radiants.entrySet()) {
            objects.add(me.getValue());
        }
        for (Map.Entry<String, ReceiverModel> me : Receivers.entrySet()) {
            objects.add(me.getValue());
        }

        //Создание базы окна
        Stage ModelGUI = new Stage();
        ModelGUI.setTitle("Модель: Ось XZ");
        ModelGUI.setX(0);
        ModelGUI.setY(0);
        //Установка расположения
        ModelScene sceneXZ = new ModelScene(ModelGUI, maxX+10, maxZ+50, objects, "XZ");
        ModelScene sceneYZ = new ModelScene(ModelGUI, maxY+10, maxZ+50, objects, "YZ");
        sceneXZ.setSceneXZ(sceneXZ.getScene());
        sceneXZ.setSceneYZ(sceneYZ.getScene());
        sceneYZ.setSceneXZ(sceneXZ.getScene());
        sceneYZ.setSceneYZ(sceneYZ.getScene());

        ModelGUI.setScene(sceneXZ.getScene());

        ModelGUI.show();
    }
    public RadiantModel createRadiantModel(RadiantGUI radiantGUI) {
        Gson gsonObject = new GsonBuilder().create();
        String jsonData = gsonObject.toJson(radiantGUI, RadiantGUI.class);
        return gsonObject.fromJson(jsonData, RadiantModel.class);
    }
    public ReceiverModel createReceiverModel(ReceiverGUI receiverGUI) {
        Gson gsonObject = new GsonBuilder().create();
        String jsonData = gsonObject.toJson(receiverGUI, ReceiverGUI.class);
        return gsonObject.fromJson(jsonData, ReceiverModel.class);
    }
}