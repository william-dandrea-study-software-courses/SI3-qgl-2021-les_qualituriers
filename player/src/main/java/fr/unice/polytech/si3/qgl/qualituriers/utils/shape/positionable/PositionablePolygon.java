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
    }

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
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PositionablePolygon that = (PositionablePolygon) o;
        return Arrays.equals(points, that.points);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Arrays.hashCode(points);
        return result;
    }

    @Override
    public PositionablePolygon getCircumscribedPolygon() {
        return this;
    }

    public PositionablePolygon scaleFromCenter(double scale) {
        return new PositionablePolygon(getShape().scaleFromCenter(scale), getTransform());
    }

    /*public PositionablePolygon enlargeOf(int length) {
        return new PositionablePolygon(getShape().enlargeOf(length), getTransform());
    }*/
}
