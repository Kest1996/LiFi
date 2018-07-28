/*package gui;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Panel {
    private int xstart;
    private int ystart;
    private int w;
    private int h;
    Panel (int xs , int ys) {
        xstart = xs;
        ystart = ys;
        w = 500;
        h = 400;
    }
    Panel (int xs , int ys, int ws, int hs) {
        xstart = xs;
        ystart = ys;
        w = ws;
        h = hs;
    }
    void addIstochnik(AnchorPane node, String img, int type, int tochn, double x, double y) {
        if (x==-1) x = w/2;
        if (x==-2) x = w;
        if (y==-1) y = h/2;
        if (y==-2) x = h;
        Istochnik id1 = new Istochnik (img, type, tochn, x, y);
        mainGUI.Obstacles.add(id1);
        node.setLeftAnchor((id1.imgv), (xstart+x));
        node.setTopAnchor((id1.imgv), (ystart+y));
        node.getChildren().addAll(id1.imgv);
    }
    void addDiod(AnchorPane node, String img, int type, double x, double y) {
        if (x==-1) x = w/2;
        if (x==-2) x = w;
        if (y==-1) y = h/2;
        if (y==-2) x = h;
        Diod id1 = new Diod (img, type, x, y);
        mainGUI.Obstacles.add(id1);
        node.setLeftAnchor((id1.imgv), xstart+x);
        node.setTopAnchor((id1.imgv), ystart+y);
        node.getChildren().addAll(id1.imgv);
    }
    Priemnik addPriemnik(AnchorPane node, int c, double x, double y, int tochn) {
        if (x==-1) x = w/2;
        if (x==-2) x = w;
        if (y==-1) y = h/2;
        if (y==-2) y = h;
        Priemnik id1 = new Priemnik(c, x, y, tochn);
        node.setLeftAnchor((id1.imgv), (xstart+x));
        node.setTopAnchor((id1.imgv), (ystart+y));
        node.getChildren().addAll(id1.imgv);
        return id1;
    }
    void addWall(AnchorPane node, double x1,double y1,double z1, double l1, double w1, double h1) {
        Wall id1 = new Wall( x1, y1, z1,  l1,  w1,  h1);
        mainGUI.Obstacles.add(id1);
        id1.imgv.setFitHeight(h1);
        id1.imgv.setFitWidth(w1);
        node.setLeftAnchor((id1.imgv), (xstart+x1));
        node.setTopAnchor((id1.imgv), (ystart+y1));
        node.getChildren().addAll(id1.imgv);
    }
    void setPomeshenie(AnchorPane node, double w, double l, double h) {
        double wwall = 10;
        addWall(node, 0,0, 0,  l , wwall, h);
        addWall(node, w,0, 0,  l , wwall, h);
        addWall(node, 0,0, 0,  l , w, wwall); //потолок
        addWall(node, 0,h, 0,  l , w+wwall, wwall); //пол
    }
    void addLuch(AnchorPane node, double x1, double y1, double x2, double y2){
        Line luch = new Line(xstart+x1, ystart+y1,xstart+x2,ystart+y2);
        luch.setStrokeWidth(0.5);
        luch.setStroke(Color.RED);
        node.getChildren().addAll(luch);
    }
}*/