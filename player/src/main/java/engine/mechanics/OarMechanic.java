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
        int left = 0;
        int right = 0;

        List<OarBoatEntity> usedoar = new ArrayList<>();

        actions.stream().filter(a -> a.getType() == Actions.OAR).map(action -> {

            var sailorOpt = Arrays.stream(race.getSailors()).filter(m -> m.getId() == action.getSailorId()).findAny();
            if(sailorOpt.isEmpty()) throw new SailorNotFoundException(action.getSailorId());

            var sailor = sailorOpt.get();
            var sailorPos = sailor.getPosition();

            var oarOpt = Arrays.stream(race.getBoat().getEntities()).filter(e -> e.getPosition().equals(sailorPos) && e.getType() == BoatEntities.OAR).findAny();
            if(oarOpt.isEmpty()) throw new SailorAssignmentNotFound(sailor.getId(), sailor.getPosition(), BoatEntities.OAR);
            var oar = (OarBoatEntity)oarOpt.get();

            return oar;
        }).forEach(usedoar::add);

        for(var oar : usedoar) {
            if(oar.isLeftOar()) left++;
            else right++;
        }
        var dtheta = (right - left) * Math.PI / 6;
        race.getBoat().getPosition().rotate(dtheta);

        race.getBoat().getPosition().translate(race.getBoat().getPosition().right().scalar(Math.min(left, right) * maxSpeed));

        //Point bendCenter =
    }
}
