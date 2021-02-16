package engine.mechanics;

import engine.exceptions.SailorNotFoundException;
import engine.exceptions.WrongSailorMovementException;
import engine.races.Race;
import fr.unice.polytech.si3.qgl.qualituriers.Config;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Actions;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Moving;

import java.util.Arrays;
import java.util.List;

public class MovingMechanic extends Mechanic {
    @Override
    public void Execute(List<Action> actions, Race race) {
        var movingAct = actions.stream().filter(a -> a.getType() == Actions.MOVING);

        movingAct.forEach(a -> {
            var act = (Moving)a;
            var sailorOpt = Arrays.stream(race.getSailors()).filter(s -> s.getId() == a.getSailorId()).findAny();
            if(sailorOpt.isEmpty()) throw new SailorNotFoundException(a.getSailorId());
            var sailor = sailorOpt.get();

            if(Math.abs(act.getDistanceX()) > Config.MAX_MOVING_CASES_MARIN || Math.abs(act.getDistanceY()) > Config.MAX_MOVING_CASES_MARIN) throw new WrongSailorMovementException(a.getSailorId(), ((Moving) a).getDistanceX(), ((Moving) a).getDistanceY());

            sailor.setX(sailor.getX() + act.getDistanceX());
            sailor.setY(sailor.getY() + act.getDistanceY());
        });
    }
}
