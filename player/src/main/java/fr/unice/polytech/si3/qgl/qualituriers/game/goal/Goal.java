package fr.unice.polytech.si3.qgl.qualituriers.game.goal;

/**
 * Cette classe a pour objectif de contenir tout ce qui est commun aux deux types d'objectifs (REGATTA & BATTLE)
 *
 * @author williamdandrea
 */

public class Goal {

    protected Goals type;

    public Goal(Goals type) {
        this.type = type;
    }
}
