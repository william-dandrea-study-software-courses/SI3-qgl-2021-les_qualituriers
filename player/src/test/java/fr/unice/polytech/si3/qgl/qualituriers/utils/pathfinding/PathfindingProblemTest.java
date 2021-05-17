package fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding;

import fr.unice.polytech.si3.qgl.qualituriers.Config;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Collisions;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableCircle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionablePolygon;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class PathfindingProblemTest {

    @Test
    void testAddPolygonTest() {

        PathfindingProblem pb = new PathfindingProblem(new PathfindingNode(Point.ZERO), new PathfindingNode(Point.ZERO));

        PositionablePolygon poly = new PositionablePolygon(new Rectangle(10, 10, 0), Transform.ZERO);
        pb.addPolygon(poly);

        assertEquals(pb.getPolygons().size(), 1);
        assertEquals(pb.getEnlargedPolygons().size(), 1);

        assertEquals(pb.getPolygons().get(0), poly);
        assertEquals(pb.getEnlargedPolygons().get(0), poly.enlargeOf(4 * Config.BOAT_MARGIN));
    }

    @Test
    void testGetNearestOutside() {
        PathfindingProblem pb = new PathfindingProblem(new PathfindingNode(Point.ZERO), new PathfindingNode(Point.ZERO));
        var poly = new PositionablePolygon(new Rectangle(10, 10, 0), Transform.ZERO);
        pb.addPolygon(poly);
        var node = pb.getNearestOutsideLimitNode(new PathfindingNode(Point.ZERO));

        assertFalse(Collisions.isColliding(new PositionableCircle(new Circle(10), new Transform(node.getPosition(), 0)), poly));
    }

    @Test
    void generateNodesTest() {
        PathfindingProblem pb = new PathfindingProblem(new PathfindingNode(Point.ZERO), new PathfindingNode(Point.ZERO));
        var poly = new PositionablePolygon(new Rectangle(10, 10, 0), Transform.ZERO);
        pb.addPolygon(poly);

        pb.generateNodes();

        assertArrayEquals(pb.getNodes().stream().map(PathfindingNode::getPosition).toArray(Point[]::new), poly.enlargeOf(4 * Config.BOAT_MARGIN).getPoints());
    }

    @Test
    void generateRoad() {
        PathfindingProblem pb = new PathfindingProblem(new PathfindingNode(Point.ZERO), new PathfindingNode(Point.ZERO));
        var poly = new PositionablePolygon(new Rectangle(10, 10, 0), Transform.ZERO);
        pb.addPolygon(poly);

        pb.generateNodes();
        pb.generateRoads();

        var roads = pb.getNodes().stream().map(PathfindingNode::getRoads).reduce(new ArrayList<>(), (pv, cv) -> {
            pv.addAll(cv.stream().filter(c -> !pv.contains(c)).collect(Collectors.toList()));
            return pv;
        });

        assertTrue(roads.contains(new PathfindingRoad(pb.getNodes().get(0), pb.getNodes().get(1))));
        assertTrue(roads.contains(new PathfindingRoad(pb.getNodes().get(1), pb.getNodes().get(2))));
        assertTrue(roads.contains(new PathfindingRoad(pb.getNodes().get(2), pb.getNodes().get(3))));
        assertTrue(roads.contains(new PathfindingRoad(pb.getNodes().get(3), pb.getNodes().get(0))));

        assertFalse(roads.contains(new PathfindingRoad(pb.getNodes().get(0), pb.getNodes().get(2))));
        assertFalse(roads.contains(new PathfindingRoad(pb.getNodes().get(1), pb.getNodes().get(3))));

    }
}
