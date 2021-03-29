package fr.unice.polytech.si3.qgl.qualituriers.engine.races;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.Wind;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.VisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.game.goal.Goal;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;

import java.util.Random;

public class WebRunnerRace {
    public static final Shape BOAT_SHAPE = new Circle(100);

    private final Goal goal;
    private final WebRunnerBoat ship;
    private final Wind wind;
    private final VisibleDeckEntity[] seaEntities;
    private final int maximumCrewSize;
    private final Transform[] startingPositions;

    @JsonCreator
    public WebRunnerRace(@JsonProperty("goal") Goal goal, @JsonProperty("ship") WebRunnerBoat ship, @JsonProperty("wind") Wind wind, @JsonProperty("seaEntities") VisibleDeckEntity[] seaEntities, @JsonProperty("maximumCrewSize") int maximumCrewSize, @JsonProperty("startingPositions") Transform[] startingPositions) {
        this.goal = goal;
        this.ship = ship;
        this.wind = wind;
        this.seaEntities = seaEntities;
        this.maximumCrewSize = maximumCrewSize;
        this.startingPositions = startingPositions;
    }

    public VisibleDeckEntity[] getSeaEntities() {
        return seaEntities;
    }

    public Wind getWind() {
        return wind;
    }

    public WebRunnerBoat getShip() {
        return ship;
    }

    public Boat getBoat() {
        Random rnd = new Random();
        var start = startingPositions[rnd.nextInt(startingPositions.length)];

        return new Boat(ship.getLife(), start, ship.getName(), ship.getDeck(), ship.getEntities(), BOAT_SHAPE);
    }

    public Goal getGoal() {
        return goal;
    }

    public int getMaximumCrewSize() {
        return maximumCrewSize;
    }
}
