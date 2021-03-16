package fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding;

import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderedCheckpointsTest {
    OrderedCheckpoints pathfinder;
    Boat boat;

    @BeforeEach
    void init() {
        pathfinder = new OrderedCheckpoints();
        boat = new Boat(10, Transform.ZERO, "", null, null, new Rectangle(10, 30, 0));
    }

    @Test
    void getNearest() {
        var ch1 = new CheckPoint(new Transform(new Point(1000, 0), 0), new Circle(10));
        var ch3 = new CheckPoint(new Transform(new Point(150, 0), 0), new Circle(10));
        var ch2 = new CheckPoint(new Transform(new Point(50, 0), 0), new Circle(10));
        var ch = pathfinder.getNextCheckpoint(new PathfindingContext(boat, new ArrayList<>(), Arrays.asList(
                ch1,
                ch2,
                ch3
        )));

        assertSame(ch, ch2);
    }
}
