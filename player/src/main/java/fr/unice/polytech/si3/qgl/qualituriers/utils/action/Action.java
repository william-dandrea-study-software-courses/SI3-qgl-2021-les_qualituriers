package fr.unice.polytech.si3.qgl.qualituriers.utils.action;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Objects;

/**
 * @author Alexandre Arcil
 * @author CLODONG Yann
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = Void.class)
@JsonSubTypes({
        @JsonSubTypes.Type(name = "OAR", value = Oar.class),
        @JsonSubTypes.Type(name = "MOVING", value = Moving.class),
        @JsonSubTypes.Type(name = "AIM", value = Aim.class),
        @JsonSubTypes.Type(name = "TURN", value = Turn.class),
        @JsonSubTypes.Type(name = "LIFT_SAIL", value = LiftSail.class),
        @JsonSubTypes.Type(name = "LOWER_SAIL", value = LowerSail.class),
        @JsonSubTypes.Type(name = "USE_WATCH", value = UseWatch.class)
})
public abstract class Action {

    protected Actions type;
    protected int sailorId;

    protected Action(@JsonProperty("type") Actions type, @JsonProperty("sailorId") int sailorId) {
        this.type = type;
        this.sailorId = sailorId;
    }

    public int getSailorId() {
        return sailorId;
    }

    public void setSailorId(int sailorId) {
        this.sailorId = sailorId;
    }

    public Actions getType() {
        return type;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof Action)) return false;
        var castedObj = (Action)obj;
        return castedObj.type == type && castedObj.sailorId == sailorId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, sailorId);
    }

    @Override
    public String toString() {
        return "Action{" +
                "type=" + type +
                ", sailorId=" + sailorId +
                '}';
    }
}
