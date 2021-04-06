package fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding;

import fr.unice.polytech.si3.qgl.qualituriers.Config;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.render.TempoRender;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Collisions;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.logger.SeaDrawer;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Segment;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableCircle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionablePolygon;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableShape;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class PathfindingProblem {
    private final List<PositionablePolygon>  polygons = new ArrayList<>();
    private final List<PositionablePolygon>  enlargedPolygons = new ArrayList<>();
    private final List<PathfindingNode> nodes = new ArrayList<>();
    private final PathfindingNode startPosition;
    private final PathfindingNode goal;
    private double currentMinimalValidPath = Double.MAX_VALUE;


    PathfindingProblem(PathfindingNode startPosition, PathfindingNode goal) {
        this.startPosition = startPosition;
        this.goal = goal;
    }

    void addPolygon(PositionablePolygon polygon) {
        polygons.add(polygon);
        var enlarged = polygon.enlargeOf(Config.BOAT_MARGIN * 4);
        enlargedPolygons.add(enlarged);

        SeaDrawer.drawPolygon(enlarged, Color.magenta);
    }

    private boolean canNavigateOn(PathfindingNode start, PathfindingNode end) {
        return !Collisions.raycastPolygon(new Segment(start.getPosition(), end.getPosition()), Config.BOAT_MARGIN * 2, polygons.stream());
    }

    private PathfindingNode getNearestOutsideLimitNode(PathfindingNode node) {
        node = new PathfindingNode(node.getPosition(), null);
        var poly = Collisions.getCollidingPolygon(node.toPositionableCircle(), enlargedPolygons.stream());

        // Move the point while it collid with an enlarged polygon.
        while(poly != null) {
            Point pt = node.getPosition();
            var dir = pt.substract(poly.getTransform().getPoint());
            var dist = dir.length();
            dir = dir.normalized();

            for(double d = dist; Collisions.isColliding(node.toPositionableCircle(), poly); d += 50)
                node.setPosition(poly.getTransform().getPoint().add(dir.scalar(d)));

            poly = Collisions.getCollidingPolygon(node.toPositionableCircle(), enlargedPolygons.stream());
        }
        return node;
    }

    private void generateNodes() {
        for(var poly : enlargedPolygons) {
            nodes.addAll(PathfindingNode.createFrom(poly));
        }
    }

    private void generateRoads() {
        // checking one by one if a road canBeCreated between the nodes
        for(var n1 : nodes) {
            for(var n2 : nodes) {
                if(n1 != n2)
                    PathfindingRoad.createIfPraticable(n1, n2, Config.BOAT_MARGIN * 3, polygons.stream());
            }
        }
    }

    PathfindingResult solve() {
        // Get node outside the limit to counter StackOverflows
        var pseudoStart = getNearestOutsideLimitNode(startPosition);
        var pseudoGoal = getNearestOutsideLimitNode(goal);

        // Generating nodes from polygons
        generateNodes();

        // Add the pseudo goal and start found earlier before generating roads
        nodes.add(pseudoGoal);
        nodes.add(pseudoStart);

        // Generate road between the nodes
        generateRoads();

        SeaDrawer.drawPin(pseudoGoal.getPosition(), Color.YELLOW);
        SeaDrawer.drawPin(pseudoStart.getPosition(), Color.YELLOW);

        // Prepare the path with the starting nodes
        var path = searchPath(pseudoStart, pseudoGoal, new PathfindingResult() {{ addNode(startPosition); addNode(pseudoStart); }});

        // Checking if a path exist
        if(path == null) throw new RuntimeException("No path founded !");

        // add the final node
        path.addNode(goal);

        return path;
    }

    private PathfindingResult searchPath(PathfindingNode from, PathfindingNode to, PathfindingResult currentPath) {

        // Getting the neighbours nodes to process
        List<PathfindingNode> connectedNodes = new ArrayList<>();
        from.getRoads().stream()
                .map(r -> r.getArriving(from))  // Get the neighboor node from the road
                .filter(n -> !currentPath.contains(n)) // Remove the processed nodes
                .sorted(Comparator.comparingDouble(r -> r.calculateHeuristic(to))) // Sort from the nearest to goal to the farest to increase processing time
                .forEach(connectedNodes::add);

        PathfindingResult bestPath = null;
        for(var n : connectedNodes) {
            // Check to don't go backward
            if(currentPath.contains(n))
                continue;

            // Prepare for the next stage of searching
            var evaluationPath = currentPath.copy();
            evaluationPath.addNode(n);

            // If this path is already longer than the currentMinimalFound we can let it here
            if(evaluationPath.length() > currentMinimalValidPath) continue;

            // Does this path reach the goal ?
            if(n.equals(to)) {
                currentMinimalValidPath = evaluationPath.length();
                bestPath = evaluationPath;
                break;
            }

            // if it doesn't, check for a path with a length increased
            evaluationPath = searchPath(n, to, evaluationPath);
            if(evaluationPath != null) {
                bestPath = evaluationPath;
                break;
            }
        }

        return bestPath;
    }
}
