package fr.unice.polytech.si3.qgl.qualituriers.utils.action;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Polygon;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;

/**
 * @author Alexandre Arcil
 * @author CLODONG Yann
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = Void.class)
@JsonSubTypes({
        @JsonSubTypes.Type(name = "oar", value = Oar.class),
        @JsonSubTypes.Type(name = "moving", value = Moving.class)
})
public abstract class Action {

    protected Actions type;
    protected int sailorId;

    public Action(@JsonProperty("type") Actions type, @JsonProperty("sailorId") int sailorId) {
        this.type = type;
        this.sailorId = sailorId;
    }

    public int getSailorId() {
        return sailorId;
    }

    public void setSailorId(int sailorId) {
        //TODO: contrainte: doit être un id doit marin existant
        this.sailorId = sailorId;
    }

    @JsonIgnore
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
    public String toString() {
        return "Action{" +
                "type=" + type +
                ", sailorId=" + sailorId +
                '}';
    }
}
