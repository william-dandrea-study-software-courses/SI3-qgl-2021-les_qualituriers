package fr.unice.polytech.si3.qgl.qualituriers.entity.deck;

/**
 * Cette classe represente le vent qui sera present sur le deck
 *
 * @author williamdandrea
 */
public class Wind {

    private double orientation;
    private double strength;

    public Wind(double orientation, double strength) {
        this.orientation = orientation;
        this.strength = strength;
    }
}
