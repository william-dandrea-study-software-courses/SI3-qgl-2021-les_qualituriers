package fr.unice.polytech.si3.qgl.qualituriers.entity.deck;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Cette classe represente le vent qui sera present sur le deck
 *
 * @author williamdandrea
 * @author CLODONG Yann
 */
public class Wind {

    private final double orientation;
    private final double strength;

    @JsonCreator
    public Wind(@JsonProperty("orientation") double orientation, @JsonProperty("strength") double strength) {
        this.orientation = orientation;
        this.strength = strength;
    }

    public double getOrientation() {
        return orientation;
    }

    public double getStrength() {
        return strength;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof Wind)) return false;
        var castedObj = (Wind)obj;
        return castedObj.orientation == orientation && castedObj.strength == strength;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orientation, strength);
    }
}
