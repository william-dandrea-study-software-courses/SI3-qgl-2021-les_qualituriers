package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.turnboatsecond.utils;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;

public class DistanceDisposition {


    private final int babordOar;
    private final int tribordOar;
    private final Point finalPositionOnTheMapX;

    public DistanceDisposition(int babordOar, int tribordOar, Point finalPositionOnTheMapX) {

        this.babordOar = babordOar;
        this.tribordOar = tribordOar;
        this.finalPositionOnTheMapX = finalPositionOnTheMapX;
    }


    public int getBabordOar() {
        return babordOar;
    }

    public int getTribordOar() {
        return tribordOar;
    }

    public Point getFinalPositionOnTheMap() {
        return finalPositionOnTheMapX;
    }

    @Override
    public String toString() {
        return "DistanceDisposition{" +
                "babordOar=" + babordOar +
                ", tribordOar=" + tribordOar +
                ", finalPositionOnTheMapX=" + finalPositionOnTheMapX +
                '}' + '\n';
    }
}
