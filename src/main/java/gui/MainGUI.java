package gui;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import loader.GLoader;
import model.Model;
import radiation.Radiant;
import radiation.Receiver;
import java.util.ArrayList;

public class MainGUI extends Application {
    private String OpenedFileName = null;

    private static AnchorPane rootNode;
    public static WindowView LFWindow;
    public static Stage primaryStage;

    private static ArrayList<RadiantGUILoad> radiants;
    private static ArrayList<Radiant> radiantsLibrary;
    private static ObservableList<Radiant> radiantsObservableList;
    public static ArrayList<RadiantGUI> RadiantGUIList = new ArrayList<>();
    private static Button addRadiantButton;

    private static ArrayList<ReceiverGUILoad> receivers;
    private static ArrayList<Receiver> receiversLibrary;
    private static ObservableList<Receiver> receiversObservableList;
    public static ArrayList<ReceiverGUI> ReceiverGUIList = new ArrayList<>();
    private static Button addReceiverButton;

    int marginY = 60;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage LFStage) {

        primaryStage = LFStage;
        // Создание базы окна
        LFStage.setTitle("LI-FI TEST");
        LFWindow = new WindowView(1280, (720 - 50));
        rootNode = LFWindow.getRootNode();
        LFStage.setScene(LFWindow.getScene());
        setDefaultView();
        openFile("resources\\saves\\test.json", LFWindow);
        LFStage.show();
    }

    //Очистка после открытия файла
    private void clearALL() {
        rootNode.getChildren().removeAll(rootNode.getChildren());
        RadiantGUIList = new ArrayList<>();
        ReceiverGUIList = new ArrayList<>();
    }

    //Отрисовка базовых элементов
    private void setDefaultView() {
        // МенюБар
        MenuBar menuBar = new MenuBar();
        rootNode.setLeftAnchor(menuBar, (double) 0);
        rootNode.setTopAnchor(menuBar, (double) 0);
        rootNode.getChildren().addAll(menuBar);
        // Элементы меню
        Menu fileMenu = new Menu("Файл");
        Menu runMenu = new Menu("Запуск");
        Menu helpMenu = new Menu("Справка");
        menuBar.getMenus().addAll(fileMenu, runMenu, helpMenu);

        // Подэлементы Файл

        //Создать новый
        MenuItem newFileMenu = new MenuItem("Новый");
        newFileMenu.setOnAction((ae) -> createFile());
        //Открыть существующий
        MenuItem openFileMenu = new MenuItem("Открыть...");
        openFileMenu.setOnAction((ae) -> openFile(LFWindow));
        //Сохранить этот
        MenuItem saveFileMenu = new MenuItem("Сохранить");
        saveFileMenu.setOnAction((ae) -> saveFile(OpenedFileName));
        //Сохранить как
        MenuItem saveAsFileMenu = new MenuItem("Сохранить как...");
        saveAsFileMenu.setOnAction((ae) -> saveFileAs());
        //Выход
        MenuItem exitFileMenu = new MenuItem("Выход");
        exitFileMenu.setOnAction((ae) -> System.exit(0));
        fileMenu.getItems().addAll(newFileMenu, openFileMenu, saveFileMenu, saveAsFileMenu, exitFileMenu);

        // Подэлементы Запуск
        MenuItem runRunMenu = new MenuItem("Запустить модель");
        runRunMenu.setOnAction((ae) -> runModel());
        runMenu.getItems().addAll(runRunMenu);

        // Разделяющие линии
        Line SeparatingLine1 = new Line(420, 50, 420, 1000);
        SeparatingLine1.setStrokeWidth(10);
        SeparatingLine1.setStroke(Color.BLACK);
        rootNode.getChildren().addAll(SeparatingLine1);
        Line SeparatingLine2 = new Line(840, 50, 840, 1000);
        SeparatingLine2.setStrokeWidth(10);
        SeparatingLine2.setStroke(Color.BLACK);
        rootNode.getChildren().addAll(SeparatingLine2);

        //Надписи
        LFWindow.addLabel("Источники:", 100, 70, 20);
        LFWindow.addLabel("Приемники:", 520, 70, 20);

        //Создание кнопки для добавления источников
        addRadiantButton = LFWindow.addButton("Добавить", 50, 100 + RadiantGUIList.size() * marginY);
        addRadiantButton.setOnAction((ae) -> {
            RadiantGUIList.add(LFWindow.addRadiantGUI(radiantsObservableList, 50, 100 + RadiantGUIList.size() * marginY, RadiantGUIList.size()));
            rootNode.setTopAnchor(addRadiantButton, (double) (100 + RadiantGUIList.size() * marginY));
        });
        //Создание кнопки для добавления приемников
        addReceiverButton = LFWindow.addButton("Добавить", 440, 100 + ReceiverGUIList.size() * marginY);
        addReceiverButton.setOnAction((ae) -> {
            ReceiverGUIList.add(LFWindow.addReceiverGUI(receiversObservableList, 440, 100 + ReceiverGUIList.size() * marginY, ReceiverGUIList.size()));
            rootNode.setTopAnchor(addReceiverButton, (double) (100 + ReceiverGUIList.size() * marginY));
        });
    }

    //Запуск модели
    private void runModel() {
        for (int i=0;i<RadiantGUIList.size();i++) {
            RadiantGUIList.get(i).updateSaveObject();
        }
        for (int i=0;i<ReceiverGUIList.size();i++) {
            ReceiverGUIList.get(i).updateSaveObject();
        }

        Model model = new Model("1", RadiantGUIList, ReceiverGUIList);
    }

    //Открытие файла
    private void openFile(String fileName, WindowView window) {
        clearALL();
        setDefaultView();
        OpenedFileName = fileName;
        //Вывод источников:
        //Чтение файла
        radiants = loader.GLoader.getRadiants(fileName);
        //Чтение библиотеки источников:
        radiantsLibrary = GLoader.loadRadiantLibrary("resources\\Library\\Radiants");
        radiantsObservableList = FXCollections.observableArrayList(radiantsLibrary);
        //Создание GUI источников
        for (int i = 0; i < radiants.size(); i++) {
            RadiantGUIList.add(window.addRadiantGUI(radiantsObservableList, 50, 100 + i * marginY, i, radiants.get(i)));
        }
        LFWindow.moveButton(addRadiantButton, 50, 100 + RadiantGUIList.size() * marginY);

        //Вывод приемников:
        //Чтение файла
        receivers = loader.GLoader.getReceivers(fileName);
        //Чтение библиотеки приемников:
        receiversLibrary = GLoader.loadReceiverLibrary("resources\\Library\\Receivers");
        receiversObservableList = FXCollections.observableArrayList(receiversLibrary);
        //Создание GUI приемников
        for (int i = 0; i < receivers.size(); i++) {
            ReceiverGUIList.add(window.addReceiverGUI(receiversObservableList, 440, 100 + i * marginY, i, receivers.get(i)));
        }
        LFWindow.moveButton(addReceiverButton, 440, 100 + ReceiverGUIList.size() * marginY);
    }

    private void openFile(WindowView window) {
        String fileName = openFileDialog();
        if (fileName != null) {
            openFile(fileName, window);
        }
    }

    //Диалог открытия файла
    private String openFileDialog(String path) {
        FileChooser fileChooser = new FileChooser();//Класс работы с диалогом выборки и сохранения
        fileChooser.setInitialDirectory(new File(path));
        fileChooser.setTitle("Открыть файл");//Заголовок диалога
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("Файлы (*.json)", "*.json");//Расширение
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            return file.toString();
        }
        return null;
    }

    private String openFileDialog() {
        String path = System.getProperty("user.dir");
        return openFileDialog(path);
    }

    //Сохранение файла
    private void saveFileAs() {
        String fileName = saveFileDialog();
        if (fileName!=null) {
            saveFile(fileName);
        }
    }

    private void saveFile(String fileName) {
        if (fileName == null) {
            saveFileAs();
            return;
        }
        String toFile = "{\n" + "  \"Radiants\": ";
        //Строка источников
        Gson gsonObject = new GsonBuilder().create();
        for (int i=0;i<RadiantGUIList.size();i++) {
            RadiantGUIList.get(i).updateSaveObject();
        }
        String jsonData = gsonObject.toJson(RadiantGUIList, ArrayList.class);
        toFile += jsonData;
        toFile += ",\n" + "  \"Receivers\": ";
        for (int i=0;i<ReceiverGUIList.size();i++) {
            ReceiverGUIList.get(i).updateSaveObject();
        }
        jsonData = gsonObject.toJson(ReceiverGUIList, ArrayList.class);
        toFile += jsonData;
        toFile += "\n" + "}";
        try (FileWriter fw = new FileWriter(fileName)) {
            fw.write(toFile);
        } catch(IOException exc) {
            System.out.println("I/O Error: " + exc);
        }
    }

    //Создание нового файла
    private void createFile() {
        clearALL();
        setDefaultView();
        OpenedFileName = null;
    }

    //Диалог сохранения файла
    private String saveFileDialog(String path) {
        FileChooser fileChooser = new FileChooser();//Класс работы с диалогом выборки и сохранения
        fileChooser.setTitle("Сохранить файл");//Заголовок диалога
        fileChooser.setInitialDirectory(new File(path));
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("Файлы (*.json)", "*.json");//Расширение
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(primaryStage);
        if (file != null) {
            return file.toString();
        }
        return null;
    }
    private String saveFileDialog() {
        String path = System.getProperty("user.dir");
        return saveFileDialog(path);
    }

    //Перемещение источников
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
        moveRadiantsGUI (0, RadiantGUIList.size());
    }

    //Перемещение приемников
    public static void moveReceiversGUI (int start, int finish){
        int marginY = 60;
        for (int i=start;i<finish;i++){
            LFWindow.moveReceiverGUI(ReceiverGUIList.get(i),440,100+i*marginY);
            ReceiverGUIList.get(i).setId(i);
        }
        LFWindow.moveButton(addReceiverButton,440,100+ReceiverGUIList.size()*marginY);
    }
    public static void moveReceiversGUI (int start){
        moveReceiversGUI (start, ReceiverGUIList.size());
    }
    public static void moveReceiversGUI (){
        moveRadiantsGUI (0, ReceiverGUIList.size());
    }
}


