package gui;
import java.lang.reflect.*;
import javafx.application.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import loader.GLoader;
import radiation.Radiant;
import radiation.Receiver;
import java.util.ArrayList;

public class MainGUI extends Application {
    static AnchorPane rootNode;

    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage LFStage)   {

        // Создание базы окна
        LFStage.setTitle("LI-FI TEST");
        WindowView LFWindow = new WindowView(1280,(720-50));
        rootNode = LFWindow.getRootNode();
        LFStage.setScene(LFWindow.getScene());

        // МенюБар
        MenuBar menuBar = new MenuBar();
        rootNode.setLeftAnchor(menuBar,(double) 0);
        rootNode.setTopAnchor(menuBar,(double) 0);
        rootNode.getChildren().addAll(menuBar);
        // Элементы меню
        Menu fileMenu = new Menu("Файл");
        Menu addMenu = new Menu("Добавить");
        Menu helpMenu = new Menu("Справка");
        menuBar.getMenus().addAll(fileMenu, addMenu, helpMenu);
        // Подэлементы Файл
        MenuItem newFileMenu = new MenuItem("Новый");
        MenuItem openFileMenu = new MenuItem("Открыть");
        MenuItem exitFileMenu = new MenuItem("Выход");
        exitFileMenu.setOnAction((ae) -> System.exit(0));  //Реакция подэлемента меню
        fileMenu.getItems().addAll(newFileMenu, openFileMenu, exitFileMenu);
        // Подэлементы Добавить
        MenuItem radiantAddMenu = new MenuItem("Источник");
        MenuItem subjectAddMenu = new MenuItem("Объект");
        MenuItem receiverAddMenu = new MenuItem("Приемник");
        addMenu.getItems().addAll(radiantAddMenu, subjectAddMenu, receiverAddMenu);

        // Разделяющие линии
        Line SeparatingLine1 = new Line(420, 50, 420, 1000);
        SeparatingLine1.setStrokeWidth(10);
        SeparatingLine1.setStroke(Color.BLACK);
        rootNode.getChildren().addAll(SeparatingLine1);
        Line SeparatingLine2 = new Line(840, 50, 840, 1000);
        SeparatingLine2.setStrokeWidth(10);
        SeparatingLine2.setStroke(Color.BLACK);
        rootNode.getChildren().addAll(SeparatingLine2);

        //Чтение файла
        String fileName = "test.json";
        ArrayList<Radiant> radiants = loader.GLoader.getRadiants(fileName);
        ArrayList<Receiver> receivers = loader.GLoader.getReceivers(fileName);

        //Вывод источников:
        LFWindow.addLabel("Источники:",100,70,20);
        //Чтение библиотеки источников:
        ArrayList<Radiant> radiantsLibrary = GLoader.loadRadiantLibrary("Library\\Radiants");
        ObservableList<Radiant> radiantsObservableList = FXCollections.observableArrayList(radiantsLibrary);
        //Создание GUI источников
        int marginY = 60;
        ArrayList<RadiantGUI> RadiantGUIList = new ArrayList<>();
        for (int i=0;i<radiants.size();i++){
            RadiantGUIList.add(LFWindow.addRadiantGUI(radiantsObservableList,50,100+i*marginY, i, radiants.get(i)));
        }
        //Создание кнопки для добавления источников
        Button addRadiantButton = LFWindow.addButton("Добавить",50,100+RadiantGUIList.size()*marginY);
        addRadiantButton.setOnAction((ae) -> {
            RadiantGUIList.add(LFWindow.addRadiantGUI(radiantsObservableList,50,100+RadiantGUIList.size()*marginY, RadiantGUIList.size()));
            rootNode.setTopAnchor(addRadiantButton, (double)(100+RadiantGUIList.size()*marginY));
        });



        //Вывод приемников:
        LFWindow.addLabel("Приемники:",520,70,20);

       /* //Кнопка
        Button buttongetE = new Button("getE()");
        buttongetE.setOnAction((ae) -> {
            double sum = 0;
            double[] luchinfo;
            for (double i=0; i<=180; i=(i+(180.0/priem.tochn))) {
                luchinfo = priem.getE(i);
                sum = sum + luchinfo[0];
                mainGUI.LeftPanel.addLuch(mainGUI.rootNode, luchinfo[1], luchinfo[2], luchinfo[3], luchinfo[4]);
            }
            //System.out.println("Приемник получил "+sum+" энергии");
            EnOut.setText("Приемник получил "+sum+" энергии");
        });
        rootNode.setLeftAnchor(buttongetE, 100.0);
        rootNode.setTopAnchor(buttongetE, 600.0);
        rootNode.getChildren().addAll(buttongetE);
*/
        //Показать сцену
        LFStage.show();
    }
}


