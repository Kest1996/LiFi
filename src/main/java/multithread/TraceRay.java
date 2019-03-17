package multithread;

import model.GuiModel;
import model.RadiantModel;
import radiation.DiagramPoint;

import java.util.ArrayList;

public class TraceRay {
    private void traceRay(RadiantModel radiant, double phi, DiagramPoint tetaP, double maxDistance, ArrayList<GuiModel> objs, double dphi) {
        double currentX = radiant.getX();
        double currentY = radiant.getY();
        double currentZ = radiant.getZ();
        double teta = tetaP.getIndex();
        for (double r = 0; r<=maxDistance; r++) {
            currentX = currentX + Math.sin(teta-Math.PI/2)*Math.cos(phi);
            currentY = currentY + Math.sin(teta-Math.PI/2)*Math.sin(phi);
            currentZ = currentZ + Math.cos(teta-Math.PI/2);
            for (int i=0; i<objs.size(); i++) {
                if (checkIntersection(currentX, currentY, currentZ, objs.get(i))) {
                    if (i>=Radiants.size()) {
                        Receivers.get(i-Radiants.size()).addEnergy(tetaP.getMeaning()/dphi);
                        return;
                    }
                }
            }
        }
    }
}
