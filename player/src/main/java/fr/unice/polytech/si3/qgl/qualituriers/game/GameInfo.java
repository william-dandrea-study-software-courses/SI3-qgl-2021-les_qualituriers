package fr.unice.polytech.si3.qgl.qualituriers.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntities;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.Wind;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.VisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.game.goal.Goal;
import fr.unice.polytech.si3.qgl.qualituriers.game.headquarterboat.sailorsmission.SailorMission;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Cette class a pour but d'initialiser le jeu et de permettre de pouvoir faire des actions plus tard sur le jeu
 * @author CLODONG Yann
 */

public class GameInfo {

    private final Goal goal;
    private Boat ship;
    private Marin[] sailors;
    private int shipCount;
    private Wind wind;
    private VisibleDeckEntity[] seaEntities;
    private int numberOfTurn;
    private double traveledDistance;
    private List<Point> pointsWhereTheBoatMoved;

    private List<Action> actionsToDoDuringOneTurn;

    @JsonCreator
    public GameInfo(@JsonProperty("goal") Goal goal, @JsonProperty("ship") Boat ship,
                    @JsonProperty("sailors") Marin[] sailors, @JsonProperty("shipCount") int shipCount,
                    @JsonProperty("wind") Wind wind, @JsonProperty("seaEntities") VisibleDeckEntity[] seaEntities) {
        this.goal = goal;
        this.ship = ship;
        this.sailors = sailors;
        this.shipCount = shipCount;
        this.wind = wind;
        this.seaEntities = seaEntities;
        this.numberOfTurn = 0;
        this.traveledDistance = 0;
        this.pointsWhereTheBoatMoved = new ArrayList<>();
    }

    public Boat getShip() {
        return ship;
    }

    public Goal getGoal() {
        return goal;
    }

    public int getShipCount() {
        return shipCount;
    }

    public Marin[] getSailors() {
        return sailors;
    }

    public Wind getWind() {
        return wind;
    }

    public VisibleDeckEntity[] getSeaEntities() {
        return seaEntities;
    }

    public int getNumberOfTurn() { return numberOfTurn; }
    public double getTraveledDistance() { return traveledDistance; }

    public List<Point> getPointsWhereTheBoatMoved() {
        return pointsWhereTheBoatMoved;
    }

    public void addPointsWhereTheBoatMoved(Point pointsWhereTheBoatMoved) {
        this.pointsWhereTheBoatMoved.add(pointsWhereTheBoatMoved);
        this.numberOfTurn++;
        if (numberOfTurn >= 2) {
            Point lastPoint = this.pointsWhereTheBoatMoved.get(this.pointsWhereTheBoatMoved.size() - 2);
            this.traveledDistance += Math.sqrt((pointsWhereTheBoatMoved.getY() - lastPoint.getY()) * (pointsWhereTheBoatMoved.getY() - lastPoint.getY()) + (pointsWhereTheBoatMoved.getX() - lastPoint.getX()) * (pointsWhereTheBoatMoved.getX() - lastPoint.getX()));
        }

    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public void setSeaEntities(VisibleDeckEntity[] seaEntities) {
        this.seaEntities = seaEntities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameInfo gameInfo = (GameInfo) o;
        return shipCount == gameInfo.shipCount && Objects.equals(goal, gameInfo.goal)
                && Objects.equals(ship, gameInfo.ship) && Arrays.equals(sailors, gameInfo.sailors)
                && Objects.equals(wind, gameInfo.wind) && Objects.equals(seaEntities, gameInfo.seaEntities);
    }

    public void setShip(Boat ship) {
        this.ship = ship;
    }

    public void setSailors(Marin[] sailors) {
        this.sailors = sailors;
    }

    public void setShipCount(int shipCount) {
        this.shipCount = shipCount;
    }


    public void initializeActionsToDoDuringOneTurn() {
        this.actionsToDoDuringOneTurn = new ArrayList<>();
    }

    public void addActionsToDoDuringOneTurn(Action action) {
        this.actionsToDoDuringOneTurn.add(action);
    }

    public void addAllActionsToDoDuringOneTurn(List<Action> actions) {
        this.actionsToDoDuringOneTurn.addAll(actions);
    }

    public List<Action> getActionsToDoDuringOneTurn() {
        return actionsToDoDuringOneTurn;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(goal, ship, shipCount);
        result = 31 * result + Arrays.hashCode(sailors);
        return result;
    }

    @Override
    public String toString() {
        return "GameInfo{" +
                "goal=" + goal +
                ", ship=" + ship +
                ", sailors=" + Arrays.toString(sailors) +
                ", shipCount=" + shipCount +
                ", wind=" + wind +
                ", seaEntities=" + seaEntities +
                '}';
    }





    public void reinitializeAffectedSailorsInBoatEntities() {

        for (BoatEntity entity : ship.getEntities()) {
            entity.setSailorAffected(null);
        }

    }

    public void reinitializeAllSailorsMissions() {
        for (Marin marin : sailors) {
            marin.setSailorMission(SailorMission.NONE_SAILOR);
        }
    }



    public List<Marin> getSailorsWithAnyMissions() {
        return Arrays.stream(sailors).filter(sailor -> sailor.getSailorMission() == SailorMission.NONE_SAILOR).collect(Collectors.toList());
    }




    /**
     * Cette méthode à pour objectif d'aller rechercher le marin le plus proche d'une certaine position sur le bateau
     * @param point le point dont on souhaite savoir qui est le plus proche
     * @return le Marin le plus proche de ce point
     */
    public Marin searchTheClosestSailorToAPoint(Point point) {

        List<Marin> sailorsIntern = Arrays.asList(sailors.clone());

        double distanceMinimale = sailorsIntern.get(0).getPosition().distance(point);
        Marin closerSailor = sailorsIntern.get(0);

        for (Marin marin : sailorsIntern) {

            if (marin.getPosition().distance(point) < distanceMinimale) {
                distanceMinimale = marin.getPosition().distance(point);
                closerSailor = marin;
            }
        }
        return closerSailor;
    }



    /**
     * Cette méthode return la liste totale des rames sur le bateau
     * @return la liste de rames sur le bateau
     */
    public List<BoatEntity> getListOfOars() {
        return Arrays.stream(getShip().getEntities()).filter(oar -> oar.getType() == BoatEntities.OAR).collect(Collectors.toList());
    }

    /**
     * @return la liste de rames sur le côté babord du bateau
     */
    public List<BoatEntity> getListOfBabordOars() {
        return Arrays.stream(ship.getEntities()).filter(oar -> oar.getType() == BoatEntities.OAR && oar.getY() == 0).collect(Collectors.toList());
    }

    /**
     * @return la liste de rames sur le côté tribord du bateau
     */
    public List<BoatEntity> getListOfTribordOars() {
        return Arrays.stream(ship.getEntities()).filter(oar -> oar.getType() == BoatEntities.OAR && oar.getY() == ship.getDeck().getWidth()-1).collect(Collectors.toList());
    }


    /**
     * @return la liste des postions ou il n'y a aucune entité
     */
    public List<Point> getListOfPlaceWithAnyEntitiesOnIt() {

        List<Point> finalListOfPositions = new ArrayList<>();

        for (int eachX = 0; eachX < ship.getDeck().getLength(); eachX++) {
            for (int eachY = 0; eachY < ship.getDeck().getWidth(); eachY++) {

                int finalEachX = eachX;
                int finalEachY = eachY;
                if (Arrays.stream(ship.getEntities()).noneMatch(entity -> entity.getX() == finalEachX && entity.getY() == finalEachY)) {
                    finalListOfPositions.add(new Point(eachX, eachY));
                }
            }
        }

        return finalListOfPositions;
    }


    /**
     * Cette méthode renvoie la liste des marins qui se situent sur une rame du coté babord
     * @return une liste de marins qui se situent sur les rames a babord
     */
    public List<Marin> getListOfSailorsOnBabordOars() {

        List<Marin> marinsAtBabord =  Arrays.stream(sailors).filter(marin -> marin.getY() == 0).collect(Collectors.toList());

        List<Marin> finalList = new ArrayList<>();
        for (Marin marin : marinsAtBabord) {
            if (getListOfBabordOars().stream().anyMatch(oar -> oar.getX() == marin.getX() && oar.getY() == marin.getY())) {
                finalList.add(marin);
            }
        }
        return finalList;
    }

    /**
     * Cette méthode renvoie la liste des marins qui se situent sur une rame du coté tribord
     * @return une liste de marins qui se situent sur les rames a tribord
     */
    public List<Marin> getListOfSailorsOnTribordOars() {

        List<Marin> marinsAtTribord = Arrays.stream(sailors).filter(marin -> marin.getY() == ship.getDeck().getWidth()-1).collect(Collectors.toList());
        List<Marin> finalList = new ArrayList<>();

        for (Marin marin : marinsAtTribord) {
            if (getListOfTribordOars().stream().anyMatch(oar -> oar.getX() == marin.getX() && oar.getY() == marin.getY())) {
                finalList.add(marin);
            }
        }
        return finalList;
    }


    /**
     * Cette méthode renvoie la liste des marins qui se situent sur une rame
     * @return une liste de marins qui se situent sur une rame
     */
    public List<Marin> getListOfSailorsOnOars() {
        return Stream.concat(getListOfSailorsOnBabordOars().stream(), getListOfSailorsOnTribordOars().stream()).collect(Collectors.toList());
    }



    /**
     * @return La liste des marins qui ne sont pas sur des rames
     */
    public List<Marin> getListOfSailorsOnAnyOar() {
        return Arrays.stream(sailors).filter(marin -> !getListOfSailorsOnOars().contains(marin)).collect(Collectors.toList());
    }

    /**
     * @return La liste des rames ou il n'y a aucun marin dessus
     */
    public List<BoatEntity> getListOfOarWithAnySailorsOnIt() {

        return getListOfOars()
                .stream()
                .filter(boatOar ->
                        getListOfSailorsOnOars()
                                .stream()
                                .noneMatch(sailorOnOar -> boatOar.getX() == sailorOnOar.getX() && boatOar.getY() == sailorOnOar.getY()))
                .collect(Collectors.toList());
    }


    /**
     * @return La liste des rames de babord ou il n'y a aucun marin dessus
     */
    public List<BoatEntity> getListOfBabordOarWithAnySailorsOnIt() {
        return getListOfOars()
                .stream()
                .filter(boatOar ->
                        getListOfSailorsOnBabordOars()
                                .stream()
                                .noneMatch(sailorOnOar -> boatOar.getX() == sailorOnOar.getX() && boatOar.getY() == sailorOnOar.getY()))
                .collect(Collectors.toList());
    }


    /**
     * @return La liste des rames de tribord ou il n'y a aucun marin dessus
     */
    public List<BoatEntity> getListOfTribordOarWithAnySailorsOnIt() {
        return getListOfOars()
                .stream()
                .filter(boatOar ->
                        getListOfSailorsOnTribordOars()
                                .stream()
                                .noneMatch(sailorOnOar -> boatOar.getX() == sailorOnOar.getX() && boatOar.getY() == sailorOnOar.getY()))
                .collect(Collectors.toList());
    }


    /**
     * Cette méthode renvoie le marin grâce à son ID
     * @param sailorID l'id du marin que l'on cherche
     * @return le marin qui a l'id que l'on cherche
     */
    public Optional<Marin> getSailorByHisID(int sailorID) {
        return Arrays.stream(sailors).filter(sailor -> sailor.getId() == sailorID).findAny();
    }


    /**
     * Cette methode renvoie le marin qui est sur une certaine position, si il n'y a pas de marins sur cette position, ca renvoie
     * Op.empty()
     * @param x la position x
     * @param y la position y
     * @return le marin si il est dispo ou un Op.empty si pas de marin dessus
     */
    public Optional<Marin> getSailorByHisPosition(int x, int y) {
        return Arrays.stream(sailors).filter(sailor -> sailor.getX() == x && sailor.getY() == y).findAny();
    }




}
