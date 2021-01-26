package fr.unice.polytech.si3.qgl.qualituriers.utils.action;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Alexandre Arcil
 */
public class Oar extends Action {

    private String oarType;
    public Oar(@JsonProperty("sailorId") int sailorId) {

        super(sailorId, Actions.OAR.getType());
    }

    @JsonProperty("type")
    public String getOarType() {
        return Actions.OAR.getType();
    }



    @Override
    public String toString() {
        return "Oar{" +
                "sailorId=" + sailorId +
                ", type='" + type + '\'' +
                '}';
    }
}
