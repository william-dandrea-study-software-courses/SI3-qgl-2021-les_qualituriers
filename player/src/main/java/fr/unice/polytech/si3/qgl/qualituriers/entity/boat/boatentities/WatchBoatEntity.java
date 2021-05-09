package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Cette classe représente l'élément VIGIE qui sera placé dans un bateau
 *
 * @author williamdandrea, Alexandre Arcil
 */
public class WatchBoatEntity extends BoatEntity {

    @JsonCreator
    public WatchBoatEntity(@JsonProperty("x") int x, @JsonProperty("y") int y) {
        super(BoatEntities.WATCH, x, y);
    }

    @Override
    public String toString() {
        return "WatchBoatEntity : " +
                "type=" + type.getType() +
                ", x=" + x +
                ", y=" + y +
                ", affectedSailor : " + getSailorAffected() +
                '\n';
    }
}
