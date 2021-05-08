package fr.unice.polytech.si3.qgl.qualituriers.game.headquarterboat.sailorsmission;

import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntities;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.RudderBoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static fr.unice.polytech.si3.qgl.qualituriers.game.headquarterboat.HeadQuarterControls.*;
import static java.lang.Math.min;

/**
 * Cette classe a pour objectif d'affecter à chaque marin une mission
 * @author D'Andréa William
 */
public class GiveMissionToSailors {

    private final GameInfo gameInfo;

    public GiveMissionToSailors(GameInfo gameInfo) {
        this.gameInfo = gameInfo;
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
        }

        if (gameInfo.getSailors().length >= MIN_SAILORS_FOR_USE_OAR_RUDDER_SAIL_WATCH) {
            // Si nous avons 4 marins ou plus, nous en affectons 1 à la voile/vigie, au gouvernail, les autres aux rames



        }
    }









    public void affectAllTheSailorsWithAnyMissionOnOar() {

        List<BoatEntity> babordOars = gameInfo.getListOfBabordOars();
        List<BoatEntity> tribordOars = gameInfo.getListOfTribordOars();

        List<Marin> sailorsOnBabordOars = gameInfo.getListOfSailorsOnBabordOars();
        List<Marin> sailorsOnTribordOars = gameInfo.getListOfSailorsOnBabordOars();
        List<Marin> sailorsOnAnyOars = gameInfo.getListOfSailorsOnAnyOar();

        int goalOfSailorsAtBabord = gameInfo.getSailors().length / 2;
        int goalOfSailorsAtTribord = gameInfo.getSailors().length - goalOfSailorsAtBabord;

        int actualNumberOfSailorsAtBabord = 0;
        int actualNumberOfSailorsAtTribord = 0;

        System.out.println(gameInfo.getSailors().length);
        System.out.println(goalOfSailorsAtBabord);
        System.out.println(goalOfSailorsAtTribord);


        for (Marin marin : gameInfo.getSailors()) {
            if (marin.getSailorMission() == null) {

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


    }



    /**
     * Méthode générique permettant d'affecter une mission a un marin en regardant d'abord si le marin est sur la entityWeWantToAffectSailor
     * => si un marin est déjà sur la entityWeWantToAffectSailor, on affecte a ce marin la sailorMission (meme si ce sailor a deja une autre mission)
     * => si aucun marin n'y est, on cherche le marin le plus proche qui n'a pas les missions inscrite dans sailorsMissionsToAvoid et affecte au marin le plus proche de entityWeWantToAffectSailor la sailorMission
     * @param entityWeWantToAffectSailor l'endroit ou l'on souhaite avoir un sailor (en gros, la destination)
     * @param sailorMission la mission que l'on souhaite affecter au marin qui est sur entityWeWantToAffectSailor ou au plus proche
     * @param sailorsMissionsToAvoid la liste des missions qu'on deja des sailors et que l'on souhaite éviter
     */
    void affectMissionForOneSailor(BoatEntities entityWeWantToAffectSailor, SailorMission sailorMission, List<SailorMission> sailorsMissionsToAvoid) {

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
