package fr.unice.polytech.si3.qgl.qualituriers.utils.action;

/**
 * @author Alexandre Arcil
 */
public class UseWatch extends Action {

    public UseWatch(int sailorId) {
        super(sailorId, Actions.USE_WATCH.getType());
    }
}