package fr.unice.polytech.si3.qgl.qualituriers.utils.shape;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
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
        Point[] projection = {new Point(-5, 0), new Point(15, 0)};
        assertTrue(Arrays.asList(projection).containsAll(Arrays.asList(this.circle.project(axis))));
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
        Point axis = new Point(-5, -8).normalized();
        List<Point> axes = Collections.singletonList(axis);
        assertEquals(axes, this.circle.axis(other));
    }

}
