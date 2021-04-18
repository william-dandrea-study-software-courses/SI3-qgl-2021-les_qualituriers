package fr.unice.polytech.si3.qgl.qualituriers.utils.action;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class LowerSailTest {

    LowerSail lowerSail;

    @BeforeEach
    public void init() {
        this.lowerSail = new LowerSail(5);
    }

    @Test
    public void testGetterSetter() {
        assertEquals(5, this.lowerSail.getSailorId());
        this.lowerSail.setSailorId(4);
        assertEquals(4, this.lowerSail.getSailorId());
    }

}
