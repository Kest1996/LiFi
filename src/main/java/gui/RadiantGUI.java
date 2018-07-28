package gui;






import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import radiation.Radiant;

import java.lang.reflect.Field;
import java.util.ArrayList;
import javafx.event.*;

import javafx.scene.control.TextField;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;






public class RadiantGUI {
    private Radiant radiant;
    private transient int id;
    private transient ObservableList<Radiant> radiantsObservableList;
    private transient ComboBox<Radiant> radiantsList;
    private transient Button deleteButton;
    private transient Button editButton;
    private transient TextField xTF;
    private transient TextField yTF;
    private transient TextField zTF;
    private transient Label xL;
    private transient Label yL;
    private transient Label zL;
   RadiantGUI(ObservableList<Radiant> radiantsObservableList, int id) {
       this.id = id;
       //Выпадающий список
       this.radiantsList = new ComboBox<>(radiantsObservableList);
       this.radiantsList.valueProperty().addListener((ae) -> {
           setObject(radiantsList.getValue());
       });
       //Кнопка для удаления
       deleteButton = new Button("Удалить");
       deleteButton.setOnAction((ae) -> {
           System.out.println("Удаление");
       });
       //Кнопка для редактирования
       editButton = new Button("Редактировать");
       editButton.setOnAction((ae) -> {
           System.out.println("Редактировать");
       });
       //X
       xL = new Label("X:");
       xTF = new TextField("0");
       xTF.textProperty().addListener((ae,oldValue,newValue)-> { {
           if (!newValue.matches("\\d*") ){
               xTF.setText(oldValue);
           }
       }
       });
       //Y
       yL = new Label("Y:");
       yTF = new TextField("0");
       yTF.textProperty().addListener((ae,oldValue,newValue)-> { {
           if (!newValue.matches("\\d*") ){
               yTF.setText(oldValue);
           }
       }
       });
       //Z
       zL = new Label("Z:");
       zTF = new TextField("0");
       zTF.textProperty().addListener((ae,oldValue,newValue)-> { {
           if (!newValue.matches("\\d*") ){
               zTF.setText(oldValue);
           }
       }
       });
   }
    RadiantGUI(ObservableList<Radiant> radiantsObservableList, int id, Radiant value) {
        this(radiantsObservableList, id);
        this.radiant = value;
        this.radiantsList.setValue(value);
    }
    public ComboBox<Radiant> getList() {
        return radiantsList;
    }
    public Button getDeleteButtonButton() {
        return deleteButton;
    }
    public Button getEditButton() {
        return editButton;
    }
    public void setObject(Radiant radiant){
        Gson gson = new GsonBuilder().create();
        String jsonData = gson.toJson(radiant,Radiant.class);
        radiant = gson.fromJson(jsonData, Radiant.class);
    }
    public TextField getxTF() { return xTF; }
    public TextField getyTF() { return yTF; }
    public TextField getzTF() { return zTF; }
    public Label getxL() { return xL; }
    public Label getyL() { return yL; }
    public Label getzL() { return zL; }
    public Radiant getObject() {
        return radiant;
    }
}
