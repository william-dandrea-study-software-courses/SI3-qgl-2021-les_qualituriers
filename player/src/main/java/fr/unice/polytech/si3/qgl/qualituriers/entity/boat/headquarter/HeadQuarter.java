package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter;


import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.SailBoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.headquarterutils.BoatPathFinding;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.headquarterutils.HeadquarterUtil;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.strategy.*;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.Wind;
import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.LiftSail;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.LowerSail;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Cette classe a pour objectif de controller tout ce qu'il se passe sur le bateau.
 * Cette méthode va affecter par exemple n marins pour ramer et n marins pour tirer des canons
 *
 * IMPORTANT : après un appel a cette méthode, il faut actualiser les marins dans le gameInfo grace a la methode getMarins de
 * HeadQuarter et SetMarin de GameInfo.
 *
 * @author williamdandrea
 */
public class HeadQuarter {

    private final Boat boat;
    private final List<Marin> sailors;
    private final Transform goal;
    private final GameInfo gameInfo;

    public HeadQuarter(Boat boat, List<Marin> sailors, Transform goal, GameInfo gameInfo) {
        this.boat = boat;
        this.sailors = sailors;
        this.goal = goal;
        this.gameInfo = gameInfo;

        if (this.gameInfo.getWind() == null) { this.gameInfo.setWind(new Wind(0,0)); }
    }

    public List<Action> playTurn() {

        List<Action> finalListOfActions = new ArrayList<>();

        // En premier lieu, nous allons mettre tout les marins a leur bonne place
        List<Marin> sailorsForOars = sailors.subList(0, sailors.size()-2);

        Marin sailorForRudder = sailors.get(sailors.size()-1);

        if ((HeadquarterUtil.getListOfSailorsOnOars(sailors, boat).size() != sailorsForOars.size() || HeadquarterUtil.getRudder(boat).isEmpty())) {
            finalListOfActions.addAll(initOarSailorsPlace(boat, sailorsForOars));
            finalListOfActions.addAll(initRudderSailorPlace(boat, sailorForRudder));
        }

        // Maintenant que nous avons initié les rames, nous allons essayer de faire bouger le bateau du bon angle
        int differenceOfOarsBetweenTribordAndBabord = recoverOarsDifferenceBetweenPortsideAndStarboardForGoingSomewhere(boat, goal); // tribord - babord
        finalListOfActions.addAll(oarTheGoodAmountOfSailors(differenceOfOarsBetweenTribordAndBabord, boat, sailors, goal));

        // Maintenant on va faire fonctioner le rudder
        double angleRudder = generateAngleRudder(boat, goal);
        Optional<Marin> sailorOnRudderOp = HeadquarterUtil.getSailorOnRudder(boat,sailors);



        if (HeadquarterUtil.getSailorOnSail(boat, sailors).isPresent() && sailorOnRudderOp.isEmpty() && !detectWind(boat, goal, sailors, gameInfo)) {

            var sailOnBoat = HeadquarterUtil.getSail(boat);
            if (sailOnBoat.isPresent()) {
                if (((SailBoatEntity) sailOnBoat.get()).isOpened()) {
                    finalListOfActions.add(new LowerSail(HeadquarterUtil.getSailorOnSail(boat, sailors).get().getId()));
                    HeadquarterUtil.getSail(boat).ifPresent(sail -> ((SailBoatEntity) sail).setOpened(false));
                } else {

                    var sailorOnSail  = HeadquarterUtil.getSailorOnSail(boat, sailors);
                    if (sailorOnSail.isPresent()) {
                        Optional<Action> moveSailGuyOnTheRudder = moveSailGuyOnTheRudder(boat, sailors, sailorOnSail.get());
                        moveSailGuyOnTheRudder.ifPresent(finalListOfActions::add);
                    }
                }
            }
        }

        if (sailorOnRudderOp.isPresent()) {

            if (detectWind(boat, goal, sailors, gameInfo)) {

                Optional<Action> actionOpMovingRudderGuyOnSail = moveRudderGuyOnTheSail(boat, sailors, sailorForRudder);

                if (actionOpMovingRudderGuyOnSail.isPresent()) {

                    finalListOfActions.add(actionOpMovingRudderGuyOnSail.get());

                    Optional<Marin> rudderOp = HeadquarterUtil.getSailorOnSail(boat,sailors);

                    if (rudderOp.isPresent()) {
                        finalListOfActions.add(new LiftSail(sailorForRudder.getId()));
                        HeadquarterUtil.getSail(boat).ifPresent(sail -> ((SailBoatEntity) sail).setOpened(true));
                    }

                }

            } else {
                Optional<Action> actionOptional = HeadquarterUtil.generateRudder(sailorOnRudderOp.get().getId(), angleRudder);
                actionOptional.ifPresent(finalListOfActions::add);
            }
        }

        return finalListOfActions;
    }


    /**
     * Cette méthode va faire bouger les marins a leur bonne place, c'est a dire :
     * A l'initiation de la partie, les marins vont aller tous se positionner sur des rames, le but va etre d'avoir
     * tout les marins sur des rames, et un marin sur le Rudder, le marin sur le rudder va osciller ensuite entre
     * Le rudder et la voile
     * @param methodBoat le boat
     * @param sailorsForOar les marins qui iront sur les rames
     * @return la liste d'action a effectuer pour bien positionner les marins
     */
    private List<Action> initOarSailorsPlace(Boat methodBoat, List<Marin> sailorsForOar) {

        // Une fois initSailorsPlaceOnRudder fait, on retirera dans le initSailorsPlaceOnOars la marin qui a été affecté au rudder
        InitSailorsPlaceOnOars initSailorsPlaceOnOars = new InitSailorsPlaceOnOars(methodBoat, sailorsForOar);
        return initSailorsPlaceOnOars.initSailorsPlace();
    }


    private List<Action> initRudderSailorPlace(Boat methodBoat, Marin sailorForRudder) {
        InitSailorsPlaceOnRudder initSailorsPlaceOnRudder = new InitSailorsPlaceOnRudder(methodBoat, sailorForRudder.getId(), sailors);
        return initSailorsPlaceOnRudder.initSailorsPlaceOnRudder();
    }


    /**
     * Cette méthode génére la différence de marin qu'il faut entre le cote tribord et le coté babord pour pouvoir atteindre le checkpoint
     * @param methodBoat le bateau
     * @param finalPoint le checkpoint de destination
     * @return la différence de sailors nécessaire : tribord - babord
     */
    private int recoverOarsDifferenceBetweenPortsideAndStarboardForGoingSomewhere(Boat methodBoat, Transform finalPoint) {
        DifferenceOfOarsForGoingSomewhere differenceOfOarsForGoingSomewhere = new DifferenceOfOarsForGoingSomewhere(methodBoat, finalPoint);
        return differenceOfOarsForGoingSomewhere.differenceOfOarsForGoingSomewhere();
    }

    /**
     * Cette méthode génére l'angle que devra avoir le rudder pour atteindre son checkpoint, il calcul un angle théorique
     * A partir de la méthode recoverOarsDifferenceBetweenPortsideAndStarboardForGoingSomewhere()
     * @param methodBoat le bateau
     * @param finalPoint le checkpoint de destination
     * @return l'angle que devra avoir le rudder
     */
    private double generateAngleRudder(Boat methodBoat, Transform finalPoint) {
        DifferenceOfOarsForGoingSomewhere differenceOfOarsForGoingSomewhere = new DifferenceOfOarsForGoingSomewhere(methodBoat, finalPoint);
        return differenceOfOarsForGoingSomewhere.differenceOfAngleForTheRudder();
    }


    /**
     * Cette méthode va généer les actions nécessaires pour faire avancer le bateau avec le bon nombre de rames de
     * chaque coté, (i.e ca va generer les actions oar pour faire avancer le bateau correctement)
     * @param differenceOfSailors la différence de rames qu'il doit y avoir entre la droite et la gauche
     * @param methodBoat  le bateau sur lequel on travail
     * @param methodSailors les marins sur le bateau
     * @return la liste d'action a effectuer pour faire avancer le bateau
     */
    private List<Action> oarTheGoodAmountOfSailors(int differenceOfSailors, Boat methodBoat, List<Marin> methodSailors, Transform goal) {
        OarTheGoodAmountOfSailors oarTheGoodAmountOfSailors = new OarTheGoodAmountOfSailors(methodBoat, methodSailors, differenceOfSailors, goal);
        return oarTheGoodAmountOfSailors.oarTheGoodAmountOfSailors();
    }


    /**
     * Cette méthode permet de détecter si il y a du vent et si il est jusdicieux de faire bouger le marin qui est sur
     * le gouvernail vers la voile (et l'activer)
     * @param methodBoat le bateau sur lequel on travaille
     * @param methodGoal l'objectif a atteindre
     * @param methodSailors la liste des marins présent sur le bateau
     * @param methodGameInfo les informations de la partie permettant de récuperer le vent
     * @return true si il est utile de deplacer un marin sur la voile, false sinon
     */
    private boolean detectWind(Boat methodBoat, Transform methodGoal, List<Marin> methodSailors, GameInfo methodGameInfo) {
        WindStrategy windStrategy = new WindStrategy(methodBoat, methodGoal, methodSailors, methodGameInfo);
        return windStrategy.detectWind();
    }




    private Optional<Action> moveRudderGuyOnTheSail(Boat methodBoat, List<Marin> methodSailors, Marin sailorOnRudder) {

        Optional<BoatEntity> sail = HeadquarterUtil.getSail(methodBoat);
        Optional<Action> actionOp = Optional.empty();

        if (sail.isPresent()) {
            BoatPathFinding boatPathFinding = new BoatPathFinding(methodSailors, methodBoat, sailorOnRudder.getId(), sail.get().getPosition());
            Point point = boatPathFinding.generateClosestPoint();

            actionOp = HeadquarterUtil.generateMovingAction(sailorOnRudder.getId(), sailorOnRudder.getX(), sailorOnRudder.getY(), (int) point.getX(), (int) point.getY());
        }

        return actionOp;

    }

    private Optional<Action> moveSailGuyOnTheRudder(Boat methodBoat, List<Marin> methodSailors, Marin sailorOnSail) {

        Optional<BoatEntity> rudder = HeadquarterUtil.getRudder(methodBoat);
        Optional<Action> actionOp = Optional.empty();

        if (rudder.isPresent()) {
            BoatPathFinding boatPathFinding = new BoatPathFinding(methodSailors, methodBoat, sailorOnSail.getId(), rudder.get().getPosition());
            Point point = boatPathFinding.generateClosestPoint();

            actionOp = HeadquarterUtil.generateMovingAction(sailorOnSail.getId(), sailorOnSail.getX(), sailorOnSail.getY(), (int) point.getX(), (int) point.getY());

        }


        return actionOp;

    }







    public List<Marin> getSailors() {
        return sailors;
    }
}
