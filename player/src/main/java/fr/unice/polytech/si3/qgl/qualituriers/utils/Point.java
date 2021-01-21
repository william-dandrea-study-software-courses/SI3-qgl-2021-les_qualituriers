package fr.unice.polytech.si3.qgl.qualituriers.utils;

/**
 * @author William D'Andrea
 * @author Clodong Yann
 */
public class Point {

    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
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
    public Point nomalized() {
        return this.scalar(1 / length());
    }

    /**
     * Perpendicularité
     * @param other: 2eme vecteur
     * @return true si les deux vecteurs sont perpendiculaire
     */
    public boolean isNormal(Point other) {
        return scalar(other) == 0;
    }

    /**
     * Parallélisme
     * @param other: 2eme vecteur
     * @return true si les vecteur sont parallèle
     */
    public boolean isColinear(Point other) {
        return cross(other) == 0;
    }
}
