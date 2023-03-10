package fr.unice.polytech.si3.qgl.qualituriers.utils.shape;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RectangleTest {
    Rectangle orthoRect;
    Rectangle orientedRect;
    Rectangle reversedRect;

    @BeforeEach
    void init() {
        orthoRect = new Rectangle(10, 20, 0);
        orientedRect = new Rectangle(10, 20, Math.PI / 4);
        reversedRect = new Rectangle(10, 20, Math.PI);
    }

    @Test
    void isInTest() {
        assertFalse(orthoRect.isIn(new Point(11, 0)));
        assertTrue(orthoRect.isIn(new Point(10, 0)));
        assertTrue(orthoRect.isIn(new Point(0, 0)));
        assertFalse(orthoRect.isIn(new Point(-1, 0)));

        assertFalse(orientedRect.isIn(new Point(10, 0)));
        assertTrue(orientedRect.isIn(new Point(0, 1)));

        assertTrue(reversedRect.isIn(new Point(-1, 0)));
        assertFalse(reversedRect.isIn(new Point(1, 0)));
    }
}
