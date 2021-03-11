package fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding;

import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableShape;

import java.util.List;

public class PathfindingContext {
    private final Boat boat;
    private final List<PositionableShape<Shape>> obstacles;
    private CheckPoint toReach;

    public PathfindingContext(Boat boat, List<PositionableShape<Shape>> obstacles, CheckPoint toReach) {
        this.boat = boat;
        this.obstacles = obstacles;
        this.toReach = toReach;
    }

    void setToReach(CheckPoint checkPoint) {
        this.toReach = checkPoint;
    }

    Boat getBoat() {
        return boat;
    }

    List<PositionableShape<Shape>> getObstacles() {
        return obstacles;
    }

    CheckPoint getToReach() {
        return toReach;
    }
}
