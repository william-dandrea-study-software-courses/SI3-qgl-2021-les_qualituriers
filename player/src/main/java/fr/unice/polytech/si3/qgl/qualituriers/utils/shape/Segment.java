package fr.unice.polytech.si3.qgl.qualituriers.utils.shape;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableShape;

public class Segment {

    private final Point start;
    private final Point end;

    protected Segment(Point start, Point end) {
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
    public boolean intersectWith(Segment other) {


        return isIntersectDontCheckOther(other) && other.isIntersectDontCheckOther(this);
    }

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

    @Override
    public String toString() {
        return "Segment{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
