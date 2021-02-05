package fr.unice.polytech.si3.qgl.qualituriers.utils.action;

/**
 * @author Alexandre Arcil
 */
public class Oar extends Action {

    public Oar(int sailorId) {
        super(Actions.OAR, sailorId);
    }

    @Override
    public String toString() {
        return "Oar{" +
                "sailorId=" + sailorId +
                ", type='" + type + '\'' +
                '}';
    }
}
