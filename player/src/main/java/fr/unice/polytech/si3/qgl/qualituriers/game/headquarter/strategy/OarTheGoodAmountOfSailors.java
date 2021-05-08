package fr.unice.polytech.si3.qgl.qualituriers.game.headquarter.strategy;

import fr.unice.polytech.si3.qgl.qualituriers.Config;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.game.headquarter.headquarterutils.HeadquarterUtil;
import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OarTheGoodAmountOfSailors {

    private final Boat boat;
    private final List<Marin> sailors;
    private final int differenceOfSailors;
    private final Transform goal;
    private final GameInfo gameInfo;

    public OarTheGoodAmountOfSailors(Boat boat, List<Marin> sailors, int differenceOfSailors, Transform goal, GameInfo gameInfo) {
        this.boat = boat;
        this.sailors = sailors;
        this.differenceOfSailors = differenceOfSailors;
        this.goal = goal;
        this.gameInfo = gameInfo;
    }



    public List<Action> oarTheGoodAmountOfSailors() {

        List<Action> finalActions = new ArrayList<>();

        // rame tribord - rame babord > 0 <=> rameTribord > rameBabord
        if (differenceOfSailors > 0 ) {
            // On doit avoir differenceOfSailors marins de plus a Tribord qu'a babord

            finalActions.addAll(generateOarActionWhenDifferenceIsPositive());

        } else if (differenceOfSailors < 0) {
            // On doit avoir differenceOfSailors marins de plus a babord qu'a tribord

            finalActions.addAll(generateOarActionWhenDifferenceIsNegative());

        } else {
            // On doit avoir le meme nombre de marin de chaque côté car on essaye d'aller droit
            finalActions.addAll(generateOarActionWhenDifferenceIsNull());
        }

        return finalActions;
    }





    /**
     * Cette méthode va retourner la liste d'action a faire pour faire bouger le bateau vers la droite, elle va
     * regarder aussi combien de marin il est optimal de faire ramer et en fera ramer le bon nombre en fonction de ça
     * @return la liste d'action a faire pour faire avancer le bateau vers la droite
     */
    List<Action> generateOarActionWhenDifferenceIsPositive() {

        // On doit avoir differenceOfSailors marins de plus a Tribord qu'a babord

        int difference = Math.abs(differenceOfSailors);
        int babord = 0;
        int tribord = babord + difference;

        return generateOarActionWhenDifference(babord, tribord);
    }


    /**
     * Cette méthode va retourner la liste d'action a faire pour faire bouger le bateau vers la gauche, elle va
     * regarder aussi combien de marin il est optimal de faire ramer et en fera ramer le bon nombre en fonction de ça
     * @return la liste d'action a faire pour faire avancer le bateau vers la gauche
     */
    List<Action> generateOarActionWhenDifferenceIsNegative() {

        // On doit avoir differenceOfSailors marins de plus a Tribord qu'a babord
        int difference = Math.abs(differenceOfSailors);
        int tribord = 0;
        int babord = tribord + difference;

        return generateOarActionWhenDifference(babord, tribord);
    }



    /**
     * Cette méthode va retourner la liste d'action a faire pour faire bouger le marin en ligne droite, elle va
     * regarder aussi combien de marin il est optimal de faire ramer et en fera ramer le bon nombre en fonction de ça
     * @return la liste d'action a faire pour faire avancer le bateau en ligne droite
     */
    private List<Action> generateOarActionWhenDifferenceIsNull() {

        List<Action> finalActions = new ArrayList<>();
        List<Marin> listOfBabordSailors = HeadquarterUtil.getListOfSailorsOnBabordOars(sailors, boat);
        List<Marin> listOfTribordSailors = HeadquarterUtil.getListOfSailorsOnTribordOars(sailors, boat);


        int numberOfSailors = Math.min(listOfTribordSailors.size(), listOfBabordSailors.size());
        int optimumNumberOfOars = Math.max(2, optimumNumberOfActiveOarsToBeOnTheCheckPoint() / 2);
        numberOfSailors = Math.min(optimumNumberOfOars, numberOfSailors);

        for (int marin = 0; marin < numberOfSailors; marin++) {

            Optional<Action> actionOptionalBab = HeadquarterUtil.generateOar(listOfBabordSailors.get(marin).getId(), sailors, boat);
            Optional<Action> actionOptionalTri = HeadquarterUtil.generateOar(listOfTribordSailors.get(marin).getId(), sailors, boat);

            if (actionOptionalBab.isPresent() && actionOptionalTri.isPresent()) {
                finalActions.add(actionOptionalBab.get());
                finalActions.add(actionOptionalTri.get());

            } else {
                return finalActions;
            }
        }

        return finalActions;
    }



    /**
     * Cette méthode retourne le nombre de marin que l'on doit faire ramer pour arriver pile-poile sur un checkpoint
     * Si le checkpoint est très loin, on fait ramer tout les marins, si le checkpoint est très proche, cette
     * méthode renvoie juste le nombre de marin nécessaire pour atterir dans ce checkpoint
     * @return le nombre de marins a faire ramer pour atteindre un checkpoint
     */
    int optimumNumberOfActiveOarsToBeOnTheCheckPoint() {

        double distanceBetweenBoatAndCheckPoint = HeadquarterUtil.distanceBetweenTwoPoints(boat.getPosition(), goal);
        int numberOfOarsOnTheBoat = HeadquarterUtil.getListOfOars(boat).size();

        boolean sailIsOpen = false;
        Optional<BoatEntity> sailOp = HeadquarterUtil.getSail(boat);
        if (sailOp.isPresent()) {
            sailIsOpen = true;
        }


        double courantSpeed = 0;
        /*
        for (VisibleDeckEntity visibleDeckEntity : gameInfo.getSeaEntities()) {

            if ( visibleDeckEntity.getType().equals(VisibleDeckEntities.STREAM) && Collisions.isColliding(visibleDeckEntity.getPositionableShape(), boat.getPositionableShape())) {

                if (Math.abs(((StreamVisibleDeckEntity) visibleDeckEntity).getPosition().getAngleToSee(boat.getPosition())) <= Math.PI / 4) {
                    courantSpeed += ((StreamVisibleDeckEntity) visibleDeckEntity).getStrength();
                } else {
                    courantSpeed -= ((StreamVisibleDeckEntity) visibleDeckEntity).getStrength();
                }
                break;
            }
        }

         */





        for (int i = 0; i <= numberOfOarsOnTheBoat ; i++) {

            double distanceWithThisAmountOfOars = (double) 165 * i / numberOfOarsOnTheBoat;
            distanceWithThisAmountOfOars += courantSpeed;

            if (sailIsOpen) {
                distanceWithThisAmountOfOars += Config.linearSpeedWind(1,1, gameInfo.getWind().getStrength(), boat.getPosition().getOrientation(), gameInfo.getWind().getOrientation());
            }

            if ( distanceWithThisAmountOfOars >= distanceBetweenBoatAndCheckPoint) {
                return i;
            }
        }

        return numberOfOarsOnTheBoat;
    }


    /**
     * Cette méthode va générer la liste d'action pour un certain nombre de marin a droite et a gauche, cette méthode va
     * essayer de faire ramer le plus de marin possible en ayant l'ecart entre babord et tribord
     * @param babord nombre de marin min a babord
     * @param tribord nombre de marin min a tribord
     * @return liste d'action a faire pour avancer
     */
    List<Action> generateOarActionWhenDifference(int babord, int tribord) {

        int numberOfOarsOnTheBoat = HeadquarterUtil.getListOfOars(boat).size();

        List<Marin> listOfBabordSailors = HeadquarterUtil.getListOfSailorsOnBabordOars(sailors, boat);
        List<Marin> listOfTribordSailors = HeadquarterUtil.getListOfSailorsOnTribordOars(sailors, boat);

        int optimumNumberOfOars = optimumNumberOfActiveOarsToBeOnTheCheckPoint();

        int increment = 1;

        while (increment <= numberOfOarsOnTheBoat) {

            if (!(babord + tribord < numberOfOarsOnTheBoat
                    && babord + tribord < optimumNumberOfOars-1
                    && babord < listOfBabordSailors.size()
                    && tribord < listOfTribordSailors.size()
            )) {
                break;
            } else {
                babord += 1;
                tribord += 1;
            }

            increment++;
        }

        List<Action> finalActions = new ArrayList<>();
        finalActions.addAll(oarOneSideSailors(tribord, listOfTribordSailors));
        finalActions.addAll(oarOneSideSailors(babord, listOfBabordSailors));

        return finalActions;
    }


    /**
     * Cette méthode génére juste n action ramer sur le coté choisi
     * @param numberOfOarWeWant le nombre de rame active que l'on veut sur un cote
     * @param listOfSailorsOnThisSide la liste des rames sur ce coté
     * @return la liste d'action ramer
     */
    private List<Action> oarOneSideSailors(int numberOfOarWeWant, List<Marin> listOfSailorsOnThisSide) {
        List<Action> finalActions = new ArrayList<>();
        for (Marin babordMarin : listOfSailorsOnThisSide) {
            if (numberOfOarWeWant == 0){
                break;
            }
            numberOfOarWeWant -= 1;


            Optional<Action> action = HeadquarterUtil.generateOar(babordMarin.getId(), sailors, boat);
            action.ifPresent(finalActions::add);
        }
        return finalActions;
    }





}
