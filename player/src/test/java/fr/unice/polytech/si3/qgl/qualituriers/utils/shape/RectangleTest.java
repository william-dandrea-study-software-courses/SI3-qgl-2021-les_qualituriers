package fr.unice.polytech.si3.qgl.qualituriers.utils.shape;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RectangleTest {
    Rectangle orthoRect;
    Rectangle orientedRect;
    Rectangle reversedRect;

    @BeforeEach
    void init() {
        orthoRect = new Rectangle(10, 20, 0);
        orientedRect = new Rectangle(10, 20, Math.PI / 4);
        reversedRect = new Rectangle(10, 20, Math.PI);
    }

    @Test
    void isInTest() {
        assertFalse(orthoRect.isIn(new Point(11, 0)));
        assertTrue(orthoRect.isIn(new Point(10, 0)));
        assertTrue(orthoRect.isIn(new Point(0, 0)));
        assertFalse(orthoRect.isIn(new Point(-1, 0)));

        assertFalse(orientedRect.isIn(new Point(10, 0)));
        assertTrue(orientedRect.isIn(new Point(0, 1)));

        assertTrue(reversedRect.isIn(new Point(-1, 0)));
        assertFalse(reversedRect.isIn(new Point(1, 0)));
    }

    @Test
    void pointsTest() {
        Point point1 = new Point(-5, 10);
        Point point2 = new Point(5, 10);
        Point point3 = new Point(5, -10);
        Point point4 = new Point(-5, -10);
        assertArrayEquals(new Point[] {point1, point2, point3, point4}, this.orthoRect.getVertices());
    }

    @Test
    void pointsOrientedTest() {
        Point point1 = new Point(-5, 10).rotate(Math.PI / 4);
        Point point2 = new Point(5, 10).rotate(Math.PI / 4);
        Point point3 = new Point(5, -10).rotate(Math.PI / 4);
        Point point4 = new Point(-5, -10).rotate(Math.PI / 4);
        assertArrayEquals(new Point[] {point1, point2, point3, point4}, this.orientedRect.getVertices());
    }

    @Test
    void pointsReversedTest() {
        Point point1 = new Point(-5, 10).rotate(Math.PI);
        Point point2 = new Point(5, 10).rotate(Math.PI);
        Point point3 = new Point(5, -10).rotate(Math.PI);
        Point point4 = new Point(-5, -10).rotate(Math.PI);
        /*Point point1 = new Point(5, -10);
        Point point2 = new Point(-5, -10);
        Point point3 = new Point(-5, 10);
        Point point4 = new Point(5, 10);*/
        assertArrayEquals(new Point[] {point1, point2, point3, point4}, this.reversedRect.getVertices());
    }
}
