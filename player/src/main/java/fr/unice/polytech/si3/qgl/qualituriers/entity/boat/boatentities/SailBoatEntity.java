package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Cette classe représente l'élément Voile qui sera placé dans un bateau
 *
 * @author williamdandrea, Alexandre Arcil
 * @author CLODONG Yann
 */

public class SailBoatEntity extends BoatEntity {

    private boolean opened;

    @JsonCreator
    public SailBoatEntity(@JsonProperty("x") int x, @JsonProperty("y") int y, @JsonProperty("opened") boolean opened) {
        super(BoatEntities.SAIL, x, y);
        this.opened = opened;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof SailBoatEntity)) return false;
        var castedObj = (SailBoatEntity)obj;
        return super.equals(obj) && castedObj.opened == opened;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), opened);
    }

    @Override
    public String toString() {
        return "SailBoatEntity : " +
                "opened=" + opened +
                ", type=" + type +
                ", x=" + x +
                ", y=" + y +
                '\n';
    }
}
