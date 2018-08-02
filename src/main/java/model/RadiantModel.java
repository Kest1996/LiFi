package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gui.RadiantGUI;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import radiation.Radiant;

public class RadiantModel {
    private String name;
    private double iMax;
    private double i;
    private int x;
    private int y;
    private int z;
    private transient ImageView imgv;
    private transient String img = "img/priemnik.png";
    RadiantModel(){
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }
    public double getE(double phi, double teta) {
        double Emax = 1000;
        return Emax/(((180/0.02)*(180/0.02)));
    }
    //RadiantModel(RadiantGUI radiantGUI) {

        /*
        Image imgo = new Image(img);
        imgv = new ImageView(imgo);
        this.i = radiantGUI.getI();
        this.iMax = radiantGUI.getiMax();
        this.name = radiantGUI.getName();
        */
    //}
}
