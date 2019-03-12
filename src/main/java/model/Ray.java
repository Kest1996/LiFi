package model;

public class Ray {
    private double startX;
    private double startY;
    private double startZ;
    private double phi;
    private double teta;
    private double energyP;
    public Ray(RadiantModel radiantModel, double phi, double teta, double dphi) {
        this.startX = radiantModel.getX();
        this.startY = radiantModel.getY();
        this.startZ = radiantModel.getZ();
        this.phi = phi;
        this.teta = teta;
        this.energyP = radiantModel.getFe()*radiantModel.getDirectivityData().getByAngle(teta).getMeaning()/dphi;
    }
}
