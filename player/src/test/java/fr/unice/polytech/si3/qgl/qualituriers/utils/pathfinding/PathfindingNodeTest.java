package fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding;

import fr.unice.polytech.si3.qgl.qualituriers.Config;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableCircle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionablePolygon;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PathfindingNodeTest {

    @Test
    void testCreateFromPolygon() {
        var nodes = PathfindingNode.createFrom(new PositionablePolygon(new Rectangle(300, 300, 0), new Transform(0, 0, 0)));
        assertTrue(nodes.contains(new PathfindingNode(new Point(-150, -150))));
        assertTrue(nodes.contains(new PathfindingNode(new Point(150, -150))));
        assertTrue(nodes.contains(new PathfindingNode(new Point(-150, 150))));
        assertTrue(nodes.contains(new PathfindingNode(new Point(150, 150))));
    }

    @Test
    void TestToPositionalCircle() {
        PathfindingNode node = new PathfindingNode(new Transform(100, 100, 0));
        assertEquals(node.toPositionableCircle(), new PositionableCircle(new Circle(Config.BOAT_MARGIN), new Transform(100, 100, 0)));
    }

    @Test
    void TestCreateRoad() {
        PathfindingNode node1 = new PathfindingNode(Transform.ZERO);
        PathfindingNode node2 = new PathfindingNode(new Transform(0, 100, 0));

        assertFalse(node1.neighboursStream().anyMatch(n -> n == node2));
        assertFalse(node2.neighboursStream().anyMatch(n -> n == node1));

        node1.createRoadTo(node2);

        assertTrue(node1.neighboursStream().anyMatch(n -> n == node2));
        assertTrue(node2.neighboursStream().anyMatch(n -> n == node1));
    }
}
