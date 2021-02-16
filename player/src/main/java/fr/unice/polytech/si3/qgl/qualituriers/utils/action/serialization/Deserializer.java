package fr.unice.polytech.si3.qgl.qualituriers.utils.action.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.*;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.*;

import java.io.IOException;
import java.rmi.UnexpectedException;
import java.util.HashMap;

public class Deserializer extends StdDeserializer<Action> {

    public Deserializer() {
        this(null);
    }

    public Deserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Action deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        var tree = p.getCodec().readTree(p);
        HashMap<String, Actions> actions = new HashMap<>() {{
            put("OAR", Actions.OAR);
            put("TURN", Actions.TURN);
            put("LIFT_SAIL", Actions.LIFT_SAIL);
            put("LOWER_SAIL", Actions.LOWER_SAIL);
            put("USE_WATCH", Actions.USE_WATCH);
            put("MOVING", Actions.MOVING);
        }};



        int sailorId = (Integer)((IntNode)tree.get("sailorId")).numberValue();
        String typeStr = (String)((TextNode)tree.get("type")).textValue();
        Actions type = actions.get(typeStr);

        switch (type) {
            case OAR:
                return new Oar(sailorId);
            case TURN:
                return new Turn(sailorId, (Double)((DoubleNode)tree.get("rotation")).numberValue());
            case MOVING:
                return new Moving(sailorId,
                        (Integer)((IntNode)tree.get("xdistance")).numberValue(),
                        (Integer)((IntNode)tree.get("ydistance")).numberValue());
            case LIFT_SAIL:
                return new LiftSail(sailorId);
            case USE_WATCH:
                return new UseWatch(sailorId);
            case LOWER_SAIL:
                return new LowerSail(sailorId);
        }

        throw new RuntimeException("The action has no type");
    }
}