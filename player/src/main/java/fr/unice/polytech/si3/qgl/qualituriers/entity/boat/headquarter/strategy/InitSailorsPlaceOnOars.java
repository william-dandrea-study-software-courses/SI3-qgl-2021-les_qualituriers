package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.strategy;

import fr.unice.polytech.si3.qgl.qualituriers.Config;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.headquarterutils.HeadquarterUtil;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Moving;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Oar;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Cette classe va, en debut de partie, aller positionner tout les marins en entrée sur des rames
 * Si il n'y a pas de rames disponible ou que les rames sont trop loin, alors cette classe va aller faire déplacer les marins
 * Au plus proche des rames afin que, au prochain tour, les marins puissent aller sur les rames
 * @author williamdandrea
 */
public class InitSailorsPlaceOnOars {

    private Boat boat;
    private List<Marin> sailors;

    private int maxMovingBoatX;
    private int maxMovingBoatY;

    public InitSailorsPlaceOnOars(Boat boat, List<Marin> sailors) {
        this.boat = boat;
        this.sailors = sailors;

        maxMovingBoatX = Math.min(boat.getDeck().getLength(), Config.MAX_MOVING_CASES_MARIN);
        maxMovingBoatY = Math.min(boat.getDeck().getWidth(), Config.MAX_MOVING_CASES_MARIN);

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
                        Optional<Action> action = getTheBestPositionForMovingTheSailor(marin);
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


    /**
     * Cette méthode génére la meilleure action possible (PS : la meilleur case du bateau) ou déplacer le marin
     * => de préférence, on le déplace sur une rame
     * => si pas de rames dispo dans son rayon de deplacement, on bouge le marin le plus loin possible
     * @param marin le amrin que l'on souhaite déplacer
     * @return la meilleure action a faire
     */
    private Optional<Action> getTheBestPositionForMovingTheSailor(Marin marin) {

        int positionNotOnAnOarX = 0;
        int positionNotOnAnOarY = 0;
        boolean farthestPoint = false;

        for (int deplacementX = maxMovingBoatX; deplacementX >=  -maxMovingBoatX; deplacementX--) {

            for (int deplacementY = maxMovingBoatY; deplacementY >= -maxMovingBoatY ; deplacementY--) {


                if (marin.getX() + deplacementX >= 0 && marin.getX() + deplacementX < boat.getDeck().getLength()
                        && marin.getY() + deplacementY >= 0 && marin.getY() + deplacementY < boat.getDeck().getWidth()) {

                    int emplacementPotentielMarinX = marin.getX() + deplacementX;
                    int emplacementPotentielMarinY = marin.getY() + deplacementY;

                    if (sailors.stream().noneMatch(sailor -> sailor.getX() == emplacementPotentielMarinX && sailor.getY() == emplacementPotentielMarinY)) {

                        Optional<BoatEntity> potentialOar = HeadquarterUtil.getListOfOarWithAnySailorsOnIt(sailors, boat).stream().filter(oar -> oar.getX() == emplacementPotentielMarinX && oar.getY() == emplacementPotentielMarinY).findAny();
                        Optional<Point> potentialPoint = HeadquarterUtil.getListOfPlaceWithAnyEntitiesOnIt(boat).stream().filter(point -> point.getX() == emplacementPotentielMarinX && point.getY() == emplacementPotentielMarinY).findAny();

                        if (potentialOar.isPresent() && marin.canMoveTo(potentialOar.get().getX(), potentialOar.get().getY(), boat)) {
                            Optional<Action> movingAction = HeadquarterUtil.generateMovingAction(marin.getId(), marin.getX(), marin.getY(), potentialOar.get().getX(), potentialOar.get().getY());

                            if (movingAction.isPresent()) {
                                marin.setPosition(potentialOar.get().getX(), potentialOar.get().getY());
                                return movingAction;
                            }
                        }

                        if (potentialPoint.isPresent() && !farthestPoint && marin.canMoveTo((int) potentialPoint.get().getX(), (int) potentialPoint.get().getY(), boat )) {

                            positionNotOnAnOarX = (int) potentialPoint.get().getX();
                            positionNotOnAnOarY = (int) potentialPoint.get().getY();
                            farthestPoint = true;
                        }
                    }
                }


                if (marin.getX() - deplacementX >= 0 && marin.getX() - deplacementX < boat.getDeck().getLength()
                        && marin.getY() - deplacementY >= 0 && marin.getY() - deplacementY < boat.getDeck().getWidth()) {

                    int emplacementPotentielMarinX = marin.getX() - deplacementX;
                    int emplacementPotentielMarinY = marin.getY() - deplacementY;

                    if (sailors.stream().noneMatch(sailor -> sailor.getX() == emplacementPotentielMarinX && sailor.getY() == emplacementPotentielMarinY)) {

                        Optional<BoatEntity> potentialOar = HeadquarterUtil.getListOfOarWithAnySailorsOnIt(sailors, boat).stream().filter(oar -> oar.getX() == emplacementPotentielMarinX && oar.getY() == emplacementPotentielMarinY).findAny();
                        Optional<Point> potentialPoint = HeadquarterUtil.getListOfPlaceWithAnyEntitiesOnIt(boat).stream().filter(point -> point.getX() == emplacementPotentielMarinX && point.getY() == emplacementPotentielMarinY).findAny();

                        if (potentialOar.isPresent() && marin.canMoveTo(potentialOar.get().getX(), potentialOar.get().getY(), boat)) {
                            Optional<Action> movingAction = HeadquarterUtil.generateMovingAction(marin.getId(), marin.getX(), marin.getY(), potentialOar.get().getX(), potentialOar.get().getY());

                            if (movingAction.isPresent()) {
                                marin.setPosition(potentialOar.get().getX(), potentialOar.get().getY());
                                return movingAction;
                            }
                        }

                        if (potentialPoint.isPresent() && !farthestPoint && marin.canMoveTo((int) potentialPoint.get().getX(), (int) potentialPoint.get().getY(), boat)) {

                            positionNotOnAnOarX = (int) potentialPoint.get().getX();
                            positionNotOnAnOarY = (int) potentialPoint.get().getY();
                            farthestPoint = true;
                        }
                    }

                }

            }

        }

        if (farthestPoint) {
            Optional<Action> movingAction = HeadquarterUtil.generateMovingAction(marin.getId(), marin.getX(), marin.getY(), positionNotOnAnOarX, positionNotOnAnOarY);

            if (movingAction.isPresent()) {
                marin.setPosition(positionNotOnAnOarX, positionNotOnAnOarY);
                return movingAction;
            }
        }

        return Optional.empty();

    }



}



