package radiation;

public class Receiver {

    protected String sensitivity;
    transient private double energy = 0;
    protected String name;

    /**
     * Конструктор
     * @param name
     * @param sensitivity
     */

    public Receiver (String name,String sensitivity) {
        this.sensitivity = sensitivity;
        this.name = name;
    }

    public String getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(String sensitivity) {
        this.sensitivity = sensitivity;
    }

    public double getEnergy() {
        return energy;
    }

    public String getName() {
        return name;
    }

    public void addEnergy(double energy) {
        this.energy += energy;
    }

    @Override
    public String toString() {
        return name;
    }
}