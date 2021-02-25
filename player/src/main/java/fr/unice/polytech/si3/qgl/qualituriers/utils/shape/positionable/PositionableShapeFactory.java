package fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.PolygonAbstract;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;

public class PositionableShapeFactory {

    private PositionableShapeFactory() {}

    /**
     * Donne la bonne instance de PositionableShape suivant shape.
     * @param shape Une forme
     * @param transform Une transform
     * @return une instance de la class qui représente la PositionableShape de la shape
     */
    public static PositionableShape<? extends Shape> getPositionable(Shape shape, Transform transform) {
        switch (shape.getType()) {
            case CIRCLE:
                return new PositionableCircle((Circle) shape, transform);
            case RECTANGLE: case POLYGON:
                return new PositionablePolygon((PolygonAbstract) shape, transform);
            default:
                return null;
        }
    }

}
