package fr.unice.polytech.si3.qgl.qualituriers.game.goal;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * @author Alexandre Arcil
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "mode", defaultImpl = Void.class)
@JsonSubTypes({
        @JsonSubTypes.Type(name = "REGATTA", value = RegattaGoal.class),
        @JsonSubTypes.Type(name = "BATTLE", value = BattleGoal.class)
})
public class Goal {

    protected Goals mode;

    public Goal(Goals mode) {
        this.mode = mode;
    }

    public Goals getMode() {
        return mode;
    }
}
