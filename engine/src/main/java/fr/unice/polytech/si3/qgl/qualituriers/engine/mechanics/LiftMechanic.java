package fr.unice.polytech.si3.qgl.qualituriers.engine.mechanics;

import fr.unice.polytech.si3.qgl.qualituriers.engine.exceptions.SailorNotFoundException;
import fr.unice.polytech.si3.qgl.qualituriers.engine.races.Race;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.SailBoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.Wind;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.LiftSail;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.LowerSail;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LiftMechanic extends Mechanic {

    @Override
    public void execute(List<Action> actions, Race race) {

        for (Action action : actions) {
            if(action instanceof LiftSail) {
                Marin marin = this.getSailor(action.getSailorId(), race);
                SailBoatEntity sail = this.getSailAt(marin, race);
                sail.setOpened(true);
            } else if(action instanceof LowerSail) {
                Marin marin = this.getSailor(action.getSailorId(), race);
                SailBoatEntity sail = this.getSailAt(marin, race);
                sail.setOpened(false);
            }
        }

        Wind wind = race.getWind();
        if(wind != null) {
            Boat boat = race.getBoat();
            List<SailBoatEntity> sails = this.getSails(boat);
            if(!sails.isEmpty()) {
                Transform position = boat.getPosition();
                long sailsOpened = sails.stream().filter(SailBoatEntity::isOpened).count();
                double speed = (sailsOpened / (double) sails.size()) * wind.getStrength() * Math.cos(wind.getOrientation() - position.getOrientation());
                Point point = new Point(position.getOrientation()).scalar(speed);
                boat.setPosition(position.translate(point));
            }
        }
    }

    private Marin getSailor(int id, Race race) {
        return Arrays.stream(race.getSailors()).filter(marin -> marin.getId() == id).findFirst().orElseThrow(() -> new SailorNotFoundException(id));
    }

    private SailBoatEntity getSailAt(Marin marin, Race race) {
        return (SailBoatEntity) Arrays.stream(race.getBoat().getEntities()).filter(boatEntity -> boatEntity instanceof SailBoatEntity &&
                boatEntity.getPosition().equals(marin.getPosition())).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Il n'y as pas de voile la où se trouve le marin "+marin.getId()+"("+marin.getPosition()+")"));
    }

    private List<SailBoatEntity> getSails(Boat boat) {
        return Arrays.stream(boat.getEntities()).filter(boatEntity -> boatEntity instanceof SailBoatEntity)
                .map(boatEntity -> (SailBoatEntity) boatEntity).collect(Collectors.toList());
    }

}
