package fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding;

import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.EnemyVisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.ReefVisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.StreamVisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.VisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Collisions;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableCircle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionablePolygon;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PathfindingContextTest {

    @Test
    void testObstacleSorting() {
        List<VisibleDeckEntity> obs = new ArrayList<>() {{
           add(new ReefVisibleDeckEntity(Transform.ZERO, new Circle(15)));
           add(new ReefVisibleDeckEntity(Transform.ZERO, new Rectangle(10, 10, 0)));
           add(new StreamVisibleDeckEntity(Transform.ZERO, new Rectangle(20, 20, 0), 0));
           add(new EnemyVisibleDeckEntity(Transform.ZERO, new Rectangle(30, 30, 0), 0));
        }};

        // Keep all obstacles for the turn and convert to polygons
        PathfindingStore store = new PathfindingStore();
        PathfindingContext context = new PathfindingContext(Point.ZERO, obs, Point.ZERO, store);

        assertEquals(context.getObstacles().size(), 4);

        // Do not store moving obstacles
        List<PositionablePolygon> storedObstacles = new ArrayList<>();
        store.addObstaclesTo(storedObstacles);
        assertEquals(storedObstacles.size(), 3);
    }
}
