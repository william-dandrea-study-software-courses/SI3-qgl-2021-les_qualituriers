package fr.unice.polytech.si3.qgl.qualituriers.engine.exceptions;

public class SailorNotFoundException extends RuntimeException {
    public SailorNotFoundException(int sailorId) {
        super("Sailor " + sailorId + " was not found !");
    }
}
