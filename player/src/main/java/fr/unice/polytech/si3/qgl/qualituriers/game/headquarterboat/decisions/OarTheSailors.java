package fr.unice.polytech.si3.qgl.qualituriers.game.headquarterboat.decisions;

import fr.unice.polytech.si3.qgl.qualituriers.Config;
import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.game.headquarter.headquarterutils.HeadquarterUtil;
import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;

import java.util.ArrayList;
import java.util.List;

/**
 * @author D'Andréa William
 */
public class OarTheSailors {

    private final GameInfo gameInfo;
    private final CheckPoint goal;

    public OarTheSailors(GameInfo gameInfo, CheckPoint goal) {
        this.gameInfo = gameInfo;
        this.goal = goal;
    }

    public List<Action> launch() {

        int differenceOfSailors = getDifferenceOfOarsForGoingToDestination();


        return null;
    }














    /**
     * Cette méthode retourne la différence du nombre de marin qui dovent ramer que nous devons avoir entre abbord et tribord
     *      1) > 0 : On doit avoir differenceOfSailors marins actifs de plus a Tribord qu'a babord
     *      2) < 0 : On doit avoir differenceOfSailors marins actifs de plus a babord qu'a tribord
     *      3) = 0 : On doit avoir le même nombre de marins actifs de chaque côté
     */
    public int getDifferenceOfOarsForGoingToDestination() {

        double angleBetweenTheBoatAndHisDirection = gameInfo.getShip().getPosition().getAngleToSee(goal.getPosition());

        if (angleBetweenTheBoatAndHisDirection >= Config.MAX_ANGLE_TURN_FOR_SAILORS) {
            angleBetweenTheBoatAndHisDirection = Config.MAX_ANGLE_TURN_FOR_SAILORS;
        }

        if (angleBetweenTheBoatAndHisDirection <= -Config.MAX_ANGLE_TURN_FOR_SAILORS) {
            angleBetweenTheBoatAndHisDirection = -Config.MAX_ANGLE_TURN_FOR_SAILORS;
        }

        // differenceOfSailors = rameActiveTribord - rameActiveBabord
        double differenceOfSailorsDouble = angleBetweenTheBoatAndHisDirection * gameInfo.getListOfOars().size() / Math.PI;
        return (int) differenceOfSailorsDouble;
    }



}
