package fr.unice.polytech.si3.qgl.qualituriers.utils;

import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CheckpointTest {

    CheckPoint checkPoint;

    @BeforeEach
    public void init() {
        this.checkPoint = new CheckPoint(new Transform(10, 10, 0), new Rectangle(5, 2, 0));
    }

    @Test
    public void testEquals() {
        CheckPoint checkPoint1 = new CheckPoint(new Transform(10, 10, 0), new Rectangle(5, 2, 0));
        CheckPoint checkPoint2 = new CheckPoint(new Transform(10, 10, 0), new Rectangle(5, 3, 0));
        CheckPoint checkPoint3 = new CheckPoint(new Transform(10, 20, 0), new Rectangle(5, 2, 0));
        assertEquals(checkPoint1, checkPoint);
        assertNotEquals(checkPoint2, checkPoint);
        assertNotEquals(checkPoint3, checkPoint);
        assertNotEquals(checkPoint, null);
        assertNotEquals(checkPoint, "test");
    }

    @Test
    public void testHashcode() {
        CheckPoint checkPoint1 = new CheckPoint(new Transform(10, 10, 0), new Rectangle(5, 2, 0));
        CheckPoint checkPoint2 = new CheckPoint(new Transform(10, 10, 0), new Rectangle(5, 3, 0));
        CheckPoint checkPoint3 = new CheckPoint(new Transform(10, 20, 0), new Rectangle(5, 2, 0));
        assertEquals(checkPoint1.hashCode(), checkPoint.hashCode());
        assertNotEquals(checkPoint2.hashCode(), checkPoint.hashCode());
        assertNotEquals(checkPoint3.hashCode(), checkPoint.hashCode());
    }

}
