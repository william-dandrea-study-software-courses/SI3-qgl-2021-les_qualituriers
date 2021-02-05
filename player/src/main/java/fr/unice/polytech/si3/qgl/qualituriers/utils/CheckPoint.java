package fr.unice.polytech.si3.qgl.qualituriers.utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;

/**
 * Cette classe represente un checkpoint auquel le bateau devra arriver pour valider une course
 *
 * @author williamdandrea, Alexandre Arcil
 */


public class CheckPoint {

    private final Transform position;
    private final Shape shape;

    @JsonCreator
    public CheckPoint(@JsonProperty("position") Transform transform, @JsonProperty("shape") Shape shape) {
        this.position = transform;
        this.shape = shape;
    }

    public Transform getPosition() {
        return position;
    }

    public Shape getShape() {
        return shape;
    }

    @Override
    public String toString() {
        return "CheckPoint{" +
                "position=" + position +
                ", shape=" + shape +
                '}';
    }
}
