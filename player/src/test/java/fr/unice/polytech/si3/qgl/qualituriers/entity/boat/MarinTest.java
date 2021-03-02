package fr.unice.polytech.si3.qgl.qualituriers.entity.boat;

import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MarinTest {

    Marin marin;

    @BeforeEach
    public void init() {
        this.marin = new Marin(3, 1, 2, "canard");
    }

    @Test
    public void testGetter() {
        assertEquals(new Point(1, 2), marin.getPosition());
        assertEquals(1, marin.getX());
        assertEquals(2, marin.getY());
        assertEquals(3, marin.getId());
        assertEquals("canard", marin.getName());
    }

    @Test
    public void testEquals() {
        assertEquals(new Marin(3, 1, 2, "canard"), this.marin);
        assertNotEquals(new Marin(1, 1, 2, "canard"), this.marin);
        assertNotEquals(marin, null);
        assertNotEquals(marin, "test");
    }

    @Test
    public void testHashcode() {
        assertEquals(new Marin(3, 1, 2, "canard").hashCode(), this.marin.hashCode());
        assertNotEquals(new Marin(1, 1, 2, "canard").hashCode(), this.marin.hashCode());
    }

}
