package radiation;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Diagram {
    protected String name;
    protected ArrayList<DiagramPoint> dependence = new ArrayList<>();
    protected transient double first;
    protected transient double last;
    public Diagram(String name) {
        this.name = name;
    }
    public Diagram(String name, ArrayList<DiagramPoint> dependence) {
        this.name = name;
        this.dependence = dependence;
        first = dependence.get(0).getIndex();
        last = dependence.get(dependence.size()-1).getIndex();
    }
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
        first = dependence.get(0).getIndex();
        last = dependence.get(dependence.size()-1).getIndex();
    }
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
        first = dependence.get(0).getIndex();
        last = dependence.get(dependence.size()-1).getIndex();
    }
    public double getFirst() {
        return first;
    }
    public double getLast() {
        return last;
    }
    public String getName() {
        return name;
    }
    public ArrayList<DiagramPoint> getDependence() {
        return dependence;
    }
    public DiagramPoint getPoint(int index) {
        return dependence.get(index);
    }
    public DiagramPoint getByX(double x) {
        return getPoint((int)(x-getFirst()));
    }
    public void add(double index, double meaning) {
        this.dependence.add(new DiagramPoint(index,meaning));
    }
    public int getSize() {
        return dependence.size();
    }
    @Override
    public String toString() {
        return name;
    }
    public void print() {
        for (int i=0; i<dependence.size();i++){
            System.out.println(dependence.get(i));
        }
    }
}
