package fr.unice.polytech.si3.qgl.qualituriers.utils.action;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Alexandre Arcil
 */
public class Oar extends Action {

    public Oar(@JsonProperty("sailorId") int sailorId) {
        super(Actions.OAR, sailorId);
    }

    @Override
    public String toString() {
        return "Oar{" +
                "sailorId=" + sailorId +
                ", type='" + type + '\'' +
                '}';
    }
}
