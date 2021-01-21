package fr.unice.polytech.si3.qgl.qualituriers.boat.boatentities;

/**
 * Cette classe a pour objectif de contenir tout ce qui est commun aux différents types d'entités dans le bateau
 *
 * @author williamdandrea
 */
public class BoatEntity {

    protected BoatEntities type;
    protected int x;
    protected int y;

    public BoatEntity(BoatEntities type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }
}
