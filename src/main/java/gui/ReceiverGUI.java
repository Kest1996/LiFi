package gui;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.stage.Modality;
import loader.GLoader;
import radiation.Receiver;
import java.util.ArrayList;
import javafx.event.*;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class ReceiverGUI {
    private transient int id;
    private transient ComboBox<Receiver> receiversList;
    private transient Button deleteButton;
    private transient Button editButton;
    private transient TextField xTF;
    private transient TextField yTF;
    private transient TextField zTF;
    private transient Label xL;
    private transient Label yL;
    private transient Label zL;
    private String name;
    private double sensetivity;
    private double responseRate;
    private int x;
    private int y;
    private int z;
    ReceiverGUI(ObservableList<Receiver> receiversObservableList, int id) {
        this.id = id;
        //Выпадающий список
        this.receiversList = new ComboBox<>(receiversObservableList);
      /*
       this.receiversList.valueProperty().addListener((ae) -> {

       });
       */
        //Кнопка для удаления
        deleteButton = new Button("Удалить");
        deleteButton.setFont(MainGUI.defaultFont);
        deleteButton.setOnAction(this::DeleteButtonReact);
        //Кнопка для редактирования
        editButton = new Button("Редактировать");
        editButton.setFont(MainGUI.defaultFont);
        editButton.setOnAction(this::EditButtonReact);
        //X
        xL = new Label("X:");
        xL.setFont(MainGUI.defaultFont);
        xTF = new TextField("0");
        xTF.setFont(MainGUI.defaultFont);
        //Чтобы только целые числа
        xTF.textProperty().addListener((ae,oldValue,newValue)-> { {
            if ((!newValue.matches("\\d{1,1000}") || newValue.startsWith("00")) & !newValue.equals("")) {
                xTF.setText(oldValue);
            }
        }
        });
        //Y
        yL = new Label("Y:");
        yL.setFont(MainGUI.defaultFont);
        yTF = new TextField("0");
        yTF.setFont(MainGUI.defaultFont);
        //Чтобы только целые числа
        yTF.textProperty().addListener((ae,oldValue,newValue)-> { {
            if ((!newValue.matches("\\d{1,1000}") || newValue.startsWith("00")) & !newValue.equals("")){
                yTF.setText(oldValue);
            }
        }
        });
        //Z
        zL = new Label("Z:");
        zL.setFont(MainGUI.defaultFont);
        zTF = new TextField("0");
        zTF.setFont(MainGUI.defaultFont);
        //Чтобы только целые числа
        zTF.textProperty().addListener((ae,oldValue,newValue)-> { {
            if ((!newValue.matches("\\d{1,1000}") || newValue.startsWith("00")) & !newValue.equals("")){
                zTF.setText(oldValue);
            }
        }
        });
    }
    ReceiverGUI(ObservableList<Receiver> receiversObservableList, int id, Receiver value) {
        this(receiversObservableList, id);
        this.receiversList.setValue(value);
    }
    ReceiverGUI(ObservableList<Receiver> receiversObservableList, int id, ReceiverGUILoad value) {
        this(receiversObservableList, id, (Receiver) value);
        //Загрузка координат
        xTF.setText(""+value.getX());
        yTF.setText(""+value.getY());
        zTF.setText(""+value.getZ());
    }
    public ComboBox<Receiver> getList() {
        return receiversList;
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
    public Receiver getObject() {
        return receiversList.getValue();
    }
    //Возврат всех элементов для удаления
    public ArrayList<Object> getDeleteItems() {
        ArrayList<Object> objects = new ArrayList<>();
        objects.add(this.xTF);
        objects.add(this.yTF);
        objects.add(this.zTF);
        objects.add(this.xL);
        objects.add(this.yL);
        objects.add(this.zL);
        objects.add(receiversList);
        objects.add(deleteButton);
        objects.add(editButton);
        return objects;
    }
    //Управление Id
    public int getId() {
        return id;
    }
    public void setId(int newId) {
        this.id = newId;
    }
    //Реакция на кнопку удаления
    private void DeleteButtonReact(ActionEvent ae) {
        MainGUI.LFWindow.removeReceiverGUI(this, id);
    }
    //Реакция на кнопку редактирования
    private void EditButtonReact(ActionEvent ae) {
        EditReceiverGUI EditWindow = new EditReceiverGUI(this, id);
    }
    //Функции возврата значений
    public String getName() {
        return getObject().getName();
    }
    public double getResponseRate() {
        return getObject().getResponseRate();
    }
    public double getSensitivity() {
        return getObject().getSensitivity();
    }
    //Обработка редактирования
    public void setObject(Receiver receiver, EditReceiverGUI EditWindow) {
        //Есть ли уже такой приемник
        int check = checkAddList(receiver);
        if (check==-1) {
            receiversList.getItems().add(receiver);
        }
        else {
            receiversList.getItems().set(check, receiver);
        }
        //Проверка наличия в библиотеке

        //Окно для запроса изменения библиотеки
        //Создание базы окна
        AnchorPane rootNodeChild2 = new AnchorPane();
        Scene scene = new Scene(rootNodeChild2, 200, 100);
        Stage stage = new Stage();
        stage.setTitle("Библиотека");
        stage.setScene(scene);
        //Указание приоритета
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        //Установка расположения
        stage.setX(200);
        stage.setY(200);
        //Расположение элементов
        //Надпись
        Label label;
        boolean check2 = checkAddLibrary(receiver);
        if (check2) {
            label = new Label("Заменить "+receiver+" в библиотеке?");
        }
        else {
            label = new Label("Сохранить "+receiver+" в библиотеке?");
        }
        label.setFont(MainGUI.defaultFont);
        rootNodeChild2.getChildren().add(label);
        rootNodeChild2.setLeftAnchor(label, 10.0);
        rootNodeChild2.setTopAnchor(label, 10.0);
        //Кнопка ДА
        Button yesButton = new Button("Да");
        yesButton.setFont(MainGUI.defaultFont);
        rootNodeChild2.getChildren().add(yesButton);
        rootNodeChild2.setLeftAnchor(yesButton, 10.0);
        rootNodeChild2.setTopAnchor(yesButton, 50.0);
        yesButton.setPrefWidth(90);
        yesButton.setOnAction((ae)->{
            if (check2) {
                GLoader.RewriteReceiver(receiver.getName(),receiver);
            }
            else {
                GLoader.SaveNewReceiver(receiver.getName(),receiver);
            }
            stage.close();
        });
        //Кнопка НЕТ
        Button noButton = new Button("Нет");
        noButton.setFont(MainGUI.defaultFont);
        rootNodeChild2.getChildren().add(noButton);
        rootNodeChild2.setLeftAnchor(noButton, 110.0);
        rootNodeChild2.setTopAnchor(noButton, 50.0);
        noButton.setPrefWidth(90);
        noButton.setOnAction((ae)->{
            stage.close();
        });

        //Установка значения и закрытие окна
        receiversList.setValue(receiver);
        EditWindow.close();
    }
    private int checkAddList(Receiver receiver){
        String name = receiver.getName();
        for (int i=0;i<receiversList.getItems().size();i++) {
            if (name.equals(receiversList.getItems().get(i).getName())) {
                return i;
            }
        }
        return -1;
    }
    private boolean checkAddLibrary(Receiver receiver){
        ArrayList<Receiver> receivers = GLoader.loadReceiverLibrary();
        String name = receiver.getName();
        for (int i=0;i<receivers.size();i++) {
            if (name.equals(receivers.get(i).getName())) {
                return true;
            }
        }
        return false;
    }
    //Обновление объекта для сохранения
    public void updateSaveObject() {
        x = Integer.parseInt(xTF.getText());
        y = Integer.parseInt(yTF.getText());
        z = Integer.parseInt(zTF.getText());
        sensetivity = getSensitivity();
        responseRate = getResponseRate();
        name = getName();
    }
    //Преобразование координат для Hashmap
    public String getCoords() {
        return (x+"_"+y+"_"+z);
    }
}
