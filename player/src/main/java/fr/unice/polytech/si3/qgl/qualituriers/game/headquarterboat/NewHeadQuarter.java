package fr.unice.polytech.si3.qgl.qualituriers.game.headquarterboat;

import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntities;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.SailBoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.game.headquarterboat.decisions.OarTheSailorsAndTurnRudder;
import fr.unice.polytech.si3.qgl.qualituriers.game.headquarterboat.movesailors.AffectSailorsWithObjectiveToTheirBoatEntities;
import fr.unice.polytech.si3.qgl.qualituriers.game.headquarterboat.movesailors.MoveSailorsOnTheirAffectedBoatEntities;
import fr.unice.polytech.si3.qgl.qualituriers.game.headquarterboat.sailorsmission.GiveMissionToSailors;
import fr.unice.polytech.si3.qgl.qualituriers.game.headquarterboat.sailorsmission.SailorMission;
import fr.unice.polytech.si3.qgl.qualituriers.utils.AngleUtil;
import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.LiftSail;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.LowerSail;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.UseWatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Cette classe a pour objectif de dispatcher les marins. Cette classe va affecter des "missions" aux marins,
 * et ensuite appeler les classes qui vont permettrent aux marins d'accomplir leurs missions
 * @author D'Andréa William
 */
public class NewHeadQuarter {

    private static final int ACTIVATE_WATCH_FOR_HOW_MANY_TOUR = 30;
    private static final int DISTANCE_WITH_CHECKPOINT_FOR_DONT_USE_SAIL = 200;

    private final GameInfo gameInfo;
    private CheckPoint goal;

    private boolean tryingToGoToWatch;
    private boolean useWatch;
    private int numberOfTurn;

    public NewHeadQuarter(GameInfo gameInfo) {
        this.gameInfo = gameInfo;

        this.tryingToGoToWatch = false;
        this.useWatch = false;

        this.numberOfTurn = 0;
    }

    public List<Action> playTurn(CheckPoint goal) {
        this.goal = goal;


        gameInfo.initializeActionsToDoDuringOneTurn();
        gameInfo.reinitializeAllSailorsMissions();
        gameInfo.reinitializeAffectedSailorsInBoatEntities();

        gameInfo.addPointsWhereTheBoatMoved(gameInfo.getShip().getPosition());

        List<Action> actions = new ArrayList<>();

        GiveMissionToSailors giveMissionToSailors = new GiveMissionToSailors(gameInfo, switchBetweenWatchAndSail());
        giveMissionToSailors.launch();

        AffectSailorsWithObjectiveToTheirBoatEntities affectSailorsWithObjectiveToTheirBoatEntities = new AffectSailorsWithObjectiveToTheirBoatEntities(gameInfo);
        affectSailorsWithObjectiveToTheirBoatEntities.launch();

        MoveSailorsOnTheirAffectedBoatEntities moveSailorsOnTheirAffectedBoatEntities = new MoveSailorsOnTheirAffectedBoatEntities(gameInfo);
        actions.addAll(moveSailorsOnTheirAffectedBoatEntities.launch());


        Optional<Action> actionWatch = activateWatch();actionWatch.ifPresent(actions::add);
        Optional<Action> actionSail = activateSail(); actionSail.ifPresent(actions::add);

        OarTheSailorsAndTurnRudder oarTheSailorsAndTurnRudder = new OarTheSailorsAndTurnRudder(gameInfo, goal);
        actions.addAll(oarTheSailorsAndTurnRudder.launch());

        System.out.println("===============");
        System.out.println(actions);
        System.out.println("===============");

        System.out.println("===============");
        System.out.println(Arrays.toString(gameInfo.getSailors()));
        System.out.println("===============");

        System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>");
        System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>");

        System.out.println(gameInfo.getNumberOfTurn());


        this.numberOfTurn++;
        return actions;
    }




    SailorMission switchBetweenWatchAndSail() {

        if (numberOfTurn % ACTIVATE_WATCH_FOR_HOW_MANY_TOUR == 0) {
            this.tryingToGoToWatch = true;
            this.useWatch = false;
        }

        // Si un marin est en route mais qu'il n'a pas encore utilisé la watch, on continu la recherche
        if (this.tryingToGoToWatch && !this.useWatch) {
            return SailorMission.WATCH_SAILOR;
        }

        return SailorMission.SAIL_SAILOR;
    }



    Optional<Action> activateWatch() {

        // On cherche la Watch
        Optional<BoatEntity> watchOp = Arrays.stream(gameInfo.getShip().getEntities()).filter(boatEntity -> boatEntity.getType() == BoatEntities.WATCH).findFirst();

        // Si la Watch est présente && que nous sommes entrain d'aller sur la watch && que nous avons pas encore activé la watch
        if (watchOp.isPresent() && tryingToGoToWatch && !useWatch) {

            // Si le marin affecté à la mission WATCH_SAILOR est sur la watch, on l'active
            Optional<Marin> watchSailorOp = Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.WATCH_SAILOR).findFirst();

            if (watchSailorOp.isPresent()) {
                if (watchSailorOp.get().getPosition().equals(watchOp.get().getPosition())) {

                    this.tryingToGoToWatch = false;
                    this.useWatch = true;
                    return Optional.of(new UseWatch(watchSailorOp.get().getId()));
                }
            }
        }

        return Optional.empty();
    }



    Optional<Action> activateSail() {

        Optional<BoatEntity> sailOp = Arrays.stream(gameInfo.getShip().getEntities()).filter(boatEntity -> boatEntity.getType() == BoatEntities.SAIL).findFirst();
        Optional<Marin> sailSailorOp = Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.SAIL_SAILOR).findFirst();


        if (!tryingToGoToWatch) {

            // Si nous sommes juste avant le moment ou nous devons bouger le marin sur la watch, nous désactivons la sail
            if (numberOfTurn % ACTIVATE_WATCH_FOR_HOW_MANY_TOUR == ACTIVATE_WATCH_FOR_HOW_MANY_TOUR - 1) {

                return desactivateSail();
            }



            // Si le vent nous pousse, nous activons la voile
            if (gameInfo.getWind() != null && gameInfo.getWind().getStrength() != 0.0) {


                if (Math.abs(AngleUtil.differenceBetweenTwoAngle(gameInfo.getShip().getPosition().getOrientation(), gameInfo.getWind().getOrientation())) < Math.PI / 2) {

                    // Activation de la voile

                    // On regarde que l'on ne soit pas trop proche du ckeckpoint => si trop proche, on désactive la voile : DISTANCE_WITH_CHECKPOINT_FOR_DONT_USE_SAIL
                    if (gameInfo.getShip().getPosition().distance(goal.getPosition()) > DISTANCE_WITH_CHECKPOINT_FOR_DONT_USE_SAIL) {
                        // On peut activer la voile

                        if (sailOp.isPresent() && sailSailorOp.isPresent()) {
                            SailBoatEntity sail = (SailBoatEntity) sailOp.get();
                            Marin marin = sailSailorOp.get();

                            if (marin.getPosition().equals(sail.getPosition()) && !sail.isOpened()) {

                                sail.setOpened(true);
                                return Optional.of(new LiftSail(marin.getId()));
                            }
                        }
                    } else {
                        return desactivateSail();
                    }
                }
            } else {
                return desactivateSail();
            }
        }
        return desactivateSail();
    }



    Optional<Action> desactivateSail() {

        Optional<BoatEntity> sailOp = Arrays.stream(gameInfo.getShip().getEntities()).filter(boatEntity -> boatEntity.getType() == BoatEntities.SAIL).findFirst();
        Optional<Marin> sailSailorOp = Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.SAIL_SAILOR).findFirst();

        // Desactivation de la voile
        if (sailOp.isPresent() && sailSailorOp.isPresent()) {
            SailBoatEntity sail = (SailBoatEntity) sailOp.get();
            Marin marin = sailSailorOp.get();

            if (marin.getPosition().equals(sail.getPosition()) && sail.isOpened()) {

                sail.setOpened(false);
                return Optional.of(new LowerSail(marin.getId()));
            }
        }
        return Optional.empty();

    }

}
