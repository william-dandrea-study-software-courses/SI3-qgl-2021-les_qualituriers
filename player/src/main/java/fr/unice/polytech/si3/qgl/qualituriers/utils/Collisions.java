package fr.unice.polytech.si3.qgl.qualituriers.utils;

import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.*;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableCircle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionablePolygon;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableShape;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Class permettant la collision entre 2 figures.
 * Elle utilise le "Théorème des axes séparateurs" (TAS):
 * https://developer.mozilla.org/fr/docs/Games/Techniques/2D_collision_detection#th%C3%A9or%C3%A8me_des_axes_s%C3%A9parateurs
 */
public class Collisions {

    private Collisions() {}

    /**
     * Teste si les formes sont en contact
     * @param shape1 La première forme
     * @param shape2 La seconde forme
     * @return true si les formes sont en contact, false sinon
     */
    public static boolean isColliding(PositionableShape<? extends Shape> shape1, PositionableShape<? extends Shape> shape2) {
        //On récupère les axes
        List<Point> axes = new ArrayList<>();
        axes.addAll(shape1.axis(shape2));
        axes.addAll(shape2.axis(shape1));
        //On supprime celle qui sont colinéaires
        removeCollinear(axes);

        //On projette chaque point sur un axe. S'il existe un espace entre les 2 figures projetées,
        // alors c'est qu'elles ne sont pas en collisions
        for (Point axe : axes) {
            double[] project = minMaxPoint(shape1.project(axe), axe);
            double[] project2 = minMaxPoint(shape2.project(axe), axe);
            if(project[1] <= project2[0] || project2[1] <= project[0])
                return false;
        }
        return true;
    }

    /**
     * Permet de supprimer les axes colinéaires entre eux
     * @param axes les axes
     */
    private static void removeCollinear(List<Point> axes) {
        Iterator<Point> points = axes.iterator();
        while (points.hasNext()) {
            if(containCollinear(points.next(), axes))
                points.remove();
        }
    }

    /**
     * Permet de savoir si axes contient déjà un axe qui serai colinéaire a axe
     * @param axe L'axe
     * @param axes Tout les axes
     * @return vrai si axes contient un axe colinéaire à axe, faux sinon.
     */
    private static boolean containCollinear(Point axe, List<Point> axes) {
        for (Point axe1 : axes) {
            if(axe.isCollinearTo(axe1) && !axe.equals(axe1))
                return true;
        }
        return false;
    }

    /**
     * Permet de récupérer les 2 points les plus éloignées sur l'axe.
     * @param points Les points projetées sur un axe
     * @return Le produit scalaire du plus petit point en 0 et le plus grand en 1
     */
    private static double[] minMaxPoint(Point[] points, Point axis) {
        double[] minMax = new double[2];
        minMax[0] = Double.POSITIVE_INFINITY;
        minMax[1] = Double.NEGATIVE_INFINITY;
        for (Point point : points) {
            double scalar = point.scalar(axis);
            minMax[0] = Math.min(minMax[0], scalar);
            minMax[1] = Math.max(minMax[1], scalar);
        }
        return minMax;
    }

    /**
     * Determine si la shape intersect avec le chemin
     * @param segment: chemin
     * @param shape: Obstacle
     * @return true si le chemin est obstrué, false sinon
     */
    public static boolean raycast(Segment segment, PositionableShape<? extends Shape> shape) {
        return isColliding(segment, shape);
    }

    public static boolean raycastPolygon(Segment segment, Stream<PositionablePolygon> shape) {
        return shape.anyMatch(s -> raycast(segment, s));
    }

    public static boolean raycast(Segment segment, Stream<PositionableShape<? extends Shape>> shape) {
        return shape.anyMatch(s -> raycast(segment, s));
    }

    public static double getDistanceCast(Point start, Point end, PositionableCircle shape, double margin) {
        var r = shape.getShape().getRadius() + margin;

        var d = end.substract(start).normalized();
        var dx = d.getX();
        var dy = d.getY();

        var dObs = shape.getTransform().getPoint().substract(start);
        var ABx = dObs.getX();
        var ABy = dObs.getY();


        var a = dx*dx + dy*dy;
        var b = -2 * (ABx * dx + ABy * dy);
        var c = ABx * ABx + ABy * ABy - r * r;
        var delta = b * b - 4 * a * c;

        if(delta < 0) return -1;

        var x1 = (-b - Math.sqrt(delta)) / (2 * a);
        var x2 = (-b + Math.sqrt(delta)) / (2 * a);

        return Math.min(x1, x2);
    }
}
