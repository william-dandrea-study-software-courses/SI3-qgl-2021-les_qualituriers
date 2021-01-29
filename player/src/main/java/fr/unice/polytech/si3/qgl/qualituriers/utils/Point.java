package fr.unice.polytech.si3.qgl.qualituriers.utils;


import static fr.unice.polytech.si3.qgl.qualituriers.utils.AngleUtil.modAngle;

/**
 * Cette classe represente un point qui sera utilise pour localiser les elements sur le deck
 *
 * @author williamdandrea
 * @author CLODONG Yann
 */

public class Point {

    private final double x;
    private final double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(double angle) {
        this.x = Math.cos(angle);
        this.y = Math.sin(angle);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    /**
     * Addition
     * @param other: Deuxieme element
     * @return this + other
     */
    public Point add(Point other) {
        return new Point(x + other.getX(), y + other.getY());
    }

    /**
     * Soustraction
     * @param other: Deuxieme element
     * @return this - other
     */
    public Point substract(Point other) {
        return new Point(x - other.getX(), y - other.getY());
    }

    /**
     * Produit scalaire entre 2 Points
     * @param other: Deuxieme element
     * @return this . other
     */
    public double scalar(Point other) {
        return x * other.x + y * other.y;
    }

    /**
     * Produit scalaire entre un Reel et un Point
     * @param n: Le reel
     * @return n * this
     */
    public Point scalar(double n) {
        return new Point(n * x, n * y);
    }

    /**
     * Produit mixte ou produit en croix
     * @param other: Deuxieme operande
     * @return this ^ other
     */
    public double cross(Point other) {
        return x * other.y - y * other.x;
    }

    /**
     * Norme
     * @return la norme du vecteur
     */
    public double length() {
        return Math.sqrt(x*x + y*y);
    }

    /**
     * Direction: vecteur colinéaire à celui-ci de norme 1
     * @return vecteur directeur
     */
    public Point normalized() {
        if(x == 0 && y == 0) return new Point(0, 0);
        return this.scalar(1 / length());
    }

    /**
     * Perpendicularité
     * @param other: 2eme vecteur
     * @return true si les deux vecteurs sont perpendiculaire
     */
    public boolean isNormalTo(Point other) {
        return Math.abs(scalar(other)) < 2 * Double.MIN_VALUE;
    }

    /**
     * Parallélisme
     * @param other: 2eme vecteur
     * @return true si les vecteur sont parallèle
     */
    public boolean isColinearTo(Point other) {
        return Math.abs(cross(other)) < 2 * Double.MIN_VALUE;
    }

    /**
     * Return angle between NORTH and this
     * @return Angle
     */
    public double getOrientation() {
        if(x == 0) return Math.PI / 2 * (y < 0 ? -1 : 1); // infinite case of atan
        var unsignedAngle = Math.atan(y / x);
        if(x < 0 ) unsignedAngle += Math.PI;
        return modAngle(unsignedAngle);
    }

    /**
     * Angle entre 2 vecteurs
     * @param other, deuxieme vecteur
     * @return angle tq tq this.rotate(angle) = other
     */
    public double angleWith(Point other) {
        return modAngle(other.getOrientation() - getOrientation());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof Point)) return false;
        var pobj = (Point)obj;
        return pobj.x == x && pobj.y == y;
    }

    @Override
    public String toString() {
        return "{ x: " + x + ", y: " + y + " }";
    }
}
