package fr.unice.polytech.si3.qgl.qualituriers.entity.boat;

/**
 * Correspond aux entités dans le bateau. Chaque enum contient le type ainsi que la classe qui le représente.
 *
 * @author williamdandrea
 * @author CLODONG Yann
 */
public enum BoatEntities {
    OAR("OAR", OarBoatEntity.class),
    SAIL("SAIL", SailBoatEntity.class),
    RUDDER("RUDDER", RudderBoatEntity.class),
    WATCH("WATCH", WatchBoatEntity.class),
    CANON("CANON", CanonBoatEntity.class);

    private final String type;
    private final Class<? extends BoatEntity> entity;

    BoatEntities(String type, Class<? extends BoatEntity> entity) {
        this.type = type;
        this.entity = entity;
    }

    public String getType() {
        return type;
    }



}
