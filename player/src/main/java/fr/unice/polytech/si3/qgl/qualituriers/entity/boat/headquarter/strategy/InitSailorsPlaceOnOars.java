package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.strategy;


import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.headquarterutils.BoatPathFinding;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.headquarterutils.HeadquarterUtil;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;

import java.util.*;


/**
 * Cette classe va, en debut de partie, aller positionner tout les marins en entrée sur des rames
 * Si il n'y a pas de rames disponible ou que les rames sont trop loin, alors cette classe va aller faire déplacer les marins
 * Au plus proche des rames afin que, au prochain tour, les marins puissent aller sur les rames
 * @author williamdandrea
 */
public class InitSailorsPlaceOnOars {

    private final Boat boat;
    private final List<Marin> sailors;

    private final List<BoatEntity> oarWeMoves;
    private final List<Integer> idOfSailorWeMove;

    public InitSailorsPlaceOnOars(Boat boat, List<Marin> sailors) {
        this.boat = boat;
        this.sailors = sailors;


        oarWeMoves = new ArrayList<>();
        idOfSailorWeMove = new ArrayList<>();

    }


    /**
     * Cette méthode va générer la liste d'action nécessaire pour aller mettre les marins sur les rames, si pas de
     * rames dispo, alors ca va déplacer les marins le plus loin possible de leur position
     * @return la liste d'action a faire pour mettre les marins au meilleur endroit
     */
    public List<Action> initSailorsPlace() {

        List<Marin> listOfSailorsOnBabordOars = HeadquarterUtil.getListOfSailorsOnBabordOars(sailors, boat);
        List<Marin> listOfSailorsOnTribordOars = HeadquarterUtil.getListOfSailorsOnTribordOars(sailors, boat);

        List<BoatEntity> listOfBabordOarWithAnySailorsOnIt = HeadquarterUtil.getListOfBabordOarWithAnySailorsOnIt(sailors, boat);
        List<BoatEntity> listOfTribordOarWithAnySailorsOnIt = HeadquarterUtil.getListOfTribordOarWithAnySailorsOnIt(sailors, boat);


        int numberOfSailorsAtBabord = (sailors.size() / 2) ;
        int numberOfSailorsAtTribord = sailors.size() - numberOfSailorsAtBabord;


        List<Marin> listOfSailorsOnAnyOars = HeadquarterUtil.getListOfSailorsOnAnyOar(sailors, boat);


        int missingBabordSailors = numberOfSailorsAtBabord - listOfSailorsOnBabordOars.size();
        int missingTribordSailors = numberOfSailorsAtTribord - listOfSailorsOnTribordOars.size();



        List<Action> finalListOfActions = new ArrayList<>(moveSailorsIn(missingBabordSailors, listOfSailorsOnAnyOars, listOfBabordOarWithAnySailorsOnIt));
        listOfSailorsOnAnyOars = HeadquarterUtil.getListOfSailorsOnAnyOar(sailors, boat);
        finalListOfActions.addAll(moveSailorsIn(missingTribordSailors, listOfSailorsOnAnyOars, listOfTribordOarWithAnySailorsOnIt));


        return finalListOfActions;
    }


    /**
     * Cette méthode prend en entrée un nombre de marin que l'on veut sur les entités de listOfEmplacementWhereWeWantSailor et
     * une liste de marins que l'on soouhaite faire bouger sur listOfEmplacementWhereWeWantSailor (en général, ce sont des
     * marins qui ne sont sur aucune entité). Ensuite, cette méthode génére une liste d'actions pour déplacer les marins
     * sur ces emplacements ou les emplacements les plus proches ou déplacer un marin.
     * @param numberOfSailors le nombre de déplacement que l'on souhaite faire
     * @param listOfSailorsWeWantToMoveTo la liste des marins que l'on souhaite faire bouger
     * @param listOfEmplacementWhereWeWantSailor la liste des emplacements potentiels
     * @return la liste d'action pour deplacer les marins sur ou au plus pret des mplacemnts que l'on souhaite
     */
     List<Action> moveSailorsIn(int numberOfSailors, List<Marin> listOfSailorsWeWantToMoveTo, List<BoatEntity> listOfEmplacementWhereWeWantSailor) {

         List<Action> finalListOfActions = new ArrayList<>();


         Map<Marin, Point> potentialPlaceWhereWeCanMoveAnSailor = generateCorrespondanceSailorWithPotentialEmplacement(listOfSailorsWeWantToMoveTo, listOfEmplacementWhereWeWantSailor);



         if (potentialPlaceWhereWeCanMoveAnSailor.size() >= numberOfSailors) {
             finalListOfActions.addAll(affectMarinToBoatEntity(potentialPlaceWhereWeCanMoveAnSailor, numberOfSailors));

         } else {

            finalListOfActions.addAll(affectMarinToBoatEntity(potentialPlaceWhereWeCanMoveAnSailor, potentialPlaceWhereWeCanMoveAnSailor.size()));

            // On va faire bouger les marins restant au plus proche des places restantes
             int missingMovingSailors = numberOfSailors - potentialPlaceWhereWeCanMoveAnSailor.size();
             finalListOfActions.addAll(generateMovingActionsWhenWeCantMoveSailorDirectly(listOfSailorsWeWantToMoveTo, listOfEmplacementWhereWeWantSailor, missingMovingSailors));

         }

        return finalListOfActions;
    }


    /**
     * Cette méthode permet de faire bouger les marins le plus proche d'une liste de destination
     * @param listOfSailorsWeWantToMoveTo l liste des marins que l'on souhaite bouger
     * @param listOfEmplacementWhereWeWantSailor la liste des emplacements ou nous voulons un marin
     * @param maxNumberOfActions le nombre maximum de depalcement que nous souhaitons faire
     * @return la liste d'action a faire pour deplacer les marins au plus proche de la destination finale
     */
    List<Action> generateMovingActionsWhenWeCantMoveSailorDirectly(List<Marin> listOfSailorsWeWantToMoveTo, List<BoatEntity> listOfEmplacementWhereWeWantSailor, int maxNumberOfActions) {

         List<Action> finalListOfActions = new ArrayList<>();

         for (Marin marin : listOfSailorsWeWantToMoveTo) {

             if (maxNumberOfActions == 0)
                return finalListOfActions;

             for (BoatEntity boatEntity : listOfEmplacementWhereWeWantSailor){

                if (HeadquarterUtil.placeIsFree(boatEntity.getPosition(), sailors)
                        && !HeadquarterUtil.sailorIsOnOar(boat, marin)
                        && !idOfSailorWeMove.contains(marin.getId())
                        && !oarWeMoves.contains(boatEntity)
                ){

                    BoatPathFinding boatPathFinding = new BoatPathFinding(sailors, boat, marin.getId(), boatEntity.getPosition());
                    Point closestPoint = boatPathFinding.generateClosestPoint();

                    Optional<Action> action = HeadquarterUtil.generateMovingAction(marin.getId(), marin.getX(), marin.getY(), (int) closestPoint.getX(), (int) closestPoint.getY());
                    if (action.isPresent()) {
                        finalListOfActions.add(action.get());
                        marin.setPosition((int) closestPoint.getX(), (int) closestPoint.getY());
                        idOfSailorWeMove.add(marin.getId());
                    }

                    maxNumberOfActions--;
                    break;

                }
             }
         }
        return finalListOfActions;
    }


    /**
     * Cette méthode parcours une liste de marins et regarde si on peut déplacer un marin sur un emplacement de bateau,
     * cette méthode vérifie aussi que l'on peut bien le deplacer
     * @param listOfSailorsWeWantToMoveTo liste des marins que l'on souhaite deplacer sur des boatEntity
     * @param placesWhereWeWantSailors liste des boat Entity ou nous voulons des marins
     * @return une correspondance Map<Marin, Point> qui correspond a l'emplacement a un point que le marin peut atteindre
     */
    Map<Marin, Point> generateCorrespondanceSailorWithPotentialEmplacement(List<Marin> listOfSailorsWeWantToMoveTo, List<BoatEntity> placesWhereWeWantSailors) {
        Map<Marin, Point> potentialPlaceWhereWeCanMoveAnSailor = new HashMap<>();
        for (Marin marinWeTryToMove : listOfSailorsWeWantToMoveTo) {
            for (BoatEntity emplacementPotentiel : placesWhereWeWantSailors) {

                if (marinWeTryToMove.canMoveTo(emplacementPotentiel.getX(), emplacementPotentiel.getY(), boat)
                        && !oarWeMoves.contains(emplacementPotentiel)
                        && HeadquarterUtil.placeIsFree(emplacementPotentiel.getPosition(), sailors)
                        && !idOfSailorWeMove.contains(marinWeTryToMove.getId())
                ) {
                    potentialPlaceWhereWeCanMoveAnSailor.put(marinWeTryToMove, new Point(emplacementPotentiel.getX(), emplacementPotentiel.getY()));
                    oarWeMoves.add(emplacementPotentiel);
                    break;
                }
            }
        }

        return potentialPlaceWhereWeCanMoveAnSailor;
    }




    /**
     * Cette méthode permet d'affecter des marins a des emplacements
     * @param potentialPlaces une map qui fait correspondre les marin avec leur emplacement final
     * @param maxNumberOfMoving le nombre maximum de deplacement autorisé, par exemple, la map a une longueur de 4
     *                          mais on ne veux que 2 déplacement, donc cette valeur doit etre a 2
     * @return liste d'action a effectuer par le moteur de jeu pour faire bouger ses marins.
     */
    List<Action> affectMarinToBoatEntity(Map<Marin, Point> potentialPlaces, int maxNumberOfMoving) {

        List<Action> finalListOfActions = new ArrayList<>();

        int increment = 0;
        for (Map.Entry<Marin, Point> correspondance : potentialPlaces.entrySet()) {

            if (increment == maxNumberOfMoving) return finalListOfActions;

            increment++;

            Optional<Action> action = HeadquarterUtil.generateMovingAction(correspondance.getKey().getId(), correspondance.getKey().getX(), correspondance.getKey().getY(), (int) correspondance.getValue().getX(), (int) correspondance.getValue().getY());

            if (action.isPresent()) {
                finalListOfActions.add(action.get());
                correspondance.getKey().setPosition((int) correspondance.getValue().getX(), (int) correspondance.getValue().getY());
                idOfSailorWeMove.add(correspondance.getKey().getId());
            }
        }

        return finalListOfActions;
    }


}



