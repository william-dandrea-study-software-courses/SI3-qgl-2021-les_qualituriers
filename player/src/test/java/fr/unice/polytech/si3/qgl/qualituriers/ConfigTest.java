package fr.unice.polytech.si3.qgl.qualituriers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfigTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void linearSpeedOarWithClassicValue() {
        assertEquals(27.5, Config.linearSpeedOar(6,1));
    }

    @Test
    void linearSpeedOarCrashTest() {
        assertThrows(IllegalArgumentException.class, () -> Config.linearSpeedOar(0, 1));
        assertThrows(IllegalArgumentException.class, () ->  Config.linearSpeedOar(6,0));
        assertThrows(IllegalArgumentException.class, () ->  Config.linearSpeedOar(-6,0));
        assertThrows(IllegalArgumentException.class, () ->  Config.linearSpeedOar(6,0));
        assertThrows(IllegalArgumentException.class, () ->  Config.linearSpeedOar(6,-0));
    }

    @Test
    void linearSpeedWind() {

        assertEquals(0, Config.linearSpeedWind(1,1,50,0, Math.PI/2), Config.EPSILON);
        // Vent de face
        assertEquals(50, Config.linearSpeedWind(1,1,50,Math.PI/2, Math.PI/2), Config.EPSILON);
        // Vent de dos
        assertEquals(-50, Config.linearSpeedWind(1,1,50,-Math.PI/2, Math.PI/2), Config.EPSILON);
    }

    @Test
    void linearSpeedWindCrashTest() {

        assertThrows(IllegalArgumentException.class, () -> Config.linearSpeedWind(2,1,50,0, Math.PI/2));
        assertEquals(0, Config.linearSpeedWind(1,1,0,-Math.PI/2, Math.PI/2), Config.EPSILON);
        assertEquals(0, Config.linearSpeedWind(0,1,0,-Math.PI/2, Math.PI/2), Config.EPSILON);

    }
}
