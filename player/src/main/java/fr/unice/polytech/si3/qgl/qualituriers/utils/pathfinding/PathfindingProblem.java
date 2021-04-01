package fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding;

import fr.unice.polytech.si3.qgl.qualituriers.Config;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.render.TempoRender;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Collisions;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Segment;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionablePolygon;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PathfindingProblem {
    private final List<PositionablePolygon>  polygons = new ArrayList<>();
    private final List<PositionablePolygon>  enlargedPolygons = new ArrayList<>();
    private final List<PathfindingNode> nodes = new ArrayList<>();
    private final PathfindingNode startPosition;
    private final PathfindingNode goal;
    private double currentMinimalValidPath = Double.MAX_VALUE;


    PathfindingProblem(PathfindingNode startPosition, PathfindingNode goal) {
        this.nodes.add(startPosition);
        this.nodes.add(goal);
        this.startPosition = startPosition;
        this.goal = goal;
    }

    void addPolygon(PositionablePolygon polygon) {
        polygons.add(polygon);
        var enlarged = polygon.enlargeOf(Config.BOAT_MARGIN * 2);
        enlargedPolygons.add(enlarged);

        if(TempoRender.SeaDrawer != null) TempoRender.SeaDrawer.drawPolygon(enlarged, Color.magenta);
    }

    private void checkAllRoad() {
        nodes.forEach(n -> n.checkRoads(polygons));
    }

    private void generateNodes() {
        for(var polygon : this.enlargedPolygons) {
            List<PathfindingNode> nodesPolygon = PathfindingNode.createFrom(polygon);

            for (var nNode : nodesPolygon) {
                for (var oNode : nodes) {
                    if(!Collisions.raycastPolygon(new Segment(nNode.getPosition(), oNode.getPosition()), Config.BOAT_MARGIN, polygons.stream())) {
                        nNode.addReachableNode(oNode);
                        oNode.addReachableNode(nNode);
                    }
                }
            }

            this.nodes.addAll(nodesPolygon);
        }
    }

    PathfindingResult solve() {

        if(!Collisions.raycastPolygon(new Segment(startPosition.getPosition(), goal.getPosition()), Config.BOAT_MARGIN * 2, polygons.stream())) {

            var tempPathResult = new PathfindingResult();
            tempPathResult.addNode(startPosition);
            tempPathResult.addNode(goal);
            return tempPathResult;
        }

        generateNodes();

        var tempPathResult = new PathfindingResult();
        tempPathResult.addNode(startPosition);
        var results = privateSolveRecusive(startPosition, tempPathResult);
        var result= results.stream()
                //.filter(p -> p.pathIsCorrect(0, polygons))
                .min(Comparator.comparing(PathfindingResult::length));
        if(result.isEmpty()) {
            throw new RuntimeException("No path found !");
            /*return new PathfindingResult() {{
                addNode(startPosition);
                addNode(goal);
            }};*/
        }

        return result.get();
    }

    private List<PathfindingResult> privateSolveRecusive(PathfindingNode from, PathfindingResult currentPath) {
        var nextPositions = from.getReachableNodes();
        nextPositions.sort(Comparator.comparingDouble(pn -> pn.calculateHeuristic(this.goal)));

        List<PathfindingResult> results = new ArrayList<>();
        for(var nextPos : nextPositions) {
            if(currentPath.length() > currentMinimalValidPath)
                break;
            // On verifie que l'on ne revient pas sur ses pas
            if(currentPath.contains(nextPos)) break; // classé par heuristic donc on peut breaker si on reviens en arriere



            // create a sandbox
            var processingPath = currentPath.copy();
            processingPath.addNode(nextPos);
            var pathIsCorrect = processingPath.pathIsCorrect(0, polygons);
            if(!pathIsCorrect) continue;

            // Exit if i reached the end
            if(nextPos == this.goal) {
                if(pathIsCorrect) {
                    results.add(processingPath);
                    if(processingPath.length() < currentMinimalValidPath)
                        currentMinimalValidPath = processingPath.length();
                    break;
                }
                continue;
            }

            // Exit if i found a path who lead to end
            results.addAll(privateSolveRecusive(nextPos, processingPath));
        }

        return results;
    }
}
