package gui;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import loader.GLoader;
import radiation.Radiant;
import java.util.ArrayList;
import javafx.event.*;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class RadiantGUI {
    private transient int id;
    private transient ComboBox<Radiant> radiantsList;
    private transient Button deleteButton;
    private transient Button editButton;
    private transient TextField xTF;
    private transient TextField yTF;
    private transient TextField zTF;
    private transient Label xL;
    private transient Label yL;
    private transient Label zL;
    private String name;
    private double Fe;
    private String spectrum;
    private int x;
    private int y;
    private int z;

    /**
     *
     * @param radiantsObservableList
     * @param id
     */

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

    /**
     *
     * @param radiantsObservableList
     * @param id
     * @param value
     */

    RadiantGUI(ObservableList<Radiant> radiantsObservableList, int id, Radiant value) {
        this(radiantsObservableList, id);
        this.radiantsList.setValue(value);
    }

    /**
     *
     * @param radiantsObservableList
     * @param id
     * @param value
     */

    RadiantGUI(ObservableList<Radiant> radiantsObservableList, int id, RadiantGUILoad value) {
        this(radiantsObservableList,id, (Radiant) value);
        //Загрузка координат
        xTF.setText(""+value.getX());
        yTF.setText(""+value.getY());
        zTF.setText(""+value.getZ());
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

    /**
     * Реакция на кнопку удаления
     * @param ae
     */

    private void DeleteButtonReact(ActionEvent ae) {
        MainGUI.LFWindow.removeRadiantGUI(this, id);
    }

    /**
     * Реакция на кнопку редактирования
     * @param ae
     */

    private void EditButtonReact(ActionEvent ae) {
        EditRadiantGUI EditWindow = new EditRadiantGUI(this, id);
    }

    //Функции возврата значений
    public String getName() {
        return getObject().getName();
    }
    public double getFe() {
        return getObject().getFe();
    }
    public String getSpectrum() {
        return getObject().getSpectrum();
    }

    /**
     * Редактирование + изменение библиотеки
     * @param radiant
     * @param EditWindow
     */

    public void setObject(Radiant radiant, EditRadiantGUI EditWindow) {
        //Есть ли уже такой источник
        int check = checkAddList(radiant);
        if (check==-1) {
            radiantsList.getItems().add(radiant);
        }
        else {
            radiantsList.getItems().set(check, radiant);
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
        boolean check2 = checkAddLibrary(radiant);
        if (check2) {
            label = new Label("Заменить "+radiant+" в библиотеке?");
        }
        else {
            label = new Label("Сохранить "+radiant+" в библиотеке?");
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
                GLoader.RewriteRadiant(radiant.getName(),radiant);
            }
            else {
                GLoader.SaveNewRadiant(radiant.getName(),radiant);
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
        radiantsList.setValue(radiant);
        EditWindow.close();
    }

    /**
     *
     * @param radiant
     * @return
     */

    private int checkAddList(Radiant radiant){
        String name = radiant.getName();
        for (int i=0;i<radiantsList.getItems().size();i++) {
            if (name.equals(radiantsList.getItems().get(i).getName())) {
                return i;
            }
        }
        return -1;
    }

    /**
     *
     * @param radiant
     * @return
     */

    private boolean checkAddLibrary(Radiant radiant){
        ArrayList<Radiant> radiants = GLoader.loadRadiantLibrary();
        String name = radiant.getName();
        for (int i=0;i<radiants.size();i++) {
            if (name.equals(radiants.get(i).getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Обновление объекта для сохранения
     */

    public void updateSaveObject() {
        x = Integer.parseInt(xTF.getText());
        y = Integer.parseInt(yTF.getText());
        z = Integer.parseInt(zTF.getText());
        Fe = getFe();
        spectrum = getSpectrum();
        name = getName();
    }

    /**
     * Преобразование координат для Hashmap
     * @return
     */

    public String getCoords() {
        return (x+"_"+y+"_"+z);
    }
}
