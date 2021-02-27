package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter;


import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.headquarterutils.HeadquarterUtil;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.strategy.InitSailorsPlace;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;

import java.util.List;

/**
 * Cette classe a pour objectif de controller tout ce qu'il se passe sur le bateau.
 * Cette méthode va affecter par exemple n marins pour ramer et n marins pour tirer des canons
 *
 * IMPORTANT : après un appel a cette méthode, il faut actualiser les marins dans le gameInfo grace a la methode getMarins de
 * HeadQuarter et SetMarin de GameInfo.
 */
public class HeadQuarter {

    private Boat boat;
    private List<Marin> sailors;
    private final Transform goal;

    public HeadQuarter(Boat boat, List<Marin> sailors, Transform goal) {
        this.boat = boat;
        this.sailors = sailors;
        this.goal = goal;
    }

    public List<Action> playTurn() {

        // Si des marins ne sont pas bien placé sur le bateau



        return null;
    }




    private List<Action> initSailorsPlace(Boat methodBoat, List<Marin> methodSailors) {
        InitSailorsPlace initSailorsPlace = new InitSailorsPlace(methodBoat, methodSailors);
        initSailorsPlace.initSailorsPlace();

        return null;
    }

    private int recoverOarsDifferenceBetweenPortsideAndStarboard(Transform initialPoint, Transform finalPoint) {
        return 0;
    }

    private List<Action> moveSailorsForMovingAction() {

        return null;
    }

    private List<Action> oarTheGoodAmountOfSailors() {
        return null;
    }





}
