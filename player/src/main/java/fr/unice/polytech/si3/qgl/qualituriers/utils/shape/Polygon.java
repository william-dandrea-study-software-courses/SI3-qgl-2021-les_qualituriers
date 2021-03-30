package fr.unice.polytech.si3.qgl.qualituriers.utils.shape;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Cette classe représente un polygone qui pourra être utiliser pour représenter un bateau car un polygone à une
 * direction
 *
 * @author williamdandrea, Alexandre Arcil
 * @author CLODONG Yann
 */
public class Polygon extends PolygonAbstract {

    @JsonCreator
    public Polygon(@JsonProperty("orientation") double orientation, @JsonProperty("vertices") Point[] vertices) {
        super(orientation, vertices, Shapes.POLYGON);
    }

    public static Polygon createRegular(int nGone, double radius) {
        double dTheta = 2 * Math.PI / nGone;
        List<Point> vertices = new ArrayList<>();
        for(int i = 0; i < nGone; i++) {
            vertices.add(new Point((double)i * dTheta).scalar(radius));
        }
        return new Polygon(0, vertices.toArray(new Point[0]));
    }

    @Override
    public PolygonAbstract scaleFromCenter(double scale) {
        var vertices = getVertices();
        var center = Arrays.stream(vertices).reduce(Point.ZERO, Point::add).scalar(1 / (double)vertices.length);

        List<Point> newVertices = new ArrayList<>();
        Arrays.stream(vertices).map(v -> v.substract(center)).map(v -> v.scalar(scale)).forEach(newVertices::add);
        return new Polygon(getOrientation(), newVertices.toArray(new Point[0]));
    }

    @Override
    public PolygonAbstract enlargeOf(int length) {
        var vertices = getVertices();
        var center = Arrays.stream(vertices).reduce(Point.ZERO, Point::add).scalar(1 / (double)vertices.length);

        List<Point> newVertices = new ArrayList<>();
        Arrays.stream(vertices).map(v -> v.substract(center)).map(v -> {
            var currentLength = v.length();
            return v.scalar((currentLength + length) / currentLength);
        }).forEach(newVertices::add);
        return new Polygon(getOrientation(), newVertices.toArray(new Point[0]));
    }
}
