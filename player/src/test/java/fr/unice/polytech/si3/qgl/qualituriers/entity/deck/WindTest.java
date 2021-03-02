package fr.unice.polytech.si3.qgl.qualituriers.entity.deck;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class WindTest {

    Wind wind;

    @BeforeEach
    public void init() {
        this.wind = new Wind(5, 6);
    }

    @Test
    public void testGetter() {
        assertEquals(5, wind.getOrientation());
        assertEquals(6, wind.getStrength());
    }

    @Test
    public void testEquals() {
        assertEquals(new Wind(5, 6), wind);
        assertNotEquals(new Wind(5, 7), wind);
        assertNotEquals(new Wind(4, 6), wind);
        assertNotEquals(wind, null);
        assertNotEquals(wind, "test");
    }

    @Test
    public void testHashcode() {
        assertEquals(new Wind(5, 6).hashCode(), wind.hashCode());
        assertNotEquals(new Wind(5, 7).hashCode(), wind.hashCode());
        assertNotEquals(new Wind(4, 6).hashCode(), wind.hashCode());
    }

}
