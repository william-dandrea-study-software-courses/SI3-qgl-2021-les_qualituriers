package fr.unice.polytech.si3.qgl.qualituriers.utils.action;

/**
 * @author Alexandre Arcil
 */
public class Reload extends Action {

    public Reload(int sailorId) {
        super(sailorId, Actions.RELOAD.getType());
    }
}
