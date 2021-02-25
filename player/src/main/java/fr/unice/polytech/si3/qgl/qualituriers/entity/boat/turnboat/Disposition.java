package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.turnboat;

public class Disposition {

    private final int babordOar;
    private final int tribordOar;

    public Disposition(int babordOar, int tribordOar) {
        this.babordOar = babordOar;
        this.tribordOar = tribordOar;
    }

    public int getBabordOar() {
        return babordOar;
    }

    public int getTribordOar() {
        return tribordOar;
    }

    @Override
    public String toString() {
        return "Disposition{" +
                "babordOar=" + babordOar +
                ", tribordOar=" + tribordOar +
                '}' + '\n';
    }
}
