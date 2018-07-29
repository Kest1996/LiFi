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
    private static AnchorPane rootNode;
    private static  ArrayList<Radiant> radiants;
    private static ArrayList<Receiver> receivers;
    private static ArrayList<Radiant> radiantsLibrary;
    private static ObservableList<Radiant> radiantsObservableList;
    public static ArrayList<RadiantGUI> RadiantGUIList = new ArrayList<>();
    private static Button addRadiantButton;
    public static WindowView LFWindow;

    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage LFStage)   {

        // Создание базы окна
        LFStage.setTitle("LI-FI TEST");
        LFWindow = new WindowView(1280,(720-50));
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

        openFile("test.json",LFWindow);


        LFStage.show();
    }
    private void openFile(String fileName, WindowView window) {
        //Чтение файла
        radiants = loader.GLoader.getRadiants(fileName);
        receivers = loader.GLoader.getReceivers(fileName);

        //Вывод источников:
        window.addLabel("Источники:",100,70,20);
        //Чтение библиотеки источников:
        radiantsLibrary = GLoader.loadRadiantLibrary("Library\\Radiants");
        radiantsObservableList = FXCollections.observableArrayList(radiantsLibrary);
        //Создание GUI источников
        int marginY = 60;
        for (int i=0;i<radiants.size();i++){
            RadiantGUIList.add(window.addRadiantGUI(radiantsObservableList,50,100+i*marginY, i, radiants.get(i)));
        }
        //Создание кнопки для добавления источников
        addRadiantButton = window.addButton("Добавить",50,100+RadiantGUIList.size()*marginY);
        addRadiantButton.setOnAction((ae) -> {
            RadiantGUIList.add(window.addRadiantGUI(radiantsObservableList,50,100+RadiantGUIList.size()*marginY, RadiantGUIList.size()));
            rootNode.setTopAnchor(addRadiantButton, (double)(100+RadiantGUIList.size()*marginY));
        });



        //Вывод приемников:
        window.addLabel("Приемники:",520,70,20);

       ///Кнопка
        /*
        Button DELETEALL = new Button("УДАЛИТЬ");
        DELETEALL.setOnAction((ae) -> {
            deleteAllRadiantsGUI();
        });
        rootNode.setLeftAnchor(DELETEALL, 100.0);
        rootNode.setTopAnchor(DELETEALL, 600.0);
        rootNode.getChildren().addAll(DELETEALL);
        */
        //Показать сцену

    }
    /*
    private static void deleteAllRadiantsGUI(int start, int finish) {
        for (int i=finish;i>=start;i--) {
            LFWindow.deleteRadiantGUI(RadiantGUIList.get(i),RadiantGUIList.get(i).getId());
        }
        RadiantGUIList = new ArrayList<>();
    }
    private static void deleteAllRadiantsGUI(int start) {
        deleteAllRadiantsGUI(start,RadiantGUIList.size()-1);
    }
    private static void deleteAllRadiantsGUI() {
        deleteAllRadiantsGUI(0,RadiantGUIList.size()-1);
    }
    */
    public static void moveRadiantsGUI (int start, int finish){
        int marginY = 60;
        for (int i=start;i<finish;i++){
            LFWindow.moveRadiantGUI(RadiantGUIList.get(i),50,100+i*marginY);
            RadiantGUIList.get(i).setId(i);
        }
        LFWindow.moveButton(addRadiantButton,50,100+RadiantGUIList.size()*marginY);
    }
    public static void moveRadiantsGUI (int start){
        moveRadiantsGUI (start, RadiantGUIList.size());
    }
    public static void moveRadiantsGUI (){
        moveRadiantsGUI (0, radiants.size());
    }
}


