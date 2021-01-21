package fr.unice.polytech.si3.qgl.qualituriers.boat.boatentities;

/**
 * Cette classe represente l'élément Canon qui sera placé dans un bateau
 *
 * @author williamdandrea
 */

public class CanonEntity extends Entity{

    private boolean loaded;
    private double angle;

    public CanonEntity(int x, int y, boolean loaded, double angle) {
        super(Entities.CANON, x, y);
        this.loaded = loaded;
        this.angle = angle;
    }
}
