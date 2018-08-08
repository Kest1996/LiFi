package model;

import gui.ReceiverCoefData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import radiation.Receiver;

import java.lang.reflect.Array;
import java.util.*;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ModelScene {
    private AnchorPane rootNodeChild = new AnchorPane();
    private Scene scene;
    private Scene sceneXZ;
    private Scene sceneYZ;
    private Scene sceneK;
    private ArrayList<GuiModel> objects = new ArrayList<>();
    //Базовые сдвиги
    double xStart = 5;
    double yStart = 45;
    ModelScene(Stage stage, int width, int height, ArrayList<GuiModel> objectsIn, String bazis) {
        this.scene = new Scene(rootNodeChild, width, height);
        Button toXZ = addButton("Ось XZ", 10, 10);
        toXZ.setOnAction((ae) -> {
            stage.setTitle("Модель: Ось XZ");
            stage.setScene(sceneXZ);
        });
        Button toYZ = addButton("Ось YZ", 110, 10);
        toYZ.setOnAction((ae) -> {
            stage.setTitle("Модель: Ось YZ");
            stage.setScene(sceneYZ);
        });
        Button toK = addButton("Коэффициенты", 210, 10);
        toK.setOnAction((ae) -> {
            stage.setTitle("Модель: Коэффициенты");
            stage.setScene(sceneK);
        });
        if (bazis.equals("XZ")) {
            //Установка базы
            addImage("resources\\img\\base.jpg", 0, 0, width - 10, height - 50);
            objects.addAll(0, objectsIn);
            Collections.sort(objects, GuiModel.COMPARE_BY_Y);
            for (int i = 0; i < objects.size(); i++) {
                addImage(objects.get(i), "XZ");
            }
        }
        else if (bazis.equals("YZ")) {
            //Установка базы
            addImage("resources\\img\\base.jpg",0,0,width-10,height-50);
            objects.addAll(0, objectsIn);
            Collections.sort(objects, GuiModel.COMPARE_BY_X);
            for (int i=0; i<objects.size(); i++) {
                addImage(objects.get(i),"YZ");
            }
        }
    }
    ModelScene(Stage stage, int width, int height, ArrayList<ReceiverModel> receiverModels, ArrayList<HashMap<String, ReceiverCoefData>> coefMap) {
        this(stage, width, height, null, "");
        double marginY = 30;
        for (int i=0; i<receiverModels.size(); i++) {
            addLabel(receiverModels.get(i).toString(),xStart,yStart+i*marginY*coefMap.get(i).size()+20*i);
            Set<Map.Entry<String, ReceiverCoefData>> set = coefMap.get(i).entrySet();
            int nnn1 = 0;
            for (Map.Entry<String, ReceiverCoefData> me : set) {
                addLabel("Radiant "+me.getKey() + ": ", xStart+200,yStart+marginY*(i*coefMap.get(i).size()+nnn1)+20*i);
                addLabel("Расстояние: "+me.getValue().getR(),xStart+400,yStart+marginY*(i*coefMap.get(i).size()+nnn1)+20*i);
                addLabel("Угол Фи: "+me.getValue().getPhi(),xStart+600,yStart+marginY*(i*coefMap.get(i).size()+nnn1)+20*i);
                addLabel("Угол Тета: "+me.getValue().getTeta(),xStart+800,yStart+marginY*(i*coefMap.get(i).size()+nnn1)+20*i);
                addLabel("Коэффициент: "+me.getValue().getK(),xStart+1000,yStart+marginY*(i*coefMap.get(i).size()+nnn1)+20*i);
                nnn1++;
            }
        }
    }

    //Установка сцен для переключения
    public void setSceneXZ(Scene sceneXZ){
        this.sceneXZ = sceneXZ;
    }
    public void setSceneYZ(Scene sceneYZ){
        this.sceneYZ = sceneYZ;
    }
    public void setSceneK(Scene sceneK){
        this.sceneK = sceneK;
    }
    public void setScenes(Scene sceneXZ, Scene sceneYZ, Scene sceneK) {
        setSceneXZ(sceneXZ);
        setSceneYZ(sceneYZ);
        setSceneK(sceneK);
    }
    public Scene getScene() {
        return scene;
    }

    //Функции вывода изображений и элементов
    public void addImage(String fileName, double x, double y, double width, double height){
        Image img;
        try {
            img = new Image("file:"+fileName);
        }
        catch (NullPointerException exc){
            System.out.println("Файл "+fileName+" не найден");
            return;
        }
        ImageView imgv = new ImageView(img);
        imgv.setFitWidth(width);
        imgv.setFitHeight(height);
        rootNodeChild.getChildren().add(imgv);
        rootNodeChild.setLeftAnchor(imgv, x+xStart);
        rootNodeChild.setTopAnchor(imgv, y+yStart);
    }
    public ImageView addImage(String fileName, double x, double y){
        Image img;
        try {
            img = new Image("file:"+fileName);
        }
        catch (NullPointerException exc){
            System.out.println("Файл "+fileName+" не найден");
            return null;
        }
        ImageView imgv = new ImageView(img);
        rootNodeChild.getChildren().add(imgv);
        rootNodeChild.setLeftAnchor(imgv, x+xStart);
        rootNodeChild.setTopAnchor(imgv, y+yStart);
        return imgv;
    }

    public void addImage(GuiModel guiModel, String bazis){
        if (bazis.equals("XZ")) {
            addImage(guiModel.getImg(),guiModel.getX(),guiModel.getZ());
        }
        if (bazis.equals("YZ")) {
            addImage(guiModel.getImg(),guiModel.getY(),guiModel.getZ());
        }
    }
    public Button addButton(String defaultText, double x, double y) {
        Button button = new Button(defaultText);
        rootNodeChild.getChildren().add(button);
        rootNodeChild.setLeftAnchor(button, x);
        rootNodeChild.setTopAnchor(button, y);
        return button;
    }
    private Label addLabel(String text, double x, double y){
        Label label = new Label(text);
        rootNodeChild.getChildren().add(label);
        rootNodeChild.setLeftAnchor(label, x);
        rootNodeChild.setTopAnchor(label, y);
        return label;
    }
    private TextField addTextField(Object defaultText, double x, double y){
        TextField textField = new TextField(defaultText.toString());
        textField.setDisable(true);
        rootNodeChild.setLeftAnchor(textField, x);
        rootNodeChild.setTopAnchor(textField, y);
        rootNodeChild.getChildren().add(textField);
        return textField;
    }

    public void setCoefficients(ArrayList<ReceiverModel> b, HashMap g) {
        //addLabel()
    }
}
