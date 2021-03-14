package fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding;

import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Collisions;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableCircle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableShape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;

public class AvoidObstaclesTest {
    AvoidObstacles pathfinder;
    Boat boat;

    @BeforeEach
    void init() {
        pathfinder = new AvoidObstacles();
        boat = new Boat(10, Transform.ZERO, "", null, null, new Rectangle(10, 30, 0));
    }

    @Test
    void TestReachSameLevel() {
        TestAttemptToReach(new Point(1000, 0), Arrays.asList(
                new PositionableCircle(new Circle(300), new Transform(new Point(500, 0), 0))
        ));
    }

    @Test
    void TestManyObstacles() {
        TestAttemptToReach(new Point(2000, 0), Arrays.asList(
                new PositionableCircle(new Circle(300), new Transform(new Point(500, 0), 0)),
                new PositionableCircle(new Circle(300), new Transform(new Point(1500, -500), 0))
        ));
    }


    void TestAttemptToReach(Point pt, List<PositionableShape<? extends Shape>> obs) {
        var toReach = new CheckPoint(new Transform(pt, 0), new Circle(50));

        var context = new PathfindingContext(boat, obs, new ArrayList<>());
        context.setToReach(toReach);

        do {
            context.setToReach(toReach);
            var ch = pathfinder.getNextCheckpoint(context);
            assertFalse(obs.stream().anyMatch(p -> Collisions.raycast(boat.getPosition().getPoint(), ch.getPosition().getPoint(), p.getCircumscribed(), 30)));
            boat.setPosition(ch.getPosition());
        } while(!context.getToReach().getPosition().getPoint().equals(toReach.getPosition().getPoint()));


    }
}
