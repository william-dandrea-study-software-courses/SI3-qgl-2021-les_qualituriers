package fr.unice.polytech.si3.qgl.qualituriers.utils.action.nonexit;

import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Actions;

import java.util.Objects;

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
        if(-Math.PI/4 <= angle && angle <= Math.PI/4)
            this.angle = angle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Aim aim = (Aim) o;
        return Double.compare(aim.angle, angle) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), angle);
    }
}
