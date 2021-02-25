package fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PositionableCircle extends PositionableShape<Circle> {

    private final Point[] points;

    public PositionableCircle(Circle shape, Transform transform) {
        super(shape, transform);
        this.points = new Point[] {transform.getPoint()};
    }

    @Override
    public Point[] getPoints() {
        return this.points;
    }

    @Override
    public Point[] project(Point axe) {
        Point projected = this.points[0].projection(axe);
        double radius = this.getShape().getRadius();
        Point normalized = axe.normalized();
        return new Point[] {
                new Point(
                        projected.getX() + normalized.getX() * radius,
                        projected.getY() + normalized.getY() * radius),
                new Point(
                        projected.getX() - normalized.getX() * radius,
                        projected.getY() - normalized.getY() * radius)
        };
    }

    @Override
    public void setTransform(Transform transform) {
        super.setTransform(transform);
        this.points[0] = transform.getPoint();
    }

    @Override
    public List<Point> axis(PositionableShape<? extends Shape> other) {
        List<Point> axis = new ArrayList<>();
        Point nearest = other.getPoints()[0];
        double distance = nearest.distanceWithoutSquare(this.points[0]);
        for (Point point : other.getPoints()) {
            if(point.distanceWithoutSquare(this.points[0]) < distance) {
                nearest = point;
                distance = point.distanceWithoutSquare(this.points[0]);
            }
        }
        axis.add(new Point(nearest.getX() - this.points[0].getX(), nearest.getY() - this.points[0].getY()).normalized());
        return axis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositionableCircle that = (PositionableCircle) o;
        return Arrays.equals(points, that.points);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(points);
    }
}
