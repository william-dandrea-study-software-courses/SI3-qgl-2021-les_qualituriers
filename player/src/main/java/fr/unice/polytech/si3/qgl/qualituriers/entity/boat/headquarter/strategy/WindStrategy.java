package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.strategy;

import fr.unice.polytech.si3.qgl.qualituriers.Config;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.HeadQuarterConfig;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.headquarterutils.HeadquarterUtil;
import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;

import java.util.List;

public class WindStrategy {

    private Boat boat;
    private Transform goal;
    private List<Marin> sailors;
    private GameInfo gameInfo;

    public WindStrategy(Boat boat, Transform goal, List<Marin> sailors, GameInfo gameInfo) {
        this.boat = boat;
        this.goal = goal;
        this.sailors = sailors;
        this.gameInfo = gameInfo;
    }

    public boolean detectWind() {

        double angleBetweenBoatAndGoal = Math.abs(goal.getAngleToSee(boat.getPosition()));
        double minimumTolerableAngle = HeadquarterUtil.getMinimumAngleOfRotation(boat);

        boolean isSailorOnRudder = HeadquarterUtil.getSailorOnRudder(boat, sailors).isPresent();

        double distanceBetweenBoatAndGoal = HeadquarterUtil.distanceBetweenTwoPoints(goal.getPoint(), boat.getPosition());
        double minimumTolerableDistance = HeadQuarterConfig.coefficientForMovingSailorToSail * HeadquarterUtil.getBoatMovingDistanceMaxInOneTurn(boat);


        var stregth =  gameInfo.getWind().getStrength();
        var orientation = boat.getPosition().getOrientation();
        var windOr = gameInfo.getWind().getOrientation();
        double speedWithWind = Config.linearSpeedWind(1,1, stregth, orientation, windOr);

        if (angleBetweenBoatAndGoal <= minimumTolerableAngle
                && isSailorOnRudder
                && distanceBetweenBoatAndGoal >= minimumTolerableDistance
                && speedWithWind > 0
        ) {
            return true;
        }

        return false;
    }
}
