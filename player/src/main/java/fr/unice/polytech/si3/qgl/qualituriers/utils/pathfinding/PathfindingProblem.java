package fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Collisions;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Segment;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionablePolygon;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PathfindingProblem {
    private final List<PositionablePolygon>  polygons = new ArrayList<>();
    private final List<PathfindingNode> nodes = new ArrayList<>();
    private final PathfindingNode startPosition;
    private final PathfindingNode goal;

    PathfindingProblem(PathfindingNode startPosition, PathfindingNode goal) {
        this.nodes.add(startPosition);
        this.nodes.add(goal);
        this.startPosition = startPosition;
        this.goal = goal;
    }

    void addPolygon(PositionablePolygon polygon) {
        polygons.add(polygon);
    }

    private void checkAllRoad() {
        nodes.forEach(n -> n.checkRoads(polygons));
    }

    private void generateNodes() {
        for(var polygon : this.polygons) {
            List<PathfindingNode> nodesPolygon = PathfindingNode.createFrom(polygon);

            for (var nNodes : nodesPolygon) {
                for (var oNode : nodes) {
                    var s = new Segment(oNode.getPosition(), nNodes.getPosition());
                    s = s.changeLength(s.length() - 1);
                    Segment finalS = s;
                    if (polygons.stream().noneMatch(p -> Collisions.raycast(finalS, p))) {
                        oNode.addReachableNode(nNodes);
                        nNodes.addReachableNode(oNode);
                    }
                }
            }

            this.nodes.addAll(nodesPolygon);
        }
        //checkAllRoad();
    }

    Path solve() {
        if(!Collisions.raycast(new Segment(startPosition.getPosition(), goal.getPosition()), polygons.stream()))
            return new Path() {{ addNode(goal); }};

        generateNodes();

        var result = privateSolveRecusive(startPosition, new Path());
        return result;
    }

    private Path privateSolveRecusive(PathfindingNode from, Path currentPath) {
        var nextPositions = from.getReachableNodes();
        nextPositions.sort(Comparator.comparingDouble(pn -> pn.calculateHeuristic(this.goal)));

        Path processingPath = null;
        for(var nextPos : nextPositions) {

            // On verifie que l'on ne revient pas sur ses pas
            if(currentPath.contains(nextPos)) break; // classé par heuristic donc on peut breaker si on reviens en arriere

            // create a sandbox
            processingPath = currentPath.copy();
            processingPath.addNode(nextPos);

            // Exit if i reached the end
            if(nextPos == this.goal)
                break;

            // Exit if i found a path who lead to end
            processingPath = privateSolveRecusive(nextPos, processingPath);
            if(processingPath != null)
                break;
        }

        return processingPath;
    }
}
