package fr.unice.polytech.si3.qgl.qualituriers.utils.action.nonexit;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Actions;

import java.util.Objects;

/**
 * @author Alexandre Arcil
 */
public class Aim extends Action {

    private double angle;

    @JsonCreator
    public Aim(@JsonProperty("sailorId")int sailorId, @JsonProperty("angle")double angle) {
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

    @Override
    public String toString() {
        return "Aim{" +
                "angle=" + angle +
                ", type=" + type +
                ", sailorId=" + sailorId +
                '}';
    }
}
