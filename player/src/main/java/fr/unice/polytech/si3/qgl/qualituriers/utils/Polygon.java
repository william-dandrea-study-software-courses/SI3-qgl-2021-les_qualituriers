package fr.unice.polytech.si3.qgl.qualituriers.utils;

public class Polygon {

    private double orientation;
    private Point[] vertices;

    public Polygon(double orientation, Point[] vertices) {
        this.orientation = orientation;
        this.vertices = vertices;
    }
}
