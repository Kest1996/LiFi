package gui;

public class ReceiverCoefData {
    private double r;
    private double phi;
    private double teta;
    private double k;
    public ReceiverCoefData(double r, double phi, double teta, double k) {
        this.r = r;
        this.phi = phi;
        this.teta = teta;
        this.k = k;
    }
    public double getK() {
        return k;
    }
    public double getPhi() {
        return phi;
    }
    public double getR() {
        return r;
    }
    public double getTeta() {
        return teta;
    }
}
