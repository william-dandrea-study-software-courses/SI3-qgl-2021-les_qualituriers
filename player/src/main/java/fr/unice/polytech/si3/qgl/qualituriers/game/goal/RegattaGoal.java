package fr.unice.polytech.si3.qgl.qualituriers.game.goal;

import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;

public class RegattaGoal extends Goal{

    private CheckPoint[] checkPoints;

    public RegattaGoal(CheckPoint[] checkPoints) {
        this.checkPoints = checkPoints;
    }
}
