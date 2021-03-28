package fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding;

import java.util.ArrayList;
import java.util.List;

public class Path {
    private List<PathfindingNode> nodes;
    private boolean resolved = false;

    Path() {
        this.nodes = new ArrayList<>();
    }

    PathfindingNode getFirstNode() {
        return nodes.get(0);
    }

    boolean contains(PathfindingNode node) {
        return nodes.contains(node);
    }

    void addNode(PathfindingNode node) {
        this.nodes.add(node);
    }

    Path copy() {
        var path = new Path();
        path.nodes = new ArrayList<>(this.nodes);
        path.resolved = resolved;
        return path;
    }
}
