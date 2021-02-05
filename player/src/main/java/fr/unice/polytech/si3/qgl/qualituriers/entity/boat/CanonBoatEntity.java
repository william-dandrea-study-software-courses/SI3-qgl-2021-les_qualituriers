package fr.unice.polytech.si3.qgl.qualituriers.entity.boat;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Cette classe représente l'élément Canon qui sera placé dans un bateau
 *
 * @author williamdandrea, Alexandre Arcil
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
}
