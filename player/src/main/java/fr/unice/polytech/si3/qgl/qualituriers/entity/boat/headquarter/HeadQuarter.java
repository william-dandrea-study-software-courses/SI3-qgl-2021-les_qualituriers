package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter;


import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.headquarterutils.HeadquarterUtil;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.strategy.DifferenceOfOarsForGoingSomewhere;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.strategy.InitSailorsPlaceOnOars;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.strategy.InitSailorsPlaceOnRudder;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.strategy.OarTheGoodAmountOfSailors;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;

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

    public HeadQuarter(Boat boat, List<Marin> sailors, Transform goal) {
        this.boat = boat;
        this.sailors = sailors;
        this.goal = goal;
    }

    public List<Action> playTurn() {

        List<Action> finalListOfActions = new ArrayList<>();

        // En premier lieu, nous allons mettre tout les marins a leur bonne place
        List<Marin> sailorsForOars = sailors.subList(1, sailors.size());
        Marin sailorForRudder = sailors.get(0);

        if (HeadquarterUtil.getListOfSailorsOnOars(sailors, boat).size() != sailorsForOars.size() || HeadquarterUtil.getRudder(boat).isEmpty()) {
            finalListOfActions.addAll(initSailorsPlace(boat, sailorsForOars, sailorForRudder));
        }

        // Maintenant que nous avons initié les rames, nous allons essayer de faire bouger le bateau du bon angle
        int differenceOfOarsBetweenTribordAndBabord = recoverOarsDifferenceBetweenPortsideAndStarboardForGoingSomewhere(boat, goal); // tribord - babord
        finalListOfActions.addAll(oarTheGoodAmountOfSailors(differenceOfOarsBetweenTribordAndBabord, boat, sailors));

        // Maintenant on va faire fonctioner le rudder
        double angleRudder = generateAngleRudder(boat, goal);
        Optional<Marin> sailorOnRudderOp = HeadquarterUtil.getSailorOnRudder(boat,sailors);


        if (sailorOnRudderOp.isPresent()) {
            Optional<Action> actionOptional = HeadquarterUtil.generateRudder(sailorOnRudderOp.get().getId(), angleRudder);
            actionOptional.ifPresent(finalListOfActions::add);
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
     * @param sailorForRudder le marin qui ira sur le rudder
     * @return la liste d'action a effectuer pour bien positionner les marins
     */
    private List<Action> initSailorsPlace(Boat methodBoat, List<Marin> sailorsForOar, Marin sailorForRudder) {


        List<Action> actionList = new ArrayList<>();
        // Le get(0) st provisoire pour l'instant, une stratégie sera faite pour trouver le marin optimal

        InitSailorsPlaceOnRudder initSailorsPlaceOnRudder = new InitSailorsPlaceOnRudder(methodBoat, sailorForRudder.getId(), sailors);
        actionList.addAll(initSailorsPlaceOnRudder.initSailorsPlaceOnRudder());

        // Une fois initSailorsPlaceOnRudder fait, on retirera dans le initSailorsPlaceOnOars la marin qui a été affecté au rudder
        InitSailorsPlaceOnOars initSailorsPlaceOnOars = new InitSailorsPlaceOnOars(methodBoat, sailorsForOar);
        actionList.addAll(initSailorsPlaceOnOars.initSailorsPlace());

        return actionList;
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
    private List<Action> oarTheGoodAmountOfSailors(int differenceOfSailors, Boat methodBoat, List<Marin> methodSailors) {
        OarTheGoodAmountOfSailors oarTheGoodAmountOfSailors = new OarTheGoodAmountOfSailors(methodBoat, methodSailors, differenceOfSailors);
        return oarTheGoodAmountOfSailors.oarTheGoodAmountOfSailors();
    }





    public List<Marin> getSailors() {
        return sailors;
    }
}
