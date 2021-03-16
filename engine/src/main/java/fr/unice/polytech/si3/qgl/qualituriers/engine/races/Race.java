package fr.unice.polytech.si3.qgl.qualituriers.engine.races;

import fr.unice.polytech.si3.qgl.qualituriers.engine.mechanics.Mechanic;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.Wind;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.VisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.game.goal.Goal;

public class Race {

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
