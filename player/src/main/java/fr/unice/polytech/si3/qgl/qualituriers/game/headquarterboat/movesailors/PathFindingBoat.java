package fr.unice.polytech.si3.qgl.qualituriers.game.headquarterboat.movesailors;

import fr.unice.polytech.si3.qgl.qualituriers.Config;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.game.headquarter.headquarterutils.HeadquarterUtil;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author D'Andréa William
 */
public class PathFindingBoat {

    private final Marin sailorWeWantToMove;
    private final Point destination;
    private final GameInfo gameInfo;

    private List<Point> aroundCoefficients;

    public PathFindingBoat(GameInfo gameInfo, Marin sailorWeWantToMove, Point destination) {
        this.gameInfo = gameInfo;
        this.sailorWeWantToMove = sailorWeWantToMove;
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

        if (sailorWeWantToMove.canMoveTo((int) destination.getX(), (int) destination.getY(), gameInfo.getShip())) {
            return destination;
        }

        return generateTheNearestPoint();
    }


    /**
     * Méthode qui départage dans quel sens nous tournons autour du sailor pour trouver la bonne position
     * @return le point optimal
     */
    Point generateTheNearestPoint() {

        aroundCoefficients = generateTurningAroundPointCoefficients();

        int differenceX = (int) destination.getX() - sailorWeWantToMove.getX();
        int differenceY = (int) destination.getY() - sailorWeWantToMove.getY();


        if ( differenceX >= 0 && differenceY >= 0 ) {
            // +X  +Y
            return generateTheIdealPoint(true, true);
        }

        if ( differenceX >= 0 && differenceY <= 0 ) {
            // +X  -Y
            return generateTheIdealPoint(true, false);
        }

        if ( differenceX <= 0 && differenceY >= 0 ) {
            // -X  +Y
            return generateTheIdealPoint(false, true);
        }

        // -X  -Y
        return generateTheIdealPoint(false, false);

    }


    /**
     * Méthode donnant le point (contenu dans la liste aroundCoefficient appliqué a la position actuelle du sailor)
     * optimal => c'est a dire le point le plus proche de la destination
     * @param xPositiv true si on additionne la position X au marin, false si on soustrait
     * @param yPositiv true si on additionne la position Y au marin, false si on soustrait
     * @return la position optimale ou deplacer le marin
     */
    Point generateTheIdealPoint(boolean xPositiv, boolean yPositiv) {

        int destX = 0; int destY = 0;
        double max = Double.POSITIVE_INFINITY;

        for (Point point : aroundCoefficients) {

            int sailorPosXOnThisPoint = (xPositiv) ? sailorWeWantToMove.getX() + (int) point.getX() : sailorWeWantToMove.getX() - (int) point.getX();
            int sailorPosYOnThisPoint = (yPositiv) ? sailorWeWantToMove.getY() + (int) point.getY() : sailorWeWantToMove.getY() - (int) point.getY();

            double distanceSailorGoal = destination.distance(new Point(sailorPosXOnThisPoint, sailorPosYOnThisPoint));

            if (sailorWeWantToMove.canMoveTo(sailorPosXOnThisPoint, sailorPosYOnThisPoint, gameInfo.getShip()) && distanceSailorGoal <= max && Arrays.stream(gameInfo.getSailors()).noneMatch(sailor -> sailor.getX() == sailorPosXOnThisPoint && sailor.getY() == sailorPosYOnThisPoint)) {
                destX = sailorPosXOnThisPoint;
                destY = sailorPosYOnThisPoint;
                max = distanceSailorGoal;
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
                if (x + y <= Config.MAX_MOVING_CASES_MARIN) {
                    pointListAroundPosition.add(new Point(x, y));
                }
            }
        }

        return pointListAroundPosition;
    }
}
