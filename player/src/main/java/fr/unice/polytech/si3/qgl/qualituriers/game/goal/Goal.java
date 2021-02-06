package fr.unice.polytech.si3.qgl.qualituriers.game.goal;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;

/**
 * @author Alexandre Arcil
 * @author CLODONG Yann
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

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof Goal)) return false;
        var castedObj = (Goal)obj;
        return castedObj.mode == mode;
    }
}
