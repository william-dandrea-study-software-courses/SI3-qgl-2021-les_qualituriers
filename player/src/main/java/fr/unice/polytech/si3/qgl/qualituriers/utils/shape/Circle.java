package fr.unice.polytech.si3.qgl.qualituriers.utils.shape;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Cette classe représente un element rond qui pourra être utiliser pour les différents obstacles
 *
 * @author williamdandrea, Alexandre Arcil
 */
public class Circle extends Shape {

    private final double radius;

    @JsonCreator
    public Circle(@JsonProperty("radius") double radius) {
        super(Shapes.CIRCLE);
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
