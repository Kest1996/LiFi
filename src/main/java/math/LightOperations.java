package math;

import radiation.Plane;
import radiation.Radiant;
import radiation.Receiver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class LightOperations {

    public static void receptionOfRadiation(Plane<Radiant> radiantPlane, Plane<Receiver> receiverPlane){

        //Получение множества координат излучателей
        Set<String> radiantsCoordinates = radiantPlane.getCoordinates();
        Iterator<String> radiantsIterator = radiantsCoordinates.iterator();

        //Получение множества координат приемников
        Set<String> receivers = receiverPlane.getCoordinates();
        Iterator<String> receiversIterator = receivers.iterator();


        while (receiversIterator.hasNext()){
            while (radiantsIterator.hasNext()){
                //Проброска исключения
                //Добавить в функционал получения объекта по стринге координат
                new Throwable();
                receiverPlane.getObject(0, 0).addEnergy(radiantPlane.getObject(0, 0).getFe());
            }
        }
    }

}
