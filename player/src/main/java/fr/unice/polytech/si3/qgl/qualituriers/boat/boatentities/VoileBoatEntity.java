package fr.unice.polytech.si3.qgl.qualituriers.boat.boatentities;

/**
 * Cette classe represente l'élément Voile qui sera placé dans un bateau
 *
 * @author williamdandrea
 */

public class VoileBoatEntity extends BoatEntity {

    private boolean openned;

    public VoileBoatEntity(int x, int y, boolean openned) {
        super(BoatEntities.VOILE, x, y);
        this.openned = openned;
    }
}
