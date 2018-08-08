package model;

import java.util.Comparator;

public class GuiModel {
    protected int x;
    protected int y;
    protected int z;
    protected transient int sizeX;
    protected transient int sizeY;
    protected transient int sizeZ;
    protected transient String img;
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int getSizeZ() {
        return sizeZ;
    }

    public String getImg(){
        return img;
    }

    //Для сортировки для вывода
    public static final Comparator<GuiModel> COMPARE_BY_X = new Comparator<GuiModel>() {
        public int compare(GuiModel lhs, GuiModel rhs) {
            return lhs.getX() - rhs.getX();
        }
    };
    public static final Comparator<GuiModel> COMPARE_BY_Y = new Comparator<GuiModel>() {
        public int compare(GuiModel gm1, GuiModel gm2) {
            return gm1.getY() - gm2.getY();
        }
    };
}
