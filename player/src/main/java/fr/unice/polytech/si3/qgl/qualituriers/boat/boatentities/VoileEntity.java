package fr.unice.polytech.si3.qgl.qualituriers.boat.boatentities;

/**
 * Cette classe represente l'élément Voile qui sera placé dans un bateau
 *
 * @author williamdandrea
 */

public class VoileEntity extends Entity{

    private boolean openned;

    public VoileEntity(int x, int y, boolean openned) {
        super(Entities.VOILE, x, y);
        this.openned = openned;
    }
}
