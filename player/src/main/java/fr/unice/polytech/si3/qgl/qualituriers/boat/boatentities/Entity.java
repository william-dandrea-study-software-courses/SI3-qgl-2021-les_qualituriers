package fr.unice.polytech.si3.qgl.qualituriers.boat.boatentities;

/**
 * Cette classe a pour objectif de contenir tout ce qui est commun aux différents types d'entités dans le bateau
 *
 * @author williamdandrea
 */
public class Entity {

    protected Entities type;
    protected int x;
    protected int y;

    public Entity(Entities type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }
}
