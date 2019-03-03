package radiation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Directivity {

    protected String name;
    protected ArrayList<DiagramPoint> dependence = new ArrayList<>();

    /**
     * Конструктор
     * @param name
     */

    public Directivity(String name) {
        this.name = name;
    }

    /**
     * Конструктор
     * @param name
     * @param dependence
     */

    public Directivity(String name, ArrayList<DiagramPoint> dependence) {
        this.name = name;
        this.dependence = dependence;
    }

    /**
     * Конструктор
     * @param name
     * @param filePath
     */

    public Directivity(String name, String filePath) {
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
    }

    /**
     * Конструктор
     * @param name
     * @param file
     */

    public Directivity(String name, File file) {
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

    public DiagramPoint getByAngle(double x) {
        for (int i=1;i<dependence.size();i++){
            if (dependence.get(i).getIndex()>x) {
                if (Math.abs(dependence.get(i).getIndex()-x)<Math.abs(dependence.get(i-1).getIndex()-x)) {
                    return dependence.get(i);
                }
                else {
                    return dependence.get(i-1);
                }
            }
        }
        return new DiagramPoint(0,0);
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
