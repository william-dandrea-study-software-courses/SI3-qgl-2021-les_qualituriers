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
    public void testGetter() {
        assertEquals(10, this.orthoRect.getWidth());
        assertEquals(20, this.orthoRect.getHeight());
        assertEquals(0, this.orthoRect.getOrientation());
        assertEquals(Math.PI / 4, this.orientedRect.getOrientation());
        assertEquals(Math.PI, this.reversedRect.getOrientation());
    }

    @Test
    void isInTest() {
        assertFalse(orthoRect.isIn(new Point(11, 0)));
        assertTrue(orthoRect.isIn(new Point(10, 0)));
        assertTrue(orthoRect.isIn(new Point(0, 0)));
        assertTrue(orthoRect.isIn(new Point(0, 20)));
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

    @Test
    public void testEquals() {
        Rectangle orientedRect1 = new Rectangle(10, 20, Math.PI / 4);
        Rectangle orientedRect2 = new Rectangle(10, 20, Math.PI / 2);
        Rectangle orientedRect3 = new Rectangle(10, 10, Math.PI / 4);
        Rectangle orientedRect4 = new Rectangle(20, 20, Math.PI / 4);
        assertEquals(orientedRect1, this.orientedRect);
        assertNotEquals(orientedRect2, this.orientedRect);
        assertNotEquals(orientedRect3, this.orientedRect);
        assertNotEquals(orientedRect4, this.orientedRect);
        assertNotEquals(orientedRect, null);
        assertNotEquals(orientedRect, "test");
    }

    @Test
    public void testHashcode() {
        Rectangle orientedRect1 = new Rectangle(10, 20, Math.PI / 4);
        Rectangle orientedRect2 = new Rectangle(10, 20, Math.PI / 2);
        Rectangle orientedRect3 = new Rectangle(10, 10, Math.PI / 4);
        Rectangle orientedRect4 = new Rectangle(20, 20, Math.PI / 4);
        assertEquals(orientedRect1.hashCode(), this.orientedRect.hashCode());
        assertNotEquals(orientedRect2.hashCode(), this.orientedRect.hashCode());
        assertNotEquals(orientedRect3.hashCode(), this.orientedRect.hashCode());
        assertNotEquals(orientedRect4.hashCode(), this.orientedRect.hashCode());
    }

}
