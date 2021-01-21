package fr.unice.polytech.si3.qgl.qualituriers.utils.action;

/**
 * @author Alexandre Arcil
 */
public class Aim extends Action {

    private double angle;

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        if(-Math.PI/4 <= this.angle && this.angle <= Math.PI/4)
            this.angle = angle;
    }
}
