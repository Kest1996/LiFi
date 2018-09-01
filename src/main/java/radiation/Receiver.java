package radiation;

public class Receiver {
    //для приемника нужны чувствительность, скорость отклика и энергия
    protected double sensitivity;
    protected double responseRate;
    transient private double energy = 0;
    protected String name;

    public Receiver (String name,double sensitivity, double response_rate) {
        this.sensitivity = sensitivity;
        this.responseRate = response_rate;
        this.name = name;
    }

    public double getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(double sensitivity) {
        this.sensitivity = sensitivity;
    }

    public double getResponseRate() {
        return responseRate;
    }

    public void setResponseRate(double response_rate) {
        this.responseRate = responseRate;
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