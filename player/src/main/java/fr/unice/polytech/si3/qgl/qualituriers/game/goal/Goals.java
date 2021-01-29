package fr.unice.polytech.si3.qgl.qualituriers.game.goal;

/**
 * Correspond aux objectifs (goals). Chaque enum contient le type ainsi que la class qui le représente.
 *
 * @author williamdandrea
 */

public enum Goals {

    REGATTA("REGATTA", RegattaGoal.class),
    BATTLE("BATTLE", BattleGoal.class);

    private final String type;
    private final Class<? extends Goal> goal;


    Goals(String type, Class<? extends Goal> goal) {
        this.type = type;
        this.goal = goal;
    }

    public String getType() {
        return type;
    }
}
