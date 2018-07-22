package radiation;

public class Radiant {

    //интенсивность (вначале примем ее за постоянную величину во всех направлениях)
    private double iMax;

    private String name;

    private double i;

    public Radiant(double iMax, String name) {
        this.iMax = iMax;
        this.name = name;
    }

    public double getiMax() {
        return iMax;
    }

    public void setiMax(double iMax) {
        this.iMax = iMax;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //Потоковое изменение свечения светодиода
    public void luminescence(double i) {
        this.i = iMax * i;
    }

    //получение свечения светодиода
    public double getI() {
        return i;
    }
}
