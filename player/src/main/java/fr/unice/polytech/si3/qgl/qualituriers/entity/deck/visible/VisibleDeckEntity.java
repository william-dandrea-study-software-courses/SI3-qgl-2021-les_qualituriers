package fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableShape;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableShapeFactory;

import java.util.Objects;


/**
 * Cette classe représente les elements commun aux différentes perturbations visibles que nous aurons sur le deck
 *
 * @author williamdandrea
 * @author CLODONG Yann
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = Void.class)
@JsonSubTypes({
        @JsonSubTypes.Type(name = "stream", value = StreamVisibleDeckEntity.class),
        @JsonSubTypes.Type(name = "ship", value = EnemyVisibleDeckEntity.class),
        @JsonSubTypes.Type(name = "reef", value = ReefVisibleDeckEntity.class)
})
public abstract class VisibleDeckEntity {

    protected VisibleDeckEntities type;
    @JsonIgnore
    protected PositionableShape<? extends Shape> positionableShape;

    protected VisibleDeckEntity(@JsonProperty("type")VisibleDeckEntities type, @JsonProperty("position") Transform position, @JsonProperty("shape") Shape shape) {
        this.type = type;
        this.positionableShape = PositionableShapeFactory.getPositionable(shape, position);
    }

    public Transform getPosition() {
        return positionableShape.getTransform();
    }

    public Shape getShape() {
        return positionableShape.getShape();
    }

    public PositionableShape<? extends Shape> getPositionableShape() {
        return positionableShape;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VisibleDeckEntity that = (VisibleDeckEntity) o;
        return type == that.type && Objects.equals(positionableShape, that.positionableShape);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, positionableShape);
    }

    @Override
    public String toString() {
        return "VisibleDeckEntity{" +
                "type=" + type +
                ", positionableShape=" + positionableShape +
                '}';
    }

    public PositionableShape<? extends Shape> getPositionableShape(){
        return PositionableShapeFactory.getPositionable(shape, position);
    }
}
