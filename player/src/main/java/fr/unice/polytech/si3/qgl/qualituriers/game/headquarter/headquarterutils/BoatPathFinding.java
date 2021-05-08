package fr.unice.polytech.si3.qgl.qualituriers.game.headquarter.headquarterutils;

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



    public BoatPathFinding(List<Marin> sailors, Boat boat, int idSailorWeWantToMove, Point destination) {
        this.sailors = sailors;
        this.boat = boat;
        HeadquarterUtil.getSailorByHisID(sailors, idSailorWeWantToMove).ifPresent(value -> this.sailorWeWantToMove = value);
        this.destination = destination;
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

        if (sailorWeWantToMove.canMoveTo((int) destination.getX(), (int) destination.getY(), boat)) {
            return destination;
        }

        return generateTheNearestPoint();
    }




    Point generateTheNearestPoint() {

        List<Point> aroundCoefficients = generateTurningAroundPointCoefficients();

        int differenceX = (int) destination.getX() - sailorWeWantToMove.getX();
        int differenceY = (int) destination.getY() - sailorWeWantToMove.getY();


        int destX = 0; int destY = 0;
        double max = (double) Config.MAX_MOVING_CASES_MARIN * (double) Config.MAX_MOVING_CASES_MARIN;

        if ( differenceX >= 0 && differenceY >= 0 ) {
            // +X  +Y


            for (Point point : aroundCoefficients) {

                int sailorPosXOnThisPoint = sailorWeWantToMove.getX() + (int) point.getX();
                int sailorPosYOnThisPoint = sailorWeWantToMove.getY() + (int) point.getY();

                double distanceSailorGoal = HeadquarterUtil.distanceBetweenTwoPoints(new Point(sailorPosXOnThisPoint, sailorPosYOnThisPoint), destination);


                if (sailorWeWantToMove.canMoveTo(sailorPosXOnThisPoint, sailorPosYOnThisPoint, boat)
                        && distanceSailorGoal <= max
                        && HeadquarterUtil.placeIsFree(new Point(sailorPosXOnThisPoint, sailorPosYOnThisPoint), sailors)
                ) {

                    destX = sailorPosXOnThisPoint;
                    destY = sailorPosYOnThisPoint;
                    max = distanceSailorGoal;
                }
            }

        }
        if ( differenceX >= 0 && differenceY <= 0 ) {
            // +X  -Y

            for (Point point : aroundCoefficients) {

                int sailorPosXOnThisPoint = sailorWeWantToMove.getX() + (int) point.getX();
                int sailorPosYOnThisPoint = sailorWeWantToMove.getY() - (int) point.getY();

                double distanceSailorGoal = HeadquarterUtil.distanceBetweenTwoPoints(new Point(sailorPosXOnThisPoint, sailorPosYOnThisPoint), destination);

                if (sailorWeWantToMove.canMoveTo(sailorPosXOnThisPoint, sailorPosYOnThisPoint, boat)
                        && distanceSailorGoal <= max
                        && HeadquarterUtil.placeIsFree(new Point(sailorPosXOnThisPoint, sailorPosYOnThisPoint), sailors)
                ) {
                    destX = sailorPosXOnThisPoint;
                    destY = sailorPosYOnThisPoint;
                    max = distanceSailorGoal;
                }
            }
        }
        if ( differenceX <= 0 && differenceY >= 0 ) {
            // -X  +Y

            for (Point point : aroundCoefficients) {

                int sailorPosXOnThisPoint = sailorWeWantToMove.getX() - (int) point.getX();
                int sailorPosYOnThisPoint = sailorWeWantToMove.getY() + (int) point.getY();

                double distanceSailorGoal = HeadquarterUtil.distanceBetweenTwoPoints(new Point(sailorPosXOnThisPoint, sailorPosYOnThisPoint), destination);




                if (sailorWeWantToMove.canMoveTo(sailorPosXOnThisPoint, sailorPosYOnThisPoint, boat)
                        && distanceSailorGoal <= max
                        && HeadquarterUtil.placeIsFree(new Point(sailorPosXOnThisPoint, sailorPosYOnThisPoint), sailors)
                ) {

                    destX = sailorPosXOnThisPoint;
                    destY = sailorPosYOnThisPoint;
                    max = distanceSailorGoal;
                }
            }
        }
        if ( differenceX <= 0 && differenceY <= 0 ) {
            // -X  -Y

            for (Point point : aroundCoefficients) {
                int sailorPosXOnThisPoint = sailorWeWantToMove.getX() - (int) point.getX();
                int sailorPosYOnThisPoint = sailorWeWantToMove.getY() - (int) point.getY();

                double distanceSailorGoal = HeadquarterUtil.distanceBetweenTwoPoints(new Point(sailorPosXOnThisPoint, sailorPosYOnThisPoint), destination);



                if (sailorWeWantToMove.canMoveTo(sailorPosXOnThisPoint, sailorPosYOnThisPoint, boat)
                        && distanceSailorGoal <= max
                        && HeadquarterUtil.placeIsFree(new Point(sailorPosXOnThisPoint, sailorPosYOnThisPoint), sailors)
                ) {

                    destX = sailorPosXOnThisPoint;
                    destY = sailorPosYOnThisPoint;
                    max = distanceSailorGoal;
                }
            }
        }

        return new Point(destX, destY);
    }


    /**
     * ATTENTION : cet algorithme ne sera pas compréhensible en le lisant car il représente une suite de valeurs
     * défini a la main, et l'algorithme reproduit cette suite de valeur, il ne faut surtout pas le toucher !
     * @return liste des coefficients de rotation autour d'un point mais que d'un côté
     */
    List<Point> generateTurningAroundPointCoefficients() {
        List<Point> pointListAroundPosition = new ArrayList<>();

        for (int x = 0; x <= Config.MAX_MOVING_CASES_MARIN ; x++) {
            for (int y = 0; y <= Config.MAX_MOVING_CASES_MARIN; y++) {
                if (x + y <= 5) {
                    pointListAroundPosition.add(new Point(x, y));
                }
            }
        }

        return pointListAroundPosition;
    }
}
