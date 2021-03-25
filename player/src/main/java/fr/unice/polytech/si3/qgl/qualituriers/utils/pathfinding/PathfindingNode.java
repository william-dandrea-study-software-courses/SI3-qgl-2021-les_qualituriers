package fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;

import java.util.ArrayList;
import java.util.List;

public class PathfindingNode {
    private final Point position;
    private final List<PathfindingNode> neighbours = new ArrayList<>();

    PathfindingNode(Point position) {
        this.position = position;
    }

    void addNeighbourNode(PathfindingNode node) {
        neighbours.add(node);
    }

    List<PathfindingNode> getNeighbours() {
        return neighbours;
    }

    Point getPosition() {
        return position;
    }
}
