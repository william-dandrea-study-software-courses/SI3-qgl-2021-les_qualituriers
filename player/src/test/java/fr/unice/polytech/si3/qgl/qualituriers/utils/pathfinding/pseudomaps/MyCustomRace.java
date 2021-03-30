package fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding.pseudomaps;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.VisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;

public class MyCustomRace {
    private final Transform startPosition;
    private final CheckPoint[] checkPoints;
    private final VisibleDeckEntity[] obstacles;

    @JsonCreator
    public MyCustomRace(
            @JsonProperty("startPosition") Transform startPosition,
            @JsonProperty("checkpoints") CheckPoint[] checkPoints,
            @JsonProperty("obstacles") VisibleDeckEntity[] obstacles) {
        this.startPosition = startPosition;
        this.checkPoints = checkPoints;
        this.obstacles = obstacles;
    }

    public CheckPoint[] getCheckPoints() {
        return checkPoints;
    }

    public VisibleDeckEntity[] getObstacles() {
        return obstacles;
    }

    public Transform getStartPosition() {
        return startPosition;
    }
}
