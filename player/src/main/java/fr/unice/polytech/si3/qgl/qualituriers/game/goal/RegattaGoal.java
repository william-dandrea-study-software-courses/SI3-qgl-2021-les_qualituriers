package fr.unice.polytech.si3.qgl.qualituriers.game.goal;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;

/**
 * Cette classe représente l'objectif Regatta (course entre des bateaux)
 *
 * @author williamdandrea
 */
public class RegattaGoal extends Goal {

    private CheckPoint[] checkPoints;

    public RegattaGoal(@JsonProperty("checkpoints") CheckPoint[] checkPoints) {
        super(Goals.REGATTA);
    }
}
