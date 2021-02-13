package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.turnboat.turnboatutils;

public class BabordTribordAngle {
    private int babord;
    private int tribord;
    private double angle;

    public BabordTribordAngle(int babord, int tribord, double angle) { this.babord = babord;this.tribord = tribord;this.angle = angle; }

    public int getBabord() { return babord; }
    public int getTribord() { return tribord; }
    public double getAngle() { return angle; }

    @Override
    public String toString() {
        return "BabordTribordAngle{" +
                "babord=" + babord +
                ", tribord=" + tribord +
                ", angle=" + angle +
                '}' + '\n';
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof BabordTribordAngle) {
            if (this.tribord == ((BabordTribordAngle) obj).tribord && this.babord == ((BabordTribordAngle) obj).babord && this.angle == ((BabordTribordAngle) obj).angle)
                return true;
        }
        return false;
    }
}
