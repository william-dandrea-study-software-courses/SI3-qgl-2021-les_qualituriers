package fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding.Dijkstra;

import fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding.PathfindingNode;

import java.util.ArrayList;
import java.util.List;

public class PathSteps {
    private final double length;
    private final List<PathfindingNode> nodes;

    PathSteps(PathfindingNode start) {
        this(new ArrayList<>() {{ add(start); }}, Double.MAX_VALUE);
    }

    private PathSteps(List<PathfindingNode> nodes, double length) {
        this.length = length;
        this.nodes = nodes;
    }

    public PathSteps complete(PathfindingNode node) {
        return new PathSteps(new ArrayList<>(nodes) {{ add(node); }}, length + last().getPosition().substract(node.getPosition()).length());
    }

    public PathfindingNode last() {
        return nodes.get(nodes.size() - 1);
    }

    public double length() {
        return length;
    }

    public List<PathfindingNode> getNodes() {
        return nodes;
    }

    public static PathSteps root(PathfindingNode node) {
        return new PathSteps(new ArrayList<>() {{ add(node); }}, 0);
    }
}