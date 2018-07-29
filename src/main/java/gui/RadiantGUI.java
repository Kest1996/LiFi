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
      /*
       this.radiantsList.valueProperty().addListener((ae) -> {

       });
       */
       //Кнопка для удаления
       deleteButton = new Button("Удалить");
       deleteButton.setOnAction(this::DeleteButtonReact);
       //Кнопка для редактирования
       editButton = new Button("Редактировать");
       editButton.setOnAction((ae) -> {
           System.out.println("Редактировать");
       });
       //X
       xL = new Label("X:");
       xTF = new TextField("0");
       //Чтобы только целые числа
       xTF.textProperty().addListener((ae,oldValue,newValue)-> { {
           if (!newValue.matches("^[1-9][0-9]*") & newValue!="0" ){
               xTF.setText(oldValue);
           }
       }
       });
       //Y
       yL = new Label("Y:");
       yTF = new TextField("0");
       //Чтобы только целые числа
       yTF.textProperty().addListener((ae,oldValue,newValue)-> { {
           if (!newValue.matches("^[1-9][0-9]*") & newValue!="0"){
               yTF.setText(oldValue);
           }
       }
       });
       //Z
       zL = new Label("Z:");
       zTF = new TextField("0");
       //Чтобы только целые числа
       zTF.textProperty().addListener((ae,oldValue,newValue)-> { {
           if (!newValue.matches("^[1-9][0-9]*") & newValue!="0"){
               zTF.setText(oldValue);
           }
       }
       });
    }
    RadiantGUI(ObservableList<Radiant> radiantsObservableList, int id, Radiant value) {
        this(radiantsObservableList, id);
        this.radiantsList.setValue(value);
    }
    public ComboBox<Radiant> getList() {
        return radiantsList;
    }
    public Button getDeleteButton() {
        return deleteButton;
    }
    public Button getEditButton() {
        return editButton;
    }
    //Функции, нужные для добавления полей и их расположения на сцене
    public TextField getxTF() { return xTF; }
    public TextField getyTF() { return yTF; }
    public TextField getzTF() { return zTF; }
    public Label getxL() { return xL; }
    public Label getyL() { return yL; }
    public Label getzL() { return zL; }
    //Возврат установленных координат
    public int getX() { return Integer.parseInt(xTF.getText()); }
    public int getY() { return Integer.parseInt(yTF.getText()); }
    public int getZ() { return Integer.parseInt(zTF.getText()); }
    //Возврат объекта
    public Radiant getObject() {
        return radiantsList.getValue();
    }
    //Возврат всех элементов для удаления
    public ArrayList<Object> getDeleteItems() {
       ArrayList<Object> objects = new ArrayList<>();
       objects.add(radiantsList);
       objects.add(deleteButton);
       objects.add(editButton);
       objects.add(xTF);
       objects.add(yTF);
       objects.add(zTF);
       objects.add(xL);
       objects.add(yL);
       objects.add(zL);
       return objects;
    }
    //Управление Id
    public int getId() {
        return id;
    }
    public void setId(int newId) {
        this.id = newId;
    }
    //Реакция на кнопку
    private void DeleteButtonReact(ActionEvent ae) {
        MainGUI.LFWindow.removeRadiantGUI(this, id);
    }
}
