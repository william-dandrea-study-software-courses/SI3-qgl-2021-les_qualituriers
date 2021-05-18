package fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding;

import fr.unice.polytech.si3.qgl.qualituriers.Config;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.ReefVisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.StreamVisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Collisions;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.logger.SeaDrawer;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Segment;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionablePolygon;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableShape;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MainPathfinding {
    private static final double WAYPOINT_SIZE = 100;

    /**
     * Retourne la prochaine étape afin de rejoindre le point le plus rapidement possible
     * TESTED
     * @param context: Les paramètres de recherche
     * @return Checkpoint de la prochaine étape
     */
    public Point getNextCheckpoint(PathfindingContext context) {

        var store = context.getStore();
        var boatPosition = context.getStart();
        var obstacles = context.getObstacles();

        // Check if we can go directly to the goal
        if(!Collisions.raycastPolygon(new Segment(boatPosition, context.getGoal()), Config.BOAT_MARGIN * 4, obstacles.stream()))
            return context.getGoal(); // TESTED

        // If the path is null (start of the game)
        if(store.getCalculatedPath() == null)
            FindANewPath(context, obstacles); // TESTED

            // if a new obstacle is encountered during the travel and it block the road
        else if(!store.getCalculatedPath().pathIsCorrect(store.getCurrentNodeToReach(), obstacles))
            FindANewPath(context, obstacles); // TESTED

            // If the checkpoint has changed on the turn
        else if(!context.getGoal().equals(store.getCalculatedPath().getLast().getPosition()))
            FindANewPath(context, obstacles); // TESTED

            // If we reached a waypoint
        else if(boatPosition.substract(store.getCalculatedPath().get(store.getCurrentNodeToReach()).getPosition()).lengthWithoutSquare() < WAYPOINT_SIZE * WAYPOINT_SIZE) {
            if(store.getCalculatedPath().length() - 1 > store.getCurrentNodeToReach()) {
                // If there is waypoints remaining in the path
                store.setCurrentNodeToReach(store.getCurrentNodeToReach() + 1);
            } else {
                // Else generate a path to the next checkpoint
                return boatPosition;
            }

            // TESTED
        }

        if(store.getCalculatedPath() == null)
            return boatPosition;

        // Getting the next checkpoint from the processed path
        var nextPos = store.getCalculatedPath().get(store.getCurrentNodeToReach()).getPosition();

        SeaDrawer.drawLine(context.getGoal(), nextPos, Color.MAGENTA);
        SeaDrawer.drawPin(nextPos, Color.ORANGE);
        //SeaDrawer.waitIfDebugMode(2000);

        return nextPos;
    }

    /**
     * NO TEST NEEDED
     */
    void FindANewPath(PathfindingContext context, List<PositionablePolygon> obstacles) {


        // Create PathfindingProblem
        PathfindingNode startPosition = new PathfindingNode(context.getStart());
        PathfindingNode goal = new PathfindingNode(context.getGoal());

        PathfindingProblem pb = new PathfindingProblem(startPosition, goal);
        obstacles.forEach(pb::addPolygon);

        // Solve pb
        PathfindingResult result = pb.solve();
        if(result == null)
            return;

        // store the path inside the store
        context.getStore().setCalculatedPath(result);
        context.getStore().setCurrentNodeToReach(1);

        result.draw();
        return;
    }
}
