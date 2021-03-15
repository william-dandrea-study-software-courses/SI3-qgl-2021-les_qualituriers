package fr.unice.polytech.si3.qgl.qualituriers.utils.shape;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableCircle;

import java.util.Objects;

/**
 * Cette classe représente un element rond qui pourra être utiliser pour les différents obstacles
 *
 * @author williamdandrea, Alexandre Arcil, CLODONG Yann
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

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof Circle)) return false;
        var castedObj = (Circle)obj;
        return castedObj.radius == radius;
    }

    @Override
    public int hashCode() {
        return Objects.hash(radius);
    }

    @Override
    public boolean isIn(Point position) {
        return position.length() <= radius;
    }

    @Override
    public PositionableCircle getCircumscribed() {
        return new PositionableCircle(this, Transform.ZERO);
    }

    public double getRadius() {
        return radius;
    }
}
