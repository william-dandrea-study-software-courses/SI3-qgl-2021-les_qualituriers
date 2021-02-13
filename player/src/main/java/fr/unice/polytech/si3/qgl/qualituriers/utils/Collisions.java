package fr.unice.polytech.si3.qgl.qualituriers.utils;

import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.*;

import java.util.ArrayList;
import java.util.List;

public class Collisions {


    private static boolean isCirclesCollidingEachOther(PositionableShape<Circle> circle1, PositionableShape<Circle> circle2) {
        var distance = circle1.getTransform().getPoint().substract(circle2.getTransform().getPoint()).length();
        return distance < circle1.getShape().getRadius() + circle2.getShape().getRadius();
    }

    /*public static List<Segment> getSegments(PositionableShape<Polygon> polygon) {
        var vertices = polygon.getShape().getVertices();
        List<Segment> segments = new ArrayList<>();

        // Create segment for each vertice and his neighboors
        for(int i = 0; i < vertices.length - 1; i++) {
            var thisAPos = polygon.getTransform().getPoint().add(vertices[i].rotate(polygon.getTransform().getOrientation() + polygon.getShape().getOrientation()));
            var afterAPos = polygon.getTransform().getPoint().add(vertices[i + 1].rotate(polygon.getTransform().getOrientation() + polygon.getShape().getOrientation()));

            segments.add(new Segment(thisAPos, afterAPos));
        }

        // Close the shape
        var firstAPos = polygon.getTransform().getPoint().add(vertices[0].rotate(polygon.getTransform().getOrientation() + polygon.getShape().getOrientation()));
        var lastAPos = polygon.getTransform().getPoint().add(vertices[vertices.length - 1].rotate(polygon.getTransform().getOrientation() + polygon.getShape().getOrientation()));
        segments.add(new Segment(lastAPos, firstAPos));

        return segments;
    }*/

    private static boolean isPolygonsCollidingEachOther(PositionableShape<Polygon> polygon1, PositionableShape<Polygon> polygon2) {
        var ss1 = polygon1.getShape().getSegments(polygon1.getTransform());
        var ss2 = polygon2.getShape().getSegments(polygon2.getTransform());

        boolean twoInOne = polygon1.isIn(polygon2.getTransform().getPoint());
        boolean oneInTwo = polygon2.isIn(polygon1.getTransform().getPoint());

        return twoInOne || oneInTwo || ss1.stream().anyMatch(s1 -> ss2.stream().anyMatch(s1::intersectWith));
    }

    private static boolean isPolygonCollidingWithCircle(PositionableShape<Polygon> polygon, PositionableShape<Circle> circle) {
        return polygon.getShape().getSegments(polygon.getTransform()).stream().anyMatch(s -> s.intersectWith(circle))
                // l'une des formes est a l'interieur de l'autre
                || circle.isIn(polygon.getTransform().getPoint())
                || polygon.isIn(circle.getTransform().getPoint());
    }

    public static boolean isColliding(PositionableShape<Shape> shape1, PositionableShape<Shape> shape2) {

        if(shape1.getShape().getType() == Shapes.RECTANGLE)
            shape1 = shape1.convertToPolygon().convertToShape();
        if(shape2.getShape().getType() == Shapes.RECTANGLE)
            shape2 = shape2.convertToPolygon().convertToShape();

        if(shape1.getShape().getType() == Shapes.CIRCLE && shape2.getShape().getType() == Shapes.CIRCLE) {
            // 2 circles
            return isCirclesCollidingEachOther(shape1.convertTo(Shapes.CIRCLE), shape2.convertTo(Shapes.CIRCLE));
        } else if(shape1.getShape().getType() == Shapes.CIRCLE) {
            // shape1 circle
            return isPolygonCollidingWithCircle(shape1.convertTo(Shapes.POLYGON), shape2.convertTo(Shapes.CIRCLE));
        } else if(shape2.getShape().getType() == Shapes.CIRCLE) {
            // shape2 circle
            return isPolygonCollidingWithCircle(shape2.convertTo(Shapes.POLYGON), shape1.convertTo(Shapes.CIRCLE));
        } else {
            // 2 polygons
            return isPolygonsCollidingEachOther(shape1.convertTo(Shapes.POLYGON), shape2.convertTo(Shapes.POLYGON));
        }

    }
}
