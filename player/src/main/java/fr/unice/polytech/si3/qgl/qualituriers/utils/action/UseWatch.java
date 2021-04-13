package fr.unice.polytech.si3.qgl.qualituriers.utils.action;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Alexandre Arcil
 */
public class UseWatch extends Action {

    public UseWatch(@JsonProperty("sailorId") int sailorId) {
        super(Actions.USE_WATCH, sailorId);
    }
}
