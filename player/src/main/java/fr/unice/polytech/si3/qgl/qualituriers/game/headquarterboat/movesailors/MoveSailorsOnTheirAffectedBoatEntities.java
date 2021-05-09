package fr.unice.polytech.si3.qgl.qualituriers.game.headquarterboat.movesailors;

import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.game.headquarterboat.sailorsmission.SailorMission;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Moving;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Classe ayant pour objectif de faire bouger les marins a leurs affectedBoatEntities
 *
 * Ordre de priorité :
 *  => 1 : WATCH_SAILOR
 *  => 2 : SAIL_SAILOR
 *  => 3 : RUDDER_SAILOR
 *  => 4 : OAR_SAILOR
 * @author D'Andréa William
 */

public class MoveSailorsOnTheirAffectedBoatEntities {

    private final GameInfo gameInfo;

    public MoveSailorsOnTheirAffectedBoatEntities(GameInfo gameInfo) {
        this.gameInfo = gameInfo;
    }


    public List<Action> launch() {

        List<Action> finalListOfActions = new ArrayList<>();

        finalListOfActions.addAll(moveSailorsForOneMission(SailorMission.WATCH_SAILOR));
        finalListOfActions.addAll(moveSailorsForOneMission(SailorMission.SAIL_SAILOR));
        finalListOfActions.addAll(moveSailorsForOneMission(SailorMission.RUDDER_SAILOR));
        finalListOfActions.addAll(moveSailorsForOneMission(SailorMission.BABORDOAR_SAILOR));
        finalListOfActions.addAll(moveSailorsForOneMission(SailorMission.TRIBORDOAR_SAILOR));

        return finalListOfActions;
    }


    /**
     * Méthode ayant pour objectif d'aller déplacer le marin sur sa place de destination, ou,si le marin ne peut pas
     * aller directement sur l'emplacement de destination, celui ci est déplacé au plus proche
     * @param sailorMission la mission des marins que l'on souhaite faire bouger
     * @return la lste d'actions a effectuer pour faire bouger les marins aux bons endroits
     */
    List<Action> moveSailorsForOneMission(SailorMission sailorMission) {

        List<Marin> listOfSailorsWithTheMission = Arrays.stream(gameInfo.getSailors()).filter(sailor -> sailor.getSailorMission() == sailorMission).collect(Collectors.toList());
        List<Action> finalListOfActions = new ArrayList<>();

        for (BoatEntity destinationEntity : gameInfo.getShip().getEntities()) {

            // Si un marin de la mission est affecté a cette entity
            if (listOfSailorsWithTheMission.contains(destinationEntity.getSailorAffected())) {

                Marin affectedSailor = destinationEntity.getSailorAffected();

                // On vérifie que le marin n'a pas encore fait d'actions Moving
                if (gameInfo.getActionsToDoDuringOneTurn().stream().filter(action -> action instanceof Moving).noneMatch(action -> ((Moving) action).getSailorId() == affectedSailor.getId()  )) {

                    // Si le marin est deja sur la destination, pas de traitement a faire
                    if (!affectedSailor.getPosition().equals(destinationEntity.getPosition())) {

                        // On déplace le marin a sa destination, ou si on ne peut pas, on le déplace au plus proche
                        PathFindingBoat pathFindingBoat = new PathFindingBoat(gameInfo, affectedSailor, destinationEntity.getPosition());
                        Point realDestination = pathFindingBoat.generateClosestPoint();

                        // Même si en général on peut toujours, on vérifie quand même que l'on peut déplacer le marin
                        Action movingAction = affectedSailor.generateMovingAction(realDestination, gameInfo.getShip());
                        finalListOfActions.add(movingAction);
                    }
                }
            }
        }

        return finalListOfActions;
    }






}
