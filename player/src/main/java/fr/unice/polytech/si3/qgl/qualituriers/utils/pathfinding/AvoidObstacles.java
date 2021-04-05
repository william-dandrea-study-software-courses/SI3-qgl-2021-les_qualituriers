package fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding;

import fr.unice.polytech.si3.qgl.qualituriers.Config;
import fr.unice.polytech.si3.qgl.qualituriers.render.TempoRender;
import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Collisions;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.logger.SeaDrawer;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Segment;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableCircle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionablePolygon;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableShape;

import java.awt.*;
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

        // Keep the obstacle in memory over the turns to take it in account in the processing of the path
        List<PositionablePolygon> obstacles = new ArrayList<>();
        context.getObstacles().stream().map(PositionableShape::getCircumscribedPolygon).forEach(obstacles::add);
        store.addObstaclesTo(obstacles);
        obstacles.forEach(store::addObstacle);

        // Check if we can go directly to the goal
        if(!Collisions.raycastPolygon(new Segment(boatPosition, context.getToReach().getPosition()), Config.BOAT_MARGIN * 4, obstacles.stream()))
            return context.getToReach();

        // If the path is null (start of the game)
        if(store.getCalculatedPath() == null)
            FindANewPath(context, obstacles);

        // if a new obstacle is encountered during the travel and it block the road
        else if(!store.getCalculatedPath().pathIsCorrect(store.getCurrentNodeToReach(), obstacles))
            FindANewPath(context, obstacles);

        // If the checkpoint has changed on the turn
        else if(!context.getToReach().getPosition().getPoint().equals(store.getCalculatedPath().getLast().getPosition()))
            FindANewPath(context, obstacles);

        // If we reached the final waypoint
        else if(Collisions.isColliding(
                context.getBoat().getPositionableShape(),
                store.getCalculatedPath().getLast().toPositionableCircle())) {
            FindANewPath(context, obstacles);
        }
        // If we reached an intermediare checkpoint
        else if(Collisions.isColliding(context.getBoat().getPositionableShape(), store.getCalculatedPath().get(store.getCurrentNodeToReach()).toPositionableCircle())) {
            store.setCurrentNodeToReach(store.getCurrentNodeToReach() + 1);
        }

        // Getting the next checkpoint from the processed path
        var nextPos = store.getCalculatedPath().get(store.getCurrentNodeToReach()).getPosition();

        SeaDrawer.drawLine(context.getBoat().getPosition().getPoint(), nextPos, Color.MAGENTA);
        SeaDrawer.drawPin(nextPos, Color.ORANGE);
        //SeaDrawer.waitIfDebugMode(2000);

        return new CheckPoint(new Transform(nextPos, 0), new Circle(50));
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

        // store the path inside the store
        context.getStore().setCalculatedPath(result);
        context.getStore().setCurrentNodeToReach(1);
        result.draw();
    }
}
