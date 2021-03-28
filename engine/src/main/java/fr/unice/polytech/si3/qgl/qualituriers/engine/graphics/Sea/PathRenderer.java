package fr.unice.polytech.si3.qgl.qualituriers.engine.graphics.Sea;

import fr.unice.polytech.si3.qgl.qualituriers.engine.graphics.Arc;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;

import java.util.ArrayList;
import java.util.List;

public class PathRenderer {

    private final MyCanvas canvas;
    private final List<Arc> waypoints = new ArrayList<>();
    private Point prevPosition;

    public PathRenderer(MyCanvas canvas) {
        this.canvas = canvas;
    }

    public void addWaypoint(Point point, Point midPoint) {
        if(this.prevPosition == null) {
            this.prevPosition = point;
            return;
        }
        this.waypoints.add(new Arc(this.prevPosition, midPoint, point));
        this.prevPosition = point;
    }

    public void draw() {
        if(this.waypoints.size() < 1) return;

        for (Arc waypoint : this.waypoints)
            canvas.drawArc(waypoint);
    }
/*
    public Rectangle2D.Double getBounds() {
        if(waypoint.size() == 0)
            return null;

        var minX = waypoint.stream().mapToDouble(Point::getX).min().getAsDouble();
        var maxX = waypoint.stream().mapToDouble(Point::getX).max().getAsDouble();
        var minY = waypoint.stream().mapToDouble(Point::getY).min().getAsDouble();
        var maxY = waypoint.stream().mapToDouble(Point::getY).max().getAsDouble();

        return new Rectangle2D.Double(minX, minY, maxX - minX, maxY - minY);
    }*/

}
