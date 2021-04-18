package fr.unice.polytech.si3.qgl.qualituriers.utils.shape;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableCircle;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
        PositionableCircle circle = new PositionableCircle(new Circle(3), new Transform(0, 0, 0));
        Segment s = new Segment(new Point(0, 0), new Point(0, 5));

        assertTrue(s.intersectWith(circle));
    }

    @Test
    void TestCircleBetweenEdges() {
        PositionableCircle circle = new PositionableCircle(new Circle(3), new Transform(0, 0, 0));
        Segment s = new Segment(new Point(2, -4), new Point(2, 4));

        assertTrue(s.intersectWith(circle));
    }

    @Test
    void TestNotCollidInside() {
        PositionableCircle circle = new PositionableCircle(new Circle(3), new Transform(0, 0, 0));
        Segment s = new Segment(new Point(0, 0), new Point(0, 2));

        assertFalse(s.intersectWith(circle));
    }
    @Test
    void TestNotCollidOutside() {
        PositionableCircle circle = new PositionableCircle(new Circle(3), new Transform(0, 0, 0));
        Segment s = new Segment(new Point(0, 4), new Point(0, 5));

        assertFalse(s.intersectWith(circle));
    }

    @Test
    void TestGetDirection(){
        Segment s = new Segment(new Point(0, 4), new Point(0, 5));
        assertEquals(new Point(0, 1), s.getDirection());
    }

    @Test
    void TestIntersectionOfSupports(){
        Segment s = new Segment(new Point(0, 4), new Point(0, 5));
        Segment s2 = new Segment(new Point(3, 4), new Point(-2, 5));
        assertEquals(new Point(0, 4.6000000000000005), s.intersectionOfSupports(s2));
    }

    @Test
    void TestAxis(){
        Segment s = new Segment(new Point(0, 4), new Point(0, 5));
        PositionableCircle r = new PositionableCircle(new Circle(30), new Transform(5, 10,30));
        List<Point> compare = new ArrayList<>();
        compare.add(new Point(0, 1));
        assertEquals(compare, s.axis(r));
    }

    @Test
    void TestLength(){
        Segment s = new Segment(new Point(0, 4), new Point(0, 5));
        assertEquals(1, s.length());
    }

    @Test
    void TestToString(){
        Segment s = new Segment(new Point(0, 4), new Point(0, 5));
        assertEquals("Segment{start={ x: 0.0, y: 4.0 }, end={ x: 0.0, y: 5.0 }}", s.toString());
    }
}
