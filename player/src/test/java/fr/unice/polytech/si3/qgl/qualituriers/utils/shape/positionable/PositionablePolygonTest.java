package fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableCircle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionablePolygon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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

    @Disabled
    @Test
    public void construct() {
        Point[] points = new Point[] {
                new Point(-2, 3).rotate(orientation, transform),
                new Point(4, 3).rotate(orientation, transform),
                new Point(4, 1).rotate(orientation, transform),
                new Point(-2, 1).rotate(orientation, transform)
        };
        //assertArrayEquals(points, this.polygon.getPoints());
    }

    @Disabled
    @Test
    public void project() {
        Point axis = new Point(1, 0);
        Point[] points = new Point[] {
                new Point(-2, 3).rotate(orientation, transform).projection(axis),
                new Point(4, 3).rotate(orientation, transform).projection(axis),
                new Point(4, 1).rotate(orientation, transform).projection(axis),
                new Point(-2, 1).rotate(orientation, transform).projection(axis)
        };
       // assertArrayEquals(points, this.polygon.project(axis));
    }

    @Test
    public void axis() {
        List<Point> points = Arrays.asList(
                new Point(0, -6).normalized(),
                new Point(-2, 0).normalized(),
                new Point(0, 6).normalized(),
                new Point(2, 0).normalized()
        );
        //assertEquals(this.polygon.axis(this.circle), points);
    }

    @Disabled
    @Test
    public void setTransform() {
        double angle = Math.PI / 4;
        Transform transform = new Transform(2, 1, angle);
        Point[] points = new Point[] {new Point(-1, 2).rotate(angle, transform), new Point(5, 2).rotate(angle, transform),
                new Point(5, 0).rotate(angle, transform), new Point(-1, 0).rotate(angle, transform)};
        this.polygon.setTransform(transform);
        assertEquals(transform, this.polygon.getTransform());
       // assertArrayEquals(points, this.polygon.getPoints());
    }

    @Test
    public void testEquals() {
        PositionablePolygon polygon1 = new PositionablePolygon(new Rectangle(6, 2, 0), transform);
        PositionablePolygon polygon2 = new PositionablePolygon(new Rectangle(6, 2, 0), new Transform(-1, 6 , 0));
        PositionablePolygon polygon3 = new PositionablePolygon(new Rectangle(6, 2, Math.PI), transform);
        assertEquals(polygon1, this.polygon);
        assertNotEquals(polygon2, this.polygon);
        assertNotEquals(polygon3, this.polygon);
        assertNotEquals(polygon, null);
        assertNotEquals(polygon, "test");
    }

    @Test
    public void testHashcode() {
        PositionablePolygon polygon1 = new PositionablePolygon(new Rectangle(6, 2, 0), transform);
        PositionablePolygon polygon2 = new PositionablePolygon(new Rectangle(6, 2, 0), new Transform(-1, 6 , 0));
        PositionablePolygon polygon3 = new PositionablePolygon(new Rectangle(6, 2, Math.PI), transform);
        assertEquals(polygon1.hashCode(), this.polygon.hashCode());
        assertNotEquals(polygon2.hashCode(), this.polygon.hashCode());
        assertNotEquals(polygon3.hashCode(), this.polygon.hashCode());
    }

}
