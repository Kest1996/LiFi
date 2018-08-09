package gui;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import radiation.Radiant;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;

public class EditRadiantGUI {
    private AnchorPane rootNodeChild = new AnchorPane();
    private Stage editRadiantWindow;
    private RadiantGUI radiantGUI;
    private TextField nameTF;
    private TextField iMaxTF;
    private TextField iTF;
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
        Label label = addLabel("name",startX,startY);
        nameTF = addTextField(radiantGUI.getName(),startX+marginX,startY);
        //iMax
        label = addLabel("iMax",startX,startY+marginY);
        iMaxTF = addTextField(radiantGUI.getiMax(),startX+marginX,startY+marginY);
        iMaxTF.textProperty().addListener((ae, oldValue, newValue)-> {
            if (!checkDecimal(newValue)) {
                iMaxTF.setText(oldValue);
            }
        });
        //i
        label = addLabel("i",startX,startY+marginY+marginY);
        iTF = addTextField(radiantGUI.getI(),startX+marginX,startY+marginY+marginY);
        iTF.textProperty().addListener((ae, oldValue, newValue)-> {
            if (!checkDecimal(newValue)) {
                iTF.setText(oldValue);
            }
        });
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
    private boolean checkDecimal(String s){
        if ((!s.matches("\\d{1,100}([\\.]\\d{0,100})?") & !s.equals("")) || (s.length()>1 & s.startsWith("0")) & !s.startsWith("0.")) {
            return false;
        }
        return true;
    }
    private Button addButton(String defaultText, double x, double y) {
        Button button = new Button(defaultText);
        button.setFont(MainGUI.defaultFont);
        rootNodeChild.getChildren().add(button);
        rootNodeChild.setLeftAnchor(button, x);
        rootNodeChild.setTopAnchor(button, y);
        return button;
    }
    private Label addLabel(String text, double x, double y){
        Label label = new Label(text);
        label.setFont(MainGUI.defaultFont);
        rootNodeChild.getChildren().add(label);
        rootNodeChild.setLeftAnchor(label, x);
        rootNodeChild.setTopAnchor(label, y);
        return label;
    }
    private TextField addTextField(Object defaultText, double x, double y){
        TextField textField = new TextField(defaultText.toString());
        textField.setFont(MainGUI.defaultFont);
        rootNodeChild.setLeftAnchor(textField, x);
        rootNodeChild.setTopAnchor(textField, y);
        rootNodeChild.getChildren().add(textField);
        return textField;
    }
    private void okButtonReact(ActionEvent ae) {
        Radiant radiant = new Radiant(Double.parseDouble(iMaxTF.getText()),nameTF.getText(),Double.parseDouble(iTF.getText()));
        radiantGUI.setObject(radiant,this);
    }
    private void cancelButtonReact(ActionEvent ae) {
        close();
    }
    public void close() {
        editRadiantWindow.close();
    }
}