package fr.unice.polytech.si3.qgl.qualituriers.game.goal;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.AutreBateauVisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.CourantVisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.RecifVisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;

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
public class Goal {

    private String mode;
    private CheckPoint[] checkPoints;
    // protected Goals type;

    /*@JsonCreator
    public Goal(@JsonProperty("mode") String mode, @JsonProperty("checkpoints") CheckPoint[] checkPoints) {
       // this.type = type;
        this.mode = mode;
        this.checkPoints = checkPoints;
    }*/

    public String getMode() {
        return mode;
    }

    public CheckPoint[] getCheckPoints() {
        return checkPoints;
    }
}
