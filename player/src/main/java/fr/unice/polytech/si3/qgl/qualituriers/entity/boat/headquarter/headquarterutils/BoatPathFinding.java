package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.headquarterutils;

import fr.unice.polytech.si3.qgl.qualituriers.Config;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe permet de trouver le point optimum sur le bateau pour déplacer le marin. On doit appeler generateClosestPoint
 * pour trouver le point optimum :
 *     -> Si le marin est sur la place de destination : return la destination ou est le marin
 *     -> Si le marin peut bouger sur la place de destination : return la place de destination
 *     -> Si le marin ne peux pas bouger directement sur la place de destination : return la place disponible la plus proche sur le bateau
 *
 * @author D'Andrea William
 */
public class BoatPathFinding {

    private final List<Marin> sailors;
    private final Boat boat;
    private Marin sailorWeWantToMove;
    private final Point destination;

    private static final int MIN_X = 0;
    private final int maxX;
    private static final int MIN_Y = 0;
    private final int maxY;

    public BoatPathFinding(List<Marin> sailors, Boat boat, int idSailorWeWantToMove, Point destination) {
        this.sailors = sailors;
        this.boat = boat;
        HeadquarterUtil.getSailorByHisID(sailors, idSailorWeWantToMove).ifPresent(value -> this.sailorWeWantToMove = value);
        this.destination = destination;
        this.maxX = boat.getDeck().getLength() - 1;
        this.maxY = boat.getDeck().getWidth() - 1;
    }


    /**
     * Cette méthode retourne le point optimum ou se placer
     * @return -> Si le marin est sur la place de destination : return la destination ou est le marin
     *         -> Si le marin peut bouger sur la place de destination : return la place de destination
     *         -> Si le marin ne peux pas bouger directement sur la place de destination : return la place disponible la plus proche sur le bateau
     */
    public Point generateClosestPoint() {

        if (destination.equals(sailorWeWantToMove.getPosition()))
            return destination;

        // NOUS NOUS METTONS D'ABORD AU PLUS PRÊT DE LA DESTIONATION (RAYON DE 5) ET ENSUITE NOUS TOURNONS AUTOUR
        Point idealDestination = getTheInitialCloserPosition();




        if (sailorWeWantToMove.canMoveTo((int) idealDestination.getX(), (int) idealDestination.getY(), boat) && HeadquarterUtil.placeIsFree(idealDestination, sailors)) {
            return idealDestination;
        }

        List<Point> movingCoefficients = generateTurningAroundPointCoefficients();

        // Maintenant, nous devons aller rechercher tout les emplacements possibles entre le marin et idealDestination


        Point finalPoint = new Point(sailorWeWantToMove.getX(), sailorWeWantToMove.getY());

        for (Point point : movingCoefficients) {

            Point position = sailorWeWantToMove.getPosition();

            int positionX = (int) ((position.getX() - idealDestination.getX() >= 0) ? (idealDestination.getX() + point.getX()) : (idealDestination.getX() - point.getX()));
            int positionY = (int) ((position.getY() - idealDestination.getY() >= 0) ? (idealDestination.getY() + point.getY()) : (idealDestination.getY() - point.getY()));


            // Si on peut bouger le marin sur cette position, on a la position la plus proche
            if (sailorWeWantToMove.canMoveTo(positionX, positionY, boat)
                    && HeadquarterUtil.placeIsFree(new Point(positionX, positionY), sailors)
            ) {

                return new Point(positionX, positionY);
            }

        }

        return finalPoint;
    }


    /**
     * Cette méthode permet de retourner la premiere destionation idéale ou deplacer la marin
     * @return le point ideal ou deplacer le marin
     */
     Point getTheInitialCloserPosition() {

        int differenceInitDestX = (int) (destination.getX() - sailorWeWantToMove.getX());
        int differenceInitDestY = (int) (destination.getY() - sailorWeWantToMove.getY());

        int pointFinalX = (int) destination.getX();
        int pointFinalY = (int) destination.getY();


        if (Math.abs(differenceInitDestX) >= Config.MAX_MOVING_CASES_MARIN ) {

            if (differenceInitDestX >= 0) {
                pointFinalX = Math.min(sailorWeWantToMove.getX() + Config.MAX_MOVING_CASES_MARIN, maxX);
            } else {
                pointFinalX = Math.max(sailorWeWantToMove.getX() - Config.MAX_MOVING_CASES_MARIN, MIN_X);
            }

        }

        if (Math.abs(differenceInitDestY) >= Config.MAX_MOVING_CASES_MARIN ) {

            if (differenceInitDestY >= 0) {
                pointFinalY = Math.min(sailorWeWantToMove.getY() + Config.MAX_MOVING_CASES_MARIN, maxY);
            } else {
                pointFinalY = Math.max(sailorWeWantToMove.getY() - Config.MAX_MOVING_CASES_MARIN, MIN_Y);
            }

        }

        return new Point(pointFinalX, pointFinalY);
    }


    /**
     * ATTENTION : cet algorithme ne sera pas compréhensible en le lisant car il représente une suite de valeurs
     * défini a la main, et l'algorithme reproduit cette suite de valeur, il ne faut surtout pas le toucher !
     * @return liste des coefficients de rotation autour d'un point mais que d'un côté
     */
    List<Point> generateTurningAroundPointCoefficients() {
        List<Point> pointListAroundPosition = new ArrayList<>();

        int start = 1;
        int leftRight = 0;   // 0 pour left, 1 pour right

        int lines = 0;

        while (lines <= Config.MAX_MOVING_CASES_MARIN*Config.MAX_MOVING_CASES_MARIN) {


            for (int i = 1; i <= start; i++) {
                if (leftRight == 0) {
                    pointListAroundPosition.add(new Point(start, (double) i-1));

                } else {
                    pointListAroundPosition.add(new Point( (double) i-1, start));

                }
                lines++;
            }

            pointListAroundPosition.add(new Point( start, start));lines++;

            int valueDecrement = start-1;

            for (int i = 1; i <= start; i++) {
                if (leftRight == 1) {
                    pointListAroundPosition.add(new Point(start, valueDecrement));

                } else {
                    pointListAroundPosition.add(new Point( valueDecrement, start));
                }
                lines++;
                valueDecrement--;
            }

            start++;
            leftRight = (leftRight == 0) ? 1 : 0;
        }


        return pointListAroundPosition;
    }
}
