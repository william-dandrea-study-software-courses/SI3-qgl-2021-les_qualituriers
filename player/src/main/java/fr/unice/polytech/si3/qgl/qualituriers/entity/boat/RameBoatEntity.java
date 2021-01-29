package fr.unice.polytech.si3.qgl.qualituriers.entity.boat;

/**
 * Cette classe represente l'élément Rame qui sera placé dans un bateau
 *
 * @author williamdandrea
 */
public class RameBoatEntity extends BoatEntity {

    public RameBoatEntity(int x, int y) {
        super(BoatEntities.RAME.getType(), x, y);
    }
}
