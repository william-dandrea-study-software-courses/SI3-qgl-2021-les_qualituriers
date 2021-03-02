package fr.unice.polytech.si3.qgl.qualituriers.game.goal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Objects;

/**
 * @author Alexandre Arcil
 * @author CLODONG Yann
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "mode", defaultImpl = Void.class)
@JsonSubTypes({
        @JsonSubTypes.Type(name = "REGATTA", value = RegattaGoal.class),
        @JsonSubTypes.Type(name = "BATTLE", value = BattleGoal.class)
})
public abstract class Goal {

    protected Goals mode;

    public Goal(Goals mode) {
        this.mode = mode;
    }

    @JsonIgnore
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

    @Override
    public int hashCode() {
        return Objects.hash(mode);
    }
}
