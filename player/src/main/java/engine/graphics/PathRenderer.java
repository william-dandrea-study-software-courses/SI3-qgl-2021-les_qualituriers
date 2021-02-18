package engine.graphics;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PathRenderer {

    private final MyCanvas canvas;
    private final List<Point> waypoint = new ArrayList<>();

    public PathRenderer(MyCanvas canvas) {
        this.canvas = canvas;
    }

    public void addWaypoint(Point point) {
        waypoint.add(point);
    }

    public void draw() {
        if(waypoint.size() < 1) return;

        for(int i = 1; i < waypoint.size(); i++) {
            canvas.drawLine(waypoint.get(i - 1), waypoint.get(i), new Color(96, 96, 255));
        }
    }

}
