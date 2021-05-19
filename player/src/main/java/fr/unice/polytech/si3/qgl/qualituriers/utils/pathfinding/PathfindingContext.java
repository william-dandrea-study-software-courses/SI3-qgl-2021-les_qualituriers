package fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding;

import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.EnemyVisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.VisibleDeckEntities;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.VisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionablePolygon;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableShape;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class PathfindingContext {
    private final Point start, goal;
    private final List<PositionablePolygon> obstacles;
    private final PathfindingStore store;

    private CheckPoint toReach;

    public PathfindingContext(Point start, List<VisibleDeckEntity> obstacles, Point goal, PathfindingStore store) {
        this.start = start;
        this.goal = goal;

        // TESTED
        this.obstacles = new ArrayList<>();
        this.obstacles.addAll(obstacles.stream()
                .map(VisibleDeckEntity::getPositionableShape)
                .map(PositionableShape::getCircumscribedPolygon)
                .collect(Collectors.toList()));

        store.addObstaclesTo(this.obstacles);
        store.addObstacles(obstacles.stream()
                .filter(vde -> !(vde instanceof EnemyVisibleDeckEntity))
                .map(VisibleDeckEntity::getPositionableShape)
                .map(PositionableShape::getCircumscribedPolygon)
                .collect(Collectors.toList()));


        this.store = store;
    }

    Point getStart() {
        return start;
    }

    Point getGoal() {
        return goal;
    }

    List<PositionablePolygon> getObstacles() {
        return obstacles;
    }

    PathfindingStore getStore() {
        return store;
    }
}
