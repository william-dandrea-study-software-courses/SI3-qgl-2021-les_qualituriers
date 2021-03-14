package fr.unice.polytech.si3.qgl.qualituriers.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.Wind;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.VisibleDeckEntities;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.VisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.game.goal.Goal;

import java.util.Arrays;
import java.util.Objects;

/**
 * Cette class a pour but d'initialiser le jeu et de permettre de pouvoir faire des actions plus tard sur le jeu
 * @author CLODONG Yann
 */

public class GameInfo {

    private final Goal goal;
    private Boat ship;
    private Marin[] sailors;
    private int shipCount;
    private Wind wind;
    private VisibleDeckEntity[] seaEntities;

    @JsonCreator
    public GameInfo(@JsonProperty("goal") Goal goal, @JsonProperty("ship") Boat ship,
                    @JsonProperty("sailors") Marin[] sailors, @JsonProperty("shipCount") int shipCount,
                    @JsonProperty("wind") Wind wind, @JsonProperty("seaEntities") VisibleDeckEntity[] seaEntities) {
        this.goal = goal;
        this.ship = ship;
        this.sailors = sailors;
        this.shipCount = shipCount;
        this.wind = wind;
        this.seaEntities = seaEntities;
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

    public Wind getWind() {
        return wind;
    }

    public VisibleDeckEntity[] getSeaEntities() {
        return seaEntities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameInfo gameInfo = (GameInfo) o;
        return shipCount == gameInfo.shipCount && Objects.equals(goal, gameInfo.goal)
                && Objects.equals(ship, gameInfo.ship) && Arrays.equals(sailors, gameInfo.sailors)
                && Objects.equals(wind, gameInfo.wind) && Arrays.equals(seaEntities, gameInfo.seaEntities);
    }

    public void setShip(Boat ship) {
        this.ship = ship;
    }

    public void setSailors(Marin[] sailors) {
        this.sailors = sailors;
    }

    public void setShipCount(int shipCount) {
        this.shipCount = shipCount;
    }



    @Override
    public int hashCode() {
        int result = Objects.hash(goal, ship, shipCount);
        result = 31 * result + Arrays.hashCode(sailors);
        return result;
    }
}
