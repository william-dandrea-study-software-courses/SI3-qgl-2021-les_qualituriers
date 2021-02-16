package fr.unice.polytech.si3.qgl.qualituriers.entity.boat;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Correspond aux entités dans le bateau. Chaque enum contient le type ainsi que la classe qui le représente.
 *
 * @author williamdandrea
 * @author CLODONG Yann
 */
public enum BoatEntities {
    OAR("oar", OarBoatEntity.class),
    SAIL("sail", SailBoatEntity.class),
    RUDDER("rudder", RudderBoatEntity.class),
    WATCH("watch", WatchBoatEntity.class),
    CANON("cannon", CanonBoatEntity.class);

    @JsonIgnore
    private final String type;
    private final Class<? extends BoatEntity> entity;

    BoatEntities(String type, Class<? extends BoatEntity> entity) {
        this.type = type;
        this.entity = entity;
    }

    @JsonIgnore
    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "BoatEntities{" +
                "type='" + type + '\'' +
                ", entity=" + entity +
                '}';
    }
}
