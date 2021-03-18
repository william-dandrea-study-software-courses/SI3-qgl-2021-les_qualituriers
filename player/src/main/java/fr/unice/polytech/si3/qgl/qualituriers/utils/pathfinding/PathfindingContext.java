package fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding;

import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableShape;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class PathfindingContext {
    private final Boat boat;
    private final List<PositionableShape<? extends Shape>> obstacles;

    private CheckPoint toReach;

    public PathfindingContext(Boat boat, List<PositionableShape<? extends Shape>> obstacles, CheckPoint checkPoint) {
        this.boat = boat;
        this.obstacles = obstacles;
        this.toReach = checkPoint;
    }

    void setToReach(CheckPoint checkPoint) {
        this.toReach = checkPoint;
    }

    Boat getBoat() {
        return boat;
    }

    List<PositionableShape<? extends Shape>> getObstacles() {
        return obstacles;
    }

    CheckPoint getToReach() {
        return toReach;
    }
}
