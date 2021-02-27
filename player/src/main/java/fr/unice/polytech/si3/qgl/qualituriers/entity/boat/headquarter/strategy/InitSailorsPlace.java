package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.strategy;

import fr.unice.polytech.si3.qgl.qualituriers.Config;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.headquarterutils.HeadquarterUtil;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Oar;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InitSailorsPlace {

    private Boat boat;
    private List<Marin> sailors;

    public InitSailorsPlace(Boat boat, List<Marin> sailors) {
        this.boat = boat;
        this.sailors = sailors;
    }

    public List<Action> initSailorsPlace() {

        List<Action> finalListOfActions = new ArrayList<>();

        // En partant du haut droit du bateau, nous allons essayer de bouger les marins le plus loin possible
        // de préférence sur une rame

        for (int eachLengthBoat = 0; eachLengthBoat < boat.getDeck().getLength(); eachLengthBoat++) {
            for (int eachWidthBoat = 0; eachWidthBoat < boat.getDeck().getWidth(); eachWidthBoat++) {

                int finalEachWidthBoat = eachWidthBoat; int finalEachLengthBoat = eachLengthBoat;
                // Si cet emplacement n'a pas de marin dessus, nous n'allons pas procéder au traitement
                // Si cet emplacement est une rame et qu'il y a deja un marin dessus, nous n'allons pas procéder au traitement


                Optional<Marin> sailorOnThisPlace = sailors.stream().filter(marin -> marin.getX() == finalEachWidthBoat && marin.getY() == finalEachLengthBoat).findAny();
                Optional<Marin> oarWithSailorOnIt = HeadquarterUtil.getListOfSailorsOnOars(sailors, boat).stream().filter(marin -> marin.getX() == finalEachWidthBoat && marin.getY() == finalEachLengthBoat).findAny();

                if (sailorOnThisPlace.isPresent() && oarWithSailorOnIt.isEmpty()) {
                    Optional<Action> actionsOp = moveSailorAtTheFarthestPosition(sailorOnThisPlace.get().getId());

                    if (actionsOp.isPresent()){
                        finalListOfActions.add(actionsOp.get());
                    }

                }
            }
        }

        return finalListOfActions;
    }




    /**
     * Cette méthode va essayer de faire bouger le marin le plus loin possible de sa position. Cette méthode va faire
     * bouger le marin sur la rame la plus loin de lui, et si il n'y a aucune rame dans un "rayon" de 5
     * blocs autour du marin, la méthode va déplacer le marin sur la position la plus éloigné de lui.
     */
    Optional<Action> moveSailorAtTheFarthestPosition(int sailorId) {

        Optional<Marin> sailorOp = sailors.stream().filter(marin -> marin.getId() == sailorId).findAny();

        int farthestPositionNotOnOarX = 0;
        int farthestPositionNotOnOarY = 0;
        boolean haveFindAFarthestDistanceNotOnOar = false;


        if (sailorOp.isPresent()) {
            Marin marin = sailorOp.get();
            int positionMarinX = marin.getX();
            int positionMarinY = marin.getY();

            List<BoatEntity> listOfOarsWithAnySailorsOnIt = HeadquarterUtil.getListOfOarWithAnySailorsOnIt(sailors, boat);


            // Maintenant que nous avons la liste des places libres, nous allons essayer de déplacer le marin le plus loin
            // possible de sa position




            for (int distanceMaxX = Config.MAX_MOVING_CASES_MARIN; distanceMaxX > 0; distanceMaxX--) {
                for (int distanceMaxY = Config.MAX_MOVING_CASES_MARIN; distanceMaxY > 0; distanceMaxY--) {

                    int futureSailorPositionPositiveX = marin.getX() + distanceMaxX; int futureSailorPositionPositiveY = marin.getY() + distanceMaxY;
                    int futureSailorPositionNegativeX = marin.getX() - distanceMaxX; int futureSailorPositionNegativeY = marin.getY() - distanceMaxY;

                    Optional<BoatEntity> placeWhereWeCanMoveSailorPositive = listOfOarsWithAnySailorsOnIt.stream().filter(oar -> oar.getX() == futureSailorPositionPositiveX && oar.getY() == futureSailorPositionPositiveY).findAny();

                    if (placeWhereWeCanMoveSailorPositive.isPresent()) {
                        marin.setX(futureSailorPositionPositiveX); marin.setY(futureSailorPositionPositiveY);
                        return HeadquarterUtil.generateMovingAction(sailorId, positionMarinX, positionMarinY, placeWhereWeCanMoveSailorPositive.get().getX(),  placeWhereWeCanMoveSailorPositive.get().getY());
                    }

                    Optional<BoatEntity> placeWhereWeCanMoveSailorNegative = listOfOarsWithAnySailorsOnIt.stream().filter(oar -> oar.getX() == futureSailorPositionNegativeX && oar.getY() == futureSailorPositionNegativeY).findAny();

                    if (placeWhereWeCanMoveSailorNegative.isPresent()) {
                        marin.setX(futureSailorPositionNegativeX); marin.setY(futureSailorPositionNegativeY);
                        return HeadquarterUtil.generateMovingAction(sailorId, positionMarinX, positionMarinY, placeWhereWeCanMoveSailorNegative.get().getX(),  placeWhereWeCanMoveSailorNegative.get().getY());
                    }

                    if (!haveFindAFarthestDistanceNotOnOar) {
                        Optional<Point> placeWhereWeCanMoveSailorPositiveNotOnOar = HeadquarterUtil.getListOfPlaceWithAnyEntitiesOnIt(boat).stream()
                                .filter(point -> point.getX() == futureSailorPositionPositiveX && point.getY() == futureSailorPositionPositiveY
                                        && sailors.stream().noneMatch(sailor -> point.getX() == sailor.getX() && point.getY() == sailor.getY()))
                                .findAny();

                        if (placeWhereWeCanMoveSailorPositiveNotOnOar.isPresent()) {
                            farthestPositionNotOnOarX = futureSailorPositionPositiveX; farthestPositionNotOnOarY = futureSailorPositionPositiveY;
                            haveFindAFarthestDistanceNotOnOar = true;
                        }

                        Optional<Point> placeWhereWeCanMoveSailorNegativeNotOnOar = HeadquarterUtil.getListOfPlaceWithAnyEntitiesOnIt(boat).stream()
                                .filter(point -> point.getX() == futureSailorPositionNegativeX && point.getY() == futureSailorPositionNegativeY
                                        && sailors.stream().noneMatch(sailor -> point.getX() == sailor.getX() && point.getY() == sailor.getY()))
                                .findAny();

                        if (placeWhereWeCanMoveSailorNegativeNotOnOar.isPresent()) {
                            farthestPositionNotOnOarX = futureSailorPositionNegativeX; farthestPositionNotOnOarY = futureSailorPositionNegativeY;
                            haveFindAFarthestDistanceNotOnOar = true;
                        }
                    }
                }
            }

            if (haveFindAFarthestDistanceNotOnOar) {
                marin.setX(farthestPositionNotOnOarX); marin.setY(farthestPositionNotOnOarY);
                return HeadquarterUtil.generateMovingAction(sailorId, positionMarinX, positionMarinY, farthestPositionNotOnOarX,  farthestPositionNotOnOarY);
            }
        }

        return Optional.empty();
    }

}
