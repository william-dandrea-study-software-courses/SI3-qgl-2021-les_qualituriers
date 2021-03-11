package fr.unice.polytech.si3.qgl.qualituriers.utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Une position est en sorte un point avec une direction, qui permet au bateau de savoir ou il peut avancer et ou il ne peux pas
 *
 * @author williamdandrea
 * @author CLODONG Yann
 */

public class Transform extends Point {
    public static final Transform ZERO = new Transform(new Point(0, 0), 0);

    private final double orientation;

    @JsonCreator
    public Transform(@JsonProperty("x") double x, @JsonProperty("y") double y, @JsonProperty("orientation") double orientation) {
        super(x, y);
        this.orientation = AngleUtil.modAngle(orientation);
    }

    @Override
    public String toString() {
        return "Transform{" +
                "Point: " + super.toString() +
                "orientation=" + orientation +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof Transform)) return false;
        var castedObj = (Transform)obj;
        return super.equals(obj) && castedObj.orientation == orientation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), orientation);
    }

    @Override
    public double getOrientation() {
        return orientation;
    }

    public Transform(Point position, double orientation) {
        super(position.getX(), position.getY());
        this.orientation = orientation;
    }

    /**
     * @return Vecteur droit de cette position
     */
    public Point right() {
        return new Point(orientation);
    }

    /**
     * @return Vecteur haut de cette position
     */
    public Point up() {
        return new Point(orientation + Math.PI / 2);
    }

    /**
     * @return Vecteur bas de cette position
     */
    public Point down() {
        return up().scalar(-1);
    }

    /**
     * @return Vecteur gauche de cette position
     */
    public Point left() {
        return right().scalar(-1);
    }

    /**
     * Location
     * @return location
     */
    @JsonIgnore
    public Point getPoint() {
        return new Point(this.getX(), this.getY());
    }

    /**
     * Direction que pointe la position
     * @return Direction
     */
    public Point direction() {
        return new Point(orientation);
    }

    /**
     * Translation
     * @param direction: Direction (normé) dans laquelle déplacer cette position
     * @return La position translatée
     */
    public Transform translate(Point direction) {
        return new Transform(direction.add(getPoint()), orientation);
    }

    /**
     * Rotation
     * @param deltaAngle: angle
     * @return Position qui a tourner de deltaAngle
     */
    @Override
    public Transform rotate(double deltaAngle) {
        return new Transform(this.getX(), this.getY(), orientation + deltaAngle);
    }

    /**
     * Rotation nécessaire pour que l'orientation pointe la location précisé<br>
     * <u>ex:</u>  La Position caractérise l'état du bateau. l'angle retourner sera la déviation de cap que le bateau devra effectué pour rejoindre le point location.
     * @param location: l'emplacement a pointer
     * @return L'angle
     */
    public double getAngleToSee(Point location) {
        var dir = location.substract(getPoint());
        return direction().angleWith(dir);
    }
}
