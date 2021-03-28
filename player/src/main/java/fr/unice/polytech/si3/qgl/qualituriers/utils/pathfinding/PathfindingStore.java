package fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding;

import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionablePolygon;

import java.util.ArrayList;
import java.util.List;

public class PathfindingStore {
    private PathfindingResult calculatedPath;
    private int currentNodeToReach = 0;
    private final List<PositionablePolygon> obstacles = new ArrayList<>();

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

    void addObstacle(PositionablePolygon polygon) {
        if(!obstacles.contains(polygon)) {
            obstacles.add(polygon);
        }
    }

    void addObstaclesTo(List<PositionablePolygon> polygons) {
        this.obstacles.forEach(o -> {
            if(!polygons.contains(o)) polygons.add(o);
        });
    }
}
