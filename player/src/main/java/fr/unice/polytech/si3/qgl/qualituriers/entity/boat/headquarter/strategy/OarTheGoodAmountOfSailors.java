package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.strategy;

import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.headquarterutils.HeadquarterUtil;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OarTheGoodAmountOfSailors {

    private final Boat boat;
    private final List<Marin> sailors;
    private int differenceOfSailors;
    private final Transform goal;

    public OarTheGoodAmountOfSailors(Boat boat, List<Marin> sailors, int differenceOfSailors, Transform goal) {
        this.boat = boat;
        this.sailors = sailors;
        this.differenceOfSailors = differenceOfSailors;
        this.goal = goal;
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


    int optimumNumberOfActiveOarsToBeOnTheCheckPoint() {

        double distanceBetweenBoatAndCheckPoint = HeadquarterUtil.distanceBetweenTwoPoints(boat.getPosition(), goal);
        int numberOfOarsOnTheBoat = HeadquarterUtil.getListOfOars(boat).size();


        for (int i = 1; i <= numberOfOarsOnTheBoat ; i++) {

            double distanceWithThisAmountOfOars = (double) 165 * i / numberOfOarsOnTheBoat;

            if ( distanceWithThisAmountOfOars >= distanceBetweenBoatAndCheckPoint) {
                return i;
            }

        }

        return numberOfOarsOnTheBoat;
    }


    List<Action> generateOarActionWhenDifferenceIsPositive2() {

        // On doit avoir differenceOfSailors marins de plus a Tribord qu'a babord
        List<Action> finalActions = new ArrayList<>();

        int numberOfOarsOnTheBoat = HeadquarterUtil.getListOfOars(boat).size();

        List<Marin> listOfBabordSailors = HeadquarterUtil.getListOfSailorsOnBabordOars(sailors, boat);
        List<Marin> listOfTribordSailors = HeadquarterUtil.getListOfSailorsOnTribordOars(sailors, boat);
        int numberOfBabordSailorOnOar = listOfBabordSailors.size();
        int numberOfTribordSailorOnOar = listOfTribordSailors.size();

        int optimumNumberOfOars = optimumNumberOfActiveOarsToBeOnTheCheckPoint();
        int difference = Math.abs(differenceOfSailors);

        int increment = 1;
        int babord = 0;
        int tribord = babord + difference;

        System.out.println("Optimum : " + optimumNumberOfOars);

        while (increment <= numberOfOarsOnTheBoat) {


            if (!(babord + tribord < numberOfOarsOnTheBoat
                    && babord + tribord < optimumNumberOfOars-1
                    && babord < numberOfBabordSailorOnOar
                    && tribord < numberOfTribordSailorOnOar
            )) {
                break;
            } else {
                babord += 1;
                tribord += 1;
            }

            increment++;
        }


        System.out.println(babord);
        System.out.println(tribord);



        for (Marin babordMarin : listOfBabordSailors) {
            if (babord == 0){
                break;
            }
            babord -= 1;


            Optional<Action> action = HeadquarterUtil.generateOar(babordMarin.getId(), sailors, boat);
            action.ifPresent(finalActions::add);
        }

        for (Marin tribordMarin : listOfTribordSailors) {
            if (tribord == 0){
                break;
            }
            tribord -= 1;


            Optional<Action> action = HeadquarterUtil.generateOar(tribordMarin.getId(), sailors, boat);
            action.ifPresent(finalActions::add);
        }

        return finalActions;
    }



    private List<Action> generateOarActionWhenDifferenceIsPositive() {

        List<Action> finalActions = new ArrayList<>();
        List<Marin> listOfTribordSailors = HeadquarterUtil.getListOfSailorsOnTribordOars(sailors, boat);

        int optimumNumberOfActiveOars = optimumNumberOfActiveOarsToBeOnTheCheckPoint();

        for (Marin marin : listOfTribordSailors) {
            if (differenceOfSailors > 0) {

                Optional<Action> actionOptional = HeadquarterUtil.generateOar(marin.getId(), sailors, boat);
                if (actionOptional.isPresent()) {
                    finalActions.add(actionOptional.get());
                    differenceOfSailors--;
                } else {
                    return finalActions;
                }
            }
        }

        return finalActions;
    }

    private List<Action> generateOarActionWhenDifferenceIsNegative() {

        List<Action> finalActions = new ArrayList<>();
        List<Marin> listOfBabordSailors = HeadquarterUtil.getListOfSailorsOnBabordOars(sailors, boat);

        for (Marin marin : listOfBabordSailors) {
            if (differenceOfSailors < 0) {

                Optional<Action> actionOptional = HeadquarterUtil.generateOar(marin.getId(), sailors, boat);
                if (actionOptional.isPresent()) {
                    finalActions.add(actionOptional.get());
                    differenceOfSailors--;
                } else {
                    return finalActions;
                }
            }
        }
        return finalActions;
    }

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







}
