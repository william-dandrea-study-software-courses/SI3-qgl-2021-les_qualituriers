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

        // Obstacle datas
        var obsRadius = obstacleToAvoid.get().getShape().getRadius();
        var obsPosition = obstacleToAvoid.get().getTransform().getPoint();

        // Directions
        var direction = end.substract(start).normalized();
        var orthoDir = direction.rotate(Math.PI / 2);

        var obsDirection = obsPosition.substract(start);
        var obsRelativePosition = obsDirection.scalar(orthoDir);

        var oppositeDirection = orthoDir.scalar(-obsRelativePosition).normalized();


        var nextPosition = direction.scalar(obsDirection.scalar(direction)).add(oppositeDirection.scalar(obsRadius + margin * 2 - Math.abs(obsRelativePosition))).add(start);



        // check if the new path is blocked or not
        context.setToReach(new CheckPoint(new Transform(nextPosition, 0), new Circle(1)));


        return getNextCheckpoint(context);
    }
}
