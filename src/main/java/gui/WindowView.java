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
    private ScrollPane scrollPane;

    /**
     *
     * @param sizeX
     * @param sizeY
     */

    WindowView(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.rootNode = new AnchorPane();
        rootNode.setMinSize(sizeX-20,sizeY);
        // ScrollPane
        this.scrollPane = new ScrollPane();
        scrollPane.setContent(rootNode);
        scrollPane.setPannable(true);
        this.scene = new Scene(scrollPane, sizeX, sizeY);
    }

    public Scene getScene() {
        return scene;
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    public AnchorPane getRootNode() {
        return rootNode;
    }

    /**
     *
     * @param text
     * @param x
     * @param y
     * @param fontSize
     * @return
     */

    public Label addLabel(String text, double x, double y, int fontSize){
        Label label = new Label(text);
        rootNode.getChildren().add(label);
        rootNode.setLeftAnchor(label, x);
        rootNode.setTopAnchor(label, y);
        label.setFont(new Font("Times New Roman",fontSize));
        return label;
    }

    /**
     *
     * @param defaultText
     * @param x
     * @param y
     * @param readonly
     * @return
     */

    public TextField addTextField(Object defaultText, double x, double y, boolean readonly){
        TextField textField = addTextField(defaultText, x, y);
        if (readonly) { textField.setDisable(readonly); }
        return textField;
    }

    /**
     *
     * @param defaultText
     * @param x
     * @param y
     * @return
     */

    public TextField addTextField(Object defaultText, double x, double y){
        TextField textField = new TextField(defaultText.toString());
        rootNode.getChildren().add(textField);
        rootNode.setLeftAnchor(textField, x);
        rootNode.setTopAnchor(textField, y);
        return textField;
    }

    /**
     *
     * @param defaultText
     * @param x
     * @param y
     * @return
     */

    public Button addButton(Object defaultText, double x, double y) {
        Button button = new Button(defaultText.toString());
        rootNode.getChildren().add(button);
        rootNode.setLeftAnchor(button, x);
        rootNode.setTopAnchor(button, y);
        return button;
    }

    /**
     *
     * @param button
     * @param newX
     * @param newY
     */

    public void moveButton(Button button, double newX, double newY) {
        rootNode.setLeftAnchor(button, newX);
        rootNode.setTopAnchor(button, newY);
    }

    /**
     * RadiantGUI
     * @param radiantsObservableList
     * @param x
     * @param y
     * @param id
     * @return
     */

    public RadiantGUI addRadiantGUI(ObservableList<Radiant> radiantsObservableList, double x, double y, int id) {
        RadiantGUI radiantGUI = addRadiantGUI(radiantsObservableList,x,y,id,radiantsObservableList.get(0));
        return radiantGUI;
    }

    /**
     *
     * @param radiantsObservableList
     * @param x
     * @param y
     * @param id
     * @param value
     * @return
     */

    public RadiantGUI addRadiantGUI(ObservableList<Radiant> radiantsObservableList, double x, double y, int id, Radiant value) {

        //Отступ между списком и кнопкой
        int marginX = 170;
        int marginX2 = 100;
        int marginY = 30;
        Label label;
        TextField textField;

        //Создание объекта
        RadiantGUI radiantGUI;
        if (value instanceof RadiantGUILoad) {
            radiantGUI = new RadiantGUI(radiantsObservableList, id, (RadiantGUILoad) value);
        }
        else {
            radiantGUI = new RadiantGUI(radiantsObservableList, id, value);
        }

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

    /**
     *
     * @param radiantGUI
     * @param id
     */

    public void removeRadiantGUI(RadiantGUI radiantGUI, int id) {
        ArrayList<Object> objects = radiantGUI.getDeleteItems();
        for (int i=(objects.size()-1); i>=0; i--) {
            rootNode.getChildren().remove(objects.get(i));
        }
        MainGUI.RadiantGUIList.remove(radiantGUI);
        MainGUI.moveRadiantsGUI(id);
    }

    /**
     *
     * @param radiantGUI
     * @param newX
     * @param newY
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

    /**
     * ReceiverGUI
     * @param receiversObservableList
     * @param x
     * @param y
     * @param id
     * @return
     */

    public ReceiverGUI addReceiverGUI(ObservableList<Receiver> receiversObservableList, double x, double y, int id) {
        ReceiverGUI receiverGUI = addReceiverGUI(receiversObservableList,x,y,id,receiversObservableList.get(0));
        return receiverGUI;
    }

    /**
     *
     * @param receiversObservableList
     * @param x
     * @param y
     * @param id
     * @param value
     * @return
     */

    public ReceiverGUI addReceiverGUI(ObservableList<Receiver> receiversObservableList, double x, double y, int id, Receiver value) {

        //Отступ между списком и кнопкой
        int marginX = 170;
        int marginX2 = 100;
        int marginY = 30;
        Label label;
        TextField textField;

        //Создание объекта
        ReceiverGUI receiverGUI;
        if (value instanceof ReceiverGUILoad) {
            receiverGUI = new ReceiverGUI(receiversObservableList, id, (ReceiverGUILoad) value);
        }
        else {
            receiverGUI = new ReceiverGUI(receiversObservableList, id, value);
        }

        //Создание и расположение списка
        ComboBox<Receiver> receiverList = receiverGUI.getList();
        rootNode.getChildren().add(receiverList);
        rootNode.setLeftAnchor(receiverList, x);
        rootNode.setTopAnchor(receiverList, y);
        receiverList.setPrefWidth(150);

        //Создание и расположение кнопки для редактирования
        Button receiverEditButton = receiverGUI.getEditButton();
        rootNode.getChildren().add(receiverEditButton);
        rootNode.setLeftAnchor(receiverEditButton, x+marginX);
        rootNode.setTopAnchor(receiverEditButton, y);

        //Создание и расположение кнопки для удаления
        Button receiverDeleteButton = receiverGUI.getDeleteButton();
        rootNode.getChildren().add(receiverDeleteButton);
        rootNode.setLeftAnchor(receiverDeleteButton, x+marginX+100);
        rootNode.setTopAnchor(receiverDeleteButton, y);

        //Создание полей и подписей для указания координат
        //X
        label =  receiverGUI.getxL();
        rootNode.getChildren().add(label);
        rootNode.setLeftAnchor(label, x);
        rootNode.setTopAnchor(label, y+marginY);
        textField = receiverGUI.getxTF();
        rootNode.getChildren().add(textField);
        rootNode.setLeftAnchor(textField, x+15);
        rootNode.setTopAnchor(textField, y+marginY);
        textField.setPrefColumnCount(7);

        //Y
        label =  receiverGUI.getyL();
        rootNode.getChildren().add(label);
        rootNode.setLeftAnchor(label, x+15+marginX2);
        rootNode.setTopAnchor(label, y+marginY);
        textField = receiverGUI.getyTF();
        rootNode.getChildren().add(textField);
        rootNode.setLeftAnchor(textField, x+15+marginX2+15);
        rootNode.setTopAnchor(textField, y+marginY);
        textField.setPrefColumnCount(7);

        //Z
        label =  receiverGUI.getzL();
        rootNode.getChildren().add(label);
        rootNode.setLeftAnchor(label, x+15+marginX2+15+marginX2);
        rootNode.setTopAnchor(label, y+marginY);
        textField = receiverGUI.getzTF();
        rootNode.getChildren().add(textField);
        rootNode.setLeftAnchor(textField, x+15+marginX2+15+marginX2+15);
        rootNode.setTopAnchor(textField, y+marginY);
        textField.setPrefColumnCount(7);

        //Установка значения
        return receiverGUI;
    }

    /**
     *
     * @param receiverGUI
     * @param id
     */

    public void removeReceiverGUI(ReceiverGUI receiverGUI, int id) {
        ArrayList<Object> objects = receiverGUI.getDeleteItems();
        for (int i=(objects.size()-1); i>=0; i--) {
            rootNode.getChildren().remove(objects.get(i));
        }
        MainGUI.ReceiverGUIList.remove(receiverGUI);
        MainGUI.moveReceiversGUI(id);
    }

    /**
     *
     * @param receiverGUI
     * @param newX
     * @param newY
     */

    public void moveReceiverGUI(ReceiverGUI receiverGUI, double newX, double newY) {
        int marginX = 170;
        int marginX2 = 100;
        int marginY = 30;
        Label label;
        TextField textField;

        //Расположение списка
        ComboBox<Receiver> receiverList = receiverGUI.getList();
        rootNode.setLeftAnchor(receiverList, newX);
        rootNode.setTopAnchor(receiverList, newY);

        //Расположение кнопки для редактирования
        Button receiverEditButton = receiverGUI.getEditButton();
        rootNode.setLeftAnchor(receiverEditButton, newX+marginX);
        rootNode.setTopAnchor(receiverEditButton, newY);

        //Расположениекнопки для удаления
        Button receiverDeleteButton = receiverGUI.getDeleteButton();
        rootNode.setLeftAnchor(receiverDeleteButton, newX+marginX+100);
        rootNode.setTopAnchor(receiverDeleteButton, newY);

        //Расположение полей и подписей для указания координат
        //X
        label =  receiverGUI.getxL();
        rootNode.setLeftAnchor(label, newX);
        rootNode.setTopAnchor(label, newY+marginY);
        textField = receiverGUI.getxTF();
        rootNode.setLeftAnchor(textField, newX+15);
        rootNode.setTopAnchor(textField, newY+marginY);
        textField.setPrefColumnCount(7);

        //Y
        label =  receiverGUI.getyL();
        rootNode.setLeftAnchor(label, newX+15+marginX2);
        rootNode.setTopAnchor(label, newY+marginY);
        textField = receiverGUI.getyTF();
        rootNode.setLeftAnchor(textField, newX+15+marginX2+15);
        rootNode.setTopAnchor(textField, newY+marginY);
        textField.setPrefColumnCount(7);

        //Z
        label =  receiverGUI.getzL();
        rootNode.setLeftAnchor(label, newX+15+marginX2+15+marginX2);
        rootNode.setTopAnchor(label, newY+marginY);
        textField = receiverGUI.getzTF();
        rootNode.setLeftAnchor(textField, newX+15+marginX2+15+marginX2+15);
        rootNode.setTopAnchor(textField, newY+marginY);
    }
}
