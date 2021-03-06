package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.strategy;

import fr.unice.polytech.si3.qgl.qualituriers.Config;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.RudderBoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.headquarterutils.HeadquarterUtil;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class InitSailorsPlaceOnRudder {

    private Boat boat;
    private List<Marin> sailors;

    private int maxMovingBoatX;
    private int maxMovingBoatY;

    private Marin assignedSailor;

    public InitSailorsPlaceOnRudder(Boat boat, List<Marin> sailors) {
        this.boat = boat;
        this.sailors = sailors;

        maxMovingBoatX = Math.min(boat.getDeck().getLength(), Config.MAX_MOVING_CASES_MARIN);
        maxMovingBoatY = Math.min(boat.getDeck().getWidth(), Config.MAX_MOVING_CASES_MARIN);

    }

    /**
     * A implémenter
     * @return la liste des actions a faire pour que le marin donné en paramètre aille sur la case rudder du bateau
     */
    public List<Action> initSailorsPlaceOnRudder() {

        List<Action> finalListOfActions = new ArrayList<>();
        List<Integer> sailorWeMoves = new ArrayList<>();

        //P On parcourt la liste des marins, puis vérifie s'ils sont sur une rame. Si non, on peut attitrer le premier au Rudder

        int rudderPosValue = HeadquarterUtil.getRudder(boat).get().getX() + HeadquarterUtil.getRudder(boat).get().getY();
            int minMovement;
                minMovement = (sailors.get(0).getX() + sailors.get(0).getY()) - rudderPosValue;
                if(minMovement<0)
                    minMovement *= -1;
        int tampon;
        Marin marinTampon = sailors.get(0);
        for(Marin s : sailors){
            tampon = s.getX() + s.getY() - rudderPosValue;
            if (tampon<0)
                tampon*=-1;
            if (tampon<minMovement){
                marinTampon = s;
                minMovement = tampon;
            }
        }

            int i=0;
            int tempCoordinateXValue = marinTampon.getX();
            int tempCoordinateYValue = marinTampon.getY();
            if(marinTampon.getX()>HeadquarterUtil.getRudder(boat).get().getX())
                {
                    while(i<maxMovingBoatX && tempCoordinateXValue!=HeadquarterUtil.getRudder(boat).get().getX())
                    {
                        tempCoordinateXValue--;
                        i++;
                    }
                }

            if(marinTampon.getX()<HeadquarterUtil.getRudder(boat).get().getX())
                {
                    while(i<maxMovingBoatY && tempCoordinateXValue!=HeadquarterUtil.getRudder(boat).get().getX())
                    {
                        tempCoordinateXValue++;
                        i++;
                    }
                }
            if(marinTampon.getY()>HeadquarterUtil.getRudder(boat).get().getY())
            {
                while(i<maxMovingBoatY && tempCoordinateYValue!=HeadquarterUtil.getRudder(boat).get().getY())
                {
                    tempCoordinateYValue--;
                    i++;
                }
            }

            if(marinTampon.getY()<HeadquarterUtil.getRudder(boat).get().getY())
            {
                while(i<maxMovingBoatY && tempCoordinateYValue!=HeadquarterUtil.getRudder(boat).get().getY())
                {
                    tempCoordinateYValue++;
                    i++;
                }
            }

            Optional<Action> movingAction = HeadquarterUtil.generateMovingAction(marinTampon.getId(), marinTampon.getX(), marinTampon.getY(), tempCoordinateXValue, tempCoordinateYValue);
            if (movingAction.isPresent()) {
                finalListOfActions.add(movingAction.get());
            }

        assignedSailor = marinTampon;
        return finalListOfActions;
    }

    public Marin getAssignedSailor(){
        return assignedSailor;
    }
}
