package gui;

import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import radiation.Diagram;
import radiation.Receiver;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;

import static gui.MainGUI.receiverDiagramsObservableList;


public class EditReceiverGUI {
    private AnchorPane rootNodeChild = new AnchorPane();
    private Stage editReceiverWindow;
    private ReceiverGUI receiverGUI;
    private TextField nameTF;
    private ComboBox<Diagram> sensetivityList = new ComboBox<>();

    /**
     *
     * @param receiverGUI
     * @param id
     */

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
        Label label = addLabel("Имя",startX,startY);
        nameTF = addTextField(receiverGUI.getName(),startX+marginX,startY);

        //sensetivity
        label = addLabel("Чувствительность",startX,startY+marginY);
        sensetivityList = new ComboBox<>(receiverDiagramsObservableList);
        rootNodeChild.getChildren().add(sensetivityList);
        rootNodeChild.setLeftAnchor(sensetivityList, (double) startX+marginX);
        rootNodeChild.setTopAnchor(sensetivityList, (double) startY+marginY);
        sensetivityList.getSelectionModel().select(getDiagInit(receiverGUI.getSensitivity()));

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
     * @param ae
     */

    private void okButtonReact(ActionEvent ae) {
        Receiver receiver = new Receiver(nameTF.getText(),sensetivityList.getValue().toString());
        receiverGUI.setObject(receiver,this);
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
        editReceiverWindow.close();
    }

    /**
     *
     * @param defaultText
     * @param x
     * @param y
     * @return
     */

    Button addButton(String defaultText, double x, double y) {
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

    Label addLabel(String text, double x, double y){
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

    TextField addTextField(Object defaultText, double x, double y){
        TextField textField = new TextField(defaultText.toString());
        textField.setFont(MainGUI.defaultFont);
        rootNodeChild.setLeftAnchor(textField, x);
        rootNodeChild.setTopAnchor(textField, y);
        rootNodeChild.getChildren().add(textField);
        return textField;
    }

    /**
     *
     * @param diagName
     * @return
     */

    private int getDiagInit(String diagName){
        for (int i=0;i<receiverDiagramsObservableList.size();i++) {
            if (diagName.equals(receiverDiagramsObservableList.get(i).getName())) {
                return i;
            }
        }
        return 0;
    }
}
