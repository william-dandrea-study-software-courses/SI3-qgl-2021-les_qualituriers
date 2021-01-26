package fr.unice.polytech.si3.qgl.qualituriers.utils.action;

/**
 * @author Alexandre Arcil
 */
public class Aim extends Action {

    private double angle;

    public Aim(int sailorId, double angle) {
        super(sailorId, Actions.AIM.getType());
        this.angle = angle;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        if(-Math.PI/4 <= this.angle && this.angle <= Math.PI/4)
            this.angle = angle;
    }
}
