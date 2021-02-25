package fr.unice.polytech.si3.qgl.qualituriers.utils.shape;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableCircle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionablePolygon;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableShapeFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PositionableShapeFactoryTest {

    @Test
    public void test() {
        Transform transform = new Transform(new Point(10, 50), 0);
        Circle circle = new Circle(50);
        Rectangle rectangle = new Rectangle(10, 10, 0);
        Polygon polygon = new Polygon(0, new Point[]{new Point(0, 1), new Point(1, 0)});
        assertEquals(new PositionableCircle(circle, transform), PositionableShapeFactory.getPositionable(circle, transform));
        assertEquals(new PositionablePolygon(rectangle, transform), PositionableShapeFactory.getPositionable(rectangle, transform));
        assertEquals(new PositionablePolygon(polygon, transform), PositionableShapeFactory.getPositionable(polygon, transform));

    }

}
