package fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Polygon;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableCircle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionablePolygon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PositionableCircleTest {

    PositionableCircle circle;

    @BeforeEach
    public void init() {
        this.circle = new PositionableCircle(new Circle(10), new Transform(new Point(5, 9), Math.PI/4));
    }

    @Test
    public void pointIsCenter() {
        assertArrayEquals(new Point[]{new Point(5, 9)}, this.circle.getPoints());
    }

    @Test
    public void project() {
        Point axis = new Point(1, 0);
        Point axis1 = new Point(0, 1);
        Point[] projection = {new Point(15, 0), new Point(-5, 0)};
        Point[] projection1 = {new Point(0, 19), new Point(0, -1)};
        assertArrayEquals(projection, this.circle.project(axis));
        assertArrayEquals(projection1, this.circle.project(axis1));
    }

    @Test
    public void changeTransform() {
        Transform transform = new Transform(new Point(9, 5), Math.PI / 2);
        this.circle.setTransform(transform);
        assertEquals(transform, this.circle.getTransform());
        assertArrayEquals(new Point[]{transform.getPoint()}, this.circle.getPoints());
    }

    @Test
    public void axisCircle() {
        PositionableCircle other = new PositionableCircle(new Circle(5), new Transform(new Point(-2, 4), 0));
        Point axis = new Point(-7, -5).normalized();
        List<Point> axes = Collections.singletonList(axis);
        assertEquals(axes, this.circle.axis(other));
    }

    @Test
    public void axisPolygon() {
        PositionablePolygon other = new PositionablePolygon(new Polygon(0,
                new Point[] {new Point(0, 1), new Point(1.75, -0.5), new Point(1.2, -3), new Point(-1.2, -3), new Point(-1.75, -0.5)}),
                new Transform(new Point(0, 0), 0));
        PositionablePolygon other1 = new PositionablePolygon(new Polygon(0, new Point[]{new Point(0, 0), new Point(10, 0)}),
                new Transform(0, 0, 0));
        Point axis = new Point(-5, -8).normalized();
        Point axis1 = new Point(-5, -9).normalized();
        List<Point> axes = Collections.singletonList(axis);
        List<Point> axes1 = Collections.singletonList(axis1);
        //assertEquals(axes, this.circle.axis(other));
        //assertEquals(axes1, this.circle.axis(other1));
    }

    @Test
    public void testEquals() {
        PositionableCircle circle1 = new PositionableCircle(new Circle(10), new Transform(new Point(5, 9), Math.PI/4));
        PositionableCircle circle2 = new PositionableCircle(new Circle(10), new Transform(new Point(5, 9), Math.PI/2));
        PositionableCircle circle3 = new PositionableCircle(new Circle(10), new Transform(new Point(6, 9), Math.PI/4));
        PositionableCircle circle4 = new PositionableCircle(new Circle(9), new Transform(new Point(5, 9), Math.PI/4));
        assertEquals(circle1, this.circle);
        assertNotEquals(circle2, this.circle);
        assertNotEquals(circle3, this.circle);
        assertNotEquals(circle4, this.circle);
        assertNotEquals(circle, null);
        assertNotEquals(circle, "test");
    }

    @Test
    public void testHashcode() {
        PositionableCircle circle1 = new PositionableCircle(new Circle(10), new Transform(new Point(5, 9), Math.PI/4));
        PositionableCircle circle2 = new PositionableCircle(new Circle(10), new Transform(new Point(5, 9), Math.PI/2));
        PositionableCircle circle3 = new PositionableCircle(new Circle(10), new Transform(new Point(6, 9), Math.PI/4));
        PositionableCircle circle4 = new PositionableCircle(new Circle(9), new Transform(new Point(5, 9), Math.PI/4));
        assertEquals(circle1.hashCode(), this.circle.hashCode());
        assertNotEquals(circle2.hashCode(), this.circle.hashCode());
        assertNotEquals(circle3.hashCode(), this.circle.hashCode());
        assertNotEquals(circle4.hashCode(), this.circle.hashCode());
    }

}
