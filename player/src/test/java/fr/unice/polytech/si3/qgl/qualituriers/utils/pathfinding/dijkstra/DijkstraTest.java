package fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding.dijkstra;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding.PathfindingNode;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionablePolygon;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableShapeFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class DijkstraTest {

    List<PathfindingNode> nodes;

    @BeforeEach
    public void init(){
        nodes = PathfindingNode.createFrom((PositionablePolygon) PositionableShapeFactory.getPositionable(new Rectangle(50, 30, 0), new Transform(1, 2, 0)));
    }

    public Map<String, PathfindingNode> createGraphA() {
        //     H - G - F
        //            /
        //   C - D   E
        //  /   /   /
        // A - B - I

        PathfindingNode A = new PathfindingNode(Point.ZERO);
        PathfindingNode B = new PathfindingNode(new Point(100, 0));
        PathfindingNode C = new PathfindingNode(new Point(100, 100));
        PathfindingNode D = new PathfindingNode(new Point(200, 100));
        PathfindingNode E = new PathfindingNode(new Point(300, 100));
        PathfindingNode F = new PathfindingNode(new Point(400, 200));
        PathfindingNode G = new PathfindingNode(new Point(300, 200));
        PathfindingNode H = new PathfindingNode(new Point(200, 200));
        PathfindingNode I = new PathfindingNode(new Point(200, 0));

        A.createRoadTo(B);
        A.createRoadTo(C);
        C.createRoadTo(D);
        D.createRoadTo(B);
        B.createRoadTo(I);
        I.createRoadTo(E);
        E.createRoadTo(F);
        F.createRoadTo(G);
        G.createRoadTo(H);

        return new HashMap<String, PathfindingNode>() {{
            put("A", A);
            put("B", B);
            put("C", C);
            put("D", D);
            put("E", E);
            put("F", F);
            put("G", G);
            put("H", H);
            put("I", I);
        }};
    }

    @Test
    void testExecution() {
        var graphA = createGraphA();
        var result = Dijkstra.execute(graphA.get("A"), graphA.get("H"), new ArrayList<>(graphA.values()));
        assertNotNull(result);
        var nodes = result.getNodes().toArray(PathfindingNode[]::new);

        var expected = new PathfindingNode[] {
                graphA.get("A"),
                graphA.get("B"),
                graphA.get("I"),
                graphA.get("E"),
                graphA.get("F"),
                graphA.get("G"),
                graphA.get("H")
        };

        assertArrayEquals(expected, nodes);

        var expected2 = new PathfindingNode[] {
                graphA.get("A"),
                graphA.get("C"),
                graphA.get("H")
        };

        // Create shortcut !
        graphA.get("C").createRoadTo(graphA.get("H"));

        result = Dijkstra.execute(graphA.get("A"), graphA.get("H"), new ArrayList<>(graphA.values()));
        assertNotNull(result);
        nodes = result.getNodes().toArray(PathfindingNode[]::new);
        assertArrayEquals(expected2, nodes);
    }

    @Test
    void testNotSolvableGraph() {
        PathfindingNode A = new PathfindingNode(Point.ZERO);
        PathfindingNode B = new PathfindingNode(new Point(0, 100));

        var result = Dijkstra.execute(A, B, List.of(A, B));

        assertNull(result);
    }

}
