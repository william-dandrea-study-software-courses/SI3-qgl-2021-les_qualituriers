package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.turnboat.turnboatutils;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;

import java.util.Objects;

public class DistanceDisposition {


    private final int babordOar;
    private final int tribordOar;
    private final Point finalPositionOnTheMapX;
    private final double distanceToCheckPoint;

    public DistanceDisposition(int babordOar, int tribordOar, Point finalPositionOnTheMapX, double distanceToCheckPoint) {

        this.babordOar = babordOar;
        this.tribordOar = tribordOar;
        this.finalPositionOnTheMapX = finalPositionOnTheMapX;
        this.distanceToCheckPoint = distanceToCheckPoint;
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

    public double getDistanceToCheckPoint() {
        return distanceToCheckPoint;
    }

    @Override
    public String toString() {
        return "DistanceDisposition{" +
                "babordOar=" + babordOar +
                ", tribordOar=" + tribordOar +
                ", finalPositionOnTheMapX=" + finalPositionOnTheMapX +
                ", distanceToTheBoat=" + distanceToCheckPoint +
                '}' + '\n';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DistanceDisposition that = (DistanceDisposition) o;
        return babordOar == that.babordOar && tribordOar == that.tribordOar && Double.compare(that.distanceToCheckPoint, distanceToCheckPoint) == 0 && Objects.equals(finalPositionOnTheMapX, that.finalPositionOnTheMapX);
    }

    @Override
    public int hashCode() {
        return Objects.hash(babordOar, tribordOar, finalPositionOnTheMapX, distanceToCheckPoint);
    }
}
