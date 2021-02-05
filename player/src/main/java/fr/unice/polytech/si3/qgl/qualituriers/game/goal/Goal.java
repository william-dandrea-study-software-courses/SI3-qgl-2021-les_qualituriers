package fr.unice.polytech.si3.qgl.qualituriers.game.goal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.AutreBateauVisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.CourantVisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.RecifVisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;


public class Goal {

    private String mode;
    private CheckPoint[] checkPoints;
    // protected Goals type;

    @JsonCreator
    public Goal(@JsonProperty("mode") String mode, @JsonProperty("checkpoints") CheckPoint[] checkPoints) {
       // this.type = type;
        this.mode = mode;
        this.checkPoints = checkPoints;
    }

    public Goal(Goals battle) {
    }

    public String getMode() {
        return mode;
    }

    public CheckPoint[] getCheckPoints() {
        return checkPoints;
    }
}
