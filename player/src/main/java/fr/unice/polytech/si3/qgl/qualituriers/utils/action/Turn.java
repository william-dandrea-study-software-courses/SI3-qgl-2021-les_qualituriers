package fr.unice.polytech.si3.qgl.qualituriers.utils.action;

/**
 * @author Alexandre Arcil
 */
public class Turn extends Action {

    private double rotation;

    public Turn(int sailorId, double rotation) {
        super(Actions.TURN, sailorId);
        this.rotation = rotation;
    }

    public void setRotation(double rotation) {
        if(-Math.PI/4 <= this.rotation && this.rotation <= Math.PI/4)
            this.rotation = rotation;
    }

    public double getRotation() {
        return rotation;
    }

}
