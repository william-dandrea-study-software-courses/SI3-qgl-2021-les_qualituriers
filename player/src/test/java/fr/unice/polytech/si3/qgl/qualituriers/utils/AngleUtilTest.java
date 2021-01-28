package fr.unice.polytech.si3.qgl.qualituriers.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author CLODONG Yann
 */
public class AngleUtilTest {

    @Test
    void AngleModTest() {
        assertEquals(AngleUtil.modAngle(Math.PI * 4), 0);
        assertEquals(AngleUtil.modAngle(Math.PI * 3), Math.PI);
        assertEquals(AngleUtil.modAngle(Math.PI * -3), Math.PI);

        assertEquals(AngleUtil.modAngle(2 * Math.PI + Math.PI / 2), Math.PI / 2);
        assertEquals(AngleUtil.modAngle(-2 * Math.PI + Math.PI / 2), Math.PI / 2);

        assertEquals(AngleUtil.modAngle(-2 * Math.PI + -Math.PI / 2), -Math.PI / 2);
        assertEquals(AngleUtil.modAngle(2 * Math.PI + -Math.PI / 2), -Math.PI / 2);
    }
}
