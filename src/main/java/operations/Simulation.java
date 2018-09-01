package operations;

import radiation.Plane;
import radiation.Radiant;
import radiation.Receiver;

public class Simulation {

    //В симуляции плоскости измениемые объекты, их состояние нам и необходимо отслеживать в программе
    Plane<Radiant> radiantPlane;
    Plane<Receiver> receiverPlane;

    public Simulation createSimulation(){
        return new Simulation();
    }

    public Plane<Radiant> getRadiantPlane() {
        return radiantPlane;
    }

    public void setRadiantPlane(Plane<Radiant> radiantPlane) {
        this.radiantPlane = radiantPlane;
    }

    public Plane<Receiver> getReceiverPlane() {
        return receiverPlane;
    }

    public void setReceiverPlane(Plane<Receiver> receiverPlane) {
        this.receiverPlane = receiverPlane;
    }


}
