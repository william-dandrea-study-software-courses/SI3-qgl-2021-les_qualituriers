package fr.unice.polytech.si3.qgl.qualituriers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Alexandre Arcil
 */
@Disabled
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
        this.serialize(liftSail, "{\"type\":\"LIFT_SAIL\",\"sailorId\":0}");
    }

    @Test
    public void serializeLowerSail() {
        LowerSail lowerSail = new LowerSail(1);
        this.serialize(lowerSail, "{\"type\":\"LOWER_SAIL\",\"sailorId\":1}");
    }

    @Test
    public void serializeMoving() {
        Moving moving = new Moving(2, 13, 6);
        this.serialize(moving, "{\"type\":\"MOVING\",\"sailorId\":2,\"xdistance\":13,\"ydistance\":6}");
    }

    @Test
    public void serializeOar() {
        Oar oar = new Oar(3);
        this.serialize(oar, "{\"type\":\"OAR\",\"sailorId\":3}");
    }

    @Test
    public void serializeTurn() {
        Turn turn = new Turn(4, Math.PI / 2);
        this.serialize(turn, "{\"type\":\"TURN\",\"sailorId\":4,\"rotation\":1.5707963267948966}");
    }

    @Test
    public void serializeUseWatch() {
        UseWatch useWatch = new UseWatch(5);
        this.serialize(useWatch, "{\"type\":\"USE_WATCH\",\"sailorId\":5}");
    }

    private void serialize(Object object, String json) {
        String serialize = assertDoesNotThrow(() -> om.writeValueAsString(object));
        assertEquals(json, serialize);
    }

}
