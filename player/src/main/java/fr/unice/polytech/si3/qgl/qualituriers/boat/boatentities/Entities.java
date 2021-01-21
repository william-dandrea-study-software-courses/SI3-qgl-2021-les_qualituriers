package fr.unice.polytech.si3.qgl.qualituriers.boat.boatentities;

/**
 * Correspond aux entités dans le bateau. Chaque enum contient le type ainsi que la classe qui le représente.
 *
 * @author williamdandrea
 */

public enum Entities {
    RAME("OAR_B", RameEntity.class),
    VOILE("SAIL", VoileEntity.class),
    GOUVERNAIL("RUDDER", GouvernailEntity.class),
    VIGIE("WATCH", VigieEntity.class),
    CANON("CANON", CanonEntity.class);

    private final String type;
    private final Class<? extends Entity> entity;

    Entities(String type, Class<? extends Entity> entity) {
        this.type = type;
        this.entity = entity;
    }
}
