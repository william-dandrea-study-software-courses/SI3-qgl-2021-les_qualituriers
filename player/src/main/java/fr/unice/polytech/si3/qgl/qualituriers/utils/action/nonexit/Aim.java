package fr.unice.polytech.si3.qgl.qualituriers.utils.action.nonexit;

import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Actions;

/**
 * @author Alexandre Arcil
 */
public class Aim extends Action {

    private double angle;

    public Aim(int sailorId, double angle) {
        super(Actions.AIM, sailorId);
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
