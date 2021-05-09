package fr.unice.polytech.si3.qgl.qualituriers.game.headquarterboat.sailorsmission;

import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntities;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.RudderBoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static fr.unice.polytech.si3.qgl.qualituriers.game.headquarterboat.HeadQuarterControls.*;


/**
 * Cette classe a pour objectif d'affecter à chaque marin une mission
 * @author D'Andréa William
 */
public class GiveMissionToSailors {

    private final GameInfo gameInfo;
    private final SailorMission watchOrSailMission;

    public GiveMissionToSailors(GameInfo gameInfo, SailorMission watchOrSailMission) {
        this.gameInfo = gameInfo;
        this.watchOrSailMission = watchOrSailMission;
    }



    public void launch() {

        // D'abord on remet toutes les missions à 0 afin de repartir proprement
        gameInfo.reinitializeAllSailorsMissions();


        if (gameInfo.getSailors().length <= MAX_SAILORS_ON_BOAT_FOR_USE_JUST_OAR) {
            // Si nous n'avons que 2 marins sur le bateau, nous les affectons aux rames
            affectAllTheSailorsWithAnyMissionOnOar();
        }

        if (gameInfo.getSailors().length == NUMBER_OF_SAILORS_FOR_USE_OAR_AND_RUDDER) {
            // Si nous avons 3 marins, nous en affectons 2 aux rames et un au gouvernail
            affectMissionForOneSailor(BoatEntities.RUDDER, SailorMission.RUDDER_SAILOR, new ArrayList<>());
            affectAllTheSailorsWithAnyMissionOnOar();
        }

        if (gameInfo.getSailors().length >= MIN_SAILORS_FOR_USE_OAR_RUDDER_SAIL_WATCH) {
            // Si nous avons 4 marins ou plus, nous en affectons 1 à la voile/vigie, au gouvernail, les autres aux rames

            affectMissionForOneSailor(BoatEntities.RUDDER, SailorMission.RUDDER_SAILOR, new ArrayList<>());

            if (watchOrSailMission == SailorMission.SAIL_SAILOR) {
                affectMissionForOneSailor(BoatEntities.SAIL, SailorMission.SAIL_SAILOR, new ArrayList<>(){{add(SailorMission.RUDDER_SAILOR);}});
            }

            if (watchOrSailMission == SailorMission.WATCH_SAILOR) {
                affectMissionForOneSailor(BoatEntities.WATCH, SailorMission.WATCH_SAILOR, new ArrayList<>(){{add(SailorMission.RUDDER_SAILOR);}});
            }

            affectAllTheSailorsWithAnyMissionOnOar();
        }
    }


    /**
     * Cette méthode a pour objectif d'affecter à tout les marins n'ayant encore aucune mission la mission OAR.
     * Les missions seront départager pour qu'il y ai autant de BABORDOAR_MISSION que de TRIBORD_OAR_MISSION.
     * Si le nombre de marin a affecter est impair, il y aura toujours un marin de plus a tribord qu'a babord
     *
     * ATTENTION : Il se peut que avec très peu de marins, un marin qui est sur une tribord oar puisse étre affecté a une
     * babord oar, mais ce phénomène est accepté par notre cahier des charges
     */
    public void affectAllTheSailorsWithAnyMissionOnOar() {

        List<Marin> sailorsOnBabordOars = gameInfo.getListOfSailorsOnBabordOars();
        List<Marin> sailorsOnTribordOars = gameInfo.getListOfSailorsOnTribordOars();
        List<Marin> sailorsOnAnyOars = gameInfo.getListOfSailorsOnAnyOar();

        List<Marin> sailorsWithAnyMissions = gameInfo.getSailorsWithAnyMissions();
        List<Marin> babordSailorsOnOarWithAnyMissions = sailorsWithAnyMissions.stream().filter(sailor -> sailor.isOnBabordOar(gameInfo.getShip())).collect(Collectors.toList());
        List<Marin> tribordSailorsOnOarWithAnyMissions = sailorsWithAnyMissions.stream().filter(sailor -> sailor.isOnTribordOar(gameInfo.getShip())).collect(Collectors.toList());

        int goalOfSailorsAtBabord = sailorsWithAnyMissions.size() / 2;
        int goalOfSailorsAtTribord = sailorsWithAnyMissions.size() - goalOfSailorsAtBabord;

        int actualNumberOfSailorsAtBabord = 0;
        int actualNumberOfSailorsAtTribord = 0;






        for (Marin marin : sailorsWithAnyMissions) {


            if (sailorsOnBabordOars.contains(marin)) {
                // le marin est déjà sur une rame de babord

                if (actualNumberOfSailorsAtBabord < goalOfSailorsAtBabord) {
                    // Si on a pas encore le bon nombre de marins a babord
                    marin.setSailorMission(SailorMission.BABORDOAR_SAILOR);
                    actualNumberOfSailorsAtBabord++;

                } else {
                    // Si on a déjà le bon nombre de marin à babord
                    marin.setSailorMission(SailorMission.TRIBORDOAR_SAILOR);
                    actualNumberOfSailorsAtTribord++;
                }
            }

            if (sailorsOnTribordOars.contains(marin)) {
                // le marin est déjà sur une rame de tribord

                if (actualNumberOfSailorsAtTribord < goalOfSailorsAtTribord) {
                    // Si on a pas encore le bon nombre de marin a tribord
                    marin.setSailorMission(SailorMission.TRIBORDOAR_SAILOR);
                    actualNumberOfSailorsAtTribord++;

                } else {
                    marin.setSailorMission(SailorMission.BABORDOAR_SAILOR);
                    actualNumberOfSailorsAtBabord++;
                }
            }

            if (sailorsOnAnyOars.contains(marin)) {

                if (actualNumberOfSailorsAtTribord < goalOfSailorsAtTribord) {
                    // Si on a pas encore le bon nombre de marin a tribord
                    marin.setSailorMission(SailorMission.TRIBORDOAR_SAILOR);
                    actualNumberOfSailorsAtTribord++;
                } else {
                    if (actualNumberOfSailorsAtBabord < goalOfSailorsAtBabord) {
                        // Si on a pas encore le bon nombre de marins a babord
                        marin.setSailorMission(SailorMission.BABORDOAR_SAILOR);
                        actualNumberOfSailorsAtBabord++;
                    }
                }
            }

        }
    }



    /**
     * Méthode générique permettant d'affecter une mission a un marin en regardant d'abord si le marin est sur la entityWeWantToAffectSailor
     * => si un marin est déjà sur la entityWeWantToAffectSailor, on affecte a ce marin la sailorMission (meme si ce sailor a deja une autre mission)
     * => si aucun marin n'y est, on cherche le marin le plus proche qui n'a pas les missions inscrite dans sailorsMissionsToAvoid et affecte au marin le plus proche de entityWeWantToAffectSailor la sailorMission
     * @param entityWeWantToAffectSailor l'endroit ou l'on souhaite avoir un sailor (en gros, la destination)
     * @param sailorMission la mission que l'on souhaite affecter au marin qui est sur entityWeWantToAffectSailor ou au plus proche
     * @param sailorsMissionsToAvoid la liste des missions qu'on deja des sailors et que l'on souhaite éviter
     */
    public void affectMissionForOneSailor(BoatEntities entityWeWantToAffectSailor, SailorMission sailorMission, List<SailorMission> sailorsMissionsToAvoid) {

        Optional<BoatEntity> entityOptional = Arrays.stream(gameInfo.getShip().getEntities()).filter(boatEntity -> boatEntity.getType() == entityWeWantToAffectSailor).findAny();


        if (entityOptional.isPresent()) {
            BoatEntity entity = entityOptional.get();

            // D'abord on regarde si on a un marin sur le gouvernail
            if (Arrays.stream(gameInfo.getSailors()).anyMatch(marin -> marin.isOn(entityWeWantToAffectSailor, gameInfo.getShip()))) {
                // S'il y déjà un marin sur le gouvernail, c'est celui-ci qui a la mission gouvernail

                for (Marin marin: gameInfo.getSailors()) {
                    if (marin.isOn(entityWeWantToAffectSailor, gameInfo.getShip())) {
                        marin.setSailorMission(sailorMission);
                        break;
                    }
                }

            } else {
                // Sinon, on prend le marin le plus proche et on lui affecte la mission gouvernail
                Optional<Marin> closerSailorOp = takeTheCloserSailorFromDestination(entity, sailorsMissionsToAvoid);
                closerSailorOp.ifPresent(closerSailor -> closerSailor.setSailorMission(sailorMission));
            }
        }
    }


    /**
     * Cette méthode récupère le marin le plus proche d'une boatEntity de destination
     * @param destination l'endroit ou nous voulons que le marin vienne
     * @param sailorsMissionsToAvoid permet de ne pas prendre en compte les marins qui ont déjà la mission indiqué dans cette présente liste
     */
    private Optional<Marin> takeTheCloserSailorFromDestination(BoatEntity destination, List<SailorMission> sailorsMissionsToAvoid) {

        double distanceMinimale = Double.POSITIVE_INFINITY;
        Marin closerSailor = null;
        Optional<Marin> finalMarin = Optional.empty();


        for (Marin marin : gameInfo.getSailors()) {

            if (marin.getPosition().distance(destination.getPosition()) < distanceMinimale && !sailorsMissionsToAvoid.contains(marin.getSailorMission())) {
                distanceMinimale = marin.getPosition().distance(destination.getPosition());
                closerSailor = marin;
                finalMarin = Optional.of(marin);
            }
        }

        return finalMarin;
    }
}
