package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatutils;

public class BoatPosition {

    private int x;
    private int y;

    public BoatPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "BoatPosition{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
