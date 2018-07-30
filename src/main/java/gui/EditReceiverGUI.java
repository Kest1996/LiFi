package gui;

import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import radiation.Receiver;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;


public class EditReceiverGUI {
    private AnchorPane rootNodeChild = new AnchorPane();
    private Stage editReceiverWindow;
    private ReceiverGUI receiverGUI;
    private TextField nameTF;
    private TextField sensetivityTF;
    private TextField responseRateTF;
    EditReceiverGUI(ReceiverGUI receiverGUI, int id) {
        //Создание базы окна
        this.receiverGUI = receiverGUI;
        Scene scene = new Scene(rootNodeChild, 300, 300);
        editReceiverWindow = new Stage();
        editReceiverWindow.setTitle("Редактирование приемника");
        editReceiverWindow.setScene(scene);
        //Указание дочерности
        editReceiverWindow.initModality(Modality.WINDOW_MODAL);
        editReceiverWindow.initOwner(MainGUI.primaryStage);
        //Установка расположения
        editReceiverWindow.setX(200);
        editReceiverWindow.setY(200);

        //Вывод полей редактирования
        int marginX = 100;
        int marginY = 30;
        int startX = 20;
        int startY = 20;
        //name
        Label label = addLabel("name",startX,startY);
        nameTF = addTextField(receiverGUI.getName(),startX+marginX,startY);
        //iMax
        label = addLabel("sensetivity",startX,startY+marginY);
        sensetivityTF = addTextField(receiverGUI.getSensitivity(),startX+marginX,startY+marginY);
        sensetivityTF.textProperty().addListener((ae, oldValue, newValue)-> {
            if (!checkDecimal(newValue)) {
                sensetivityTF.setText(oldValue);
            }
        });
        //i
        label = addLabel("responseRate",startX,startY+marginY+marginY);
        responseRateTF = addTextField(receiverGUI.getResponseRate(),startX+marginX,startY+marginY+marginY);
        responseRateTF.textProperty().addListener((ae, oldValue, newValue)-> {
            if (!checkDecimal(newValue)) {
                responseRateTF.setText(oldValue);
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
        editReceiverWindow.show();
    }
    private boolean checkDecimal(String s){
        if ((!s.matches("\\d{1,100}([\\.]\\d{0,100})?") & !s.equals("")) || (s.length()>1 & s.startsWith("0")) & !s.startsWith("0.")) {
            return false;
        }
        return true;
    }
    private void okButtonReact(ActionEvent ae) {
        Receiver receiver = new Receiver(nameTF.getText(),Double.parseDouble(sensetivityTF.getText()),Double.parseDouble(responseRateTF.getText()));
        receiverGUI.setObject(receiver,this);
    }
    private void cancelButtonReact(ActionEvent ae) {
        close();
    }
    public void close() {
        editReceiverWindow.close();
    }
    Button addButton(String defaultText, double x, double y) {
        Button button = new Button(defaultText);
        rootNodeChild.getChildren().add(button);
        rootNodeChild.setLeftAnchor(button, x);
        rootNodeChild.setTopAnchor(button, y);
        return button;
    }
    Label addLabel(String text, double x, double y){
        Label label = new Label(text);
        rootNodeChild.getChildren().add(label);
        rootNodeChild.setLeftAnchor(label, x);
        rootNodeChild.setTopAnchor(label, y);
        return label;
    }
    TextField addTextField(Object defaultText, double x, double y){
        TextField textField = new TextField(defaultText.toString());
        rootNodeChild.setLeftAnchor(textField, x);
        rootNodeChild.setTopAnchor(textField, y);
        rootNodeChild.getChildren().add(textField);
        return textField;
    }
}
