package gui;

import javafx.application.*;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import radiation.Radiant;
import radiation.Receiver;
import java.util.ArrayList;

public class WindowView{
    //private String name;
    private int sizeX, sizeY;
    private AnchorPane rootNode;
    private Scene scene;
    WindowView(int sizeX, int sizeY) {
        //this.name = name;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.rootNode = new AnchorPane();
        this.scene = new Scene(rootNode, sizeX, sizeY);
    }
    public Scene getScene() {
        return scene;
    }
    public AnchorPane getRootNode() {
        return rootNode;
    }
    public Label addLabel(String text, double x, double y, int fontSize){
        Label label = new Label(text);
        rootNode.getChildren().add(label);
        rootNode.setLeftAnchor(label, x);
        rootNode.setTopAnchor(label, y);
        label.setFont(new Font("Times New Roman",fontSize));
        return label;
    }
    public TextField addTextField(Object defaultText, double x, double y, boolean readonly){
        TextField textField = addTextField(defaultText, x, y);
        if (readonly) { textField.setDisable(readonly); }
        return textField;
    }
    public TextField addTextField(Object defaultText, double x, double y){
        TextField textField = new TextField(defaultText.toString());
        rootNode.getChildren().add(textField);
        rootNode.setLeftAnchor(textField, x);
        rootNode.setTopAnchor(textField, y);
        return textField;
    }
    public Button addButton(Object defaultText, double x, double y) {
        Button button = new Button(defaultText.toString());
        rootNode.getChildren().add(button);
        rootNode.setLeftAnchor(button, x);
        rootNode.setTopAnchor(button, y);
        return button;
    }
    public RadiantGUI addRadiantGUI(ObservableList<Radiant> radiantsObservableList, double x, double y, int id) {
        RadiantGUI radiantGUI = addRadiantGUI(radiantsObservableList,x,y,id,radiantsObservableList.get(0));
        return radiantGUI;
    }
    public RadiantGUI addRadiantGUI(ObservableList<Radiant> radiantsObservableList, double x, double y, int id, Radiant value) {
        //Отступ между списком и кнопкой
        int marginX = 170;
        int marginX2 = 100;
        int marginY = 30;
        Label label;
        TextField textField;
        //Создание объекта
        RadiantGUI radiantGUI = new RadiantGUI(radiantsObservableList, id, value);
        //Создание и расположение списка
        ComboBox<Radiant> radiantList = radiantGUI.getList();
        rootNode.getChildren().add(radiantList);
        rootNode.setLeftAnchor(radiantList, x);
        rootNode.setTopAnchor(radiantList, y);
        radiantList.setPrefWidth(150);
        //Создание и расположение кнопки для редактирования
        Button radiantEditButton = radiantGUI.getEditButton();
        rootNode.getChildren().add(radiantEditButton);
        rootNode.setLeftAnchor(radiantEditButton, x+marginX);
        rootNode.setTopAnchor(radiantEditButton, y);
        //Создание и расположение кнопки для удаления
        Button radiantDeleteButton = radiantGUI.getDeleteButton();
        rootNode.getChildren().add(radiantDeleteButton);
        rootNode.setLeftAnchor(radiantDeleteButton, x+marginX+100);
        rootNode.setTopAnchor(radiantDeleteButton, y);
        //Создание полей и подписей для указания координат
        //X
        label =  radiantGUI.getxL();
        rootNode.getChildren().add(label);
        rootNode.setLeftAnchor(label, x);
        rootNode.setTopAnchor(label, y+marginY);
        textField = radiantGUI.getxTF();
        rootNode.getChildren().add(textField);
        rootNode.setLeftAnchor(textField, x+15);
        rootNode.setTopAnchor(textField, y+marginY);
        textField.setPrefColumnCount(7);
        //Y
        label =  radiantGUI.getyL();
        rootNode.getChildren().add(label);
        rootNode.setLeftAnchor(label, x+15+marginX2);
        rootNode.setTopAnchor(label, y+marginY);
        textField = radiantGUI.getyTF();
        rootNode.getChildren().add(textField);
        rootNode.setLeftAnchor(textField, x+15+marginX2+15);
        rootNode.setTopAnchor(textField, y+marginY);
        textField.setPrefColumnCount(7);
        //Z
        label =  radiantGUI.getzL();
        rootNode.getChildren().add(label);
        rootNode.setLeftAnchor(label, x+15+marginX2+15+marginX2);
        rootNode.setTopAnchor(label, y+marginY);
        textField = radiantGUI.getzTF();
        rootNode.getChildren().add(textField);
        rootNode.setLeftAnchor(textField, x+15+marginX2+15+marginX2+15);
        rootNode.setTopAnchor(textField, y+marginY);
        textField.setPrefColumnCount(7);
        //Установка значения
        return radiantGUI;
    }

    public void removeRadiantGUI(RadiantGUI radiantGUI, int id) {
        ArrayList<Object> objects = radiantGUI.getDeleteItems();
        for (int i=(objects.size()-1); i>=0; i--) {
            rootNode.getChildren().remove(objects.get(i));
        }
        MainGUI.RadiantGUIList.remove(radiantGUI);
        MainGUI.moveRadiantsGUI(id);
    }
    /*
    public void deleteRadiantGUI(RadiantGUI r, int id) {
        ArrayList<Object> objects = r.getdelete();
        for (int i=(objects.size()-1); i>=0; i--) {
            rootNode.getChildren().remove(objects.get(i));
        }
        MainGUI.RadiantGUIList.remove(r);
    }
    */

    public void moveRadiantGUI(RadiantGUI radiantGUI, double newX, double newY) {
        int marginX = 170;
        int marginX2 = 100;
        int marginY = 30;
        Label label;
        TextField textField;
        //Расположение списка
        ComboBox<Radiant> radiantList = radiantGUI.getList();
        rootNode.setLeftAnchor(radiantList, newX);
        rootNode.setTopAnchor(radiantList, newY);
        //Расположение кнопки для редактирования
        Button radiantEditButton = radiantGUI.getEditButton();
        rootNode.setLeftAnchor(radiantEditButton, newX+marginX);
        rootNode.setTopAnchor(radiantEditButton, newY);
        //Расположениекнопки для удаления
        Button radiantDeleteButton = radiantGUI.getDeleteButton();
        rootNode.setLeftAnchor(radiantDeleteButton, newX+marginX+100);
        rootNode.setTopAnchor(radiantDeleteButton, newY);
        //Расположение полей и подписей для указания координат
        //X
        label =  radiantGUI.getxL();
        rootNode.setLeftAnchor(label, newX);
        rootNode.setTopAnchor(label, newY+marginY);
        textField = radiantGUI.getxTF();
        rootNode.setLeftAnchor(textField, newX+15);
        rootNode.setTopAnchor(textField, newY+marginY);
        textField.setPrefColumnCount(7);
        //Y
        label =  radiantGUI.getyL();
        rootNode.setLeftAnchor(label, newX+15+marginX2);
        rootNode.setTopAnchor(label, newY+marginY);
        textField = radiantGUI.getyTF();
        rootNode.setLeftAnchor(textField, newX+15+marginX2+15);
        rootNode.setTopAnchor(textField, newY+marginY);
        textField.setPrefColumnCount(7);
        //Z
        label =  radiantGUI.getzL();
        rootNode.setLeftAnchor(label, newX+15+marginX2+15+marginX2);
        rootNode.setTopAnchor(label, newY+marginY);
        textField = radiantGUI.getzTF();
        rootNode.setLeftAnchor(textField, newX+15+marginX2+15+marginX2+15);
        rootNode.setTopAnchor(textField, newY+marginY);
    }
    public void moveButton(Button button, double newX, double newY) {
        rootNode.setLeftAnchor(button, newX);
        rootNode.setTopAnchor(button, newY);
    }
}
