package fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding;

import fr.unice.polytech.si3.qgl.qualituriers.Config;
import fr.unice.polytech.si3.qgl.qualituriers.render.TempoRender;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Collisions;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Segment;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionablePolygon;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class PathfindingResult {
    private List<PathfindingNode> nodes;
    private boolean resolved = false;

    PathfindingResult() {
        this.nodes = new ArrayList<>();
    }

    PathfindingNode get(int index) {
        return nodes.get(index);
    }
    PathfindingNode getLast() {
        return nodes.get(nodes.size() - 1);
    }

    int size() {
        return nodes.size();
    }

    Stream<PathfindingNode> stream() {
        return nodes.stream();
    }

    boolean contains(PathfindingNode node) {
        return nodes.stream().anyMatch(n -> n.getPosition().equals(node.getPosition()));
    }

    void addNode(PathfindingNode node) {
        this.nodes.add(node);
    }

    PathfindingResult copy() {
        var path = new PathfindingResult();
        path.nodes = new ArrayList<>(this.nodes);
        path.resolved = resolved;
        return path;
    }

    double length() {
        double result = 0;
        for(int i = 0; i < size() - 1; i++) {
            result += nodes.get(i + 1).getPosition().substract(nodes.get(i).getPosition()).length();
        }
        return result;
    }

    boolean pathIsCorrect(int startWayPoint, List<PositionablePolygon> obstacles) {
        for (int i = startWayPoint; i < nodes.size() - 1; i++) {
            var nodeA = nodes.get(i);
            var nodeB = nodes.get(i + 1);

            if(Collisions.raycastPolygon(new Segment(nodeA.getPosition(), nodeB.getPosition()), 2 * Config.BOAT_MARGIN, obstacles.stream()))
                return false;
        }

        return true;
    }

    void draw() {
        if(TempoRender.SeaDrawer == null) return;

        for(int i = 0; i < nodes.size() - 1; i++) {
            TempoRender.SeaDrawer.drawLine(nodes.get(i).getPosition(), nodes.get(i + 1).getPosition(), Color.RED);
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
