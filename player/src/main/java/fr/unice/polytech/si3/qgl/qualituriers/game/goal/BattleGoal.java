package fr.unice.polytech.si3.qgl.qualituriers.game.goal;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Cette classe represent l'objectif Bataille (confrontation entre plusieurs bateaux)
 *
 * @author williamdandrea
 */
public class BattleGoal extends Goal {

    @JsonCreator
    public BattleGoal() {
       super(Goals.BATTLE);
    }
}
