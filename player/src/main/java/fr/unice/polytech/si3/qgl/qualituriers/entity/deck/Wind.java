package fr.unice.polytech.si3.qgl.qualituriers.entity.deck;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Cette classe represente le vent qui sera present sur le deck
 *
 * @author williamdandrea
 * @author CLODONG Yann
 */
public class Wind {

    private double orientation;
    private double strength;

    @JsonCreator
    public Wind(@JsonProperty("orientation") double orientation, @JsonProperty("strength") double strength) {
        this.orientation = orientation;
        this.strength = strength;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof Wind)) return false;
        var castedObj = (Wind)obj;
        return castedObj.orientation == orientation && castedObj.strength == strength;
    }
}
