package model;

import gui.MainGUI;
import gui.ReceiverCoefData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import radiation.Radiant;
import radiation.Receiver;

import java.lang.reflect.Array;
import java.util.*;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ModelScene {

    private ScrollPane scrollPane;
    private AnchorPane rootNodeChild = new AnchorPane();
    private Scene scene;
    private ArrayList<GuiModel> objects = new ArrayList<>();

    //Базовые сдвиги
    double xStart = 5;
    double yStart = 45;

    /**
     * Конструткор
     * @param stage
     * @param width
     * @param height
     */

    ModelScene(Stage stage, int width, int height) {
        rootNodeChild.setMinSize(width-20,height);
        // ScrollPane
        this.scrollPane = new ScrollPane();
        scrollPane.setContent(rootNodeChild);
        scrollPane.setPannable(true);
        this.scene = new Scene(scrollPane, width, height);
    }

    /**
     *
     * @param stage
     * @param width
     * @param height
     * @param Radiants
     * @param Receivers
     * @param resultData
     */

    ModelScene(Stage stage, int width, int height, ArrayList<RadiantModel> Radiants, ArrayList<ReceiverModel> Receivers, ResultDataTable[][] resultData) {
        this(stage, width, height);
        double marginY = 300;
        double c = 0;
        for (int i=0;i<Receivers.size();i++){
            for (int j=0;j<Radiants.size();j++){
                ModelTable modelTable = new ModelTable(resultData[i][j].getList());
                TableView<ResultData> table = modelTable.getTable();
                rootNodeChild.getChildren().add(table);
                rootNodeChild.setLeftAnchor(table, 10.0);
                rootNodeChild.setTopAnchor(table, marginY*c);
                rootNodeChild.setRightAnchor(table, width-730.0);
                modelTable.setSizes();
                addLabel("Приемник: "+Receivers.get(i).getName(),740,marginY*c);
                addLabel("Источник: "+Radiants.get(j).getName(),740,marginY*c+30);
                c = c+1;
            }
        }
    }

    public Scene getScene() {
        return scene;
    }

    /**
     * Добавление кнопки
     * @param defaultText
     * @param x
     * @param y
     * @return
     */

    public Button addButton(String defaultText, double x, double y) {
        Button button = new Button(defaultText);
        rootNodeChild.getChildren().add(button);
        rootNodeChild.setLeftAnchor(button, x);
        rootNodeChild.setTopAnchor(button, y);
        return button;
    }

    /**
     * Добавление тектсового поля
     * @param text
     * @param x
     * @param y
     * @return
     */

    private Label addLabel(String text, double x, double y){
        Label label = new Label(text);
        label.setFont(MainGUI.defaultFont);
        rootNodeChild.getChildren().add(label);
        rootNodeChild.setLeftAnchor(label, x);
        rootNodeChild.setTopAnchor(label, y);
        return label;
    }

    /**
     * Добавление поля для ввода текста
     * @param defaultText
     * @param x
     * @param y
     * @return
     */

    private TextField addTextField(Object defaultText, double x, double y){
        TextField textField = new TextField(defaultText.toString());
        textField.setDisable(true);
        rootNodeChild.setLeftAnchor(textField, x);
        rootNodeChild.setTopAnchor(textField, y);
        rootNodeChild.getChildren().add(textField);
        return textField;
    }
}
