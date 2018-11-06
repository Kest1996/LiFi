package radiation;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Diagram {

    protected String name;
    protected ArrayList<DiagramPoint> dependence = new ArrayList<>();
    protected transient double begin;
    protected transient double end;

    /**
     * Конструктор
     * @param name
     */

    public Diagram(String name) {
        this.name = name;
    }

    /**
     * Конструктор
     * @param name
     * @param dependence
     */

    public Diagram(String name, ArrayList<DiagramPoint> dependence) {
        this.name = name;
        this.dependence = dependence;
        begin = dependence.get(0).getIndex();
        end = dependence.get(dependence.size()-1).getIndex();
    }

    /**
     * Конструктор
     * @param name
     * @param filePath
     */

    public Diagram(String name, String filePath) {
        this.name = name;
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(filePath));
        } catch (FileNotFoundException e) {
            System.out.println("Файл "+filePath+" не найден");
        }
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] point = line.split("\t");
            this.dependence.add(new DiagramPoint(Double.parseDouble(point[0]),Double.parseDouble(point[1])));
        }
        begin = dependence.get(0).getIndex();
        end = dependence.get(dependence.size()-1).getIndex();
    }

    /**
     * Конструктор
     * @param name
     * @param file
     */

    public Diagram(String name, File file) {
        this.name = name;
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("Файл "+file+" не найден");
        }
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] point = line.split("\t");
            this.dependence.add(new DiagramPoint(Double.parseDouble(point[0]),Double.parseDouble(point[1])));
        }
        begin = dependence.get(0).getIndex();
        end = dependence.get(dependence.size()-1).getIndex();
    }

    public double getBegin() {
        return begin;
    }

    public double getEnd() {
        return end;
    }

    public String getName() {
        return name;
    }

    /**
     *
     * @return
     */

    public ArrayList<DiagramPoint> getDependence() {
        return dependence;
    }

    /**
     *
     * @param index
     * @return
     */

    public DiagramPoint getPoint(int index) {
        return dependence.get(index);
    }

    /**
     *
     * @param x
     * @return
     */

    public DiagramPoint getByX(double x) {
        return getPoint(Math.abs((int)(x-getBegin())));
    }

    /**
     *
     * @param index
     * @param meaning
     */

    public void add(double index, double meaning) {
        this.dependence.add(new DiagramPoint(index,meaning));
        begin = dependence.get(0).getIndex();
        end = dependence.get(dependence.size()-1).getIndex();
    }

    public int getSize() {
        return dependence.size();
    }

    /**
     *
     * @return
     */

    public double[] getXAxis() {
        double[] AxisX = new double[getSize()];
        for (int i=0;i<getSize();i++){
            AxisX[i] = getPoint(i).getIndex();
        }
        return AxisX;
    }

    public double[] getYAxis() {
        double[] AxisY = new double[getSize()];
        for (int i=0;i<getSize();i++){
            AxisY[i] = getPoint(i).getMeaning();
        }
        return AxisY;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     *
     */

    public void print() {
        for (int i=0; i<dependence.size();i++){
            System.out.println(dependence.get(i));
        }
    }
}
