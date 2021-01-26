package fr.unice.polytech.si3.qgl.qualituriers.utils.action;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Alexandre Arcil
 */
public class Moving extends Action {

    private int distanceX;
    private int distanceY;


    public Moving(int sailorId, int distanceX, int distanceY) {
        super(sailorId, Actions.MOVING.getType());
        this.distanceX = distanceX;
        this.distanceY = distanceY;
    }


    @JsonProperty("action")
    public String getAction() {
        return Actions.MOVING.getType();
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
        // TODO : Créer cette fonction et l'implementer dans MOVING de cockpitMethods
        return true;
    }

}
