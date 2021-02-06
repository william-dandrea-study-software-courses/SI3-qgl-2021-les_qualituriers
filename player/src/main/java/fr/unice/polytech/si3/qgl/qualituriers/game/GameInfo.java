package fr.unice.polytech.si3.qgl.qualituriers.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.game.goal.Goal;
import fr.unice.polytech.si3.qgl.qualituriers.game.goal.Goals;

import java.util.Arrays;

/**
 * Cette class a pour but d'initialiser le jeu et de permettre de pouvoir faire des actions plus tard sur le jeu
 * @author CLODONG Yann
 */

public class GameInfo {

    private final Goal goal;
    private final Boat ship;
    private final Marin[] sailors;
    private final int shipCount;

    @JsonCreator
    public GameInfo(@JsonProperty("goal") Goal goal, @JsonProperty("ship") Boat ship,
                    @JsonProperty("sailors") Marin[] sailors, @JsonProperty("shipCount") int shipCount) {
        this.goal = goal;
        this.ship = ship;
        this.sailors = sailors;
        this.shipCount = shipCount;
    }

    public Boat getShip() {
        return ship;
    }

    public Goal getGoal() {
        return goal;
    }

    public int getShipCount() {
        return shipCount;
    }

    public Marin[] getSailors() {
        return sailors;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof GameInfo)) return false;
        var castedObj = (GameInfo)obj;
        return castedObj.goal.equals(goal) &&
                castedObj.ship.equals(ship) &&
                castedObj.shipCount == shipCount &&
                Arrays.equals(castedObj.sailors, sailors);
    }
}
