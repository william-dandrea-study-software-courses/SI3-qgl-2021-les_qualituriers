package fr.unice.polytech.si3.qgl.qualituriers.utils;

import org.junit.jupiter.api.Test;

import static fr.unice.polytech.si3.qgl.qualituriers.utils.AngleUtil.modAngle;
import static org.junit.jupiter.api.Assertions.*;

public class PointTest {

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
