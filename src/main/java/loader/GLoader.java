package loader;
import com.google.gson.*;
import radiation.Radiant;
import radiation.Receiver;
import java.io.*;
import java.util.ArrayList;

class GLoader2 {

}

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
}
