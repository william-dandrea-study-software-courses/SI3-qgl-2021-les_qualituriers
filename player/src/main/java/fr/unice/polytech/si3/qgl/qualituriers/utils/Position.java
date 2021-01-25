package fr.unice.polytech.si3.qgl.qualituriers.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Une position est en sorte un point avec une dirrection, qui permet au bateau de savoir ou il peut avancer et ou il ne peux pas
 *
 * @author williamdandrea
 */

public class Position {
    private double x;
    private double y;
    private double orientation;

    public Position(@JsonProperty("x") double x, @JsonProperty("y") double y, @JsonProperty("orientation") double orientation) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                ", orientation=" + orientation +
                '}';
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getOrientation() {
        return orientation;
    }
}
