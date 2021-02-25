package fr.unice.polytech.si3.qgl.qualituriers.utils.shape;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableCircle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionablePolygon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PositionablePolygonTest {

    PositionablePolygon polygon;
    Transform transform;
    double orientation;
    private PositionableCircle circle;

    @BeforeEach
    public void init() {
        this.orientation = Math.PI / 2;
        this.transform = new Transform(new Point(1, 2), orientation);
        this.polygon = new PositionablePolygon(new Rectangle(6, 2, 0), transform);
        this.circle = new PositionableCircle(new Circle(10), new Transform(new Point(5, 9), Math.PI/4));
    }

    @Test
    public void construct() {
        Point[] points = new Point[] {
                new Point(-2, 3).rotate(orientation, transform),
                new Point(4, 3).rotate(orientation, transform),
                new Point(4, 1).rotate(orientation, transform),
                new Point(-2, 1).rotate(orientation, transform)
        };
        assertArrayEquals(points, this.polygon.getPoints());
    }

    @Test
    public void project() {
        Point axis = new Point(1, 0);
        Point[] points = new Point[] {
                new Point(-2, 3).rotate(orientation, transform).projection(axis),
                new Point(4, 3).rotate(orientation, transform).projection(axis),
                new Point(4, 1).rotate(orientation, transform).projection(axis),
                new Point(-2, 1).rotate(orientation, transform).projection(axis)
        };
        assertArrayEquals(points, this.polygon.project(axis));
    }

    @Test
    public void axis() {
        List<Point> points = Arrays.asList(
                new Point(0, -6).normalized(),
                new Point(-2, 0).normalized(),
                new Point(0, 6).normalized(),
                new Point(2, 0).normalized()
        );
        assertTrue(points.containsAll(this.polygon.axis(this.circle)));
    }

}
