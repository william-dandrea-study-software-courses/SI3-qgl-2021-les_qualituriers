package fr.unice.polytech.si3.qgl.qualituriers.game.headquarterboat.movesailors;

import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntities;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.game.headquarterboat.decisions.SailorMission;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Cette classe a pour objectif de déplacer les marins a l'endroit de leur mission.
 * Elle déplace par ordre le priorité :
 *  => 1 : WATCH_SAILOR
 *  => 2 : SAIL_SAILOR
 *  => 3 : RUDDER_SAILOR
 *  => 4 : OAR_SAILOR
 *
 *
 * @author D'Andréa William
 */
public class AffectSailorsWithObjectiveToTheirBoatEntities {

    private final GameInfo gameInfo;


    public AffectSailorsWithObjectiveToTheirBoatEntities(GameInfo gameInfo) {
        this.gameInfo = gameInfo;
    }

    public void launch() {

        List<BoatEntity> watchBoatEntities = Arrays.stream(gameInfo.getShip().getEntities()).filter(boatEntity -> boatEntity.getType() == BoatEntities.WATCH).collect(Collectors.toList());
        affectSailorsToBoatEntity(SailorMission.WATCH_SAILOR, watchBoatEntities);

        List<BoatEntity> sailBoatEntities = Arrays.stream(gameInfo.getShip().getEntities()).filter(boatEntity -> boatEntity.getType() == BoatEntities.SAIL).collect(Collectors.toList());
        affectSailorsToBoatEntity(SailorMission.SAIL_SAILOR, sailBoatEntities);

        List<BoatEntity> rudderBoatEntities = Arrays.stream(gameInfo.getShip().getEntities()).filter(boatEntity -> boatEntity.getType() == BoatEntities.RUDDER).collect(Collectors.toList());
        affectSailorsToBoatEntity(SailorMission.RUDDER_SAILOR, rudderBoatEntities);

        List<BoatEntity> tribordOarBoatEntities = gameInfo.getListOfTribordOars();
        affectSailorsToBoatEntity(SailorMission.TRIBORDOAR_SAILOR, tribordOarBoatEntities);

        List<BoatEntity> babordOarBoatEntities = gameInfo.getListOfBabordOars();
        affectSailorsToBoatEntity(SailorMission.BABORDOAR_SAILOR, babordOarBoatEntities);
    }


    /**
     * Cette méthode a pour objectif de trouver la BoatEntities judicieuse pour affecter les marins qui ont une certaine missions
     * et ensuite, une fois que la méthode a trouvé la boatEntities la plus proche de chaque marin, elle affecte ce marin
     * a cette boatEntities
     * @param sailorMission mission affecté à un marin
     * @param goalBoatEntities les endroits ou nous voulons que la liste de marin se déplace
     */
    public void affectSailorsToBoatEntity(SailorMission sailorMission, List<BoatEntity> goalBoatEntities) {

        // D'abord on sélectionne la liste de marins que l'on souhaite affecter à des boatEntities
        List<Marin> sailorsToAffects = Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == sailorMission).collect(Collectors.toList());
        List<Marin> sailorsToAffectsClone = Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == sailorMission).collect(Collectors.toList());;

        // D'abord, on va chercher les marins qui sont déjà bien positionné
        for (Marin marin : sailorsToAffectsClone) {

            // On vérifie que le marin n'est pas déjà affecté à une action
            if (!Arrays.asList(gameInfo.getShip().getEntities()).contains(marin)) {

                for (BoatEntity possiblePlace: goalBoatEntities) {
                    if (marin.getPosition().equals(possiblePlace.getPosition())) {
                        possiblePlace.setSailorAffected(marin);
                        sailorsToAffects.remove(marin);
                    }
                }
            }


        }


        // Ensuite on affetcte tout ceux qui n'ont pas été affecté
        for (Marin marin : sailorsToAffects) {
            double distance = Double.POSITIVE_INFINITY;
            Optional<BoatEntity> bestPlace = Optional.empty();

            // On vérifie que le marin n'est pas déjà affecté à une action
            if (!Arrays.asList(gameInfo.getShip().getEntities()).contains(marin)) {

                // Maintenant on regarde la place la plus proche
                for (BoatEntity possiblePlace : goalBoatEntities) {
                    double distanceBetweenMarinAndPossiblePlace =  possiblePlace.getPosition().distance(marin.getPosition());

                    // Si aucun marin n'est affecté à ectte place et que la distance entre le marin et la destination est plus courte,
                    // On affecte ce marin a cette place
                    if (possiblePlace.getSailorAffected() == null && distanceBetweenMarinAndPossiblePlace <= distance) {
                        distance = distanceBetweenMarinAndPossiblePlace;
                        bestPlace = Optional.of(possiblePlace);
                    }
                }
            }

            // Si le marin le plus proche est trouvé, on l'affecte à la boatEntity
            bestPlace.ifPresent(boatEntity -> boatEntity.setSailorAffected(marin));
        }
    }










}
