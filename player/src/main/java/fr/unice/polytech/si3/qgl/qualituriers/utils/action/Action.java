package fr.unice.polytech.si3.qgl.qualituriers.utils.action;

/**
 * @author Alexandre Arcil
 * @author CLODONG Yann
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

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof Action)) return false;
        var castedObj = (Action)obj;
        return castedObj.type == type && castedObj.sailorId == sailorId;
    }

    @Override
    public String toString() {
        return "Action{" +
                "type=" + type +
                ", sailorId=" + sailorId +
                '}';
    }
}
