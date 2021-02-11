package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatutils;

import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;

/**
 * @author CLODONG Yann
 */
public class Captain {
    private final Boat boat;
    private Point target;

    public Captain(Boat boat) {
        this.boat = boat;
    }

    public void decide() {
        if(target == null) return;

        setSpeed();
        setBend();
    }

    private void setSpeed() {
        double dist = boat.getTransform().getPoint().substract(target).length();
        double distRal = 3;
        if(dist > distRal) dist = distRal;

        boat.getForeman().setSpeed(dist / distRal);
    }

    private void setBend() {
        double angle = boat.getTransform().getAngleToSee(target);
        double bend = 0;

        if(Math.abs(angle) > Math.PI / 2) {
            bend = 1;
        } else if(Math.abs(angle) > Math.PI / 4) {
            bend = 0.5f;
        }
        boolean right = angle < 0;

        if(right) boat.getForeman().setBendRight(bend);
        else boat.getForeman().setBendLeft(bend);
    }

    public void goTo(Point position) {
        this.target = position;
    }
}
