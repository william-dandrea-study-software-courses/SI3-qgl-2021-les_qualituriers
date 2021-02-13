package fr.unice.polytech.si3.qgl.qualituriers.utils;

import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.*;

public class PositionableShape<T extends Shape> {
    private final Transform transform;
    private final T shape;

    public PositionableShape(T shape, Transform transform) {
        this.shape = shape;
        this.transform = transform;
    }

    public Transform getTransform() {
        return transform;
    }

    public PositionableShape<Polygon> convertToPolygon() {
        if(shape.getType() == Shapes.RECTANGLE) {
            return new PositionableShape<>(((Rectangle)shape).toPolygon(), transform);
        }
        throw  new ClassCastException("Only rectangles can be converted to polygons");
    }

    public <T2 extends Shape> PositionableShape<T2> convertTo(Shapes shape) {
        if(shape != this.shape.getType()) throw new ClassCastException(this.shape.getType() + " can't be converted to " + shape);
        return new PositionableShape<>((T2)this.shape, transform);
    }

    public PositionableShape<Shape> convertToShape() {
        return new PositionableShape<>(shape, transform);
    }

    public boolean isIn(Point pt) {
        return shape.isIn(pt.substract(transform.getPoint()).rotate(-transform.getOrientation()));
    }

    public T getShape() {
        return shape;
    }
}
