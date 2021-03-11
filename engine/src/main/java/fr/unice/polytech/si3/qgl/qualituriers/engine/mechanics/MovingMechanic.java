package fr.unice.polytech.si3.qgl.qualituriers.engine.mechanics;

import fr.unice.polytech.si3.qgl.qualituriers.engine.races.Race;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Moving;

import java.util.List;

public class MovingMechanic extends Mechanic {
    @Override
    public void execute(List<Action> actions, Race race) {


        for (Action action : actions) {

            //boolean breaking = false;
            if (action instanceof Moving) {

                int id = action.getSailorId();
                int distanceX = ((Moving) action).getDistanceX();
                int distanceY = ((Moving) action).getDistanceY();

                for (Marin sailor : race.getSailors()) {

                    if (sailor.getId() == id) {
                        sailor.setX(sailor.getX() + distanceX);
                        sailor.setY(sailor.getY() + distanceY);
                        //breaking = true;
                        break;
                    }
                }
            }
            /*
            if (breaking) {
                break;
            }*/
        }
    }
}
