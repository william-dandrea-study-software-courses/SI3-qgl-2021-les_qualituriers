package fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.*;

import java.util.List;

public abstract class PositionableShape<T extends Shape> {

    private final T shape;
    private Transform transform;

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
     * Les points qui constitue la shape
     * @return au moins 1 point de la shape
     */
    public abstract Point[] getPoints();

    /**
     * Calcul la projetée des points sur l'axe
     * @param axe L'axe où projetée les points
     * @return Des points sur l'axe
     */
    public abstract Point[] project(Point axe);

    /**
     * Calcul l'axe suivant les points qui constitue la shape et/ou l'autre shape
     * @param other L'autre shape de la collision
     * @return Les axes où sera projetée les points
     */
    public abstract List<Point> axis(PositionableShape<? extends Shape> other);

    public void setTransform(Transform transform) {
        this.transform = transform;
    }

    /*public List<Vec> getFaces() {

    }*/


    /**
     * Converti la forme en Polygon, ne fonctionne que pour les rectangle
     * @throws ClassCastException si la forme n'est pas un rectangle
     * @return PositionableShape<Polygon>
     */
    /*public PositionableShape<Polygon> convertToPolygon() {

        if(shape.getType() == Shapes.RECTANGLE) {
            return new PositionableShape<>(((Rectangle) shape).toPolygon(), transform);
        }
        throw  new ClassCastException("Only rectangles can be converted to polygons");
    }*/

    /**
     * Convertis la forme en forme shape
     * @throws ClassCastException si la forme ne peut etre convertis
     * @param shape la forme dans laquel convertir cette forme
     * @param <T2> idem
     * @return Une forme du bon type
     */
    /*public <T2 extends Shape> PositionableShape<T2> convertTo(Shapes shape) {

        if(shape != this.shape.getType()) throw new ClassCastException(this.shape.getType() + " can't be converted to " + shape);
        return new PositionableShape<>((T2)this.shape, transform);
    }*/

    /**
     * Permet de convertir n'importe quelle forme en PositionableShape<Shape>
     * @return un PositionableShape<Shape>
     */
    /*public PositionableShape<Shape> convertToShape() {
        return new PositionableShape<>(shape, transform);
    }*/

    /**
     * Teste si le point est dans la forme
     * @param pt le point
     * @return true si le point est dans la forme, false sinon
     */
    public boolean isIn(Point pt) {
        return shape.isIn(pt.substract(transform.getPoint()).rotate(-transform.getOrientation()));
    }


    @Override
    public String toString() {
        return "PositionableShape{" +
                "transform=" + transform +
                ", shape=" + shape +
                '}';
    }
}
