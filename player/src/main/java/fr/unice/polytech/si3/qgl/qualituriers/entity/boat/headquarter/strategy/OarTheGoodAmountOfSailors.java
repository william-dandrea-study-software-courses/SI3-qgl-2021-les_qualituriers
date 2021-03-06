package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.strategy;

import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.headquarterutils.HeadquarterUtil;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OarTheGoodAmountOfSailors {

    private Boat boat;
    private List<Marin> sailors;
    private int differenceOfSailors;

    public OarTheGoodAmountOfSailors(Boat boat, List<Marin> sailors, int differenceOfSailors) {
        this.boat = boat;
        this.sailors = sailors;
        this.differenceOfSailors = differenceOfSailors;
    }



    public List<Action> oarTheGoodAmountOfSailors() {
        List<Action> finalActions = new ArrayList<>();
        // rame tribord - rame babord > 0 <=> rameTribord > rameBabord
        if (differenceOfSailors > 0 ) {
            // On doit avoir differenceOfSailors marins de plus a Tribord qu'a babord

            List<Marin> listOfTribordSailors = HeadquarterUtil.getListOfSailorsOnTribordOars(sailors, boat);

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


        } else if (differenceOfSailors < 0) {
            // On doit avoir differenceOfSailors marins de plus a babord qu'a tribord

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


        } else {
            // On doit avoir le meme nombre de marin de chaque côté car on essaye d'aller droit

            List<Marin> listOfBabordSailors = HeadquarterUtil.getListOfSailorsOnBabordOars(sailors, boat);
            List<Marin> listOfTribordSailors = HeadquarterUtil.getListOfSailorsOnTribordOars(sailors, boat);

            int numberOfSailors = Math.min(listOfTribordSailors.size(), listOfBabordSailors.size());

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

        }
        return finalActions;
    }


}
