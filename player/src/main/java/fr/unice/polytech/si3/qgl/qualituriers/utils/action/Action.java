package fr.unice.polytech.si3.qgl.qualituriers.utils.action;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Alexandre Arcil
 */
public class Action {

    protected int sailorId;
    protected String type;

    public Action(int sailorId, @JsonProperty("type")String type) {
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
}
