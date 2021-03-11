package fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PositionableShapeFactoryTest {

    @Test
    public void test() {
        Transform transform = new Transform(new Point(10, 50), 0);
        Circle circle = new Circle(50);
        Rectangle rectangle = new Rectangle(10, 10, 0);
        Polygon polygon = new Polygon(0, new Point[]{new Point(0, 1), new Point(1, 0)});
        Circle polygon1 = mock(Circle.class);
        when(polygon1.getType()).thenReturn(null);
        assertEquals(new PositionableCircle(circle, transform), PositionableShapeFactory.getPositionable(circle, transform));
        assertEquals(new PositionablePolygon(rectangle, transform), PositionableShapeFactory.getPositionable(rectangle, transform));
        assertEquals(new PositionablePolygon(polygon, transform), PositionableShapeFactory.getPositionable(polygon, transform));
    }

}
