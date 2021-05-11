package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    /**
     * la rame est-elle une rame droite du bateau ?
     * @return true si la rame est une rame droite du bateau
     */
    @JsonIgnore
    public boolean isRightOar() {
        return y != 0;
    }

    /**
     * la rame est-elle une rame gauche du bateau ?
     * @return true si la rame est une rame gauche du bateau
     */
    @JsonIgnore
    public boolean isLeftOar() {
        return y == 0;
    }


    @Override
    public String toString() {
        return "OarBoatEntity : " +
                "type=" + type.getType() +
                ", x=" + x +
                ", y=" + y +
                ", affectedSailor : " + getSailorAffected() +
                '\n';
    }
}
