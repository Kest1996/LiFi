package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import gui.RadiantGUI;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import radiation.Diagram;
import radiation.Directivity;
import radiation.Radiant;

import java.io.File;
import java.util.ArrayList;

public class RadiantModel extends GuiModel {

    private double Fe;
    private String name;
    private String spectrum;
    private String directivity;

    private transient Diagram spectrumData;
    private transient Directivity directivityData;
    private transient ImageView imgv;

    /**
     * Конструктор
     */

    RadiantModel(){
        img = "resources\\img\\radiant.jpg";
        sizeX = 0;
        sizeY = 0;
        sizeZ = 0;
    }

    /**
     * Получение энергии
     * @param phi
     * @param teta
     * @return
     */

    public double getE(double phi, double teta) {
        double Emax = 1000;
        return Emax/(((180/0.02)*(180/0.02)));
    }

    /**
     * Получение спектра излучения
     * @return
     */

    public Diagram getSpectrumData() {
        return spectrumData;
    }

    /**
     * Получение диаграммы направленности
     * @return
     */

    public Directivity getDirectivityData() {
        return directivityData;
    }

    /**
     * Получение имени излучателя
     * @return
     */

    public String getName() {
        return name;
    }

    public double getFe() {
        return Fe;
    }

    /**
     * Запись спектра
     */

    public void setSpectrum() {
        String libPath = "src/main/resources/Library/RadiantDiagram/";
        File file = new File(libPath+spectrum+".txt");
        spectrumData = new Diagram(file.getName().substring(0,file.getName().length()-4),file);;
    }
    public void setDirectivity() {
        String libPath = "src/main/resources/Library/Directivity/";
        File file = new File(libPath+directivity+".txt");
        directivityData = new Directivity(file.getName().substring(0,file.getName().length()-4),file);;
    }

    public void printDirectivityData() {
        directivityData.print();
    }
}
