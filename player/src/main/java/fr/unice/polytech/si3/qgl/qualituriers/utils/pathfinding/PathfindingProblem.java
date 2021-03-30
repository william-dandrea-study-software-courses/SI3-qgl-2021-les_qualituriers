package fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Collisions;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Segment;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionablePolygon;

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
        enlargedPolygons.add(polygon.scaleFromCenter(1.19));
    }

    private void checkAllRoad() {
        nodes.forEach(n -> n.checkRoads(polygons));
    }

    private void generateNodes() {
        for(var polygon : this.enlargedPolygons) {
            List<PathfindingNode> nodesPolygon = PathfindingNode.createFrom(polygon);

            for (var nNode : nodesPolygon) {
                for (var oNode : nodes) {
                    if(!Collisions.raycastPolygon(new Segment(nNode.getPosition(), oNode.getPosition()), polygons.stream())) {
                        nNode.addReachableNode(oNode);
                        oNode.addReachableNode(nNode);
                    }
                }
            }

            this.nodes.addAll(nodesPolygon);
        }

        ///checkAllRoad();
    }

    PathfindingResult solve() {
        if(!Collisions.raycastPolygon(new Segment(startPosition.getPosition(), goal.getPosition()), polygons.stream()))
            return new PathfindingResult() {{ addNode(startPosition); addNode(goal); }};

        generateNodes();

        var results = privateSolveRecusive(startPosition, new PathfindingResult() {{ addNode(startPosition); }});
        var result= results.stream()
                .filter(p -> p.pathIsCorrect(0, polygons))
                .min(Comparator.comparing(PathfindingResult::length));
        if(result.isEmpty())
            throw new RuntimeException("No path found !");

        return result.get();
    }

    private List<PathfindingResult> privateSolveRecusive(PathfindingNode from, PathfindingResult currentPath) {
        var nextPositions = from.getReachableNodes();
        nextPositions.sort(Comparator.comparingDouble(pn -> pn.calculateHeuristic(this.goal)));

        List<PathfindingResult> results = new ArrayList<>();
        for(var nextPos : nextPositions) {
            if(currentPath.length() > currentMinimalValidPath) break;
            // On verifie que l'on ne revient pas sur ses pas
            if(currentPath.contains(nextPos)) break; // classé par heuristic donc on peut breaker si on reviens en arriere

            // create a sandbox
            var processingPath = currentPath.copy();
            processingPath.addNode(nextPos);

            // Exit if i reached the end
            if(nextPos == this.goal) {
                results.add(processingPath);
                if(processingPath.length() < currentMinimalValidPath && processingPath.size() > 0 && processingPath.pathIsCorrect(0, polygons))
                    currentMinimalValidPath = processingPath.length();
                break;
            }

            // Exit if i found a path who lead to end
            results.addAll(privateSolveRecusive(nextPos, processingPath));
        }

        return results;
    }
}
