package fr.unice.polytech.si3.qgl.qualituriers.utils.shape;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.PositionableShape;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SegmentTest {

    @Test
    void TestIntersection() {
        Segment s1 = new Segment(new Point(0, 0), new Point(1, 0));
        Segment s2 = new Segment(new Point(0.5, -0.5), new Point(0.5, 0.5));

        assertTrue(s1.intersectWith(s2));
    }

    @Test
    void TestInintersection() {
        Segment s1 = new Segment(new Point(0, 0), new Point(0, 1));
        Segment s2 = new Segment(new Point(0.5, -0.5), new Point(0.5, 0.5));

        assertFalse(s1.intersectWith(s2));
    }

    @Test
    void TestInintersectionForDroiteIntersecting() {
        Segment s1 = new Segment(new Point(0, 0), new Point(1, 0));
        Segment s2 = new Segment(new Point(2, -0.5), new Point(2, 0.5));

        assertFalse(s1.intersectWith(s2));
    }

    @Test
    void TestConfondu() {
        Segment s1 = new Segment(new Point(0, 0), new Point(1, 0));
        Segment s2 = new Segment(new Point(0, 0), new Point(1, 0));

        assertTrue(s1.intersectWith(s2));
    }

    @Test
    void PointOnDroite() {
        Segment s1 = new Segment(new Point(0, 0), new Point(1, 0));
        Segment s2 = new Segment(new Point(2, 0), new Point(2, 1));

        assertFalse(s1.intersectWith(s2));
    }

    @Test
    void PointOnSegment() {
        Segment s1 = new Segment(new Point(0, 0), new Point(1, 0));
        Segment s2 = new Segment(new Point(1, 0), new Point(1, 1));

        assertTrue(s1.intersectWith(s2));
    }

    @Test
    void SegmentIntersectWithCircle() {
        PositionableShape<Circle> circle = new PositionableShape<>(new Circle(3), new Transform(0, 0, 0));
        Segment s = new Segment(new Point(0, 0), new Point(0, 5));

        assertTrue(s.intersectWith(circle));
    }

    @Test
    void TestCircleBetweenEdges() {
        PositionableShape<Circle> circle = new PositionableShape<>(new Circle(3), new Transform(0, 0, 0));
        Segment s = new Segment(new Point(2, -4), new Point(2, 4));

        assertTrue(s.intersectWith(circle));
    }

    @Test
    void TestNotCollidInside() {
        PositionableShape<Circle> circle = new PositionableShape<>(new Circle(3), new Transform(0, 0, 0));
        Segment s = new Segment(new Point(0, 0), new Point(0, 2));

        assertFalse(s.intersectWith(circle));
    }
    @Test
    void TestNotCollidOutside() {
        PositionableShape<Circle> circle = new PositionableShape<>(new Circle(3), new Transform(0, 0, 0));
        Segment s = new Segment(new Point(0, 4), new Point(0, 5));

        assertFalse(s.intersectWith(circle));
    }
}
