package fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible;

/**
 * Correspond aux perturbations sur le deck visible. Chaque enum contient le type ainsi que la class qui le représente.
 *
 * @author williamdandrea
 */

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Cette classe a pour objectif de contenir tout ce qui est commun aux deux types d'objectifs (REGATTA & BATTLE)
 *
 * @author williamdandrea
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "mode", defaultImpl = Void.class)
@JsonSubTypes({
        @JsonSubTypes.Type(name = "STREAM", value = CourantVisibleDeckEntity.class),
        @JsonSubTypes.Type(name = "SHIP", value = AutreBateauVisibleDeckEntity.class),
        @JsonSubTypes.Type(name = "REEF", value = RecifVisibleDeckEntity.class)
})

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
