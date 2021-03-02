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
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.nonexit.Aim;


import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class HeadquarterUtil {


    /**
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

    public static Optional<Action> generateRudder(int sailorId, double angle) {
        if (angle > Config.MAX_ANGLE_FOR_RUDDER + Config.EPSILON || angle < -Config.MAX_ANGLE_FOR_RUDDER - Config.EPSILON) {
            throw new MaxAngleRudderException(angle);
        }

        return Optional.of(new Aim(sailorId, angle));
    }



    public static Optional<Action> generateOar(int sailorId, List<Marin> sailors, Boat boat) {

        if (getListOfSailorsOnOars(sailors, boat).stream().noneMatch(marin -> marin.getId() == sailorId)) {
            throw new SailorCantOarException(sailorId);
        }


        return Optional.of(new Oar(sailorId));
    }

    /**
     * Cette méthode return la voile qui est situé sur le bateau
     * @param boat le bateau ou l'on veut trouver la voile
     * @return la voile qui est situé sur le bateau
     */
    public static Optional<BoatEntity> getSail(Boat boat) {
        return Arrays.stream(boat.getEntities()).filter(oar -> oar.getType() == BoatEntities.SAIL).findFirst();
    }

    /**
     * Cette méthode return le gouvernail qui est situé sur le bateau
     * @param boat le bateau ou l'on veut trouver le gouvernail
     * @return le gouvernail qui est situé sur le bateau
     */
    public static Optional<BoatEntity> getRudder(Boat boat) {
        return Arrays.stream(boat.getEntities()).filter(oar -> oar.getType() == BoatEntities.RUDDER).findFirst();
    }


    public static Optional<Marin> getSailorOnRudder(Boat boat, List<Marin> sailors) {

        if (getRudder(boat).isPresent()) {

            return sailors.stream().filter(marin -> marin.getX() == getRudder(boat).get().getX() && marin.getY() == getRudder(boat).get().getY()).findAny();

        }
        return Optional.empty();
    }


    /**
     * Cette méthode return la liste totale des rames sur le bateau
     * @param boat le bateau ou l'on veut compter les rames
     * @return la liste de rames sur le bateau
     */
    public static List<BoatEntity> getListOfOars(Boat boat) {
        return Arrays.stream(boat.getEntities()).filter(oar -> oar.getType() == BoatEntities.OAR).collect(Collectors.toList());
    }

    /**
     * @param boat le bateau ou l'on veut compter les rames
     * @return la liste de rames sur le côté babord du bateau
     */
    public static List<BoatEntity> getListOfBabordOars(Boat boat) {
        return Arrays.stream(boat.getEntities()).filter(oar -> oar.getType() == BoatEntities.OAR && oar.getY() == 0).collect(Collectors.toList());
    }

    /**
     * @param boat le bateau ou l'on veut compter les rames
     * @return la liste de rames sur le côté tribord du bateau
     */
    public static List<BoatEntity> getListOfTribordOars(Boat boat) {
        return Arrays.stream(boat.getEntities()).filter(oar -> oar.getType() == BoatEntities.OAR && oar.getY() == boat.getDeck().getWidth()-1).collect(Collectors.toList());
    }

    /**
     * @param boat le bateau ou l'on veut connaitre les emplacements ou il n'y a aucune entité
     * @return la liste des postions ou il n'y a aucune entité
     */
    public static List<Point> getListOfPlaceWithAnyEntitiesOnIt(Boat boat) {

        List<Point> finalListOfPositions = new ArrayList<>();
        for (int eachX = 0; eachX < boat.getDeck().getLength(); eachX++) {
            for (int eachY = 0; eachY < boat.getDeck().getWidth(); eachY++) {

                int finalEachX = eachX; int finalEachY = eachY;
                if (Arrays.stream(boat.getEntities()).noneMatch(entity -> entity.getX() == finalEachX && entity.getY() == finalEachY)) {
                    finalListOfPositions.add(new Point(eachX, eachY));
                }
            }
        }

        return finalListOfPositions;
    }



    /**
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
     * Cette méthode renvoie la liste des marins qui se situent sur une rame
     * @param sailors liste des marins sur le bateau
     * @param boat le bateau sur lequel on travaille
     * @return une liste de marins qui se situent sur une rame
     */
    public static List<Marin> getListOfSailorsOnOars(List<Marin> sailors, Boat boat) {
        return Stream.concat(getListOfSailorsOnBabordOars(sailors, boat).stream(), getListOfSailorsOnTribordOars(sailors, boat).stream()).collect(Collectors.toList());
    }


    /**
     * @param boat le bateau sur lequel on travaille
     * @param sailors la liste des marins sur le bateau
     * @return La liste des marins qui ne sont pas sur des rames
     */
    public static List<Marin> getListOfSailorsOnAnyOar(List<Marin> sailors, Boat boat) {
        return sailors.stream().filter(marin -> !getListOfSailorsOnOars(sailors, boat).contains(marin)).collect(Collectors.toList());
    }


    /**
     * @param boat le bateau sur lequel on travaille
     * @param sailors la liste des marins sur le bateau
     * @return La liste des rames ou il n'y a aucun marin dessus
     */
    public static List<BoatEntity> getListOfOarWithAnySailorsOnIt(List<Marin> sailors, Boat boat) {

        return getListOfOars(boat)
                .stream()
                .filter(boatOar ->
                        !getListOfSailorsOnOars(sailors, boat)
                                .stream()
                                .filter(sailorOnOar -> boatOar.getX() == sailorOnOar.getX() && boatOar.getY() == sailorOnOar.getY()).findAny().isPresent())
                .collect(Collectors.toList());
    }


    /**
     * Cette méthode renvoie le marin grâce à son ID
     * @param sailors la liste des marins
     * @param sailorID l'id du marin que l'on cherche
     * @return le marin qui a l'id que l'on cherche
     */
    public static Optional<Marin> getSailorByHisID(List<Marin> sailors, int sailorID) {
        return sailors.stream().filter(sailor -> sailor.getId() == sailorID).findAny();
    }

    public static Optional<Marin> getSailorByHisPosition(List<Marin> sailors, int x, int y) {
        return sailors.stream().filter(sailor -> sailor.getX() == x && sailor.getY() == y).findAny();
    }





}
