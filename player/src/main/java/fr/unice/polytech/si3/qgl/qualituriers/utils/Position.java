package fr.unice.polytech.si3.qgl.qualituriers.utils;

/**
 * Une position est en sorte un point avec une dirrection, qui permet au bateau de savoir ou il peut avancer et ou il ne peux pas
 *
 * @author williamdandrea
 */

public class Position {
    private double x;
    private double y;
    private double orientation;

    public Position(double x, double y, double orientation) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
    }
}
