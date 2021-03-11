package fr.unice.polytech.si3.qgl.qualituriers.engine.mechanics;

import fr.unice.polytech.si3.qgl.qualituriers.engine.races.Race;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;

import java.util.List;

public abstract class Mechanic {

    public abstract void execute(List<Action> actions, Race race);
}
