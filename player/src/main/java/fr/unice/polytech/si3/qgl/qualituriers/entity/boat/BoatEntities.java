package fr.unice.polytech.si3.qgl.qualituriers.entity.boat;

/**
 * Correspond aux entités dans le bateau. Chaque enum contient le type ainsi que la classe qui le représente.
 *
 * @author williamdandrea
 */

public enum BoatEntities {
    RAME("OAR", RameBoatEntity.class),
    VOILE("SAIL", VoileBoatEntity.class),
    GOUVERNAIL("RUDDER", GouvernailBoatEntity.class),
    VIGIE("WATCH", VigieBoatEntity.class),
    CANON("CANON", CanonBoatEntity.class);

    private final String type;
    private final Class<? extends BoatEntity> entity;

    BoatEntities(String type, Class<? extends BoatEntity> entity) {
        this.type = type;
        this.entity = entity;
    }
}
