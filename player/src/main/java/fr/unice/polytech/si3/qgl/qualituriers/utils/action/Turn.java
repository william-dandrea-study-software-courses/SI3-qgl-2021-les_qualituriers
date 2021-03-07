package fr.unice.polytech.si3.qgl.qualituriers.utils.action;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Objects;

/**
 * @author Alexandre Arcil
 * @author CLODONG Yann
 */
public class Turn extends Action {

    private double rotation;

    public Turn(@JsonProperty("sailorId") int sailorId, @JsonProperty("rotation") double rotation) {
        super(Actions.TURN, sailorId);
        this.rotation = rotation;
    }

    public void setRotation(double rotation) {
        if(-Math.PI/4 <= rotation && rotation <= Math.PI/4)
            this.rotation = rotation;
    }

    public double getRotation() {
        return rotation;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof Turn)) return false;
        var castedObj = (Turn)obj;
        return super.equals(obj) && castedObj.rotation == rotation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), rotation);
    }

    @Override
    public String toString() {
        return "Turn{" +
                "sailorId=" + sailorId +
                ", type='" + type + "', rotation=" + rotation +
                "}";
    }
}
