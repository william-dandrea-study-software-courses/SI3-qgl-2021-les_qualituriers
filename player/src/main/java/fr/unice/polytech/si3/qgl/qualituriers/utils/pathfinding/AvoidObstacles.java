package fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding;

import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionablePolygon;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AvoidObstacles implements IPathfinder {
    @Override
    public int getPriorityRank() {
        return Integer.MAX_VALUE;
    }

    @Override
    public CheckPoint getNextCheckpoint(PathfindingContext context) {
        return getNextCheckpoint(context, 0);
    }

    private CheckPoint getNextCheckpoint(PathfindingContext context, int pass) {
        // Enlarge obstacles
        List<PositionablePolygon> obstacles = new ArrayList<>();
        context.getObstacles().stream().map(o -> o.getCircumscribedPolygon().scaleFromCenter(1.1)).forEach(obstacles::add);

        // Create PathfindingProblem
        PathfindingNode startPosition = new PathfindingNode(context.getBoat().getPosition(), null);
        PathfindingNode goal = new PathfindingNode(context.getToReach().getPosition(), null);

        PathfindingProblem pb = new PathfindingProblem(startPosition, goal);
        obstacles.forEach(pb::addPolygon);

        // Solve pb
        Path result = pb.solve();
        if(result == null) throw new RuntimeException("No path founded !");

        return new CheckPoint(new Transform(result.getFirstNode().getPosition(), 0), new Circle(200));

    }

}
