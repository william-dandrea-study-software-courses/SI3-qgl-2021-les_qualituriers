package fr.unice.polytech.si3.qgl.qualituriers.game.goal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;

import java.util.Arrays;

/**
 * Cette classe représente l'objectif Regatta (course entre des bateaux)
 *
 * @author williamdandrea, CLODONG Yann
 */
public class RegattaGoal extends Goal {

    private final CheckPoint[] checkPoints;

    @JsonCreator
    public RegattaGoal(@JsonProperty("checkpoints") CheckPoint[] checkpoints) {
        super(Goals.REGATTA);
        this.checkPoints = checkpoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RegattaGoal goal = (RegattaGoal) o;
        return Arrays.equals(checkPoints, goal.checkPoints);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Arrays.hashCode(checkPoints);
        return result;
    }

    public CheckPoint[] getCheckPoints() {
        return checkPoints;
    }
}
