package fr.unice.polytech.si3.qgl.qualituriers.game;

import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.game.goal.Goals;

/**
 * Cette class a pour but d'initialiser le jeu et de permettre de pouvoir faire des actions plus tard sur le jeu
 */

public class InitGame {

    private Goals goal;
    private Boat ship;
    private Marin[] sailors;
    private int shipCount;

    public InitGame(Goals goal, Boat ship, Marin[] sailors, int shipCount) {
        this.goal = goal;
        this.ship = ship;
        this.sailors = sailors;
        this.shipCount = shipCount;
    }
}
