package fr.unice.polytech.si3.qgl.qualituriers.utils.action.noexit;

import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Actions;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Aim;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AimTest {

    Aim aim;

    @BeforeEach
    public void init() {
        this.aim = new Aim(1, Math.PI * 1.5);
    }

    @Test
    public void testGetterSetter() {
        assertEquals(Math.PI * 1.5, this.aim.getAngle());
        assertEquals(1, this.aim.getSailorId());
        assertEquals(Actions.AIM, this.aim.getType());
        this.aim.setSailorId(2);
        assertEquals(2, this.aim.getSailorId());
    }

    @Test
    public void testAngle() {
        this.aim.setAngle(Math.PI / 5);
        assertEquals(Math.PI / 5, this.aim.getAngle());
        this.aim.setAngle(- Math.PI / 4);
        assertEquals(- Math.PI / 4, this.aim.getAngle());
        this.aim.setAngle(Math.PI / 4);
        assertEquals(Math.PI / 4, this.aim.getAngle());
        this.aim.setAngle(- Math.PI / 2);
        assertEquals(Math.PI / 4, this.aim.getAngle());
        this.aim.setAngle(Math.PI / 2);
        assertEquals(Math.PI / 4, this.aim.getAngle());
    }

    @Test
    public void testEquals() {
        Aim aim1 = new Aim(1, Math.PI * 1.5);
        Aim aim2 = new Aim(1, Math.PI * 1);
        Aim aim3 = new Aim(2, Math.PI * 1.5);
        assertEquals(aim1, this.aim);
        assertNotEquals(aim2, this.aim);
        assertNotEquals(aim3, this.aim);
        assertNotEquals(aim, null);
        assertNotEquals(aim, "test");
    }

    @Test
    public void testHashcode() {
        Aim aim1 = new Aim(1, Math.PI * 1.5);
        Aim aim2 = new Aim(1, Math.PI * 1);
        Aim aim3 = new Aim(2, Math.PI * 1.5);
        assertEquals(aim1.hashCode(), this.aim.hashCode());
        assertNotEquals(aim2.hashCode(), this.aim.hashCode());
        assertNotEquals(aim3.hashCode(), this.aim.hashCode());
    }

}
