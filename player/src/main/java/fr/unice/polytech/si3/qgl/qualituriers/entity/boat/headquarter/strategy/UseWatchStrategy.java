package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.strategy;

import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.WatchBoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.headquarterutils.BoatPathFinding;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.headquarterutils.HeadquarterUtil;
import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.UseWatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author D'Andréa William
 */
public class UseWatchStrategy {

    private final GameInfo gameInfo;
    private final List<Marin> sailors;
    private final Boat boat;
    private final List<Integer> idSailorsWeUsesMoving;
    private final Marin marinWeChoose;


    public UseWatchStrategy(GameInfo gameInfo, List<Marin> sailors, Boat boat, List<Integer> idSailorsWeUsesMoving, Marin marinWeChoose) {
        this.gameInfo = gameInfo;
        this.sailors = sailors;
        this.boat = boat;
        this.idSailorsWeUsesMoving = idSailorsWeUsesMoving;
        this.marinWeChoose = marinWeChoose;
    }

    public List<Action> useWatchStrategy() {

        List<Action> finalListOfAction = new ArrayList<>();

        if (isSmartToUseUseWatch()) {


            // HeadquarterUtil.searchTheClosestSailorToAPoint();
            Optional<BoatEntity> watchOp = HeadquarterUtil.getWatch(boat);

            if (watchOp.isPresent()) {
                BoatEntity watch = watchOp.get();

                Marin marin = marinWeChoose;

                BoatPathFinding boatPathFinding = new BoatPathFinding(sailors, boat, marin.getId(), watch.getPosition());
                Point objectifPoint = boatPathFinding.generateClosestPoint();

                Optional<Action> actionOp = HeadquarterUtil.generateMovingAction(marin.getId(), marin.getX(), marin.getY(), (int) objectifPoint.getX(), (int) objectifPoint.getY());

                if (actionOp.isPresent()) {
                    marin.setPosition((int) objectifPoint.getX(), (int) objectifPoint.getY());
                    if (marin.getPosition().equals(objectifPoint)) {
                        Action useWatchAction = new UseWatch(marin.getId());
                        finalListOfAction.add(useWatchAction);
                        System.out.println(finalListOfAction);
                    }
                }
            }
        }


        return finalListOfAction;
    }



    public boolean isSmartToUseUseWatch() {



        Optional<BoatEntity> boatEntityOptional = HeadquarterUtil.getWatch(boat);
        if (boatEntityOptional.isEmpty()) return false;

        boolean useWatch = (gameInfo.getNumberOfTurn() == 0);


        if (useWatch) return true;

        double moduloDistance = (gameInfo.getTraveledDistance() % 5000);


        if (moduloDistance <= 250) return true;


        return false;
    }
}
