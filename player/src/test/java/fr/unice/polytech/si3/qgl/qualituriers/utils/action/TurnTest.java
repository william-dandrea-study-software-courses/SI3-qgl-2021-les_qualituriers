package fr.unice.polytech.si3.qgl.qualituriers.utils.action;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TurnTest {

    Turn turn;

    @BeforeEach
    public void init() {
        this.turn = new Turn(0, Math.PI / 6);
    }

    @Test
    public void testRotation() {
        assertEquals(Math.PI / 6, this.turn.getRotation());
        this.turn.setRotation(0);
        assertEquals(0, this.turn.getRotation());
        this.turn.setRotation(-Math.PI);
        assertEquals(0, this.turn.getRotation());
        this.turn.setRotation(-Math.PI/4);
        assertEquals(-Math.PI/4, this.turn.getRotation());
        this.turn.setRotation(Math.PI/4);
        assertEquals(Math.PI/4, this.turn.getRotation());
    }

    @Test
    public void testEquals() {
        Turn turn1 = new Turn(0, Math.PI / 6);
        Turn turn2 = new Turn(0, Math.PI / 2);
        Turn turn3 = new Turn(6, Math.PI / 6);
        assertEquals(turn1, this.turn);
        assertNotEquals(turn2, this.turn);
        assertNotEquals(turn3, this.turn);
        assertNotEquals(turn, null);
        assertNotEquals(turn, "test");
    }

    @Test
    public void testHashcode() {
        Turn turn1 = new Turn(0, Math.PI / 6);
        Turn turn2 = new Turn(0, Math.PI / 2);
        Turn turn3 = new Turn(6, Math.PI / 6);
        assertEquals(turn1.hashCode(), this.turn.hashCode());
        assertNotEquals(turn2.hashCode(), this.turn.hashCode());
        assertNotEquals(turn3.hashCode(), this.turn.hashCode());
    }

}
