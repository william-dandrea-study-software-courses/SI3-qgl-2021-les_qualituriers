package fr.unice.polytech.si3.qgl.qualituriers.engine.mechanics;

import fr.unice.polytech.si3.qgl.qualituriers.engine.races.Race;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Turn;

import java.util.List;

public class RudderMechanic extends Mechanic {

    @Override
    public void execute(List<Action> actions, Race race) {
        for (Action action : actions) {
            if(action instanceof Turn) {
                Turn turnAction = (Turn) action;
                race.getBoat().setPosition(race.getBoat().getPosition().rotate(turnAction.getRotation()));
            }
        }
    }

}
