package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gui.RadiantGUI;
import gui.ReceiverGUI;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import radiation.Radiant;
import radiation.Receiver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ReceiverModel extends GuiModel {
    private double sensetivity;
    private double responseRate;
    private String name;
    private transient ImageView imgv;
    private transient Map<String, Double> coefficients = new HashMap<>();
    ReceiverModel() {
        img = "resources\\img\\receiver.jpg";
    }
    ReceiverModel(ReceiverGUI receiverGUI) {
        this();
        Image imgo = new Image(img);
        imgv = new ImageView(imgo);
        this.sensetivity = receiverGUI.getSensitivity();
        this.responseRate = receiverGUI.getResponseRate();
        this.name = receiverGUI.getName();
    }

    public void getE(int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
        coefficients = new HashMap<>();
        double delta = 1;
        double k = Math.PI/180; //коэффициент преобразования в градусы
        /*
        Set<Map.Entry<String, RadiantModel>> set1 = Model.Radiants.entrySet();
        for (Map.Entry<String, RadiantModel> me : set1) {
            System.out.print(me.getKey() + ": ");
            System.out.println(me.getValue());
        }
        */
        for (double teta = 0; teta < 180*k; teta = (teta/k + delta)*k) {
            String str;
            for (double phi = 0; phi < (360*k); phi = (phi/k + delta)*k) {
                double currentX = x;
                double currentY = y;
                double currentZ = z;
                boolean checker = true;
                double dX = Math.sin(teta)*Math.cos(phi);
                double dY = Math.sin(teta)*Math.sin(phi);
                double dZ = Math.cos(teta);
                //Обход направления
                while (checker) {
                    //Увеличение координат
                    currentX += dX;
                    currentY += dY;
                    currentZ -= dZ;
                    str = ((int) currentX+"_"+(int)currentY+"_"+(int)currentZ);
                    if (Model.Radiants.get(str)!=null) {
                        if (coefficients.get(str)!=null) {
                            coefficients.put(str,(coefficients.get(str)+Model.Radiants.get(str).getE(phi,teta)));
                        }
                        else {
                            coefficients.put(str,Model.Radiants.get(str).getE(phi,teta));
                        }
                        checker = false;
                    }
                    else {
                        //Проверка выхода за границы
                        checker = check(minX, maxX, minY, maxY, minZ, maxZ, currentX, currentY, currentZ);
                    }
                }
                if (((Double)teta).equals(0.0)) {
                    break;
                }
            }
        }
        System.out.println("Коэффициенты:");
        try {
            Set<Map.Entry<String, Double>> set = coefficients.entrySet();
            for (Map.Entry<String, Double> me : set) {
                System.out.print(me.getKey() + ": ");
                System.out.println(me.getValue());
            }
        }
        catch (NullPointerException exc) {
            System.out.println("Нет пересечений");
        }
    }

    private boolean check(int minX, int maxX, int minY, int maxY, int minZ, int maxZ,double currentX, double currentY, double currentZ){
        if ((currentX <= minX) || (currentX > maxX)) {
            return false;
        }
        if ((currentY <= minY) || (currentY > maxY)) {
            return false;
        }
        if ((currentZ <= minZ) || (currentZ > maxZ)) {
            return false;
        }
        return true;
    }
}