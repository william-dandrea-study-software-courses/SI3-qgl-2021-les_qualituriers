package fr.unice.polytech.si3.qgl.qualituriers.entity.boat;

/**
 * Cette classe represente l'élément Canon qui sera placé dans un bateau
 *
 * @author williamdandrea
 */

public class CanonBoatEntity extends BoatEntity {

    private final boolean loaded;
    private final double angle;

    public CanonBoatEntity(int x, int y, boolean loaded, double angle) {
        super(BoatEntities.CANON.getType(), x, y);
        this.loaded = loaded;
        this.angle = angle;
    }
}
