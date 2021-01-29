package fr.unice.polytech.si3.qgl.qualituriers.entity.boat;

/**
 * Cette classe represente l'élément VIGIE qui sera placé dans un bateau
 *
 * @author williamdandrea
 */


public class VigieBoatEntity extends BoatEntity {

    public VigieBoatEntity(int x, int y) {
        super(BoatEntities.VIGIE.getType(), x, y);
    }
}
