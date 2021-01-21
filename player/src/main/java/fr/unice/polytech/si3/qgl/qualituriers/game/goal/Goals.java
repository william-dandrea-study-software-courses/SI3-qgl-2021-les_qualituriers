package fr.unice.polytech.si3.qgl.qualituriers.game.goal;

import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;

public enum Goals {

    REGATTA("REGATTA", RegattaGoal.class),
    BATTLE("BATTLE", BattleGoal.class);

    private final String type;
    private final Class<? extends Goal> goal;


    Goals(String type, Class<? extends Goal> goal) {
        this.type = type;
        this.goal = goal;
    }
}
