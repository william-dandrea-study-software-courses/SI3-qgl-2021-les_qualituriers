package fr.unice.polytech.si3.qgl.qualituriers.entity.boat;

import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.OarBoatEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OarTest {

    @Test
    public void testSide() {
        OarBoatEntity oar = new OarBoatEntity(0, 0);
        OarBoatEntity oar2 = new OarBoatEntity(0, 1);
        assertTrue(oar.isLeftOar());
        assertFalse(oar.isRightOar());
        assertFalse(oar2.isLeftOar());
        assertTrue(oar2.isRightOar());
    }

}
