package radiation;

public class DiagramPoint {
    private double index;
    private double meaning;
    DiagramPoint(double index, double meaning) {
        this.index = index;
        this.meaning = meaning;
    }
    DiagramPoint(DiagramPoint diagramPoint) {
        this.index = diagramPoint.getIndex();
        this.meaning = diagramPoint.getMeaning();
    }
    public double getIndex() {
        return index;
    }

    public void setIndex(double index) {
        this.index = index;
    }

    public double getMeaning() {
        return meaning;
    }

    public void setMeaning(double meaning) {
        this.meaning = meaning;
    }

    @Override
    public String toString() {
        return (index+"\t"+meaning);
    }
}
