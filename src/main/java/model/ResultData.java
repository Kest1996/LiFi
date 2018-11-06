package model;

public class ResultData {

    private double L;
    private double SNR;
    private double modulation;
    private String modulationName;
    private double speed;

    /**
     * Конструктор
     * @param L
     * @param Ip
     */

    public ResultData(double L, double Ip) {
        this.L = L;
        this.SNR = ReceiverModel.countSNR(Ip);
        this.modulation = countModulation(this.SNR);
        this.modulationName = countModulationName(this.modulation);
        this.speed = countSpeed(this.modulation);
    }

    private double log(double a, double b) {
        return Math.log(a)/Math.log(b);
    }

    private double countModulation(double x) {
        return Math.floor(log(1+x,2));
    }

    private String countModulationName(double x){
        if (x<1) {
            return "NO";
        }
        if ((x<2) && (x>=1)) {
            return "BPSK";
        }
        if ((x<3) && (x>=2)) {
            return "QPSK";
        }
        return (((int)Math.pow(2,x))+"-QAM");
    }

    /**
     * Расчет скорости передачи данных
     * @param x
     * @return
     */

    private double countSpeed(double x) {
        double cr; //coding rate
        if (x<2) {
            cr = 1.0/2.0;
        }
        else {
            cr = 3.0/4.0;
        }
        double Tb = (11.0+1/9)*Math.pow(10,-6); //полезное время символа
        double Tg = Tb/16.0; //время префикса
        double Ts = Tb+Tg;
        double Nused = 200.0;
        double c = (Nused*x*cr)/(Ts*1024*1024);
        return (Math.round(c*100)/100.0);
    }

    public double getL() {
        return L;
    }

    public double getModulation() {
        return modulation;
    }

    public double getSNR() {
        return SNR;
    }

    public double getSpeed() {
        return speed;
    }

    public String getModulationName() {
        return modulationName;
    }
}
