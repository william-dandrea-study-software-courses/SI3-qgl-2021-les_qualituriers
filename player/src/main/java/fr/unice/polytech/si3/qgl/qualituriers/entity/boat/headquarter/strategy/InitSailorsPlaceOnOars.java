package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.strategy;


import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.headquarterutils.BoatPathFinding;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.headquarterutils.HeadquarterUtil;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;

import java.util.*;


/**
 * Cette classe va, en debut de partie, aller positionner tout les marins en entrée sur des rames
 * Si il n'y a pas de rames disponible ou que les rames sont trop loin, alors cette classe va aller faire déplacer les marins
 * Au plus proche des rames afin que, au prochain tour, les marins puissent aller sur les rames
 * @author williamdandrea
 */
public class InitSailorsPlaceOnOars {

    private final Boat boat;
    private final List<Marin> sailors;


    private final List<BoatEntity> oarWeMoves;

    public InitSailorsPlaceOnOars(Boat boat, List<Marin> sailors) {
        this.boat = boat;
        this.sailors = sailors;


        oarWeMoves = new ArrayList<>();

    }


    /**
     * Cette méthode va générer la liste d'action nécessaire pour aller mettre les marins sur les rames, si pas de
     * rames dispo, alors ca va déplacer les marins le plus loin possible de leur position
     * @return la liste d'action a faire pour mettre les marins au meilleur endroit
     */
    public List<Action> initSailorsPlace() {
        List<Action> finalListOfActions = new ArrayList<>();
        List<Integer> sailorWeMoves = new ArrayList<>();

        // Si le marin n'est pas sur une rame, le déplacer le plus loin possible de sa position
        
        // On parcours tout les marins du haut gauche vers le haut droit jusqu'en bas
        for (int xBoat = 0; xBoat <= boat.getDeck().getLength(); xBoat++) {
            for (int yBoat = 0; yBoat <= boat.getDeck().getWidth(); yBoat++) {

                Optional<Marin> marinOptional = HeadquarterUtil.getSailorByHisPosition(sailors, xBoat, yBoat);
                if (marinOptional.isPresent() && !sailorWeMoves.contains(marinOptional.get().getId())) {

                    Marin marin = marinOptional.get();

                    if (HeadquarterUtil.getListOfSailorsOnOars(sailors, boat).stream().noneMatch(sailor -> sailor.getId() == marin.getId())) {
                        Optional<Action> action = getTheBestPositionForMovingTheSailorWithPathFindingClass(marin);
                        if (action.isPresent()) {
                            finalListOfActions.add(action.get());
                            sailorWeMoves.add(marin.getId());
                        }
                    }
                }

            }
        }

        return finalListOfActions;
    }







    private Optional<Action>  getTheBestPositionForMovingTheSailorWithPathFindingClass(Marin marin) {

        Optional<Action> finalAction = Optional.empty();
        Optional<Point> bestPosition = Optional.empty();


        double distanceBetweenOarAndSailor = 0;
        for (BoatEntity oar : HeadquarterUtil.getListOfOarWithAnySailorsOnIt(sailors, boat)) {

            double currentDistance = HeadquarterUtil.distanceBetweenTwoPoints(oar.getPosition(), marin.getPosition());
            if (marin.canMoveTo(oar.getX(), oar.getY(), boat) && currentDistance >= distanceBetweenOarAndSailor) {
                bestPosition = Optional.of(new Point(oar.getX(), oar.getY()));
            }
        }

        if (bestPosition.isPresent()) {
            Optional<Action> actionOp= HeadquarterUtil.generateMovingAction(marin.getId(), marin.getX(), marin.getY(), (int) bestPosition.get().getX(), (int) bestPosition.get().getY());
            if (actionOp.isPresent()){
                marin.setPosition((int) bestPosition.get().getX(), (int) bestPosition.get().getY());
                return actionOp;
            }
        }


        Optional<BoatEntity> positionPotentialOp = HeadquarterUtil.getTheFarthestOarFromAPosition(marin.getPosition(), boat, sailors, oarWeMoves);



        if (positionPotentialOp.isPresent()) {
            oarWeMoves.add(positionPotentialOp.get());
            BoatPathFinding boatPathFinding = new BoatPathFinding(sailors,boat, marin.getId(), positionPotentialOp.get().getPosition());
            Point positionFinale = boatPathFinding.generateClosestPoint();

            Optional<Action> actionOp= HeadquarterUtil.generateMovingAction(marin.getId(), marin.getX(), marin.getY(), (int) positionFinale.getX(), (int) positionFinale.getY());
            if (actionOp.isPresent()){
                marin.setPosition((int) positionFinale.getX(), (int) positionFinale.getY());
                return actionOp;
            }

        }

        return finalAction;
    }









}



