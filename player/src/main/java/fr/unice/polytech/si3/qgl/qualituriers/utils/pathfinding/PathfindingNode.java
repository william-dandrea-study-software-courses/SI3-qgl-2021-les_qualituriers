package fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Collisions;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Segment;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionablePolygon;

import java.util.ArrayList;
import java.util.List;

public class PathfindingNode {
    private final Point position;
    private final List<PathfindingNode> reachableNodes = new ArrayList<>();
    private final PositionablePolygon owner;

    PathfindingNode(Point position, PositionablePolygon owner) {
        this.position = position;
        this.owner = owner;
    }

    void addReachableNode(PathfindingNode node) {
        reachableNodes.add(node);
    }
    void removeReachableNode(PathfindingNode node) {
        reachableNodes.remove(node);
    }

    List<PathfindingNode> getReachableNodes() {
        return reachableNodes;
    }

    Point getPosition() {
        return position;
    }

    double calculateHeuristic(PathfindingNode goal) {
        return goal.getPosition().substract(position).length();
    }

    static List<PathfindingNode> createFrom(PositionablePolygon positionablePolygon) {
        var vertices = positionablePolygon.getShape().getVertices(positionablePolygon.getTransform());
        List<PathfindingNode> nodes = new ArrayList<>();

        for (Point vertex : vertices) nodes.add(new PathfindingNode(vertex, positionablePolygon));

        for(int i = 0; i < vertices.length; i++) {
            int indexNeighbourSide1 = i + 1 >= nodes.size() ? 0 : i + 1;
            int indexNeighbourSide2 = i <= 0 ? nodes.size() - 1 : i - 1;
            nodes.get(i).addReachableNode(nodes.get(indexNeighbourSide1));
            nodes.get(i).addReachableNode(nodes.get(indexNeighbourSide2));
        }

        return nodes;
    }

    public void checkRoads(List<PositionablePolygon> obstacles) {
        List<PathfindingNode> unreachable = new ArrayList<>();
        for (var reachable : reachableNodes) {
            if(Collisions.raycast(new Segment(reachable.getPosition(), getPosition()), obstacles.stream().filter(o -> o != this.owner))) {
                reachable.removeReachableNode(this);
                unreachable.add(reachable);
            }
        }
        unreachable.forEach(this::removeReachableNode);
    }
}
