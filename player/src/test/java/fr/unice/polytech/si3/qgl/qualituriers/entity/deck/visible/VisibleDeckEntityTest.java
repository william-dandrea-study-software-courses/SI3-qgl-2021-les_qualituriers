package fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableShapeFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class VisibleDeckEntityTest {

    StreamVisibleDeckEntity stream;

    @BeforeEach
    void init(){
        this.stream = new StreamVisibleDeckEntity(new Transform(1, 2, 0), new Circle(10), 15);
    }

    @Test
    void getShapeTest(){
        assertEquals(new Circle(10), stream.getShape());
    }

    @Test
    void getPositionTest(){
        assertEquals(new Transform(1, 2, 0), stream.getPosition());
    }

    @Test
    void getPositionableShapeTest(){
        assertEquals(PositionableShapeFactory.getPositionable(new Circle(10), new Transform(1, 2, 0)), stream.getPositionableShape());
    }

    @Test
    void toStringTest(){
        assertEquals("VisibleDeckEntities{type='STREAM', visibleDeckEntity=class fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.StreamVisibleDeckEntity}", stream.getType().toString());
    }
}
