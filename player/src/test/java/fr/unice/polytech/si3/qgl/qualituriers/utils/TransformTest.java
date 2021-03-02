package fr.unice.polytech.si3.qgl.qualituriers.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TransformTest {

    Transform transform;

    @BeforeEach
    public void init() {
        this.transform = new Transform(10, 0, Math.PI / 4);
    }

    @Test
    public void testVector() {
        assertEquals(new Point(Math.sqrt(2)/2, Math.sqrt(2)/2), this.transform.right());
        assertEquals(new Point(-Math.sqrt(2)/2, Math.sqrt(2)/2), this.transform.up());
        assertEquals(new Point(-Math.sqrt(2)/2, -Math.sqrt(2)/2), this.transform.left());
        assertEquals(new Point(Math.sqrt(2)/2, -Math.sqrt(2)/2), this.transform.down());
        assertEquals(new Point(Math.sqrt(2)/2, Math.sqrt(2)/2), this.transform.direction());
        assertEquals(new Point(15, 2), this.transform.translate(new Point(5, 2)));
        assertEquals(new Transform(10, 0, 3*Math.PI/4), this.transform.rotate(Math.PI / 2));
        assertEquals(3*Math.PI/4, this.transform.getAngleToSee(new Point(-1, 0)));
    }

    @Test
    public void testEquals() {
        Transform transform1 = new Transform(10, 0, Math.PI / 4);
        Transform transform2 = new Transform(10, 0, Math.PI / 2);
        Transform transform3 = new Transform(10, 2, Math.PI / 4);
        Transform transform4 = new Transform(4, 0, Math.PI / 4);
        assertEquals(transform1, transform);
        assertNotEquals(transform2, transform);
        assertNotEquals(transform3, transform);
        assertNotEquals(transform4, transform);
        assertNotEquals(transform, null);
        assertNotEquals(transform, "test");
    }

    @Test
    public void testHashcode() {
        Transform transform1 = new Transform(10, 0, Math.PI / 4);
        Transform transform2 = new Transform(10, 0, Math.PI / 2);
        Transform transform3 = new Transform(10, 2, Math.PI / 4);
        Transform transform4 = new Transform(4, 0, Math.PI / 4);
        assertEquals(transform1.hashCode(), transform.hashCode());
        assertNotEquals(transform2.hashCode(), transform.hashCode());
        assertNotEquals(transform3.hashCode(), transform.hashCode());
        assertNotEquals(transform4.hashCode(), transform.hashCode());
    }

}
