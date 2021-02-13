package fr.unice.polytech.si3.qgl.qualituriers.utils;

import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CollisionsTest {

    @Test
    void TestCirclesCollisions() {
        PositionableShape<Circle> cicle1 = new PositionableShape<Circle>(new Circle(5), new Transform(0, 0, 0));
        PositionableShape<Circle> cicle2 = new PositionableShape<Circle>(new Circle(10), new Transform(14, 0, 0));
        PositionableShape<Circle> cicle3 = new PositionableShape<Circle>(new Circle(10), new Transform(15, 0, 0));

        assertTrue(Collisions.isColliding(cicle1.convertToShape(), cicle2.convertToShape()));
        assertFalse(Collisions.isColliding(cicle1.convertToShape(), cicle3.convertToShape()));
    }

    @Test
    void RectangleCollidiong() {
        PositionableShape<Rectangle> rect1 = new PositionableShape<>(new Rectangle(3, 3, 0), new Transform(0, 0, 0));
        PositionableShape<Rectangle> rect2 = new PositionableShape<>(new Rectangle(3, 3, 0), new Transform(2, 2, 0));

        assertTrue(Collisions.isColliding(rect1.convertToShape(), rect2.convertToShape()));
    }

    @Test
    void OrientedRectangleColliding() {
        PositionableShape<Rectangle> rect1 = new PositionableShape<>(new Rectangle(10, 10, 0), new Transform(0, 0, 0));
        PositionableShape<Rectangle> rect2 = new PositionableShape<>(new Rectangle(10, 10, 0), new Transform(0, 11, Math.PI / 4));

        assertTrue(Collisions.isColliding(rect1.convertToShape(), rect2.convertToShape()));
    }

    @Test
    void RectangleDontCollid() {
        PositionableShape<Rectangle> rect1 = new PositionableShape<>(new Rectangle(3, 3, 0), new Transform(0, 0, 0));
        PositionableShape<Rectangle> rect2 = new PositionableShape<>(new Rectangle(3, 3, 0), new Transform(0, 9, Math.PI / 4));

        assertFalse(Collisions.isColliding(rect1.convertToShape(), rect2.convertToShape()));
    }




}
