package model;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class ModelScene {
    private AnchorPane rootNodeChild = new AnchorPane();
    private Scene scene;
    private Scene sceneXZ;
    private Scene sceneYZ;
    private ArrayList<GuiModel> objects = new ArrayList<>();
    //Базовые сдвиги
    int xStart = 5;
    int yStart = 45;
    ModelScene(Stage stage, int width, int height, ArrayList<GuiModel> objectsIn, String bazis) {
        this.scene = new Scene(rootNodeChild, width, height);
        objects.addAll(0,objectsIn);
        Button toXZ = addButton("Ось XZ",10,10);
        toXZ.setOnAction((ae) -> {
            stage.setTitle("Модель: Ось XZ");
            stage.setScene(sceneXZ);
            System.out.println(sceneXZ);
        });
        Button toYZ = addButton("Ось YZ",110,10);
        toYZ.setOnAction((ae) -> {
            stage.setTitle("Модель: Ось YZ");
            stage.setScene(sceneYZ);
            System.out.println(sceneYZ);
        });
        if (bazis.equals("XZ")) {
            //Установка базы
            addImage("img\\base.jpg",0,0,width-10,height-50);
            Collections.sort(objects, GuiModel.COMPARE_BY_Y);
            for (int i=0; i<objects.size(); i++) {
                addImage(objects.get(i),"XZ");
            }
        }
        if (bazis.equals("YZ")) {
            //Установка базы
            addImage("img\\base.jpg",0,0,width-10,height-50);
            Collections.sort(objects, GuiModel.COMPARE_BY_X);
            for (int i=0; i<objects.size(); i++) {
                addImage(objects.get(i),"YZ");
            }
        }
    }
    public void setSceneXZ(Scene sceneXZ){
        this.sceneXZ = sceneXZ;
    }
    public void setSceneYZ(Scene sceneYZ){
        this.sceneYZ = sceneYZ;
    }
    public Scene getScene() {
        return scene;
    }

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
        rootNodeChild.setLeftAnchor(textField, x);
        rootNodeChild.setTopAnchor(textField, y);
        rootNodeChild.getChildren().add(textField);
        return textField;
    }
}
