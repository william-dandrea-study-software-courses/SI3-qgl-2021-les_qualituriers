package fr.unice.polytech.si3.qgl.qualituriers.utils.shape;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;

/**
 * Cette classe représente un polygone qui pourra être utiliser pour représenter un bateau car un polygone à une
 * direction
 *
 * @author williamdandrea, Alexandre Arcil
 */
public class Polygon extends Shape {

    private final double orientation;
    private final Point[] vertices;

    @JsonCreator
    public Polygon(@JsonProperty("orientation") double orientation, @JsonProperty("vertices") Point[] vertices) {
        super(Shapes.POLYGON);
        this.orientation = orientation;
        this.vertices = vertices;
    }

    public double getOrientation() {
        return orientation;
    }

    public Point[] getVertices() {
        return vertices;
    }

    @Override
    public boolean isIn(Point position) {
        return false;
    }
}
