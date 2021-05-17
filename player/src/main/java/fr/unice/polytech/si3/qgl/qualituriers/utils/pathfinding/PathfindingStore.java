package fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding;

import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionablePolygon;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PathfindingStore {
    // TESTED
    private PathfindingResult calculatedPath;
    private int currentNodeToReach = 0;
    private final Set<PositionablePolygon> obstacles = new HashSet<>();

    void setCalculatedPath(PathfindingResult calculatedPath) {
        this.calculatedPath = calculatedPath;
    }
    PathfindingResult getCalculatedPath() {
        return calculatedPath;
    }

    int getCurrentNodeToReach() {
        return currentNodeToReach;
    }
    void setCurrentNodeToReach(int currentNodeToReach) {
        this.currentNodeToReach = currentNodeToReach;
    }

    void addObstacles(List<PositionablePolygon> polygons) {
        this.obstacles.addAll(polygons);
    }

    void addObstaclesTo(List<PositionablePolygon> polygons) {
        this.obstacles.forEach(o -> {
            if(!polygons.contains(o)) polygons.add(o);
        });
    }
}
