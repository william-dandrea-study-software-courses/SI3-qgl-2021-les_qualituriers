package fr.unice.polytech.si3.qgl.qualituriers.utils.action;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class LiftSailTest {

    LiftSail liftSail;

    @BeforeEach
    public void init() {
        this.liftSail = new LiftSail(5);
    }

    @Test
    public void testGetterSetter() {
        assertEquals(5, this.liftSail.getSailorId());
        this.liftSail.setSailorId(4);
        assertEquals(4, this.liftSail.getSailorId());
    }

}
