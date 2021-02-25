package fr.unice.polytech.si3.qgl.qualituriers.entity.boat;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Cette classe représente l'élément Canon qui sera placé dans un bateau
 *
 * @author williamdandrea, Alexandre Arcil
 * @author CLODONG Yann
 */
public class CanonBoatEntity extends BoatEntity {

    private final boolean loaded;
    private final double angle;

    @JsonCreator
    public CanonBoatEntity(@JsonProperty("x") int x, @JsonProperty("y") int y,
                           @JsonProperty("loaded") boolean loaded, @JsonProperty("angle") double angle) {
        super(BoatEntities.CANON, x, y);
        this.loaded = loaded;
        this.angle = angle;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof CanonBoatEntity)) return false;
        var castedObj = (CanonBoatEntity)obj;
        return super.equals(obj) && castedObj.loaded == loaded && castedObj.angle == angle;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), loaded, angle);
    }
}
