package fr.unice.polytech.si3.qgl.qualituriers.engine.graphics;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;

public class Arc {

    private final Point point1;
    private final Point point2;
    private final Point point3;

    public Arc(Point point1, Point point2, Point point3) {
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
    }

    public Point getPoint1() {
        return point1;
    }

    public Point getPoint2() {
        return point2;
    }

    public Point getPoint3() {
        return point3;
    }

}
