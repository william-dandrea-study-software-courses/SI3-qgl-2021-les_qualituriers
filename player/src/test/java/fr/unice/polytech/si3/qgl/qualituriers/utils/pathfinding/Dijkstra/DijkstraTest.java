package fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding.Dijkstra;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding.PathfindingNode;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionablePolygon;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableShapeFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

public class DijkstraTest {

    List<PathfindingNode> nodes;

    @BeforeEach
    public void init(){
        nodes = PathfindingNode.createFrom((PositionablePolygon) PositionableShapeFactory.getPositionable(new Rectangle(50, 30, 0), new Transform(1, 2, 0)));
    }

    @Test
    public void initTest(){
    Dijkstra test = new Dijkstra();
    var oke = test.execute(nodes.get(0), nodes.get(nodes.size()-1), nodes);
        assertEquals(null, test.execute(nodes.get(0), nodes.get(nodes.size()-1), nodes));
    }
}
