package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatutils;

import java.util.List;

public class HowTurn {

    private List<BabordTribordAngle> canDoAngle;
    private double cantDoAngle;

    public HowTurn(List<BabordTribordAngle> canDoAngle, double cantDoAngle) {
        this.canDoAngle = canDoAngle;
        this.cantDoAngle = cantDoAngle;
    }

    public List<BabordTribordAngle> getCanDoAngle() {
        return canDoAngle;
    }

    public double getCantDoAngle() {
        return cantDoAngle;
    }

    @Override
    public String toString() {
        return "HowTurn{" +
                "canDoAngle=" + canDoAngle +
                ", cantDoAngle=" + cantDoAngle +
                '}';
    }
}
