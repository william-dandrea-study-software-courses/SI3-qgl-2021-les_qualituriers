package fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding;

import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Collisions;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableShape;

import java.util.Comparator;

public class AvoidObstacles implements IPathfinder {
    @Override
    public int getPriorityRank() {
        return Integer.MAX_VALUE;
    }

    @Override
    public CheckPoint getNextCheckpoint(PathfindingContext context) {
        var end = context.getToReach().getPosition();
        var boatRadius = context.getBoat().getPositionableShape().getCircumscribed();
        var start = boatRadius.getTransform().getPoint();
        var margin = 2 * boatRadius.getShape().getRadius();

        // Map the obstacle to circles obstacle
        // get the one who will collid with the boat
        // get the nearest
        var obstacleToAvoid = context.getObstacles().stream()
                .map(PositionableShape::getCircumscribed) // Map the obstacle to circles obstacle
                .filter(p -> Collisions.raycast(start, end, p, margin)) // get the ones who collids
                .min(Comparator.comparingDouble(p -> p.getTransform().getPoint().substract(start).length())); // get the nearests

        if(obstacleToAvoid.isEmpty()) return context.getToReach();

        var circleObs = obstacleToAvoid.get().getCircumscribed();
        var delta = circleObs.getTransform().getPoint().substract(start).normalized().rotate(Math.PI / 2).scalar(circleObs.getShape().getRadius() + margin);


        // Search the best path
        var leftPossiblility = circleObs.getTransform().getPoint().add(delta);
        var rightPossiblility = circleObs.getTransform().getPoint().substract(delta);

        boolean leftWillCollidAgain = context.getObstacles().stream()
                .map(PositionableShape::getCircumscribed) // Map the obstacle to circles obstacle
                .anyMatch(p -> Collisions.raycast(leftPossiblility, end, p, margin));

        boolean rightWillCollidAgain = context.getObstacles().stream()
                .map(PositionableShape::getCircumscribed) // Map the obstacle to circles obstacle
                .anyMatch(p -> Collisions.raycast(rightPossiblility, end, p, margin));

        Point bestPossibility = null;

        if(!leftWillCollidAgain)
            bestPossibility = leftPossiblility;
        else if(!rightWillCollidAgain)
            bestPossibility = rightPossiblility;
        else bestPossibility = leftPossiblility;

        // check if the new path is blocked or not
        context.setToReach(new CheckPoint(new Transform(bestPossibility, 0), new Circle(1)));
        return getNextCheckpoint(context);
    }
}
