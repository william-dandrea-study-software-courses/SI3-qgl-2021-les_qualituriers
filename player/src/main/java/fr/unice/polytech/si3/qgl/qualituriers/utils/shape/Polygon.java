package fr.unice.polytech.si3.qgl.qualituriers.utils.shape;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    public PolygonAbstract enlargeOf(double length) {
        var vertices = getVertices();

        var arretes = getSegments();
        List<Segment> enlargedArrete = new ArrayList<>();

        if(vertices.length == 2) {
            throw new RuntimeException("Error");
        }

        // On ecarte les arrètes
        for(var arr : arretes) {
            var dir = arr.getEnd().substract(arr.getStart());
            var directionDecalage = dir.rotate(Math.PI / 2);
            var sensDecalage = arr.getStart().projection(directionDecalage).normalized();

            var newArrete = new Segment(
                    arr.getStart().add(sensDecalage.scalar(length)),
                    arr.getEnd().add(sensDecalage.scalar(length))
            );
            enlargedArrete.add(newArrete);
        }

        List<Point> finalVertices = new ArrayList<>();

        // création d'un arrondi
        for(int i = 0; i < enlargedArrete.size(); i++) {
            var seg1 = enlargedArrete.get(i);
            var seg2 = enlargedArrete.get((i + 1) % enlargedArrete.size());

            finalVertices.add(seg1.getStart());
            finalVertices.add(seg1.getEnd());
            /*var isc = seg1.intersectionOfSupports(seg2);
            if(isc == null) throw new RuntimeException("Poygon bizarre qui n'est pas un segment et qui à deux coté consecutifs parrallele");
            finalVertices.add(isc);*/
        }


        return new Polygon(getOrientation(), finalVertices.toArray(new Point[0]));
    }
}
