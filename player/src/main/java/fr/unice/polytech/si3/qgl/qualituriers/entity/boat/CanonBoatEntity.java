package fr.unice.polytech.si3.qgl.qualituriers.entity.boat;

/**
 * Cette classe represente l'élément Canon qui sera placé dans un bateau
 *
 * @author williamdandrea
 */

public class CanonBoatEntity extends BoatEntity {

    private boolean loaded;
    private double angle;

    public CanonBoatEntity(int x, int y, boolean loaded, double angle) {
        super(BoatEntities.CANON, x, y);
        this.loaded = loaded;
        this.angle = angle;
    }
}
