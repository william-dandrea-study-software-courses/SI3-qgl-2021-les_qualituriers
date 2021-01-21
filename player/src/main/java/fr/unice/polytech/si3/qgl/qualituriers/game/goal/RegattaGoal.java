package fr.unice.polytech.si3.qgl.qualituriers.game.goal;

import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;


/**
 * Cette classe represente l'objectif Regatta (course entre des bateaux)
 *
 * @author williamdandrea
 */


public class RegattaGoal extends Goal{

    private CheckPoint[] checkPoints;

    public RegattaGoal(CheckPoint[] checkPoints) {
        super(Goals.REGATTA);
        this.checkPoints = checkPoints;
    }
}
