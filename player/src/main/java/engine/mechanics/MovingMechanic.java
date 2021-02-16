package engine.mechanics;

import com.fasterxml.jackson.databind.ObjectMapper;
import engine.exceptions.SailorNotFoundException;
import engine.exceptions.WrongSailorMovementException;
import engine.races.Race;
import fr.unice.polytech.si3.qgl.qualituriers.Config;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Actions;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Moving;

import java.util.Arrays;
import java.util.List;

public class MovingMechanic extends Mechanic {
    @Override
    public void execute(List<Action> actions, Race race) {


        for (Action action : actions) {

            boolean breaking = false;
            if (action instanceof Moving) {

                int id = action.getSailorId();
                int distanceX = ((Moving) action).getDistanceX();
                int distanceY = ((Moving) action).getDistanceY();

                for (Marin sailor : race.getSailors()) {

                    if (sailor.getId() == id) {
                        sailor.setX(sailor.getX() + distanceX);
                        sailor.setY(sailor.getY() + distanceY);
                        breaking = true;
                        break;
                    }
                }
            }

            if (breaking) {
                break;
            }
        }
    }
}
