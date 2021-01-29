package fr.unice.polytech.si3.qgl.qualituriers.entity.boat;

/**
 * Cette classe represente l'élément Voile qui sera placé dans un bateau
 *
 * @author williamdandrea
 */

public class VoileBoatEntity extends BoatEntity {

    private final boolean opened;

    public VoileBoatEntity(int x, int y, boolean opened) {
        super(BoatEntities.VOILE.getType(), x, y);
        this.opened = opened;
    }
}
