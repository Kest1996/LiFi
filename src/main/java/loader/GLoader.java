package loader;
import com.google.gson.*;
import radiation.Radiant;
import radiation.Receiver;
import java.io.*;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GLoader {
    //Класс для метода fromJson()
    private ArrayList<Radiant> Radiants;
    private ArrayList<Receiver> Receivers;

    //Метод для чтения данных
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
    //Вернуть источники
    public static ArrayList<Radiant> getRadiants(String fileName) {
        return getAll(fileName).Radiants;
    }
    //Вернуть приемники
    public static ArrayList<Receiver> getReceivers(String fileName) {
        return getAll(fileName).Receivers;
    }
    public static ArrayList<Radiant> loadRadiantLibrary(String path) {
        //Считывание всех файлов в папке
        File directory = new File(path);
        File[] arrayFiles = directory.listFiles();
        //Считывание файлов в формате json в ArrayList источников
        ArrayList<Radiant> arrayList= new ArrayList<>();
        Gson gson = new GsonBuilder().create();
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
}
