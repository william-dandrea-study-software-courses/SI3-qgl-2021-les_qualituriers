package fr.unice.polytech.si3.qgl.qualituriers.utils.action.nonexit;

import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Actions;

/**
 * @author Alexandre Arcil
 */
public class Fire extends Action {

    public Fire(int sailorId) {
        super(Actions.FIRE, sailorId);
    }
}
