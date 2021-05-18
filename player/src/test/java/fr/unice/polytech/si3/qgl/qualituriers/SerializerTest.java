package fr.unice.polytech.si3.qgl.qualituriers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Alexandre Arcil
 */


public class SerializerTest {

    static ObjectMapper om;

    @BeforeAll
    public static void init() {
        om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Test
    public void serializeLifeSail() {
        LiftSail liftSail = new LiftSail(0);
        String serialize = assertDoesNotThrow(() -> om.writeValueAsString(liftSail));
        assertTrue(serialize.contains("LIFT_SAIL"));
        assertTrue(serialize.contains("0"));
    }

    @Test
    public void serializeLowerSail() {
        LowerSail lowerSail = new LowerSail(1);
        String serialize = assertDoesNotThrow(() -> om.writeValueAsString(lowerSail));
        assertTrue(serialize.contains("LOWER_SAIL"));
        assertTrue(serialize.contains("1"));
    }

    @Test
    public void serializeMoving() {
        Moving moving = new Moving(2, 13, 6);
        String serialize = assertDoesNotThrow(() -> om.writeValueAsString(moving));
        assertTrue(serialize.contains("MOVING"));
        assertTrue(serialize.contains("2"));
    }

    @Test
    public void serializeOar() {
        Oar oar = new Oar(3);
        String serialize = assertDoesNotThrow(() -> om.writeValueAsString(oar));
        assertTrue(serialize.contains("OAR"));
        assertTrue(serialize.contains("3"));
    }

    @Test
    public void serializeTurn() {
        Turn turn = new Turn(4, Math.PI / 2);
        String serialize = assertDoesNotThrow(() -> om.writeValueAsString(turn));
        assertTrue(serialize.contains("TURN"));
        assertTrue(serialize.contains("4"));
        assertTrue(serialize.contains("rotation"));
    }

    @Test
    public void serializeUseWatch() {
        UseWatch useWatch = new UseWatch(5);
        String serialize = assertDoesNotThrow(() -> om.writeValueAsString(useWatch));
        assertTrue(serialize.contains("USE_WATCH"));
        assertTrue(serialize.contains("5"));
    }


}
