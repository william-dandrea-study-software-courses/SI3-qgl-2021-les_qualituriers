package fr.unice.polytech.si3.qgl.qualituriers.game.goal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;

import java.util.Arrays;

/**
 * Cette classe représente l'objectif Regatta (course entre des bateaux)
 *
 * @author williamdandrea, CLODONG Yann
 */
public class RegattaGoal extends Goal {

    private CheckPoint[] checkPoints;

    @JsonCreator
    public RegattaGoal(@JsonProperty("checkpoints") CheckPoint[] checkpoints) {
        super(Goals.REGATTA);
        this.checkPoints = checkpoints;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof RegattaGoal)) return false;
        var castedObj = (RegattaGoal)obj;
        return Arrays.equals(checkPoints, castedObj.checkPoints);
    }

    public CheckPoint[] getCheckPoints() {
        return checkPoints;
    }
}
