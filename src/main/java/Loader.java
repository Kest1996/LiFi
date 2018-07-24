package loader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import radiation.*;

class Loader2 {
    public static void main(String[] args) {
        String fileName = "test.json";
        Loader loader = new Loader(fileName);
        System.out.println("Файл: " + loader.getFileName());
        //тест загрузки источников
        ArrayList<Radiant> test1 = loader.loadRadiants();
        System.out.println("Источники: " + test1.size());
        for (int i=0;i<test1.size();i++) {
            System.out.print(test1.get(i).getName()+"\t");
            System.out.println(test1.get(i).getiMax());
        }
        //тест загрузки приемников
        ArrayList<Receiver> test2 = loader.loadReceivers();
        System.out.println("Приемники: " + test2.size());
        for (int i=0;i<test2.size();i++) {
            System.out.print(test2.get(i).getSensitivity()+"\t");
            System.out.println(test2.get(i).getResponse_rate());
        }
    }
}

public class Loader {
    private JSONParser parser = new JSONParser();
    private JSONObject object;
    private JSONObject fileObject;
    private String fileName;
    Loader (String fileName) {
        this.fileName = fileName;
        try {
            //Загрузка файла
            fileObject = (JSONObject) parser.parse(new FileReader(this.fileName));
        }
        catch (IOException | ParseException ex) {
                System.out.println("Ошибка загрузки файла");
                System.exit(1);
        }
    }
    public ArrayList<Radiant> loadRadiants() {
        ArrayList<Radiant> radiantsList = new ArrayList<>();
        String name;
        double iMax;
        //Загрузка блока
        JSONArray radiants = (JSONArray) fileObject.get("Radiant");
        //Загрузка каждого элемента
        for (int i = 0; i<radiants.size(); i++) {
            object = (JSONObject) radiants.get(i);
            try {
                name = object.get("name").toString();
            }
            catch (NullPointerException ex) {
                System.out.println("Ошибка: отсутствует name в источнике "+i);
                continue;
            }
            try {
                iMax = Double.parseDouble(object.get("iMax").toString());
            }
            catch (NullPointerException ex) {
                System.out.println("Ошибка: отсутствует iMax в источнике "+i);
                continue;
            }
            radiantsList.add(new Radiant(iMax, name));
        }
        //Возврат ArrayList со всем источниками
        return radiantsList;
    }
    public ArrayList<Receiver> loadReceivers() {
        ArrayList<Receiver> receiversList = new ArrayList<>();
        double sensitivity;
        double response_rate;
        //Загрузка блока
        JSONArray receivers = (JSONArray) fileObject.get("Receiver");
        //Загрузка каждого элемента
        for (int i = 0; i<receivers.size(); i++) {
            object = (JSONObject) receivers.get(i);
            try {
                sensitivity = Double.parseDouble(object.get("sensitivity").toString());
            }
            catch (NullPointerException ex) {
                System.out.println("Ошибка: отсутствует sensitivity в приемнике "+i);
                continue;
            }
            try {
                response_rate = Double.parseDouble(object.get("response_rate").toString());
            }
            catch (NullPointerException ex) {
                System.out.println("Ошибка: отсутствует response_rate в источнике "+i);
                continue;
            }
            receiversList.add(new Receiver(sensitivity,response_rate));

        }
        //Возврат ArrayList со всем приемниками
        return receiversList;
    }
    public String getFileName() {
        return fileName;
    }
}