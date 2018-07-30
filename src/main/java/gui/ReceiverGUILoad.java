package gui;

import radiation.Receiver;

public class ReceiverGUILoad extends Receiver {
    private int x;
    private int y;
    private int z;
    ReceiverGUILoad(String name,double sensitivity, double response_rate, int x, int y, int z) {
        super(name, sensitivity, response_rate);
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getZ() {
        return z;
    }
}
