package fr.unice.polytech.si3.qgl.qualituriers.utils.shape;

/**
 * Correspond aux différentes formes que peuvent prendre les obstacles. Chaque enum contient le type ainsi que la
 * class qui le représente.
 *
 * @author williamdandrea
 */

public enum Shapes {

    RECTANGLE("RECTANGLE", Rectangle.class),
    CIRCLE("CIRCLE", Circle.class),
    POLYGON("POLYGONE", Polygon.class);

    private final String type;
    private final Class<? extends Shape> shape;


    Shapes(String type, Class<? extends Shape> shape) {
        this.type = type;
        this.shape = shape;
    }
}
