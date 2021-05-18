package fr.unice.polytech.si3.qgl.qualituriers.utils.shape;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.helpers.MyColumn2DMat;
import fr.unice.polytech.si3.qgl.qualituriers.utils.helpers.MySquared2DMat;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableShape;

import java.util.ArrayList;
import java.util.List;

public class Segment extends Polygon {

    private final Point start;
    private final Point end;

    public Segment(Point start, Point end) {
        super(0, new Point[] {start,end});
        //super(0D, new Point[]{start, end} );
        //Est-ce utile de calculer l'angle entre les 2 points ?
        this.start = start;
        this.end = end;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    /**
     * Teste si le segment intersect avec le cercle
     * @param other le cercle
     * @return true si le segment intersect avec le cercle, false sinon
     */
    @JsonIgnore
    public boolean intersectWith(PositionableShape<Circle> other) {
        var segmentLength = end.substract(start).length();
        var vector = end.substract(start).normalized();
        var normal = new Point(vector.getY(), -vector.getX());

        var relativePos = other.getTransform().getPoint().substract(start);
        var distFromDroite = relativePos.scalar(normal);

        // Circle is too far from the droite
        if(Math.abs(distFromDroite) > other.getShape().getRadius()) return false;

        // check if collid in the segment
        var projOnDroite = relativePos.scalar(vector);

        boolean startInside = other.getShape().isIn(start.substract(other.getTransform().getPoint()));
        boolean endInside = other.getShape().isIn(end.substract(other.getTransform().getPoint()));

        return (projOnDroite >= 0 && projOnDroite <= segmentLength && !startInside && !endInside) ||
                // L'un des deux extremités sont dans le cercle
                (startInside != endInside);
    }

    /**
     * Teste si les 2 segments intersect
     * @param other le deuxieme segment
     * @return true si les segments intersect, false sinon
     */
    @JsonIgnore
    public boolean intersectWith(Segment other) {
        return isIntersectDontCheckOther(other) && other.isIntersectDontCheckOther(this);
    }

    @JsonIgnore
    private boolean isIntersectDontCheckOther(Segment other) {
        var vector = end.substract(start);
        var normal = new Point(vector.getY(), -vector.getX());

        var relativeEnd = other.end.substract(start);
        var relativeStart = other.start.substract(start);

        var endDistToDroite = relativeEnd.scalar(normal);
        var startDistToDroite = relativeStart.scalar(normal);

        // si un point est sur la droite
        if(endDistToDroite == 0 || startDistToDroite == 0) return true;

        // Check opposite sign
        return endDistToDroite * startDistToDroite < 0;
    }

    private double[] getCartesianFactors() {
        var dir = getEnd().substract(getStart());
        double a = dir.getY();
        double b = -dir.getX();
        double c = -(getStart().getX() * dir.getY() - getStart().getY() * dir.getX());

        double verif = a * getEnd().getX() + b * getEnd().getY() + c;

        return new double[] { a, b, c };
    }

    public Point getDirection() {
        return end.substract(start);
    }

    public Point intersectionOfSupports(Segment other) {
        var cf1 = getCartesianFactors();
        double a1 = cf1[0];
        double b1 = cf1[1];
        double c1 = cf1[2];
        var cf2 = other.getCartesianFactors();
        double a2 = cf2[0];
        double b2 = cf2[1];
        double c2 = cf2[2];

        try {
            var mat = new MySquared2DMat(a1, b1, a2, b2);
            var inv = mat.inverse();

            var det1 = mat.determinant();
            var det2 = inv.determinant();

            var matricialCalcResult = inv.multiply(new MyColumn2DMat(-c1, -c2));
            return new Point(matricialCalcResult.getA(), matricialCalcResult.getB());
        } catch (Exception e) {
            return null;
        }
    }


    public List<Point> axis(PositionableShape<? extends Shape> other) {
        var delta = end.substract(start);
        List<Point> a = new ArrayList<>();
        a.add(delta);
        return a;
    }

    public double length() {
        return end.substract(start).length();
    }

    /*public Segment scale(double scale) {
        return changeLength(length() * scale);
    }

    public Segment changeLength(double length) {
        var dir = end.substract(start);
        var currentLength = dir.length();
        var oldCenter = start.add(dir.scalar(0.5));
        dir = dir.normalized();

        var nStart = oldCenter.add(dir.scalar(-length / 2));
        var nEnd = oldCenter.add(dir.scalar(length / 2));

        return new Segment(nStart, nEnd);
    }*/

    @Override
    public String toString() {
        return "Segment{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }

}
