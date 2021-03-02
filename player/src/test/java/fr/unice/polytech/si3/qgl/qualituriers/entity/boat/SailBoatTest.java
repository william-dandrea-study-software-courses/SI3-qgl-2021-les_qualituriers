package fr.unice.polytech.si3.qgl.qualituriers.entity.boat;

import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.SailBoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SailBoatTest {

    SailBoatEntity entity;
    SailBoatEntity entity2;

    @BeforeEach
    public void init() {
        this.entity = new SailBoatEntity(1, 1, false);
        this.entity2 = new SailBoatEntity(0, 1, true);
    }

    @Test
    public void testOpened() {
        assertFalse(entity.isOpened());
        assertTrue(entity2.isOpened());
    }

    @Test
    public void testGetter() {
        assertEquals(1, entity.getX());
        assertEquals(1, entity.getY());
        assertEquals(new Point(1, 1), entity.getPosition());
    }

    @Test
    public void testEquals() {
        assertEquals(new SailBoatEntity(1, 1, false), entity);
        assertNotEquals(entity, entity2);
        assertNotEquals(new SailBoatEntity(0, 2, false), entity2);
        assertNotEquals(new SailBoatEntity(1, 1, false), entity2);
        assertNotEquals(entity, null);
        assertNotEquals(entity, "test");
    }

    @Test
    public void testHashcode() {
        assertEquals(new SailBoatEntity(1, 1, false).hashCode(), entity.hashCode());
        assertNotEquals(entity.hashCode(), entity2.hashCode());
        assertNotEquals(new SailBoatEntity(0, 2, false).hashCode(), entity2.hashCode());
        assertNotEquals(new SailBoatEntity(1, 1, false).hashCode(), entity2.hashCode());
    }

}
