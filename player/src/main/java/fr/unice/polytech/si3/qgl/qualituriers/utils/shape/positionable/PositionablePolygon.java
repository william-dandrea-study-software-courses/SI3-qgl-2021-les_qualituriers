package fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.PolygonAbstract;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PositionablePolygon extends PositionableShape<PolygonAbstract> {

    private final Point[] points;

    public PositionablePolygon(PolygonAbstract shape, Transform transform) {
        super(shape, transform);
        this.points = new Point[shape.getSegments().size()];
        this.setTransform(transform);
        //System.arraycopy(shape.getVertices(), 0, this.points, 0, shape.getVertices().length);

    }

    /*private Point calculCenter(Point[] points) {
        double A = 0;
        double x = 0;
        double y = 0;
        for (int i = 0; i < points.length - 1; i++) {
            Point point = this.points[i];
            Point nextPoint = this.points[i + 1];
            A += point.getX() * nextPoint.getY() - nextPoint.getX() * point.getY();
            x += (point.getX() + nextPoint.getX()) * (point.getX() * nextPoint.getY() - nextPoint.getX() * point.getY());
            y += (point.getY() + nextPoint.getY()) * (point.getX() * nextPoint.getY() - nextPoint.getX() * point.getY());
        }
        A *= 0.5D;
        x *= 1 / (6 * A);
        y *= 1 / (6 * A);
        return new Point(x, y);
    }*/

    @Override
    public Point[] getPoints() {
        return this.points;
    }

    @Override
    public Point[] project(Point axe) {
        Point[] pointsProjected = new Point[this.points.length];
        for (int i = 0; i < this.points.length; i++)
            pointsProjected[i] = this.points[i].projection(axe);
        return pointsProjected;
    }

    @Override
    public List<Point> axis(PositionableShape<? extends Shape> other) {
        List<Point> axis = new ArrayList<>();
        int length = this.points.length;
        for (int i = 0; i < length; i++) {
            Point pointOne = this.points[i % length];
            Point pointTwo = this.points[(i + 1) % length];
            axis.add(new Point(pointOne.getX() - pointTwo.getX(), pointOne.getY() - pointTwo.getY()).normalized());
        }
        return axis;
    }

    @Override
    public void setTransform(Transform transform) {
        super.setTransform(transform);
        for (int i = 0; i < this.points.length; i++)
            this.points[i] = this.getShape().getVertices()[i]
                    .add(transform.getPoint())
                    .rotate(transform.getOrientation(), transform.getPoint());
        /*Point center = transform.getPoint();*//*this.calculCenter(this.points);*//*
        for (int i = 0; i < this.points.length; i++)
            this.points[i] = this.points[i].rotate(transform.getOrientation(), center);*/
        /*Point center = transform.getPoint();
        for (int i = 0; i < this.points.length; i++) {
            this.points[i] = this.points[i]
                    .add(transform.getPoint())
                    .rotate(transform.getOrientation(), center);
        }*/
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositionablePolygon that = (PositionablePolygon) o;
        return Arrays.equals(points, that.points);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(points);
    }
}
