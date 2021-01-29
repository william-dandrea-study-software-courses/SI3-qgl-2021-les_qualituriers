package fr.unice.polytech.si3.qgl.qualituriers.entity.boat;

/**
 * Cette classe represente l'élément Gouvernail qui sera placé dans un bateau
 *
 * @author williamdandrea
 */


public class GouvernailBoatEntity extends BoatEntity {

    public GouvernailBoatEntity(int x, int y) {
        super(BoatEntities.GOUVERNAIL.getType(), x, y);
    }
}
