package fr.unice.polytech.si3.qgl.qualituriers.engine.races;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import fr.unice.polytech.si3.qgl.qualituriers.engine.mechanics.Mechanic;
import fr.unice.polytech.si3.qgl.qualituriers.engine.serializers.RectangleSerializer;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.Wind;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.VisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.game.goal.Goal;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;

public class Race {

    private GameInfo gi;
    private final Goal goal;
    private final Boat boat;
    private final Marin[] sailors;
    private final Mechanic[] mechanics;
    private final VisibleDeckEntity[] entities;
    private Wind wind;

    public Race(Goal goal, Boat boat, Marin[] sailors, Wind wind, Mechanic[] mechanics, VisibleDeckEntity[] entities) {
        this.goal = goal;
        this.boat = boat;
        this.sailors = sailors;
        this.wind = wind;
        this.mechanics = mechanics;
        this.entities = entities;

        this.gi = new GameInfo(goal, boat, sailors, 1, wind, entities);
    }

    public Race(String json, Mechanic[] mechanics) throws JsonProcessingException {
        var om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        this.gi = om.readValue(json, GameInfo.class);
        this.goal = gi.getGoal();
        this.boat = gi.getShip();
        this.sailors = gi.getSailors();
        this.wind = gi.getWind();
        this.entities = gi.getSeaEntities();

        this.mechanics = mechanics;
    }

    public Goal getGoal() {
        return goal;
    }

    public Boat getBoat() {
        return boat;
    }

    public Marin[] getSailors() {
        return sailors;
    }

    public Mechanic[] getMechanics() {
        return mechanics;
    }

    public VisibleDeckEntity[] getEntities() {
        return entities;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

}
