package fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible;

/**
 * Correspond aux perturbations sur le deck visible. Chaque enum contient le type ainsi que la class qui le représente.
 *
 * @author williamdandrea
 */
public enum VisibleDeckEntities {

    STREAM("STREAM", StreamVisibleDeckEntity.class),
    ENEMY("SHIP", EnemyVisibleDeckEntity.class),
    REEF("REEF", ReefVisibleDeckEntity.class);

    private final String type;
    private final Class<? extends VisibleDeckEntity> visibleDeckEntity;


    VisibleDeckEntities(String type, Class<? extends VisibleDeckEntity> visibleDeckEntity) {
        this.type = type;
        this.visibleDeckEntity = visibleDeckEntity;
    }

    @Override
    public String toString() {
        return "VisibleDeckEntities{" +
                "type='" + type + '\'' +
                ", visibleDeckEntity=" + visibleDeckEntity +
                '}';
    }
}
