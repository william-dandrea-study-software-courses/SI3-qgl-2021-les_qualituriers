package fr.unice.polytech.si3.qgl.qualituriers.utils.shape;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableCircle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Cette classe représente un polygone qui pourra être utiliser pour représenter un bateau car un polygone à une
 * direction
 *
 * @author williamdandrea, Alexandre Arcil
 * @author CLODONG Yann
 */
public abstract class PolygonAbstract extends Shape {

    private final double orientation;
    private final Point[] vertices;

    protected PolygonAbstract(double orientation, Point[] vertices, Shapes shape) {
        super(shape);
        this.orientation = orientation;
        this.vertices = vertices;
        for (int i = 0; i < this.vertices.length; i++)
            this.vertices[i] = this.vertices[i].rotate(orientation);
    }

    public double getOrientation() {
        return orientation;
    }

    public Point[] getVertices() {
        return vertices;
    }

    public Point[] getVertices(Transform transform) {
        List<Point> pts = new ArrayList<>();
        for(var vertice : vertices) {
            pts.add(transform.getPoint().add(vertice.rotate(orientation + transform.getOrientation())));
            //orientation nécessaire ? ils le sont déjà dans le constructeur
        }

        return pts.toArray(new Point[0]);
    }

    /**
     * Calcul les segments du polygon dans sont propre repère : relatif au transform de la shape
     * @return La liste des segments
     */
    @JsonIgnore
    public List<Segment> getSegments() {
        List<Segment> segments = new ArrayList<>();

        // Create segment for each vertice and his neighboors
        for(int i = 0; i < vertices.length - 1; i++) {
            var thisAPos = vertices[i].rotate(orientation);
            var afterAPos = vertices[i + 1].rotate(orientation);

            segments.add(new Segment(thisAPos, afterAPos));
        }

        // Close the shape
        var firstAPos = vertices[0].rotate(orientation);
        var lastAPos = vertices[vertices.length - 1].rotate(orientation);
        segments.add(new Segment(lastAPos, firstAPos));

        return segments;
    }

    @Override
    public boolean isIn(Point position) {
        var pointSegment = new Segment(new Point(0, 0), position);
        return getSegments().stream().noneMatch(pointSegment::intersectWith);
    }

    @Override
    public PositionableCircle getCircumscribed() {
        var vertices = getVertices(Transform.ZERO);
        var center = Arrays.stream(vertices).reduce(Point.ZERO, Point::add).scalar(1 / (double)vertices.length);
        var maxDist = Arrays.stream(vertices).mapToDouble(p -> p.subtract(center).length()).max();

        if(maxDist.isEmpty()) return new PositionableCircle(new Circle(0), Transform.ZERO);

        return new PositionableCircle(new Circle(maxDist.getAsDouble()), new Transform(center, 0));
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof PolygonAbstract)) return false;
        var castedObj = (PolygonAbstract)obj;
        return Arrays.equals(castedObj.vertices, vertices) && castedObj.orientation == orientation;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(orientation);
        result = 31 * result + Arrays.hashCode(vertices);
        return result;
    }

    public abstract PolygonAbstract scaleFromCenter(double scale);

    public abstract PolygonAbstract enlargeOf(double length);
}
