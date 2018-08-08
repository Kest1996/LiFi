package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gui.RadiantGUI;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import radiation.Radiant;

public class RadiantModel extends GuiModel {
    private String name;
    private double iMax;
    private double i;
    private transient ImageView imgv;
    RadiantModel(){
        img = "resources\\img\\radiant.jpg";
        sizeX = 0;
        sizeY = 0;
        sizeZ = 0;
    }
    public double getE(double phi, double teta) {
        double Emax = 1000;
        return Emax/(((180/0.02)*(180/0.02)));
    }
    //RadiantModel(RadiantGUI radiantGUI) {

        /*
        this();
        Image imgo = new Image(img);
        imgv = new ImageView(imgo);
        this.i = radiantGUI.getI();
        this.iMax = radiantGUI.getiMax();
        this.name = radiantGUI.getName();
        */
    //}
}
