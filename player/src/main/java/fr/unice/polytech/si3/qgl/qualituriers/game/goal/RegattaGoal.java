package fr.unice.polytech.si3.qgl.qualituriers.game.goal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;

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

    public CheckPoint[] getCheckPoints() {
        return checkPoints;
    }
}
