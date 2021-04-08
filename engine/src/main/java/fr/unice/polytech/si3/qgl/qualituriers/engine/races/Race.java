package fr.unice.polytech.si3.qgl.qualituriers.engine.races;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.qualituriers.engine.TurnConfig;
import fr.unice.polytech.si3.qgl.qualituriers.engine.mechanics.Mechanic;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.Wind;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.ReefVisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.VisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.game.goal.Goal;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Collisions;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableCircle;

import java.util.*;

public class Race {

    private GameInfo gi;
    private final Goal goal;
    private final Boat boat;
    private final Marin[] sailors;
    private final Mechanic[] mechanics;
    private final VisibleDeckEntity[] entities;
    private Wind wind;
    private Transform speed;
    private boolean useWatch;
    private final Set<ReefVisibleDeckEntity> seenReefs;

    public Race(Goal goal, Boat boat, Marin[] sailors, Wind wind, Mechanic[] mechanics, VisibleDeckEntity[] entities) {
        this.goal = goal;
        this.boat = boat;
        this.sailors = sailors;
        this.wind = wind;
        this.mechanics = mechanics;
        this.entities = entities;
        this.seenReefs = new HashSet<>();

        this.gi = new GameInfo(goal, boat, sailors, 1, wind, entities);
        this.resetSpeed();
    }

    public Race(String json, Mechanic[] mechanics) throws JsonProcessingException {
        var om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        WebRunnerRace race = om.readValue(json, WebRunnerRace.class);

        List<Marin> sailors = new ArrayList<>();

        int xMax = race.getShip().getDeck().getLength()-1;
        int yMax = race.getShip().getDeck().getWidth()-1;

        int maxSailors = race.getMaximumCrewSize();

        int curSailor = 0;
        for (int y = 0; y <= yMax; y++) {
            for (int x = 0; x < xMax; x++) {
                sailors.add(new Marin(curSailor, x,y,"name"));
                curSailor++;
                if (curSailor == maxSailors) break;
            }
        }

        this.gi = new GameInfo(race.getGoal(), race.getBoat(), sailors.toArray(Marin[]::new), 1, race.getWind(), race.getSeaEntities());
        this.goal = gi.getGoal();
        this.boat = gi.getShip();
        this.sailors = gi.getSailors();
        this.wind = gi.getWind();
        this.entities = gi.getSeaEntities();
        this.seenReefs = new HashSet<>();

        this.mechanics = mechanics;
        this.resetSpeed();
    }

    public Goal getGoal() {
        return goal;
    }

    public Boat getBoat() {
        return boat;
    }

    public Marin[] getSailors() {
        return sailors;
    }

    public Mechanic[] getMechanics() {
        return mechanics;
    }

    public VisibleDeckEntity[] getEntities() {
        return entities;
    }

    public VisibleDeckEntity[] getVisiblesEntities() {
        PositionableCircle circle = new PositionableCircle(new Circle(this.isUsingWatch() ? TurnConfig.FIELD_VISION_ENLARGE : TurnConfig.FIELD_VISION), this.boat.getPosition());
        VisibleDeckEntity[] visibleEntities = Arrays.stream(entities).filter(entity -> Collisions.isColliding(circle, entity.getPositionableShape())).toArray(VisibleDeckEntity[]::new);
        for (VisibleDeckEntity visibleEnt : visibleEntities) {
            if(visibleEnt instanceof ReefVisibleDeckEntity)
                this.seenReefs.add((ReefVisibleDeckEntity) visibleEnt);
        }
        return visibleEntities;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public void addSpeed(Transform point) {
        this.speed = this.speed.translate(point);
    }

    public void resetSpeed() {
        this.speed = new Transform(0, 0, 0);
    }

    public Transform getSpeed() {
        return speed;
    }

    public void setUseWatch(boolean useWatch) {
        this.useWatch = useWatch;
    }

    public boolean isUsingWatch() {
        return useWatch;
    }

    public Set<ReefVisibleDeckEntity> getSeenReefs() {
        return seenReefs;
    }
}
