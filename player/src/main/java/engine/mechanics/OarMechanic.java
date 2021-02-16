package engine.mechanics;

import engine.exceptions.SailorAssignmentNotFound;
import engine.exceptions.SailorNotFoundException;
import engine.races.Race;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.BoatEntities;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.OarBoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OarMechanic extends Mechanic {
    private final double maxSpeed = 1;

    @Override
    public void Execute(List<Action> actions, Race race) {
    }
}
