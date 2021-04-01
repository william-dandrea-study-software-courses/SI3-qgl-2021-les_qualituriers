package fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding;

import fr.unice.polytech.si3.qgl.qualituriers.Config;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.render.TempoRender;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Collisions;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Segment;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionablePolygon;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableShape;

import java.awt.*;
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

        var result = privateSolveRecusive(startPosition, tempPathResult);
        if(result == null) throw new RuntimeException("No path found");
        
        return result;
    }

    private boolean roadInTrouble(PathfindingNode start, PathfindingNode end) {
        return Collisions.raycastPolygon(new Segment(start.getPosition(), end.getPosition()), 2 * Config.BOAT_MARGIN, polygons.stream());
    }

    private PathfindingResult privateSolveRecusive(PathfindingNode from, PathfindingResult currentPath) {
        var nextPositions = from.getReachableNodes();
        List<PathfindingNode> nextNodes = new ArrayList<>();
        nextPositions.stream()
            .filter(n -> !currentPath.contains(n))
            .forEach(nextNodes::add);

        nextNodes.sort(Comparator.comparingDouble(pn -> pn.calculateHeuristic(this.goal)));

        if(TempoRender.SeaDrawer != null && false) {
            for(int i = 0; i < nextPositions.size(); i++) {
                TempoRender.SeaDrawer.drawLine(from.getPosition(), nextPositions.get(i).getPosition(), Color.MAGENTA);
            }
            try {
                while(System.in.available() == 0) ;
            } catch(Exception e) {}
        }


        for(var nextPos : nextNodes) {
            if(roadInTrouble(from, nextPos)) continue;
            // On verifie que l'on ne revient pas sur ses pas
            //if(currentPath.contains(nextPos)) break; // classé par heuristic donc on peut breaker si on reviens en arriere

            // create a sandbox
            var processingPath = currentPath.copy();
            processingPath.addNode(nextPos);

            // Exit if i reached the end
            if(nextPos.getPosition().equals(this.goal.getPosition())) return processingPath;

            // Exit if i found a path who lead to end
            var r = privateSolveRecusive(nextPos, processingPath);
            if(r != null)
                return r;
        }

        return null;
    }
}
