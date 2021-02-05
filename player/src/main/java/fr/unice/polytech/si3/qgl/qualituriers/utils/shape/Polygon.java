package fr.unice.polytech.si3.qgl.qualituriers.utils.shape;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;

/**
 * Cette classe represente un polygone qui pourra etre utiliser pour representer un bateau car un polygone à une
 * dirrection
 *
 * @author williamdandrea
 */


public class Polygon extends Shape{

    private double orientation;
    private Point[] vertices;

    public Polygon(@JsonProperty("orientation")double orientation, @JsonProperty("vertices") Point[] vertices) {
        super(Shapes.POLYGON.getType());
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
