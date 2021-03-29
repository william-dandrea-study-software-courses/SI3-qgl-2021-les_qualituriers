package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter;


import fr.unice.polytech.si3.qgl.qualituriers.Config;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.SailBoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.headquarterutils.BoatPathFinding;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.headquarterutils.HeadquarterUtil;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.strategy.*;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.Wind;
import fr.unice.polytech.si3.qgl.qualituriers.exceptions.MovingSailorException;
import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.LiftSail;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.LowerSail;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Moving;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private List<Integer> idSailorsWeUsesMoving;

    public HeadQuarter(Boat boat, List<Marin> sailors, Transform goal, GameInfo gameInfo) {
        this.boat = boat;
        this.sailors = sailors;
        this.goal = goal;
        this.gameInfo = gameInfo;
        this.idSailorsWeUsesMoving = new ArrayList<>();

        if (gameInfo.getWind() == null) { gameInfo.setWind(new Wind(0,0)); }
    }

    public List<Action> playTurn() {

        // Nous allons d'abord dispatcher les marins au bon endroit
        List<Action> finalListOfActions = new ArrayList<>(dispatchAllTheSailors(boat, sailors));

        // Maintenant que nous avons initié les rames, nous allons essayer de faire bouger le bateau du bon angle
        int differenceOfOarsBetweenTribordAndBabord = recoverOarsDifferenceBetweenPortsideAndStarboardForGoingSomewhere(boat, goal); // tribord - babord
        finalListOfActions.addAll(oarTheGoodAmountOfSailors(differenceOfOarsBetweenTribordAndBabord, boat, sailors, goal));

        // Maintenant on va faire fonctioner le rudder
        double angleRudder = generateAngleRudder(boat, goal);
        Optional<Marin> sailorOnRudderOp = HeadquarterUtil.getSailorOnRudder(boat,sailors);

        if (sailorOnRudderOp.isPresent()) {
            Optional<Action> actionOptional = HeadquarterUtil.generateRudder(sailorOnRudderOp.get().getId(), angleRudder);
            actionOptional.ifPresent(finalListOfActions::add);
        }

        // Maintenant on sélectionne un marin que nous allons déplacer sur la voile si cela est nécessaire
        if (HeadquarterUtil.getSail(boat).isPresent()) {
            finalListOfActions.addAll(setupWind(sailorOnRudderOp, HeadquarterUtil.getSail(boat).get() ));
        }

        return finalListOfActions;
    }


    public List<Action> setupWind(Optional<Marin> sailorForRudder, BoatEntity sail) {

        WindStrategy windStrategy = new WindStrategy(boat, sailors, gameInfo, sailorForRudder, sail, idSailorsWeUsesMoving);
        List<Action> actions = windStrategy.setupWind();
        idSailorsWeUsesMoving = windStrategy.getIdSailorsWeUsesMoving();
        return actions;
    }


    /**
     * Cette méthode a pour objectif de dispatcher les marins un peu partout sur le bateau, de maniere plus ou moins
     * intteligente
     * @param methodBoat le bateau sur lequel on travail
     * @param methodSailors la liste des marins a faire bouger
     * @return la liste d'action a faire pour bien dispatcher les marins
     */
    private List<Action> dispatchAllTheSailors(Boat methodBoat, List<Marin> methodSailors) {

        List<Action> finalsActions = new ArrayList<>();
        List<Integer> sailorsWeUsed = new ArrayList<>();

        // Nous cherchons à faire bouger au moins un marin sur le gouvernail => si on a un gouvernail et qu'il n'y a encore aucun marins sur le gouvernail, on en bouge un
        Optional<BoatEntity> rudderOptional =  HeadquarterUtil.getRudder(methodBoat);
        if (rudderOptional.isPresent() && HeadquarterUtil.getSailorOnRudder(methodBoat, methodSailors).isEmpty()) {
            Marin closestSailor = HeadquarterUtil.searchTheClosestSailorToAPoint(methodSailors, rudderOptional.get().getPosition(), new ArrayList<>());
            finalsActions.addAll(initRudderSailorPlace(methodBoat, closestSailor));
            sailorsWeUsed.add(closestSailor.getId());

        }

        // Nous ajoutons maintenant les sailors sur les oars
        List<Marin> sailorsForOar = sailors.stream().filter(marin -> !sailorsWeUsed.contains(marin.getId())).collect(Collectors.toList());
        finalsActions.addAll(initOarSailorsPlace(methodBoat, sailorsForOar));
        updateUsesSailors(finalsActions);

        return finalsActions;
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


    /**
     * Cette méthode a pour objectif d'initialiser la place du rudder, c'est a dire de le faire se déplacer jusqu'à ce
     * sur le gouvernail ou le plus proche du gouvernail
     * @param methodBoat le boat sur lequel est le marin
     * @param sailorForRudder le marin que l'on souhaite déplacer
     * @return une liste d'action pour déplacer le marin sur la rame
     */
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
     * Cette méthode met a jour une instance de classse qui regroupe tout les marins qui ont déjà été bougé
     * @param actions les actions que l'on souhaite ajouter à la liste des marins que l'on a deja bougé
     */
    private void updateUsesSailors(List<Action> actions) {

        List<Moving> movingActions = actions.stream().filter(action -> action instanceof Moving).map (action -> (Moving) action).collect(Collectors.toList());

        for (Moving moving : movingActions) {

            if (idSailorsWeUsesMoving.contains(moving.getSailorId()))
                throw new MovingSailorException(moving.getSailorId());

            idSailorsWeUsesMoving.add(moving.getSailorId());


        }
    }



    public List<Marin> getSailors() {
        return sailors;
    }
}
