package fr.unice.polytech.si3.qgl.qualituriers.utils;

import org.junit.jupiter.api.Test;

import static fr.unice.polytech.si3.qgl.qualituriers.utils.AngleUtil.modAngle;
import static org.junit.jupiter.api.Assertions.*;

public class PointTest {

    @Test
    void additionTest() {
        assertEquals(new Point(4, 6), new Point(1, 2).add(new Point(3, 4)));
        assertEquals(new Point(5, 2), new Point(2, 1).add(new Point(3, 1)));
    }

    @Test
    void substractionTest() {
        assertEquals(new Point(1, 2), new Point(4, 6).substract(new Point(3, 4)));
        assertEquals(new Point(-1, -5), new Point(4, 10).substract(new Point(5, 15)));
    }

    @Test
    void scalarTest( ) {
        assertEquals(24, new Point(3, 6).scalar(new Point(4, 2)));
        assertEquals(0, new Point(3, 6).scalar(new Point(4, -2)));

        assertEquals(new Point(2, 4), new Point(1, 2).scalar(2));
    }

    @Test
    void crossProductTest() {
        assertEquals(0, new Point(1, 2).cross(new Point(2, 4)));
        assertEquals(-2, new Point(1, 3).cross(new Point(2, 4)));
    }

    @Test
    void normTest() {
        assertEquals(Math.sqrt(2), new Point(1, 1).length());
        assertEquals(3, new Point(0, 3).length());
        assertEquals(Math.sqrt(10), new Point(1, 3).length());
    }

    @Test
    void normalizedTest() {
        assertEquals(new Point(1, 0), new Point(5, 0).normalized());
        assertEquals(new Point(0, 1), new Point(0, 6).normalized());
        assertEquals(new Point(0, 0), new Point(0, 0).normalized());
    }

    @Test
    void isNormalTest() {
        assertTrue(new Point(1, 0).isNormalTo(new Point(0, 1)));
        assertFalse(new Point(1, 0).isNormalTo(new Point(3 * Double.MIN_VALUE, 1)));
        assertFalse(new Point(1, 0).isNormalTo(new Point(1, 1)));
        assertTrue(new Point(1, 1).isNormalTo(new Point(1, -1)));
    }

    @Test
    void isColinearTest() {
        assertTrue(new Point(1, 0).isColinearTo(new Point(1, 0)));
        assertTrue(new Point(1, 0).isColinearTo(new Point(1, Double.MIN_VALUE)));
        assertFalse(new Point(1, 0).isColinearTo(new Point(0, 1)));
        assertFalse(new Point(1, 0).isColinearTo(new Point(1, 1)));
    }

    @Test
    void OrthogonalOrientationTest() {
        Point p = new Point(0, 1);
        assertEquals(Math.PI / 2, p.getOrientation());

        p = new Point(0, -1);
        assertEquals(-Math.PI / 2, p.getOrientation());
    }

    @Test
    void HalfOrthOrientationTest() {
        Point p = new Point(1, 1);
        assertEquals(Math.PI / 4, p.getOrientation());

        p = new Point(1, -1);
        assertEquals(-Math.PI / 4, p.getOrientation());
    }

    @Test
    void RandomAngleOrientationTest() {
        for(int i = 0; i < 10; i++) {
            double angle = Math.random() * 2 * Math.PI - Math.PI;
            Point dir = new Point(angle);
            assertEquals(Math.floor(angle * 1000000), Math.floor(dir.getOrientation() * 1000000)); // Take precision
        }
    }

    @Test
    void angleBetweenDirs() {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(1, -1);

        assertEquals(Math.PI / 2, p2.angleWith(p1));
    }

    @Test
    void randomAngleBetweenDirs() {
        for(int i = 0; i < 100; i++) {
            double angle = Math.random() * Math.PI;
            double angle2 = -Math.random() * Math.PI;

            Point dir = new Point(angle);
            Point dir2 = new Point(angle2);
            assertEquals(Math.floor(modAngle(-angle + angle2) * 1000000), Math.floor(dir.angleWith(dir2) * 1000000)); // Take precision
        }
    }
}
