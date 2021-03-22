package fr.unice.polytech.si3.qgl.qualituriers.utils.shape;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PolygonTest {

    @Test
    void getCircumscribedTest() {
        Rectangle rect1 = new Rectangle(1, 1, 0);

        var circumscribed1 = ((PolygonAbstract)rect1).getCircumscribed();
        assertEquals(circumscribed1.getTransform(), Transform.ZERO);
        double d = Math.abs(circumscribed1.getShape().getRadius() - Math.sqrt(0.5));
        assertTrue(d < 0.0001);

        Rectangle rect2 = new Rectangle(15, 1, 12);

        var circumscribed2 = ((PolygonAbstract)rect2).getCircumscribed();
        assertEquals(circumscribed2.getTransform(), Transform.ZERO);
        d = Math.abs(circumscribed2.getShape().getRadius() - Math.sqrt(7.5 * 7.5 + 0.25));
        assertTrue(d < 0.0001);
    }
}
