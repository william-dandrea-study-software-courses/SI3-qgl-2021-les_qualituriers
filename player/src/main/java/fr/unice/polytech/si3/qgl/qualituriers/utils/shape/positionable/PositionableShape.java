package fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.*;

import java.util.List;
import java.util.Objects;

public abstract class PositionableShape<T extends Shape> {

    private final T shape;
    private Transform transform;

    protected PositionableShape(T shape, Transform transform) {
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

    /**
     * Teste si le point est dans la forme
     * @param pt le point
     * @return true si le point est dans la forme, false sinon
     */
    /*public boolean isIn(Point pt) {
        return shape.isIn(pt.substract(transform.getPoint()).rotate(-transform.getOrientation()));
    }*/


    /**
     * @return Le cercle circonscrit de la forme
     */
    @JsonIgnore
    public PositionableCircle getCircumscribed() {
        var c = shape.getCircumscribed();
        return new PositionableCircle(c.getShape(), transform.getInParentLandmark(c.getTransform()));
    }


    @Override
    public String toString() {
        return "PositionableShape{" +
                "transform=" + transform +
                ", shape=" + shape +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositionableShape<?> that = (PositionableShape<?>) o;
        return Objects.equals(shape, that.shape) && Objects.equals(transform, that.transform);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shape, transform);
    }
}
