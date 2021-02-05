package fr.unice.polytech.si3.qgl.qualituriers.entity.boat;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Cette classe a pour objectif de contenir tout ce qui est commun aux différents types d'entités dans le bateau
 *
 * @author williamdandrea, Alexandre Arcil
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = Void.class)
@JsonSubTypes({
        @JsonSubTypes.Type(name = "OAR", value = OarBoatEntity.class),
        @JsonSubTypes.Type(name = "SAIL", value = SailBoatEntity.class),
        @JsonSubTypes.Type(name = "RUDDER", value = RudderBoatEntity.class),
        @JsonSubTypes.Type(name = "WATCH", value = WatchBoatEntity.class),
        @JsonSubTypes.Type(name = "CANON", value = CanonBoatEntity.class)
})
public abstract class BoatEntity {

    protected BoatEntities type;
    protected int x;
    protected int y;

    public BoatEntity(BoatEntities type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }
}
