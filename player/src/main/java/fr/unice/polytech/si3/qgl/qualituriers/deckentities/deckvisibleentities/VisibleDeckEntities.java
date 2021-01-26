package fr.unice.polytech.si3.qgl.qualituriers.deckentities.deckvisibleentities;

import fr.unice.polytech.si3.qgl.qualituriers.game.goal.Goal;

/**
 * Correspond aux perturbations sur le deck visible. Chaque enum contient le type ainsi que la class qui le représente.
 *
 * @author williamdandrea
 */

public enum VisibleDeckEntities {

    COURANT("STREAM", CourantVisibleDeckEntity.class),
    AUTREBATEAU("SHIP", AutreBateauVisibleDeckEntity.class),
    RECIF("REEF", RecifVisibleDeckEntity.class);

    private final String type;
    private final Class<? extends VisibleDeckEntity> visibleDeckEntity;


    VisibleDeckEntities(String type, Class<? extends VisibleDeckEntity> visibleDeckEntity) {
        this.type = type;
        this.visibleDeckEntity = visibleDeckEntity;
    }
}
