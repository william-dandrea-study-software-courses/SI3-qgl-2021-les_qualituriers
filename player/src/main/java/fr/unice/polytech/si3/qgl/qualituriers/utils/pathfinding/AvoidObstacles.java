package fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding;

import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Collisions;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Segment;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionablePolygon;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableShape;

import java.util.ArrayList;
import java.util.List;

public class AvoidObstacles implements IPathfinder {
    @Override
    public int getPriorityRank() {
        return Integer.MAX_VALUE - 1;
    }

    @Override
    public CheckPoint getNextCheckpoint(PathfindingContext context) {
        return getNextCheckpoint(context, 0);
    }

    private CheckPoint getNextCheckpoint(PathfindingContext context, int pass) {
        return test(context, pass);
    }

    private CheckPoint test(PathfindingContext context, int pass) {
        var store = context.getStore();
        var boatPosition = context.getBoat().getPosition();

        // Enlarge obstacles
        List<PositionablePolygon> obstacles = new ArrayList<>();
        context.getObstacles().stream().map(PositionableShape::getCircumscribedPolygon).forEach(obstacles::add);
        store.addObstaclesTo(obstacles);
        obstacles.forEach(store::addObstacle);

        if(!Collisions.raycastPolygon(new Segment(boatPosition, context.getToReach().getPosition()), obstacles.stream()))
            return context.getToReach();

        if(store.getCalculatedPath() == null)
            FindANewPath(context, obstacles);

        var currentPt = store.getCalculatedPath().get(store.getCurrentNodeToReach());

        // Boat on checkpoint
        if(boatPosition.substract(currentPt.getPosition()).length() < 100) {
            if(!store.getCalculatedPath().pathIsCorrect(store.getCurrentNodeToReach(), obstacles))
                FindANewPath(context, obstacles);
            else if(store.getCalculatedPath().size() <= store.getCurrentNodeToReach() + 1)
                FindANewPath(context, obstacles);
            else
                store.setCurrentNodeToReach(store.getCurrentNodeToReach() + 1);
        } else if(Collisions.raycastPolygon(new Segment(boatPosition, currentPt.getPosition()), obstacles.stream())) {
            FindANewPath(context, obstacles);
        }

        var nodeToReach = store.getCalculatedPath().get(store.getCurrentNodeToReach());
        return new CheckPoint(new Transform(nodeToReach.getPosition(), 0), new Circle(50));
    }

    private void FindANewPath(PathfindingContext context, List<PositionablePolygon> obstacles) {


        // Create PathfindingProblem
        PathfindingNode startPosition = new PathfindingNode(context.getBoat().getPosition(), null);
        PathfindingNode goal = new PathfindingNode(context.getToReach().getPosition(), null);

        PathfindingProblem pb = new PathfindingProblem(startPosition, goal);
        obstacles.forEach(pb::addPolygon);

        // Solve pb
        PathfindingResult result = pb.solve();
        if(result == null) throw new RuntimeException("No path founded !");

        context.getStore().setCalculatedPath(result);
        context.getStore().setCurrentNodeToReach(1);
    }
}
