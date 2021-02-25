package fr.unice.polytech.si3.qgl.qualituriers.utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableShape;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableShapeFactory;

import java.util.Objects;

/**
 * Cette classe représente un checkpoint auquel le bateau devra arriver pour valider une course
 *
 * @author williamdandrea, Alexandre Arcil
 * @author CLODONG Yann
 */
public class CheckPoint {

    @JsonIgnore
    private final PositionableShape<? extends Shape> positionableShape;

    @JsonCreator
    public CheckPoint(@JsonProperty("position") Transform transform, @JsonProperty("shape") Shape shape) {
        this.positionableShape = PositionableShapeFactory.getPositionable(shape, transform);
    }

    public Transform getPosition() {
        return positionableShape.getTransform();
    }

    public Shape getShape() {
        return positionableShape.getShape();
    }

    @JsonIgnore
    public PositionableShape<? extends Shape> getPositionableShape() {
        return this.positionableShape;
    }

    @Override
    public String toString() {
        return "CheckPoint{" +
                "position=" + positionableShape +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckPoint that = (CheckPoint) o;
        return Objects.equals(positionableShape, that.positionableShape);
    }

    @Override
    public int hashCode() {
        return Objects.hash(positionableShape);
    }

}
