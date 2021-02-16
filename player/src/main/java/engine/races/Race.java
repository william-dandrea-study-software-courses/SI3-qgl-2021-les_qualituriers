package engine.races;

import engine.mechanics.Mechanic;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.game.goal.Goal;

public class Race {
    private final Goal goal;
    private final Boat boat;
    private final Marin[] sailors;

    private final Mechanic[] mechanics;

    public Race(Goal goal, Boat boat, Marin[] sailors, Mechanic[] mechanics) {
        this.goal = goal;
        this.boat = boat;
        this.sailors = sailors;
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
}
