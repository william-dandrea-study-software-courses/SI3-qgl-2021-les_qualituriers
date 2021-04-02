package fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding;

import fr.unice.polytech.si3.qgl.qualituriers.Config;
import fr.unice.polytech.si3.qgl.qualituriers.render.TempoRender;
import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Collisions;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
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

        // Enlarge obstacles
        List<PositionablePolygon> obstacles = new ArrayList<>();
        context.getObstacles().stream().map(PositionableShape::getCircumscribedPolygon).forEach(obstacles::add);
        store.addObstaclesTo(obstacles);
        obstacles.forEach(store::addObstacle);


        if(!Collisions.raycastPolygon(new Segment(boatPosition, context.getToReach().getPosition()), Config.BOAT_MARGIN * 2, obstacles.stream()))
            return context.getToReach();

        if(store.getCalculatedPath() == null)
            FindANewPath(context, obstacles);

        else if(!context.getToReach().getPosition().getPoint().equals(store.getCalculatedPath().getLast().getPosition()))
            FindANewPath(context, obstacles);

        else if(Collisions.isColliding(
                context.getBoat().getPositionableShape(),
                store.getCalculatedPath().getLast().toPositionableCircle())) {
            FindANewPath(context, obstacles);
        } else if(Collisions.isColliding(context.getBoat().getPositionableShape(), store.getCalculatedPath().get(store.getCurrentNodeToReach()).toPositionableCircle())) {
            store.setCurrentNodeToReach(store.getCurrentNodeToReach() + 1);
        } else {
            int i = 0;
        }

        var nextPos = store.getCalculatedPath().get(store.getCurrentNodeToReach()).getPosition();
        if(TempoRender.SeaDrawer != null)
            TempoRender.SeaDrawer.drawLine(context.getBoat().getPosition().getPoint(), nextPos, Color.MAGENTA);

        if(TempoRender.SeaDrawer != null) {
            TempoRender.SeaDrawer.drawPin(nextPos, Color.ORANGE);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

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

        context.getStore().setCalculatedPath(result);
        context.getStore().setCurrentNodeToReach(1);
        result.draw();
    }
}
