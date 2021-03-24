package fr.unice.polytech.si3.qgl.qualituriers.utils.action;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Alexandre Arcil
 */
public class LiftSail extends Action {

    public LiftSail(@JsonProperty("sailorId") int sailorId) {
        super(Actions.LIFT_SAIL, sailorId);
    }
}
