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
import radiation.Receiver;

import java.lang.reflect.Array;
import java.util.*;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ModelScene {
    private AnchorPane rootNodeChild = new AnchorPane();
    private Scene scene;
    private ArrayList<GuiModel> objects = new ArrayList<>();
    //Базовые сдвиги
    double xStart = 5;
    double yStart = 45;
    ModelScene(Stage stage, int width, int height, ArrayList<GuiModel> objectsIn, String bazis) {
        this.scene = new Scene(rootNodeChild, width, height);

    }
    ModelScene(Stage stage, int width, int height, ArrayList<ReceiverModel> receiverModels, ArrayList<HashMap<String, ReceiverCoefData>> coefMap) {
        this(stage, width, height, null, "");
        double marginY = 30;
        for (int i=0; i<receiverModels.size(); i++) {
            addLabel(receiverModels.get(i).toString(),xStart,yStart+i*marginY*coefMap.get(i).size()+20*i);
            Set<Map.Entry<String, ReceiverCoefData>> set = coefMap.get(i).entrySet();
            int nnn1 = 0;
            for (Map.Entry<String, ReceiverCoefData> me : set) {
                addLabel("Radiant "+me.getKey() + ": ", xStart+200,yStart+marginY*(i*coefMap.get(i).size()+nnn1)+20*i);
                addLabel("Расстояние: "+me.getValue().getR(),xStart+400,yStart+marginY*(i*coefMap.get(i).size()+nnn1)+20*i);
                addLabel("Угол Фи: "+me.getValue().getPhi(),xStart+600,yStart+marginY*(i*coefMap.get(i).size()+nnn1)+20*i);
                addLabel("Угол Тета: "+me.getValue().getTeta(),xStart+800,yStart+marginY*(i*coefMap.get(i).size()+nnn1)+20*i);
                addLabel("Коэффициент: "+me.getValue().getK(),xStart+1000,yStart+marginY*(i*coefMap.get(i).size()+nnn1)+20*i);
                nnn1++;
            }
        }
    }

    public Scene getScene() {
        return scene;
    }

    public Button addButton(String defaultText, double x, double y) {
        Button button = new Button(defaultText);
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
        textField.setDisable(true);
        rootNodeChild.setLeftAnchor(textField, x);
        rootNodeChild.setTopAnchor(textField, y);
        rootNodeChild.getChildren().add(textField);
        return textField;
    }
}
