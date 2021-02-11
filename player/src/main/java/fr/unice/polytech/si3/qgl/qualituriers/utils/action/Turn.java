package fr.unice.polytech.si3.qgl.qualituriers.utils.action;

/**
 * @author Alexandre Arcil
 * @author CLODONG Yann
 */
public class Turn extends Action {

    private double rotation;

    public Turn(int sailorId, double rotation) {
        super(Actions.TURN, sailorId);
        this.rotation = rotation;
    }

    public void setRotation(double rotation) {
        if(-Math.PI/4 <= this.rotation && this.rotation <= Math.PI/4)
            this.rotation = rotation;
    }

    public double getRotation() {
        return rotation;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof Turn)) return false;
        var castedObj = (Turn)obj;
        return super.equals(obj) && castedObj.rotation == rotation;
    }
}
