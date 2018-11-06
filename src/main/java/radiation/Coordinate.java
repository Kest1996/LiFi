package radiation;

public class Coordinate <T> {

    //координаты источника/приемника
    private int x;
    private int y;

    //излучение в ячейке
    private double i = 0;

    //источник/приемник
    private T object;


    /**
     * Конструктор
     * @param x
     * @param y
     * @param object
     */

    public Coordinate(int x, int y, T object) {
        this.x = x;
        this.y = y;
        this.object = object;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    /**
     * Получение из объекта источника/приемника
     * @return
     */

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    /**
     * добавление интенсивности к итоговой светимости ячейки
     * @param i
     */

    public void addI(double i) {
        this.i += i;
    }

    /**
     * Получение светимости ячейки
     * @return
     */

    public double getI() {
        return i;
    }
}
