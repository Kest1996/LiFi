package gui;

import radiation.Radiant;

public class RadiantGUILoad extends Radiant {
    private int x;
    private int y;
    private int z;

    /**
     *
     * @param radiant
     * @param x
     * @param y
     * @param z
     */

    RadiantGUILoad(Radiant radiant, int x, int y, int z) {
        super(radiant.getFe(),radiant.getName(),radiant.getSpectrum());
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
