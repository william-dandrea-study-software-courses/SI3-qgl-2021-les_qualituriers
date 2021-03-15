package fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;

/**
 * Cette classe représente la perturbation récif qui sera un obstacle pour notre bateau
 *
 * @author williamdandrea
 */
public class ReefVisibleDeckEntity extends VisibleDeckEntity {

    @JsonCreator
    public ReefVisibleDeckEntity(@JsonProperty("position") Transform position, @JsonProperty("shape") Shape shape) {
        super(VisibleDeckEntities.REEF, position, shape);
    }

    @Override
    public String toString() {
        return "ReefVisibleDeckEntity{" +
                "type=" + type +
                ", position=" + position +
                ", shape=" + shape +
                '}';
    }
}
