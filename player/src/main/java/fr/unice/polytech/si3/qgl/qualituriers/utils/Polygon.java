package fr.unice.polytech.si3.qgl.qualituriers.utils;

/**
 * Cette classe represente un polygone qui pourra etre utiliser pour representer un bateau car un polygone à une
 * dirrection
 *
 * @author williamdandrea
 */


public class Polygon extends Shape{

    private double orientation;
    private Point[] vertices;

    public Polygon(double orientation, Point[] vertices) {
        super(Shapes.POLYGON);
        this.orientation = orientation;
        this.vertices = vertices;
    }
}
