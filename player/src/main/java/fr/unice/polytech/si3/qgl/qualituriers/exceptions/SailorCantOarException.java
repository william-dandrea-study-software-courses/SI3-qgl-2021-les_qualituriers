package fr.unice.polytech.si3.qgl.qualituriers.exceptions;

public class SailorCantOarException extends IllegalArgumentException {

    public SailorCantOarException(int sailorId) {
        super("Un marin ne peux pas ramer s'il n'est pas sur une rame. Sailor : " + sailorId);
    }
}
