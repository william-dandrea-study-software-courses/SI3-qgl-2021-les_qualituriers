package fr.unice.polytech.si3.qgl.qualituriers.game.headquarterboat;

import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.game.headquarterboat.movesailors.AffectSailorsWithObjectiveToTheirBoatEntities;
import fr.unice.polytech.si3.qgl.qualituriers.game.headquarterboat.movesailors.MoveSailorsOnTheirAffectedBoatEntities;
import fr.unice.polytech.si3.qgl.qualituriers.game.headquarterboat.sailorsmission.GiveMissionToSailors;
import fr.unice.polytech.si3.qgl.qualituriers.game.headquarterboat.sailorsmission.SailorMission;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe a pour objectif de dispatcher les marins. Cette classe va affecter des "missions" aux marins,
 * et ensuite appeler les classes qui vont permettrent aux marins d'accomplir leurs missions
 * @author D'Andréa William
 */
public class NewHeadQuarter {

    private final GameInfo gameInfo;

    public NewHeadQuarter(GameInfo gameInfo) {
        this.gameInfo = gameInfo;
    }

    public List<Action> playTurn() {

        gameInfo.initializeActionsToDoDuringOneTurn();

        List<Action> finalListOfActions = new ArrayList<>();

        GiveMissionToSailors giveMissionToSailors = new GiveMissionToSailors(gameInfo, SailorMission.SAIL_SAILOR);
        giveMissionToSailors.launch();

        AffectSailorsWithObjectiveToTheirBoatEntities affectSailorsWithObjectiveToTheirBoatEntities = new AffectSailorsWithObjectiveToTheirBoatEntities(gameInfo);
        affectSailorsWithObjectiveToTheirBoatEntities.launch();

        MoveSailorsOnTheirAffectedBoatEntities moveSailorsOnTheirAffectedBoatEntities = new MoveSailorsOnTheirAffectedBoatEntities(gameInfo);
        gameInfo.addAllActionsToDoDuringOneTurn(moveSailorsOnTheirAffectedBoatEntities.launch());




        return gameInfo.getActionsToDoDuringOneTurn();
    }










}
