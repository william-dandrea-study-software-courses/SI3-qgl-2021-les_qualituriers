package fr.unice.polytech.si3.qgl.qualituriers.utils.shape;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
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

    @Test
    void createRegularTest(){
        Point[] vertices = {new Point(30, 0), new Point(-14.999999999999993, 25.98076211353316), new Point(-15.000000000000014, -25.980762113533153)};
        Polygon compare = new Polygon(0, vertices);
        assertTrue(compare.equals(Polygon.createRegular(3, 30)));
    }

    @Test
    void scaleFromCenterTest(){
        Point[] vertices = {new Point(30, 0), new Point(-14.999999999999993, 25.98076211353316), new Point(-15.000000000000014, -25.980762113533153)};
        Polygon polygon = new Polygon(0, vertices);
        Point[] vertices2 = {new Point(900.0000000000001, -7.105427357601002E-14), new Point(-449.9999999999997, 779.4228634059947), new Point(-450.0000000000004, -779.4228634059947)};
        Polygon compare = new Polygon(0, vertices2);
        assertEquals(compare, polygon.scaleFromCenter(30));
    }
}
