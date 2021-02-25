package fr.unice.polytech.si3.qgl.qualituriers.utils.shape;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;

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

    /**
     * Calcul les segments du polygon dans le repere global
     * @param transform le transform de la forme
     * @return La liste des segments
     */
    public List<Segment> getSegments(Transform transform) {
        List<Segment> segments = new ArrayList<>();

        // Create segment for each vertice and his neighboors
        for(int i = 0; i < vertices.length - 1; i++) {
            var thisAPos = transform.getPoint().add(vertices[i].rotate(transform.getOrientation() + orientation));
            var afterAPos = transform.getPoint().add(vertices[i + 1].rotate(transform.getOrientation() + orientation));

            segments.add(new Segment(thisAPos, afterAPos));
        }

        // Close the shape
        var firstAPos = transform.getPoint().add(vertices[0].rotate(transform.getOrientation() + orientation));
        var lastAPos = transform.getPoint().add(vertices[vertices.length - 1].rotate(transform.getOrientation() + orientation));
        segments.add(new Segment(lastAPos, firstAPos));

        return segments;
    }


    @Override
    public boolean isIn(Point position) {
        var pointSegment = new Segment(new Point(0, 0), position);
        return getSegments().stream().noneMatch(pointSegment::intersectWith);
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
}
