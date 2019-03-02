package gui;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import radiation.Diagram;
import radiation.Directivity;
import radiation.Radiant;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;

import java.io.File;
import java.util.ArrayList;

import static gui.MainGUI.radiantDiagramsObservableList;
import static gui.MainGUI.radiantDirectivitiesObservableList;

public class EditRadiantGUI {
    private AnchorPane rootNodeChild = new AnchorPane();
    private Stage editRadiantWindow;
    private RadiantGUI radiantGUI;
    private TextField nameTF;
    private TextField FeTF;
    private ComboBox<Diagram> spectrumList = new ComboBox<>();
    private ComboBox<Directivity> directivityList = new ComboBox<>();

    /**
     *
     * @param radiantGUI
     * @param id
     */

    EditRadiantGUI(RadiantGUI radiantGUI, int id) {

        //Создание базы окна
        this.radiantGUI = radiantGUI;
        Scene scene = new Scene(rootNodeChild, 300, 300);
        editRadiantWindow = new Stage();
        editRadiantWindow.setTitle("Редактирование источника");
        editRadiantWindow.setScene(scene);

        //Указание дочерности
        editRadiantWindow.initModality(Modality.WINDOW_MODAL);
        editRadiantWindow.initOwner(MainGUI.primaryStage);

        //Установка расположения
        editRadiantWindow.setX(200);
        editRadiantWindow.setY(200);

        //Вывод полей редактирования
        int marginX = 100;
        int marginY = 30;
        int startX = 20;
        int startY = 20;

        //name
        Label label = addLabel("Имя",startX,startY);
        nameTF = addTextField(radiantGUI.getName(),startX+marginX,startY);

        //Fe
        label = addLabel("Фe",startX,startY+marginY);
        FeTF = addTextField(radiantGUI.getFe(),startX+marginX,startY+marginY);
        FeTF.textProperty().addListener((ae, oldValue, newValue)-> {
            if (!checkDecimal(newValue)) {
                FeTF.setText(oldValue);
            }
        });

        //spectrum
        label = addLabel("Спектр",startX,startY+marginY+marginY);
        spectrumList = new ComboBox<>(radiantDiagramsObservableList);
        rootNodeChild.getChildren().add(spectrumList);
        rootNodeChild.setLeftAnchor(spectrumList, (double) startX+marginX);
        rootNodeChild.setTopAnchor(spectrumList, (double) startY+marginY+marginY);
        spectrumList.getSelectionModel().select(getDiagInit(radiantGUI.getSpectrum()));

        //directivity
        label = addLabel("Направленность",startX,startY+marginY+marginY+marginY);
        directivityList = new ComboBox<>(radiantDirectivitiesObservableList);
        rootNodeChild.getChildren().add(directivityList);
        rootNodeChild.setLeftAnchor(directivityList, (double) startX+marginX);
        rootNodeChild.setTopAnchor(directivityList, (double) startY+marginY+marginY+marginY);
        directivityList.getSelectionModel().select(getDirectivityInit(radiantGUI.getDirectivity()));

        //Кнопки
        //OK
        Button okButton = addButton("OK", 20,250);
        okButton.setPrefWidth(80);
        okButton.setOnAction(this::okButtonReact);

        //Отмена
        Button cancelButton = addButton("Отмена", 20+2*90,250);
        cancelButton.setPrefWidth(80);
        cancelButton.setOnAction(this::cancelButtonReact);

        //Показать окно
        editRadiantWindow.show();
    }

    /**
     *
     * @param s
     * @return
     */

    private boolean checkDecimal(String s){
        if ((!s.matches("\\d{1,100}([\\.]\\d{0,100})?") & !s.equals("")) || (s.length()>1 & s.startsWith("0")) & !s.startsWith("0.")) {
            return false;
        }
        return true;
    }

    /**
     *
     * @param defaultText
     * @param x
     * @param y
     * @return
     */

    private Button addButton(String defaultText, double x, double y) {
        Button button = new Button(defaultText);
        button.setFont(MainGUI.defaultFont);
        rootNodeChild.getChildren().add(button);
        rootNodeChild.setLeftAnchor(button, x);
        rootNodeChild.setTopAnchor(button, y);
        return button;
    }

    /**
     *
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
     *
     * @param defaultText
     * @param x
     * @param y
     * @return
     */

    private TextField addTextField(Object defaultText, double x, double y){
        TextField textField = new TextField(defaultText.toString());
        textField.setFont(MainGUI.defaultFont);
        rootNodeChild.setLeftAnchor(textField, x);
        rootNodeChild.setTopAnchor(textField, y);
        rootNodeChild.getChildren().add(textField);
        return textField;
    }

    /**
     *
     * @param ae
     */

    private void okButtonReact(ActionEvent ae) {
        System.out.println(spectrumList);
        Radiant radiant = new Radiant(Double.parseDouble(FeTF.getText()),nameTF.getText(),spectrumList.getValue().toString(),directivityList.getValue().toString());
        radiantGUI.setObject(radiant,this);
    }

    /**
     *
     * @param ae
     */

    private void cancelButtonReact(ActionEvent ae) {
        close();
    }

    /**
     *
     */

    public void close() {
        editRadiantWindow.close();
    }


    /**
     *
     * @param diagName
     * @return
     */

    private int getDiagInit(String diagName){
        for (int i=0;i<radiantDiagramsObservableList.size();i++) {
            if (diagName.equals(radiantDiagramsObservableList.get(i).getName())) {
                return i;
            }
        }
        return 0;
    }
    private int getDirectivityInit(String diagName){
        for (int i=0;i<radiantDirectivitiesObservableList.size();i++) {
            if (diagName.equals(radiantDirectivitiesObservableList.get(i).getName())) {
                return i;
            }
        }
        return 0;
    }
}
