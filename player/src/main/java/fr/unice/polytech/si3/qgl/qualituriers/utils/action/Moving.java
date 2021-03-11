package fr.unice.polytech.si3.qgl.qualituriers.utils.action;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.qualituriers.Config;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;

import java.util.Objects;

/**
 * @author Alexandre Arcil
 * @author CLODONG Yann
 */
public class Moving extends Action {

    private int distanceX;
    private int distanceY;

    @JsonCreator
    public Moving(@JsonProperty("sailorId") int sailorId, @JsonProperty("xdistance") int distanceX, @JsonProperty("ydistance") int distanceY) {
        super(Actions.MOVING, sailorId);
        this.distanceX = distanceX;
        this.distanceY = distanceY;
    }

    @JsonProperty("xdistance")
    public int getDistanceX() {
        return distanceX;
    }

    @JsonProperty("ydistance")
    public int getDistanceY() {
        return distanceY;
    }

    @JsonProperty("xdistance")
    public void setDistanceX(int distanceX) {
        this.distanceX = distanceX;
    }

    @JsonProperty("ydistance")
    public void setDistanceY(int distanceY) {
        this.distanceY = distanceY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Moving moving = (Moving) o;
        return distanceX == moving.distanceX && distanceY == moving.distanceY;
    }

    @Override
    public String toString() {
        return "Moving{" +
                "distanceX=" + distanceX +
                ", distanceY=" + distanceY +
                ", type=" + type +
                ", sailorId=" + sailorId +
                '}' + '\n';
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), distanceX, distanceY);
    }
}
