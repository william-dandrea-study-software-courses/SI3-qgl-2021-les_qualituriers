package fr.unice.polytech.si3.qgl.qualituriers.engine.graphics.Sea;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
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

    public Rectangle2D.Double getBounds() {
        if(waypoint.size() == 0)
            return null;

        var minX = waypoint.stream().mapToDouble(Point::getX).min().getAsDouble();
        var maxX = waypoint.stream().mapToDouble(Point::getX).max().getAsDouble();
        var minY = waypoint.stream().mapToDouble(Point::getY).min().getAsDouble();
        var maxY = waypoint.stream().mapToDouble(Point::getY).max().getAsDouble();

        return new Rectangle2D.Double(minX, minY, maxX - minX, maxY - minY);
    }

}
