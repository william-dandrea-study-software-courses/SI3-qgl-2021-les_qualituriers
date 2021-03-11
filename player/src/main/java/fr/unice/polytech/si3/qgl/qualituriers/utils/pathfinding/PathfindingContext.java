package fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding;

import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableShape;

import java.util.ArrayList;
import java.util.List;

public class PathfindingContext {
    private final Boat boat;
    private final List<PositionableShape<Shape>> obstacles;

    private final List<CheckPoint> checkPoints;
    private CheckPoint toReach;

    public PathfindingContext(Boat boat, List<PositionableShape<Shape>> obstacles, List<CheckPoint> checkPoints) {
        this.boat = boat;
        this.obstacles = obstacles;
        this.checkPoints = checkPoints;
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

    List<CheckPoint> getCheckPoints() {
        return checkPoints;
    }
}
