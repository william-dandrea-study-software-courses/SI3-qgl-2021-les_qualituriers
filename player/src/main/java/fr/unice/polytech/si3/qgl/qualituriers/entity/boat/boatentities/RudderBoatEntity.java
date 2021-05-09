package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Cette classe représente l'élément Gouvernail qui sera placé dans un bateau
 *
 * @author williamdandrea, Alexandre Arcil
 */
public class RudderBoatEntity extends BoatEntity {

    @JsonCreator
    public RudderBoatEntity(@JsonProperty("x") int x, @JsonProperty("y") int y) {
        super(BoatEntities.RUDDER, x, y);
    }

    @Override
    public String toString() {
        return "RudderBoatEntity : " +
                "type=" + type.getType() +
                ", x=" + x +
                ", y=" + y +
                ", affectedSailor : " + getSailorAffected() +
                '\n';
    }
}
