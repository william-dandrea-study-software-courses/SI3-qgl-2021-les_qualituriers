package fr.unice.polytech.si3.qgl.qualituriers.engine.races;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.qualituriers.engine.mechanics.Mechanic;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.Wind;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.VisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.game.goal.Goal;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;

import java.util.ArrayList;
import java.util.List;

public class Race {

    private GameInfo gi;
    private final Goal goal;
    private final Boat boat;
    private final Marin[] sailors;
    private final Mechanic[] mechanics;
    private final VisibleDeckEntity[] entities;
    private Wind wind;
    private Transform speed;

    public Race(Goal goal, Boat boat, Marin[] sailors, Wind wind, Mechanic[] mechanics, VisibleDeckEntity[] entities) {
        this.goal = goal;
        this.boat = boat;
        this.sailors = sailors;
        this.wind = wind;
        this.mechanics = mechanics;
        this.entities = entities;

        this.gi = new GameInfo(goal, boat, sailors, 1, wind, entities);
        this.resetSpeed();
    }

    public Race(String json, Mechanic[] mechanics) throws JsonProcessingException {
        var om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        WebRunnerRace race = om.readValue(json, WebRunnerRace.class);

        List<Marin> sailors = new ArrayList<>();
        for(int i = 0; i < race.getMaximumCrewSize(); i++) {
            sailors.add(new Marin(i, 0, 0, "marin"+i));
        }

        this.gi = new GameInfo(race.getGoal(), race.getBoat(), sailors.toArray(Marin[]::new), 1, race.getWind(), race.getSeaEntities());
        this.goal = gi.getGoal();
        this.boat = gi.getShip();
        this.sailors = gi.getSailors();
        this.wind = gi.getWind();
        this.entities = gi.getSeaEntities();

        this.mechanics = mechanics;
        this.resetSpeed();
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

    public void addSpeed(Transform point) {
        this.speed = this.speed.translate(point);
    }

    public void resetSpeed() {
        this.speed = new Transform(0, 0, 0);
    }

    public Transform getSpeed() {
        return speed;
    }
}
