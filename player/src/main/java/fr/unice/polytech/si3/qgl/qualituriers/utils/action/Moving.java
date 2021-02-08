package fr.unice.polytech.si3.qgl.qualituriers.utils.action;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.Config;

/**
 * @author Alexandre Arcil
 * @author CLODONG Yann
 */
public class Moving extends Action {

    private int distanceX;
    private int distanceY;

    public Moving(int sailorId, int distanceX, int distanceY) {
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


    /**
     * Cette méthode dit si on peut se déplacer sur le pont du bateau, d'un maximum de 5 cases
     * @param xInit
     * @param yInit
     * @param xFinal
     * @param yFinal
     * @return
     */
    public boolean canMove(int xInit, int yInit, int xFinal, int yFinal) {
        // TODO : 'implementer dans MOVING de cockpitMethods
        return (xFinal - xInit) <= Config.MAX_MOVING_CASES_MARIN && (yFinal - yInit) <= Config.MAX_MOVING_CASES_MARIN;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof Moving)) return false;
        var castedObj = (Moving)obj;
        return super.equals(obj) && castedObj.distanceX == distanceX && castedObj.distanceY == distanceY;
    }

    @Override
    public String toString() {
        return "Moving{" +
                "distanceX=" + distanceX +
                ", distanceY=" + distanceY +
                ", type=" + type +
                ", sailorId=" + sailorId +
                '}';
    }
}
