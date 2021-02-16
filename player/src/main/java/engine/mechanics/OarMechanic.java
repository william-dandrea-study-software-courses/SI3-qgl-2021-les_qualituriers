package engine.mechanics;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import engine.exceptions.SailorAssignmentNotFound;
import engine.exceptions.SailorNotFoundException;
import engine.races.Race;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.BoatEntities;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.OarBoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Actions;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Oar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OarMechanic extends Mechanic {
    private final double maxSpeed = 1;

    @Override
    public void execute(List<Action> actions, Race race)  {
        int left = 0;
        int right = 0;

        for(var action : actions) {
            if(action instanceof Oar) {
                var sailorOpt = Arrays.stream(race.getSailors()).filter(m -> m.getId() == action.getSailorId()).findAny();
                if(sailorOpt.isEmpty()) throw new SailorNotFoundException(action.getSailorId());
                var sailor = sailorOpt.get();

                var oarOpt = Arrays.stream(race.getBoat().getEntities()).filter(a -> a.getType() == BoatEntities.OAR && a.getPosition().equals(sailor.getPosition())).findAny();
                if(oarOpt.isEmpty()) throw new SailorAssignmentNotFound(sailor.getId(), sailor.getPosition(), BoatEntities.OAR);
                var oar = (OarBoatEntity)oarOpt.get();

                if(oar.isLeftOar()) left++;
                else right++;
            }
        }

        int nOar = (int)Arrays.stream(race.getBoat().getEntities()).filter(e -> e.getType() == BoatEntities.OAR).count();
        var vitesse = 165 * (left + right) / nOar;

        var phi = race.getBoat().getPosition().getOrientation();
        var angle = Math.PI * (right - left) / nOar + phi;


        var dx = vitesse * Math.cos(angle);
        var dy = vitesse * Math.sin(angle);
        var p = new Point(dx + race.getBoat().getPosition().getX(), dy + race.getBoat().getPosition().getY());

        race.getBoat().setTransform(new Transform(p, angle));
    }
}
