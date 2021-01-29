package fr.unice.polytech.si3.qgl.qualituriers.entity.deck;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Cette classe represente le vent qui sera present sur le deck
 *
 * @author williamdandrea
 */
public class Wind {

    private double orientation;
    private double strength;

    @JsonCreator
    public Wind(@JsonProperty("orientation") double orientation, @JsonProperty("strength") double strength) {
        this.orientation = orientation;
        this.strength = strength;
    }
}
