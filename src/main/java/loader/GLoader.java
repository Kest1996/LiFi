package loader;
import com.google.gson.*;
import radiation.Radiant;
import radiation.Receiver;
import java.io.*;
import java.util.ArrayList;

class GLoader2 {
    public static void main(String[] args) {
        String fileName = "test.json";
        ArrayList<Radiant> radiants = GLoader.getRadiants(fileName);
        //тест загрузки источников
        System.out.println("Источники: " + radiants.size());
        for (int i=0;i<radiants.size();i++) {
            System.out.print(radiants.get(i).getName()+"\t");
            System.out.println(radiants.get(i).getiMax());
        }
        //тест загрузки приемников
        ArrayList<Receiver> receivers = GLoader.getReceivers(fileName);
        System.out.println("Приемники: " + receivers.size());
        for (int i=0;i<receivers.size();i++) {
            System.out.print(receivers.get(i).getSensitivity()+"\t");
            System.out.println(receivers.get(i).getResponseRate());
        }
    }
}

public class GLoader {
    //Класс для метода fromJson()
    private class GLoaderInit {
        ArrayList<Radiant> Radiants;
        ArrayList<Receiver> Receivers;
    }
    //Метод для чтения данных
    private static GLoaderInit getAll(String fileName) {
        String s;
        String jsonString = "";

        //Считывание файла в строку
        try (BufferedReader br = new BufferedReader(new FileReader(fileName)))
        {
            while((s = br.readLine()) != null) {
                jsonString += s;
            }
        } catch(IOException ex) {
            System.out.println("Файл "+fileName+" не найден");
        }

        //Инициализация Gson
        Gson gsonObject = new GsonBuilder().create();

        //Возврат всех значений
        return gsonObject.fromJson(jsonString, GLoaderInit.class);
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
