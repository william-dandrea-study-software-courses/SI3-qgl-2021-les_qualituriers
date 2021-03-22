package fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible;

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
    protected Transform position;
    protected Shape shape;

    protected VisibleDeckEntity(VisibleDeckEntities type, Transform position, Shape shape) {
        this.type = type;
        this.position = position;
        this.shape = shape;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof VisibleDeckEntity)) return false;
        var castedObj = (VisibleDeckEntity)obj;
        return castedObj.position.equals(position) && castedObj.shape.equals(shape) && castedObj.type == type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, position, shape);
    }

    public PositionableShape<? extends Shape> getPositionableShape(){
        return PositionableShapeFactory.getPositionable(shape, position);
    }
}
