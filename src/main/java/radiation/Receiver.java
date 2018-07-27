package radiation;

public class Receiver {
    //для приемника нужны чувствительность, скорость отклика и энергия
    private double sensitivity;
    private double responseRate;
    private double energy = 0;

    public Receiver (double sensitivity, double response_rate) {
        this.sensitivity = sensitivity;
        this.responseRate = response_rate;
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

}