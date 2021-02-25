package fr.unice.polytech.si3.qgl.qualituriers.utils;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.qualituriers.Config;

import java.util.Objects;

import static fr.unice.polytech.si3.qgl.qualituriers.utils.AngleUtil.modAngle;

/**
 * Cette classe représente un point qui sera utilise pour localiser les elements sur le deck
 *
 * @author williamdandrea
 * @author CLODONG Yann
 */

public class Point {

    private final double x;
    private final double y;

    @JsonCreator
    public Point(@JsonProperty("x") double x, @JsonProperty("y") double y) {
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
        return Math.sqrt(this.lengthWithoutSquare());
    }

    /**
     * Norme sans la racine carré
     * @return la norme du vecteur au carré
     */
    public double lengthWithoutSquare() {
        return x*x + y*y;
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
    public boolean isCollinearTo(Point other) {
        return Math.abs(cross(other)) < Config.EPSILON;
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
     * Le vecteur tourné par un angle
     * @param angle: l'Angle
     * @return le nouveau vecteur
     */
    public Point rotate(double angle) {
        double xRotate = Math.cos(angle) * this.x - Math.sin(angle) * this.y;
        double yRotate = Math.sin(angle) * this.x + Math.cos(angle) * this.y;
        return new Point(xRotate, yRotate);
    }

    /**
     * Le vecteur tourné par un angle par rapport à un point d'origine
     * @param angle: l'angle
     * @return le nouveau vecteur
     */
    public Point rotate(double angle, Point origin) {
        return this.substract(origin).rotate(angle).add(origin);
    }

    /**
     * Angle entre 2 vecteurs
     * @param other, deuxième vecteur
     * @return angle tq tq this.rotate(angle) = other
     */
    public double angleWith(Point other) {
        return modAngle(other.getOrientation() - getOrientation());
    }

    /**
     * Projection
     * @param axe L'axe où projeter le point
     * @return La projection du point sur l'axe
     */
    public Point projection(Point axe) {
        double length = axe.lengthWithoutSquare();
        double dotProduct = this.scalar(axe);
        return new Point((dotProduct/length) * axe.getX(), (dotProduct/length) * axe.getY());
    }

    /**
     * Distance entre 2 points sans racine carré
     * @param other L'autre point
     * @return la distance
     */
    public double distanceWithoutSquare(Point other) {
        return Math.pow((this.x - other.x), 2) + Math.pow((this.y - other.y), 2);
    }

    /**
     * Distance entre 2 points
     * @param other L'autre point
     * @return la distance
     */
    public double distance(Point other) {
        return Math.sqrt(this.distanceWithoutSquare(other));
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof Point)) return false;
        var pobj = (Point)obj;
        return pobj.x - x < Config.EPSILON && pobj.x - x > -Config.EPSILON
                && pobj.y - y < Config.EPSILON && pobj.y - y > -Config.EPSILON;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "{ x: " + x + ", y: " + y + " }";
    }
}
