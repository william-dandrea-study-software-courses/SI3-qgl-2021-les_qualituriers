package fr.unice.polytech.si3.qgl.qualituriers.utils.action;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MovingTest {

    Moving moving;

    @BeforeEach
    public void init() {
        this.moving = new Moving(5, 2, 3);
    }

    @Test
    public void testGetterSetter() {
        assertEquals(5, this.moving.getSailorId());
        assertEquals(2, this.moving.getDistanceX());
        assertEquals(3, this.moving.getDistanceY());
        this.moving.setDistanceX(6);
        this.moving.setDistanceY(21);
        this.moving.setSailorId(4);
        assertEquals(4, this.moving.getSailorId());
        assertEquals(6, this.moving.getDistanceX());
        assertEquals(21, this.moving.getDistanceY());
    }

    @Test
    public void testEquals() {
        Moving moving1 = new Moving(5, 2, 3);
        Moving moving2 = new Moving(5, 2, 4);
        Moving moving3 = new Moving(5, 1, 3);
        Moving moving4 = new Moving(1, 2, 3);
        assertEquals(moving1, this.moving);
        assertNotEquals(moving2, this.moving);
        assertNotEquals(moving3, this.moving);
        assertNotEquals(moving4, this.moving);
        assertNotEquals(moving, null);
        assertNotEquals(moving, "test");
    }

    @Test
    public void testHashcode() {
        Moving moving1 = new Moving(5, 2, 3);
        Moving moving2 = new Moving(0, 2, 4);
        Moving moving3 = new Moving(0, 1, 3);
        Moving moving4 = new Moving(1, 2, 3);
        assertEquals(moving1.hashCode(), this.moving.hashCode());
        assertNotEquals(moving2.hashCode(), this.moving.hashCode());
        assertNotEquals(moving3.hashCode(), this.moving.hashCode());
        assertNotEquals(moving4.hashCode(), this.moving.hashCode());
    }

}
