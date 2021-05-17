package fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionablePolygon;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PathfindingResultTest {

    @Test
    void testPathIsCorrect() {
        PathfindingResult result = new PathfindingResult();
        result.addNode(new PathfindingNode(Point.ZERO));
        result.addNode(new PathfindingNode(new Point(0, 500)));
        result.addNode(new PathfindingNode(new Point(0, 1000)));
        result.addNode(new PathfindingNode(new Point(0, 1500)));

        List<PositionablePolygon> polygons = new ArrayList<>() {{
            add(new PositionablePolygon(new Rectangle(10, 10, 0), new Transform(0, 250, 0)));
            add(new PositionablePolygon(new Rectangle(10, 10, 0), new Transform(0, 750, 0)));
        }};

        assertFalse(result.pathIsCorrect(0, polygons));
        assertFalse(result.pathIsCorrect(1, polygons));
        assertTrue(result.pathIsCorrect(2, polygons));
    }

    @Test
    void testLinearPathLength() {
        PathfindingResult result = new PathfindingResult();
        result.addNode(new PathfindingNode(Point.ZERO));
        result.addNode(new PathfindingNode(new Point(0, 500)));
        result.addNode(new PathfindingNode(new Point(0, 1000)));
        result.addNode(new PathfindingNode(new Point(0, 1500)));

        assertEquals(1500, result.length());
    }

    @Test
    void testNonLinearPath() {
        PathfindingResult result = new PathfindingResult();
        result.addNode(new PathfindingNode(Point.ZERO));
        result.addNode(new PathfindingNode(new Point(0, 500)));
        result.addNode(new PathfindingNode(new Point(0, 1000)));
        result.addNode(new PathfindingNode(new Point(0, 1500)));
        result.addNode(new PathfindingNode(new Point(1500, 1500)));

        assertEquals(3000, result.length());
    }
}
