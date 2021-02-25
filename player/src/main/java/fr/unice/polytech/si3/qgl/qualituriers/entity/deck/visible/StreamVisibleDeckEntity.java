package fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;

import java.util.Objects;

/**
 * Cette classe représente la perturbation courant qui déviera le bateau
 *
 * @author williamdandrea
 * @author CLODONG Yann
 */
public class StreamVisibleDeckEntity extends VisibleDeckEntity{

    private final double strength;

    @JsonCreator
    public StreamVisibleDeckEntity(@JsonProperty("position") Transform position,
                                   @JsonProperty("shape") Shape shape, @JsonProperty("strength") double strength) {
        super(VisibleDeckEntities.STREAM, position, shape);
        this.strength = strength;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof StreamVisibleDeckEntity)) return false;
        var castedObj = (StreamVisibleDeckEntity)obj;
        return super.equals(obj) && castedObj.strength == strength;
    }

    @Override
    public int hashCode() {
        return Objects.hash(strength);
    }
}
