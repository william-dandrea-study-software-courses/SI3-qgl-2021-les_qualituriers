package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.headquarterutils;

import fr.unice.polytech.si3.qgl.qualituriers.Config;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntities;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.exceptions.MaxAngleRudderException;
import fr.unice.polytech.si3.qgl.qualituriers.exceptions.MovingSailorException;
import fr.unice.polytech.si3.qgl.qualituriers.exceptions.SailorCantOarException;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Moving;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Oar;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Turn;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Cette classe regroupe plusieurs methodes utiles au PathFinding
 * @author D'Andrea William
 */
public class HeadquarterUtil {

    private HeadquarterUtil() {}


    /**
     * TESTED
     * Cette méthode genère une action moving grâce a deux points distants, un point de depart, et un point d'arrivée
     * @param sailorId l'id du marin
     * @param initialX la position X initiale du marin
     * @param initialY la position Y initiale du marin
     * @param finalX la position X finale du marin
     * @param finalY la position Y finale du marin
     * @return une action Moving
     */
    public static Optional<Action> generateMovingAction(int sailorId, int initialX, int initialY, int finalX, int finalY) {

        int distanceX  = finalX - initialX;
        int distanceY = finalY - initialY;

        if (Math.abs(distanceX) > Config.MAX_MOVING_CASES_MARIN || Math.abs(distanceY) > Config.MAX_MOVING_CASES_MARIN) {
            throw new MovingSailorException(sailorId);
        }

        if (distanceX == 0 && distanceY == 0) {
            return Optional.empty();
        }

        return Optional.of(new Moving(sailorId, finalX - initialX, finalY - initialY));
    }


    /**
     * TESTED
     * Cette méthode génére une action Rudder
     * @param sailorId l'id du marin que l'on souhaite faire rudder
     * @param angle l'angle du gouvernail
     * @return une action optionnal si on peut bien faire tourner le rudder
     */
    public static Optional<Action> generateRudder(int sailorId, double angle) {
        if (angle > Config.MAX_ANGLE_FOR_RUDDER + Config.EPSILON || angle < -Config.MAX_ANGLE_FOR_RUDDER - Config.EPSILON) {
            throw new MaxAngleRudderException(angle);
        }

        return Optional.of(new Turn(sailorId, angle));
    }


    /**
     * TESTED
     * Cette méthode génére une action de type oar pour faire ramer un marin
     * @param sailorId le marin que l'on souhaite faire ramer
     * @param sailors la liste des marins
     * @param boat le bateau en question
     * @return une action opt d'une action Oar ou releve une exception
     */
    public static Optional<Action> generateOar(int sailorId, List<Marin> sailors, Boat boat) {

        if (getListOfSailorsOnOars(sailors, boat).stream().noneMatch(marin -> marin.getId() == sailorId)) {
            throw new SailorCantOarException(sailorId);
        }
        return Optional.of(new Oar(sailorId));
    }

    /**
     * TESTED
     * cette méthode a pour but de regarder si le marin est sur une boatEntity ou pas
     * @param boat le bateau en question
     * @param marin le marin que l'on souhaite saoir si il est sur une oar ou non
     * @return true si le marin est sur une oar, false sinon
     */
    public static boolean sailorIsOnOar(Boat boat, Marin marin) {

        return getListOfOars(boat).stream().anyMatch(entity -> entity.getX() == marin.getX() && entity.getY() == marin.getY());

    }


    /**
     * TESTED
     * Cette méthode regarde un emplacement n'est squatté par aucun marins
     * @param position l'endroit ou l'on souhaite voir si il y a un marin ou pas
     * @param sailors la liste des marins sur le bateau
     * @return true si la place est libre, false sinon
     */
    public static boolean placeIsFree(Point position,List<Marin> sailors) {

        for (Marin sailor: sailors) {
            if (sailor.getX() == position.getX() && sailor.getY() == position.getY())
                return false;
        }
        return true;
    }

    /**
     * TESTED
     * Cette methode regarde si la place souhaité n'est pas une boat entity, par exemple, si une place sur un bateau n'est pas
     * une rame ou un gouvernail
     * @param position la position que l'on souhaite analysé
     * @param boat le bateau
     * @return true si la place est sans aucune entité, false sinon
     */
    public static boolean placeIsNotAnBoatEntity(Point position, Boat boat) {

        return Arrays.stream(boat.getEntities()).noneMatch(entity -> entity.getX() == (int) position.getX() && entity.getY() == (int) position.getY());

    }

    /**
     * TESTED
     * Cette méthode return la voile qui est situé sur le bateau
     * @param boat le bateau ou l'on veut trouver la voile
     * @return la voile qui est situé sur le bateau
     */
    public static Optional<BoatEntity> getSail(Boat boat) {
        return Arrays.stream(boat.getEntities()).filter(oar -> oar.getType() == BoatEntities.SAIL).findFirst();
    }


    /**
     * TESTED
     * Cette méthode return le gouvernail qui est situé sur le bateau
     * @param boat le bateau ou l'on veut trouver le gouvernail
     * @return le gouvernail qui est situé sur le bateau
     */
    public static Optional<BoatEntity> getRudder(Boat boat) {
        return Arrays.stream(boat.getEntities()).filter(oar -> oar.getType() == BoatEntities.RUDDER).findFirst();
    }


    /**
     * TESTED
     * Cette méthode retourne le marin qui est sur le gouvernal
     * @param boat le bateau
     * @param sailors la liste des marins sur le bateau
     * @return le sailor sur le rudder ou Op.empty() s'il n'y a personne dessus
     */
    public static Optional<Marin> getSailorOnRudder(Boat boat, List<Marin> sailors) {

        Optional<BoatEntity> rudder = getRudder(boat);
        return rudder.flatMap(boatEntity -> sailors.stream().filter(marin -> marin.getX() == boatEntity.getX() && marin.getY() == boatEntity.getY()).findAny());
    }


    /**
     * TESTED
     * Cette méthode retourne le marin qui est sur la voile
     * @param boat le bateau
     * @param sailors la liste des marins sur le bateau
     * @return le sailor sur la voile ou Op.empty() s'il n'y a personne dessus
     */
    public static Optional<Marin> getSailorOnSail(Boat boat, List<Marin> sailors) {
        Optional<BoatEntity> sailer = getSail(boat);
        return sailer.flatMap(boatEntity -> sailors.stream().filter(marin -> marin.getX() == boatEntity.getX() && marin.getY() == boatEntity.getY()).findAny());
    }


    /**
     * TESTED
     * Cette méthode return la liste totale des rames sur le bateau
     * @param boat le bateau ou l'on veut compter les rames
     * @return la liste de rames sur le bateau
     */
    public static List<BoatEntity> getListOfOars(Boat boat) {
        return Arrays.stream(boat.getEntities()).filter(oar -> oar.getType() == BoatEntities.OAR).collect(Collectors.toList());
    }

    /**
     * TESTED
     * @param boat le bateau ou l'on veut compter les rames
     * @return la liste de rames sur le côté babord du bateau
     */
    public static List<BoatEntity> getListOfBabordOars(Boat boat) {
        return Arrays.stream(boat.getEntities()).filter(oar -> oar.getType() == BoatEntities.OAR && oar.getY() == 0).collect(Collectors.toList());
    }

    /**
     * TESTED
     * @param boat le bateau ou l'on veut compter les rames
     * @return la liste de rames sur le côté tribord du bateau
     */
    public static List<BoatEntity> getListOfTribordOars(Boat boat) {
        return Arrays.stream(boat.getEntities()).filter(oar -> oar.getType() == BoatEntities.OAR && oar.getY() == boat.getDeck().getWidth()-1).collect(Collectors.toList());
    }

    /**
     * TESTED
     * @param boat le bateau ou l'on veut connaitre les emplacements ou il n'y a aucune entité
     * @return la liste des postions ou il n'y a aucune entité
     */
    public static List<Point> getListOfPlaceWithAnyEntitiesOnIt(Boat boat) {

        List<Point> finalListOfPositions = new ArrayList<>();
        for (int eachX = 0; eachX < boat.getDeck().getLength(); eachX++) {
            for (int eachY = 0; eachY < boat.getDeck().getWidth(); eachY++) {

                int finalEachX = eachX;
                int finalEachY = eachY;
                if (Arrays.stream(boat.getEntities()).noneMatch(entity -> entity.getX() == finalEachX && entity.getY() == finalEachY)) {
                    finalListOfPositions.add(new Point(eachX, eachY));
                }
            }
        }

        return finalListOfPositions;
    }



    /**
     * TESTED
     * Cette méthode renvoie la liste des marins qui se situent sur une rame du coté babord
     * @param sailors liste des marins sur le bateau
     * @param boat le bateau sur lequel on travaille
     * @return une liste de marins qui se situent sur les rames a babord
     */
    public static List<Marin> getListOfSailorsOnBabordOars(List<Marin> sailors, Boat boat) {

        List<Marin> marinsAtBabord =  sailors.stream().filter(marin -> marin.getY() == 0).collect(Collectors.toList());
        List<Marin> finalList = new ArrayList<>();
        for (Marin marin : marinsAtBabord) {
            if (getListOfBabordOars(boat).stream().anyMatch(oar -> oar.getX() == marin.getX() && oar.getY() == marin.getY())) {
                finalList.add(marin);
            }
        }
        return finalList;
    }


    /**
     * TESTED
     * Cette méthode renvoie la liste des marins qui se situent sur une rame du coté tribord
     * @param sailors liste des marins sur le bateau
     * @param boat le bateau sur lequel on travaille
     * @return une liste de marins qui se situent sur les rames a tribord
     */
    public static List<Marin> getListOfSailorsOnTribordOars(List<Marin> sailors, Boat boat) {

        List<Marin> marinsAtTribord = sailors.stream().filter(marin -> marin.getY() == boat.getDeck().getWidth()-1).collect(Collectors.toList());
        List<Marin> finalList = new ArrayList<>();
        for (Marin marin : marinsAtTribord) {
            if (getListOfTribordOars(boat).stream().anyMatch(oar -> oar.getX() == marin.getX() && oar.getY() == marin.getY())) {
                finalList.add(marin);
            }
        }
        return finalList;
    }

    /**
     * TESTED
     * Cette méthode renvoie la liste des marins qui se situent sur une rame
     * @param sailors liste des marins sur le bateau
     * @param boat le bateau sur lequel on travaille
     * @return une liste de marins qui se situent sur une rame
     */
    public static List<Marin> getListOfSailorsOnOars(List<Marin> sailors, Boat boat) {
        return Stream.concat(getListOfSailorsOnBabordOars(sailors, boat).stream(), getListOfSailorsOnTribordOars(sailors, boat).stream()).collect(Collectors.toList());
    }


    /**
     * TESTED
     * @param boat le bateau sur lequel on travaille
     * @param sailors la liste des marins sur le bateau
     * @return La liste des marins qui ne sont pas sur des rames
     */
    public static List<Marin> getListOfSailorsOnAnyOar(List<Marin> sailors, Boat boat) {
        return sailors.stream().filter(marin -> !getListOfSailorsOnOars(sailors, boat).contains(marin)).collect(Collectors.toList());
    }


    /**
     * TESTED
     * @param boat le bateau sur lequel on travaille
     * @param sailors la liste des marins sur le bateau
     * @return La liste des rames ou il n'y a aucun marin dessus
     */
    public static List<BoatEntity> getListOfOarWithAnySailorsOnIt(List<Marin> sailors, Boat boat) {

        return getListOfOars(boat)
                .stream()
                .filter(boatOar ->
                        getListOfSailorsOnOars(sailors, boat)
                                .stream()
                                .noneMatch(sailorOnOar -> boatOar.getX() == sailorOnOar.getX() && boatOar.getY() == sailorOnOar.getY()))
                .collect(Collectors.toList());
    }

    /**
     * TESTED
     * @param boat le bateau sur lequel on travaille
     * @param sailors la liste des marins sur le bateau
     * @return La liste des rames de babord ou il n'y a aucun marin dessus
     */
    public static List<BoatEntity> getListOfBabordOarWithAnySailorsOnIt(List<Marin> sailors, Boat boat) {
        return getListOfOars(boat)
                .stream()
                .filter(boatOar ->
                        getListOfSailorsOnBabordOars(sailors, boat)
                                .stream()
                                .noneMatch(sailorOnOar -> boatOar.getX() == sailorOnOar.getX() && boatOar.getY() == sailorOnOar.getY()))
                .collect(Collectors.toList());
    }

    /**
     * TESTED
     * @param boat le bateau sur lequel on travaille
     * @param sailors la liste des marins sur le bateau
     * @return La liste des rames de tribord ou il n'y a aucun marin dessus
     */
    public static List<BoatEntity> getListOfTribordOarWithAnySailorsOnIt(List<Marin> sailors, Boat boat) {
        return getListOfOars(boat)
                .stream()
                .filter(boatOar ->
                        getListOfSailorsOnTribordOars(sailors, boat)
                                .stream()
                                .noneMatch(sailorOnOar -> boatOar.getX() == sailorOnOar.getX() && boatOar.getY() == sailorOnOar.getY()))
                .collect(Collectors.toList());
    }


    /**
     * TESTED
     * Cette méthode renvoie le marin grâce à son ID
     * @param sailors la liste des marins
     * @param sailorID l'id du marin que l'on cherche
     * @return le marin qui a l'id que l'on cherche
     */
    public static Optional<Marin> getSailorByHisID(List<Marin> sailors, int sailorID) {
        return sailors.stream().filter(sailor -> sailor.getId() == sailorID).findAny();
    }


    /**
     * TESTED
     * Cette methode renvoie le marin qui est sur une certaine position, si il n'y a pas de marins sur cette position, ca renvoie
     * Op.empty()
     * @param sailors liste des marins sur le bateau
     * @param x la position x
     * @param y la position y
     * @return le marin si il est dispo ou un Op.empty si pas de marin dessus
     */
    public static Optional<Marin> getSailorByHisPosition(List<Marin> sailors, int x, int y) {
        return sailors.stream().filter(sailor -> sailor.getX() == x && sailor.getY() == y).findAny();
    }

    /**
     * TESTED
     * Cette methode renvoie la distance entre 2 points de la map
     * @param pointA le point A
     * @param pointB le point B
     * @return la distance entre le point A et le point B
     */
    public static double distanceBetweenTwoPoints(Point pointA, Point pointB) {

        return Math.sqrt((pointB.getY() - pointA.getY()) * (pointB.getY() - pointA.getY()) + (pointB.getX() - pointA.getX()) * (pointB.getX() - pointA.getX()));

    }


    /**
     * TESTED
     * Cette méthode à pour objectif d'aller rechercher le marin le plus proche d'une certaine position sur le bateau
     * @param sailors la liste de marins sur le bateau
     * @param point le point dont on souhaite savoir qui est le plus proche
     * @return le Marin le plus proche de ce point
     */
    public static Marin searchTheClosestSailorToAPoint(List<Marin> sailors, Point point, List<Integer> sailorsWeDontWant) {

        double distanceMinimale = distanceBetweenTwoPoints(sailors.get(0).getPosition(), point);
        Marin closerSailor = sailors.get(0);

        for (Marin marin : sailors) {

            if (distanceBetweenTwoPoints(marin.getPosition(), point) < distanceMinimale
                    && !sailorsWeDontWant.contains(marin.getId())
            ) {
                closerSailor = marin;
            }
        }
        return closerSailor;
    }



}
