package fr.unice.polytech.si3.qgl.qualituriers.utils;

import fr.unice.polytech.si3.qgl.qualituriers.Config;
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

    @Test
    void differenceBetweenTwoAngle() {
        assertEquals(AngleUtil.differenceBetweenTwoAngle(0, Math.PI/2),Math.PI/2);
        assertEquals(AngleUtil.differenceBetweenTwoAngle(Math.PI/2, 0),-Math.PI/2);
        assertEquals(AngleUtil.differenceBetweenTwoAngle(0, 0),0);
        assertTrue(AngleUtil.differenceBetweenTwoAngle(-5*Math.PI/6, 5*Math.PI/6)>= -2*Math.PI/6 - Config.EPSILON && AngleUtil.differenceBetweenTwoAngle(-5*Math.PI/6, 5*Math.PI/6)<= -2*Math.PI/6 + Config.EPSILON );
    }


}
