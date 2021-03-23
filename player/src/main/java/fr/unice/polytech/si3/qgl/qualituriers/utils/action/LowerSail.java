package fr.unice.polytech.si3.qgl.qualituriers.utils.action;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Alexandre Arcil
 */
public class LowerSail extends Action {

    public LowerSail(@JsonProperty("sailorId") int sailorId) {
        super(Actions.LOWER_SAIL, sailorId);
    }
}
