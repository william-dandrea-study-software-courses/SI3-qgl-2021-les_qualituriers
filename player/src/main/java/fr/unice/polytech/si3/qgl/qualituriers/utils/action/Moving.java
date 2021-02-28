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

    public Moving(int sailorId, Point direction) {
        super(Actions.MOVING, sailorId);
        this.distanceY = (int)direction.getX();
        this.distanceX = (int)direction.getY();
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

    @JsonIgnore
    public Point getDirection() {
        return new Point(distanceX, distanceY);
    }

    private static boolean isPointInt(Point pt) {
        double dx = pt.getX() - (int)pt.getX();
        double dy = pt.getY() - (int)pt.getY();
        return dx == 0 && dy == 0;
    }

    /**
     * Cette méthode dit si on peut se déplacer sur le pont du bateau, d'un maximum de 5 cases
     * @param direction: la direction normée du déplacement du marin
     * @return true si le marin peux bouger suivant cette direction en 1 tour
     */
    public static boolean canMove(Point direction) {
        if(!isPointInt(direction)) throw  new IllegalArgumentException("The direction is not an integer : the sailors can only be on an integer position");
        return Math.abs(direction.getX()) <= Config.MAX_MOVING_CASES_MARIN && Math.abs(direction.getY()) <= Config.MAX_MOVING_CASES_MARIN;
    }

    /**
     * Cette méthode dit si on peut se déplacer sur le pont du bateau, d'un maximum de 5 cases
     * @param from d'où part le marin
     * @param to où veux aller le marin
     * @return true si le marin peux bouger de la position from vers la position to en 1 tour
     */
    public static boolean canMove(Point from, Point to) {
        return canMove(to.substract(from));
    }

    /**
     * Réduit la norme de la direction pour que le marin puisse s'y déplacer
     * @param direction la direction selon laquelle le marin doit se déplacer
     * @return La direction réduite.
     */
    public static Point clamp(Point direction) {
        if(canMove(direction))
            return direction;
        if(Math.abs(direction.getX()) > Math.abs(direction.getY())) {
            // clamp x to 5
            int dirX = direction.getX() > 0 ? 1 : -1;
            var multiplier = (double)Config.MAX_MOVING_CASES_MARIN / Math.abs(direction.getX());

            return new Point((double) Config.MAX_MOVING_CASES_MARIN * dirX, multiplier * direction.getY());
        } else {
            // clamp y to 5
            int dirY = direction.getY() > 0 ? 1 : -1;
            var multiplier = (double)Config.MAX_MOVING_CASES_MARIN / Math.abs(direction.getY());

            return new Point(multiplier * direction.getX(), (double) Config.MAX_MOVING_CASES_MARIN * dirY);
        }
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
                '}' + '\n';
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), distanceX, distanceY);
    }
}
