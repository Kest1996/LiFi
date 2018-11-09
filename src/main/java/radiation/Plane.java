package radiation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Plane<T> {

    private HashMap<String, Coordinate<T>> coordinates = new HashMap<>();

    private int sizeX;
    private int sizeY;

    public Plane(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    /**
     * Запись в плоскость источника/приемника
     * @param x
     * @param y
     * @param object
     */

    public void setObject(int x, int y, T object) {
        coordinates.put("" + x + "_" + y, new Coordinate(x, y, object));
    }

    /**
     * Получение из массива источника/приемника
     * @param x
     * @param y
     * @return
     */

    public T getObject (int x, int y){
        return coordinates.get("" + x + "_" + y).getObject();
    }

    /**
     * Получение множества координат
     * @return
     */

    public Set<String> getCoordinates() {
        return coordinates.keySet();
    }
}
