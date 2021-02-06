package fr.unice.polytech.si3.qgl.qualituriers.utils.shape;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;

import java.util.Arrays;

/**
 * Cette classe représente un polygone qui pourra être utiliser pour représenter un bateau car un polygone à une
 * direction
 *
 * @author williamdandrea, Alexandre Arcil
 * @author CLODONG Yann
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

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof Polygon)) return false;
        var castedObj = (Polygon)obj;
        return Arrays.equals(castedObj.vertices, vertices) && castedObj.orientation == orientation;
    }
}
