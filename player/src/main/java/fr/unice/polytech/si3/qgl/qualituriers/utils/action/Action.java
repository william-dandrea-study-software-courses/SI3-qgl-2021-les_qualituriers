package fr.unice.polytech.si3.qgl.qualituriers.utils.action;

/**
 * @author Alexandre Arcil
 */
public abstract class Action {

    protected Actions type;
    protected int sailorId;

    public Action(Actions type, int sailorId) {
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

    public Actions getType() {
        return type;
    }
}
