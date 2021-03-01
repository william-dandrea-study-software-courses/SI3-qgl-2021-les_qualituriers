package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.strategy;

import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;

import java.util.ArrayList;
import java.util.List;

public class InitSailorsPlaceOnRudder {

    private Boat boat;
    private Marin sailor;

    public InitSailorsPlaceOnRudder(Boat boat, Marin sailor) {
        this.boat = boat;
        this.sailor = sailor;
    }

    /**
     * A implémenter
     * @return la liste des actions a faire pour que le marin donné en paramètre aille sur la case rudder du bateau
     */
    public List<Action> initSailorsPlaceOnRudder() {
        return new ArrayList<>();
    }
}
