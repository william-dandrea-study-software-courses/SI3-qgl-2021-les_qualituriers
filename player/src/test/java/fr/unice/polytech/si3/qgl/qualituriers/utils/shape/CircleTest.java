package fr.unice.polytech.si3.qgl.qualituriers.utils.shape;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CircleTest {

    Circle circle;

    @BeforeEach
    public void init() {
        this.circle = new Circle(20);
    }

    @Test
    public void testIsIn() {
        assertTrue(this.circle.isIn(new Point(10, 10)));
        assertTrue(this.circle.isIn(new Point(20, 0)));
        assertTrue(this.circle.isIn(new Point(0, 20)));
        assertFalse(this.circle.isIn(new Point(20, 20)));
        assertFalse(this.circle.isIn(new Point(20.01, 0)));
        assertFalse(this.circle.isIn(new Point(0, 20.01)));
    }

    @Test
    public void testEquals() {
        Circle circle1 = new Circle(20);
        Circle circle2 = new Circle(19);
        assertEquals(circle1, this.circle);
        assertNotEquals(circle2, this.circle);
        assertNotEquals(circle, null);
        assertNotEquals(circle, "test");
    }

    @Test
    public void testHashcode() {
        Circle circle1 = new Circle(20);
        Circle circle2 = new Circle(19);
        assertEquals(circle1.hashCode(), this.circle.hashCode());
        assertNotEquals(circle2.hashCode(), this.circle.hashCode());
    }

}
