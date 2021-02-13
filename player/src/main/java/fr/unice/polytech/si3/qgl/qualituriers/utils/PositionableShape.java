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

    public T getShape() {
        return shape;
    }

    /**
     * Converti la forme en Polygon, ne fonctionne que pour les rectangle
     * @throws ClassCastException si la forme n'est pas un rectangle
     * @return PositionableShape<Polygon>
     */
    public PositionableShape<Polygon> convertToPolygon() {
        if(shape.getType() == Shapes.RECTANGLE) {
            return new PositionableShape<>(((Rectangle)shape).toPolygon(), transform);
        }
        throw  new ClassCastException("Only rectangles can be converted to polygons");
    }

    /**
     * Convertis la forme en forme shape
     * @throws ClassCastException si la forme ne peut etre convertis
     * @param shape la forme dans laquel convertir cette forme
     * @param <T2> idem
     * @return Une forme du bon type
     */
    public <T2 extends Shape> PositionableShape<T2> convertTo(Shapes shape) {
        if(shape != this.shape.getType()) throw new ClassCastException(this.shape.getType() + " can't be converted to " + shape);
        return new PositionableShape<>((T2)this.shape, transform);
    }

    /**
     * Permet de convertir n'importe quelle forme en PositionableShape<Shape>
     * @return un PositionableShape<Shape>
     */
    public PositionableShape<Shape> convertToShape() {
        return new PositionableShape<>(shape, transform);
    }

    /**
     * Teste si le point est dans la forme
     * @param pt le point
     * @return true si le point est dans la forme, false sinon
     */
    public boolean isIn(Point pt) {
        return shape.isIn(pt.substract(transform.getPoint()).rotate(-transform.getOrientation()));
    }
}
