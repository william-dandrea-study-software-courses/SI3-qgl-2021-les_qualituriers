package fr.unice.polytech.si3.qgl.qualituriers.utils.shape;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Cette classe represente un element rond qui pourra etre utiliser pour les différents obstacles
 *
 * @author williamdandrea
 */


public class Circle extends Shape{

    private double radius;

    public Circle(@JsonProperty("radius") double radius) {
        super(Shapes.CIRCLE.getType());
        this.radius = radius;
    }


    @Override
    public String toString() {
        return "Circle{" +
                "radius=" + radius +
                ", type=" + type +
                '}';
    }

    public double getRadius() {
        return radius;
    }
}
