package fr.unice.polytech.si3.qgl.qualituriers.exceptions;

import fr.unice.polytech.si3.qgl.qualituriers.Config;

public class MaxAngleRudderException extends IllegalArgumentException {

    public MaxAngleRudderException(double angle) {
        super("Vous ne pouvez pas faire bouger le rudder de plus de " + Config.MAX_ANGLE_FOR_RUDDER + " rad, or vous essayer de le faire " +
                "bouger de " + angle + " rad");
    }
}
