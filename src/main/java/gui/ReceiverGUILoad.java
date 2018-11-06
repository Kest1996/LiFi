package gui;

import radiation.Receiver;

public class ReceiverGUILoad extends Receiver {
    private int x;
    private int y;
    private int z;

    /**
     *
     * @param name
     * @param sensitivity
     * @param x
     * @param y
     * @param z
     */

    ReceiverGUILoad(String name,String sensitivity, int x, int y, int z) {
        super(name, sensitivity);
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
