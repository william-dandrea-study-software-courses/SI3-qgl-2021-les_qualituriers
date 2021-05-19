package fr.unice.polytech.si3.qgl.qualituriers.utils;

import fr.unice.polytech.si3.qgl.qualituriers.Config;
import org.junit.jupiter.api.Test;

import static fr.unice.polytech.si3.qgl.qualituriers.utils.AngleUtil.modAngle;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author CLODONG Yann
 */
public class PointTest {

    @Test
    void additionTest() {
        assertEquals(new Point(4, 6), new Point(1, 2).add(new Point(3, 4)));
        assertEquals(new Point(5, 2), new Point(2, 1).add(new Point(3, 1)));
    }

    @Test
    void substractionTest() {
        assertEquals(new Point(1, 2), new Point(4, 6).subtract(new Point(3, 4)));
        assertEquals(new Point(-1, -5), new Point(4, 10).subtract(new Point(5, 15)));
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
        assertFalse(new Point(1, 0).isNormalTo(new Point(2 * Config.EPSILON, 1)));
        assertFalse(new Point(1, 0).isNormalTo(new Point(1, 1)));
        assertTrue(new Point(1, 1).isNormalTo(new Point(1, -1)));
    }

    @Test
    void isColinearTest() {
        assertTrue(new Point(1, 0).isCollinearTo(new Point(1, 0)));
        assertTrue(new Point(1, 0).isCollinearTo(new Point(1, Config.EPSILON * 0.9)));
        assertFalse(new Point(1, 0).isCollinearTo(new Point(0, 1)));
        assertFalse(new Point(1, 0).isCollinearTo(new Point(1, 1)));
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


    @Test
    void lengthWithoutSquare() {
        Point point = new Point(0, 0);
        Point point1 = new Point(5.3, 0);
        Point point2 = new Point(0, 5.2);
        Point point3 = new Point(5.3, 5.2);
        assertEquals(0, point.lengthWithoutSquare(), Config.EPSILON);
        assertEquals(28.09, point1.lengthWithoutSquare(), Config.EPSILON);
        assertEquals(27.04, point2.lengthWithoutSquare(), Config.EPSILON);
        assertEquals(55.13, point3.lengthWithoutSquare(), Config.EPSILON);
    }

    @Test
    public void rotate() {
        Point point = new Point(1, 0);
        Point point2 = new Point(2.5, 0);
        Point point3 = new Point(2, 2.5);
        Point rotate1 = point.rotate(Math.PI / 2);
        Point rotate2 = point.rotate(Math.PI / 6);
        Point rotate3 = point2.rotate(Math.PI / 6);
        Point rotate4 = point3.rotate(Math.PI / 2);
        assertEquals(0, rotate1.getX(), Config.EPSILON);
        assertEquals(1, rotate1.getY(), Config.EPSILON);
        assertEquals(Math.sqrt(3)/2, rotate2.getX(), Config.EPSILON);
        assertEquals(0.5, rotate2.getY(), Config.EPSILON);
        assertEquals((Math.sqrt(3)/2) * 2.5, rotate3.getX(), Config.EPSILON);
        assertEquals(0.5 * 2.5, rotate3.getY(), Config.EPSILON);
        assertEquals(-2.5, rotate4.getX(), Config.EPSILON);
        assertEquals(2.0, rotate4.getY(), Config.EPSILON);
    }

    @Test
    public void rotateOrigin() {
        Point origin = new Point(5, 10);
        Point point = new Point(6, 10);
        Point point2 = new Point(7.5, 10);
        Point rotate1 = point.rotate(Math.PI / 2, origin);
        Point rotate2 = point.rotate(Math.PI / 6, origin);
        Point rotate3 = point2.rotate(Math.PI / 6, origin);
        assertEquals(5, rotate1.getX(), Config.EPSILON);
        assertEquals(11, rotate1.getY(), Config.EPSILON);
        assertEquals(Math.sqrt(3)/2 + 5, rotate2.getX(), Config.EPSILON);
        assertEquals(10.5, rotate2.getY(), Config.EPSILON);
        assertEquals((Math.sqrt(3)/2) * 2.5 + 5, rotate3.getX(), Config.EPSILON);
        assertEquals(0.5 * 2.5 + 10, rotate3.getY(), Config.EPSILON);
    }

    @Test
    public void projection() {
        Point axe = new Point(1, 0);
        Point axe2 = new Point(0.5, 0.5);
        Point point = new Point(1, 0).rotate(Math.PI / 4);
        Point point2 = new Point(2.5, 0).rotate(Math.PI / 2);
        Point projection = point.projection(axe);
        Point projection2 = point2.projection(axe2);
        assertEquals(Math.sqrt(2)/2, projection.getX(), Config.EPSILON);
        assertEquals(0, projection.getY(), Config.EPSILON);
        assertEquals(0.5 * 2.5, projection2.getX(), Config.EPSILON);
        assertEquals(0.5 * 2.5, projection2.getY(), Config.EPSILON);
    }

    @Test
    public void distance() {
        Point point = new Point(1.5, 1.5);
        Point point1 = new Point(5, 5);
        assertEquals(4.94974746831, point.distance(point1), Config.EPSILON);
    }

    @Test
    public void distanceWithoutSquare() {
        Point point = new Point(1.5, 1.5);
        Point point1 = new Point(5, 5);
        assertEquals(24.5, point.distanceWithoutSquare(point1), Config.EPSILON);
    }

    @Test
    public void testEquals() {
        Point point = new Point(1.5, 1.5);
        Point point1 = new Point(1.5, 1.5);
        Point point2 = new Point(1.5, 2.5);
        Point point3 = new Point(2.5, 1.5);
        assertEquals(point, point1);
        assertNotEquals(point, point2);
        assertNotEquals(point, point3);
        assertNotEquals(point, null);
        assertNotEquals(point, "test");
    }

    @Test
    public void testHashcode() {
        Point point = new Point(1.5, 1.5);
        Point point1 = new Point(1.5, 1.5);
        Point point2 = new Point(1.5, 2.5);
        Point point3 = new Point(2.5, 1.5);
        assertEquals(point.hashCode(), point1.hashCode());
        assertNotEquals(point.hashCode(), point2.hashCode());
        assertNotEquals(point.hashCode(), point3.hashCode());
    }

}
