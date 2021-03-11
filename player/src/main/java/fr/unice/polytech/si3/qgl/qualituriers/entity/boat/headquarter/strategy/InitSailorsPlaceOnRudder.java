package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.strategy;

import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.headquarterutils.BoatPathFinding;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.headquarterutils.HeadquarterUtil;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InitSailorsPlaceOnRudder {

    private final Boat boat;
    private Marin rudder;
    private final List<Marin> sailors;

    public InitSailorsPlaceOnRudder(Boat boat, int rudderId, List<Marin> sailors) {
        this.boat = boat;
        this.sailors = sailors;
        HeadquarterUtil.getSailorByHisID(sailors,rudderId).ifPresent(value -> this.rudder = value);
    }

    /**
     * Cette méthode va aller déplacer le marin mis en paramètre vers le Rudder
     * @return la liste des actions a faire pour que le marin donné en paramètre aille sur la case rudder du bateau
     */
    public List<Action> initSailorsPlaceOnRudder() {

        List<Action> finalListOfActions = new ArrayList<>();

        Optional<BoatEntity> boatRudderOp = HeadquarterUtil.getRudder(boat);

        // Si on a un gouvernail
        if (boatRudderOp.isPresent()) {
            BoatEntity boatRudder = boatRudderOp.get();


            // Si il y a deja un marin sur le gouvernail
            if (HeadquarterUtil.getSailorOnRudder(boat, sailors).isPresent()) {
                return new ArrayList<>();
            }

            // Si il n'y a pas de marin sur le gouvernail mais que l'on peut bouger le marin sur le gouvernail
            if (rudder.canMoveTo(boatRudder.getX(), boatRudder.getY(), boat)) {

                Optional<Action> movingAction = HeadquarterUtil.generateMovingAction(rudder.getId(), rudder.getX(), rudder.getY(), boatRudder.getX(), boatRudder.getY());

                if (movingAction.isPresent()) {
                    finalListOfActions.add(movingAction.get());
                    rudder.setPosition(boatRudder.getX(), boatRudder.getY());
                }
            } else {

                // On ne peux pas bouger le marin directement sur le gouvernail
                return moveTheSailorTheNearest(boatRudder);

            }


        }

        return finalListOfActions;
    }



    private List<Action> moveTheSailorTheNearest(BoatEntity boatRudder) {

        BoatPathFinding boatPathFinding = new BoatPathFinding(sailors, boat, rudder.getId(), boatRudder.getPosition());
        Point point = boatPathFinding.generateClosestPoint();

        if (rudder.canMoveTo((int) point.getX(),(int) point.getY(), boat)) {
            Optional<Action> movingAction = HeadquarterUtil.generateMovingAction(rudder.getId(), rudder.getX(), rudder.getY(), (int) point.getX(), (int) point.getY());
            if (movingAction.isPresent()) {
                Action actionToAdd = movingAction.get();
                rudder.setPosition((int) point.getX(), (int) point.getY());
                return new ArrayList<>(){{add(actionToAdd);}};
            }
        }


        return new ArrayList<>();
    }

}
