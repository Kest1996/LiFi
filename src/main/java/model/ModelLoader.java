package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ModelLoader {
    //Данные для загрузки сохранения
    private ArrayList<RadiantModel> Radiants;
    private ArrayList<ReceiverModel> Receivers;

    //Метод для чтения данных сохранеия

    /**
     *
     * @param fileName
     * @return
     */

    private static ModelLoader getAll(String fileName) {
        //Считывание файла в строку
        try (FileReader jsonData = new FileReader(fileName))
        {
            //Инициализация Gson
            Gson gsonObject = new GsonBuilder().create();
            //Возврат всех значений
            return gsonObject.fromJson(jsonData, ModelLoader.class);

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

    public static ArrayList<RadiantModel> getRadiants(String fileName) {
        return getAll(fileName).Radiants;
    }

    /**
     * Вернуть приемники
     * @param fileName
     * @return
     */

    public static ArrayList<ReceiverModel> getReceivers(String fileName) {
        return getAll(fileName).Receivers;
    }
}
