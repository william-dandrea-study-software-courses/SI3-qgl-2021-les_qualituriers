package fr.unice.polytech.si3.qgl.qualituriers.exceptions;

import fr.unice.polytech.si3.qgl.qualituriers.Config;

public class MovingSailorException extends IllegalArgumentException {

    public MovingSailorException(int sailorID) {
        super("Vous ne pouvez pas bouger le marin" + sailorID + "de plus de " + Config.MAX_MOVING_CASES_MARIN + " cases");
    }
}
