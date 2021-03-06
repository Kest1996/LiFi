package loader;
import com.google.gson.*;
import gui.RadiantGUILoad;
import gui.ReceiverGUILoad;
import radiation.Radiant;
import radiation.Receiver;
import java.io.*;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GLoader {

    //Данные для загрузки сохранения
    private ArrayList<RadiantGUILoad> Radiants;
    private ArrayList<ReceiverGUILoad> Receivers;

    /**
     * Метод для чтения данных сохранеия
     * @param fileName
     * @return
     */

    private static GLoader getAll(String fileName) {
        //Считывание файла в строку
        try (FileReader jsonData = new FileReader(fileName))
        {
            //Инициализация Gson
            Gson gsonObject = new GsonBuilder().create();
            //Возврат всех значений
            return gsonObject.fromJson(jsonData, GLoader.class);

        } catch(IOException ex) {
            System.out.println("Файл "+fileName+" не найден");
        }
        return null;
    }

    /**
     * Вернуть источники
     * @param fileName
     * @return
     */

    public static ArrayList<RadiantGUILoad> getRadiants(String fileName) {
        return getAll(fileName).Radiants;
    }

    /**
     * Вернуть приемники
     * @param fileName
     * @return
     */

    public static ArrayList<ReceiverGUILoad> getReceivers(String fileName) {
        return getAll(fileName).Receivers;
    }

    /**
     * Загрузка библиотеки источников
     * @param path
     * @return
     */

    public static ArrayList<Radiant> loadRadiantLibrary(String path) {

        //Считывание всех файлов в папке
        File directory = new File(path);
        File[] arrayFiles = directory.listFiles();

        //Считывание файлов в формате json в ArrayList источников
        ArrayList<Radiant> arrayList= new ArrayList<>();
        Gson gson = new Gson();
        for (int i=0;i<arrayFiles.length;i++) {
            try (FileReader jsonData = new FileReader(arrayFiles[i])) {
                arrayList.add(gson.fromJson(jsonData, Radiant.class));
            } catch(IOException ex) {
                System.out.println("Файл "+arrayFiles[i]+" не найден");
            }
        }

        //Возврат ArrayList источников
        return arrayList;
    }

    /**
     *
     * @return
     */

    public static ArrayList<Radiant> loadRadiantLibrary() {
        return loadRadiantLibrary("src\\main\\resources/Library/Radiants");
    }

    /**
     * Загрузка библиотеки приемников
     * @param path
     * @return
     */

    public static ArrayList<Receiver> loadReceiverLibrary(String path) {

        //Считывание всех файлов в папке
        File directory = new File(path);
        File[] arrayFiles = directory.listFiles();

        //Считывание файлов в формате json в ArrayList приемников
        ArrayList<Receiver> arrayList= new ArrayList<>();
        Gson gson = new GsonBuilder().create();
        for (int i=0;i<arrayFiles.length;i++) {
            try (FileReader jsonData = new FileReader(arrayFiles[i])) {
                arrayList.add(gson.fromJson(jsonData, Receiver.class));
            } catch(IOException ex) {
                System.out.println("Файл "+arrayFiles[i]+" не найден");
            }
        }

        //Возврат ArrayList источников
        return arrayList;
    }

    /**
     *
     * @return
     */

    public static ArrayList<Receiver> loadReceiverLibrary() {
        return loadReceiverLibrary("src\\main\\resources\\Library\\Receivers");
    }

    /**
     * Функции записи источников
     * @param fileName
     * @param radiant
     */

    public static void SaveNewRadiant(String fileName, Radiant radiant) {
        SaveNewRadiant("src\\main\\resources\\Library\\Radiants", fileName, radiant);
    }

    /**
     *
     * @param path
     * @param fileName
     * @param radiant
     */

    public static void SaveNewRadiant(String path, String fileName, Radiant radiant) {
        Gson gsonObject = new GsonBuilder().create();
        String jsonData = gsonObject.toJson(radiant, Radiant.class);
        try (FileWriter fw = new FileWriter(path+"\\"+fileName+".json")) {
            fw.write(jsonData);
        } catch(IOException exc) {
            System.out.println("I/O Error: " + exc);
        }
    }

    /**
     *
     * @param path
     * @param fileName
     * @param radiant
     */

    public static void RewriteRadiant(String path, String fileName, Radiant radiant) {
        File file = new File(path+"\\"+fileName+".json");
        file.delete();
        SaveNewRadiant(path, fileName, radiant);
    }

    /**
     *
     * @param fileName
     * @param radiant
     */

    public static void RewriteRadiant(String fileName, Radiant radiant) {
        RewriteRadiant("src\\main\\resources\\Library\\Radiants", fileName, radiant);
    }

    /**
     * Функции записи приемников
     * @param fileName
     * @param receiver
     */

    public static void SaveNewReceiver(String fileName, Receiver receiver) {
        SaveNewReceiver("src\\main\\resources\\Library\\Receivers", fileName, receiver);
    }

    /**
     *
     * @param path
     * @param fileName
     * @param receiver
     */

    public static void SaveNewReceiver(String path, String fileName, Receiver receiver) {
        Gson gsonObject = new GsonBuilder().create();
        String jsonData = gsonObject.toJson(receiver, Receiver.class);
        try (FileWriter fw = new FileWriter(path+"\\"+fileName+".json")) {
            fw.write(jsonData);
        } catch(IOException exc) {
            System.out.println("I/O Error: " + exc);
        }
    }

    /**
     *
     * @param path
     * @param fileName
     * @param receiver
     */

    public static void RewriteReceiver(String path, String fileName, Receiver receiver) {
        File file = new File(path+"\\"+fileName+".json");
        file.delete();
        SaveNewReceiver(path, fileName, receiver);
    }

    /**
     *
     * @param fileName
     * @param receiver
     */

    public static void RewriteReceiver(String fileName, Receiver receiver) {
        RewriteReceiver("src\\main\\resources\\Library\\Receivers", fileName, receiver);
    }
}
