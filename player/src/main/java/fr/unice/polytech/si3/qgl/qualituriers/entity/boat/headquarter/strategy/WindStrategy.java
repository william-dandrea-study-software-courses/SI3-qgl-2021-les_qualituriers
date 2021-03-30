package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.strategy;

import fr.unice.polytech.si3.qgl.qualituriers.Config;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.SailBoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.headquarterutils.BoatPathFinding;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.headquarterutils.HeadquarterUtil;
import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.LiftSail;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.LowerSail;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class WindStrategy {

    private Boat boat;
    private List<Marin> sailors;
    private GameInfo gameInfo;
    private Optional<Marin> sailorForRudder;
    private BoatEntity sail;
    private List<Integer> idSailorsWeUsesMoving;

    public WindStrategy(Boat boat,  List<Marin> sailors, GameInfo gameInfo, Optional<Marin> sailorForRudder, BoatEntity sail, List<Integer> idSailorsWeUsesMoving) {
        this.boat = boat;

        this.sailors = sailors;
        this.gameInfo = gameInfo;
        this.sailorForRudder = sailorForRudder;
        this.sail = sail;
        this.idSailorsWeUsesMoving = idSailorsWeUsesMoving;
    }

    public List<Action> setupWind() {

        List<Action> finalListOfActions = new ArrayList<>();
        // D'abord, on regarde si le vent nous pousse
        double speedWind = Config.linearSpeedWind(1, HeadquarterUtil.getListOfOars(boat).size(), gameInfo.getWind().getStrength(), boat.getPosition().getOrientation(), gameInfo.getWind().getOrientation());

        SailBoatEntity realSail = (SailBoatEntity) sail;


        Optional<Marin> sailorOnSail = HeadquarterUtil.getSailorOnSail(boat, sailors);
        if (sailorOnSail.isPresent()) {

            openOrCloseTheSail(finalListOfActions, speedWind, realSail, sailorOnSail);


        } else {

            // Marin potentiels : les marins qui n'ont pas encore bougé et le marin qui n'est pas sur le gouvernail
            List<Marin> potentialSailors = sailorForRudder.map(value -> sailors.stream().filter(marin -> marin.getId() != value.getId() && !idSailorsWeUsesMoving.contains(marin.getId())).collect(Collectors.toList())).orElseGet(() -> sailors.stream().filter(marin -> !idSailorsWeUsesMoving.contains(marin.getId())).collect(Collectors.toList()));

            if (potentialSailors.isEmpty()) return new ArrayList<>();

            Marin closestSailor = HeadquarterUtil.searchTheClosestSailorToAPoint(potentialSailors, sail.getPosition(), new ArrayList<>());
            BoatPathFinding boatPathFinding = new BoatPathFinding(sailors, boat, closestSailor.getId(), sail.getPosition());
            Point pointFinal = boatPathFinding.generateClosestPoint();

            Optional<Action> movingAction = HeadquarterUtil.generateMovingAction(closestSailor.getId(), closestSailor.getX(), closestSailor.getY(), (int) pointFinal.getX(), (int) pointFinal.getY());
            movingAction.ifPresent(finalListOfActions::add);

            sailorOnSail = HeadquarterUtil.getSailorOnSail(boat, sailors);
            if (sailorOnSail.isPresent()) {

                openOrCloseTheSail(finalListOfActions, speedWind, realSail, sailorOnSail);
            }

        }

        return finalListOfActions;
    }

    private void openOrCloseTheSail(List<Action> finalListOfActions, double speedWind, SailBoatEntity realSail, Optional<Marin> sailorOnSail) {
        if (speedWind > 0 && !realSail.isOpened()) {
            // Si c'est judicieux d'avoir la voile ouverte
            finalListOfActions.add(new LiftSail(sailorOnSail.get().getId()));
            realSail.setOpened(true);
        } else {

            if (realSail.isOpened() && speedWind <= 0) {
                // Si ce n'est pas judicieux d'avoir la voile ouverte
                finalListOfActions.add(new LowerSail(sailorOnSail.get().getId()));
                realSail.setOpened(false);
            }
        }
    }

    public List<Integer> getIdSailorsWeUsesMoving() {
        return idSailorsWeUsesMoving;
    }
}
