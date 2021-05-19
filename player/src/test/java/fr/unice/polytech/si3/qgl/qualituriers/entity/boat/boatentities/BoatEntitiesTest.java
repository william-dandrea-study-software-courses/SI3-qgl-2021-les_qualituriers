package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author D'Andréa William
 */
class BoatEntitiesTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void boatEntitiesTest() {

        assertEquals("oar", BoatEntities.OAR.getType());
        assertEquals("sail", BoatEntities.SAIL.getType());
        assertEquals("rudder", BoatEntities.RUDDER.getType());
        assertEquals("watch", BoatEntities.WATCH.getType());
        assertEquals("cannon", BoatEntities.CANON.getType());

        assertEquals("BoatEntities{type='oar', entity=class fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.OarBoatEntity}", BoatEntities.OAR.toString());


    }


}
