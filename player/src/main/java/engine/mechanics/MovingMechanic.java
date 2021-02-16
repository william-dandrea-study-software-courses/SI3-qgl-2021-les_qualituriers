package engine.mechanics;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    }
}
