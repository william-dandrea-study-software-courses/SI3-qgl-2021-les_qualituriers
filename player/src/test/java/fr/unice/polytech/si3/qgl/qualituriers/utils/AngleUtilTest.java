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
        assertEquals(0, AngleUtil.modAngle(Math.PI * 4));
        assertEquals(Math.PI, AngleUtil.modAngle(Math.PI * 3));
        assertEquals(Math.PI, AngleUtil.modAngle(Math.PI * -3));

        assertEquals(Math.PI / 2, AngleUtil.modAngle(2 * Math.PI + Math.PI / 2));
        assertEquals(Math.PI / 2, AngleUtil.modAngle(-2 * Math.PI + Math.PI / 2));

        assertEquals(-Math.PI / 2, AngleUtil.modAngle(-2 * Math.PI + -Math.PI / 2));
        assertEquals(-Math.PI / 2, AngleUtil.modAngle(2 * Math.PI + -Math.PI / 2));
    }

    @Test
    void differenceBetweenTwoAngle() {
        assertEquals(Math.PI/2, AngleUtil.differenceBetweenTwoAngle(0, Math.PI/2));
        assertEquals(-Math.PI/2, AngleUtil.differenceBetweenTwoAngle(Math.PI/2, 0));
        assertEquals(0, AngleUtil.differenceBetweenTwoAngle(0, 0));
        assertTrue(AngleUtil.differenceBetweenTwoAngle(-5*Math.PI/6, 5*Math.PI/6)>= -2*Math.PI/6 - Config.EPSILON && AngleUtil.differenceBetweenTwoAngle(-5*Math.PI/6, 5*Math.PI/6)<= -2*Math.PI/6 + Config.EPSILON );
    }

    @Test
    void mod2PIAngleTest(){
        assertEquals(3, AngleUtil.mod2PIAngle(3));
        assertEquals(0.7168146928204138, AngleUtil.mod2PIAngle(7));
        assertEquals(-3, AngleUtil.mod2PIAngle(-3));
    }


}
