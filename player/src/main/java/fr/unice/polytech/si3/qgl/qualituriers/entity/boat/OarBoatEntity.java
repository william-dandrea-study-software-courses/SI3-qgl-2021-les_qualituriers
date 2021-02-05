package fr.unice.polytech.si3.qgl.qualituriers.entity.boat;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Cette classe représente l'élément Rame qui sera placé dans un bateau
 *
 * @author williamdandrea, Alexandre Arcil
 */
public class OarBoatEntity extends BoatEntity {

    @JsonCreator
    public OarBoatEntity(@JsonProperty("x") int x, @JsonProperty("y") int y) {
        super(BoatEntities.OAR, x, y);
    }
}
