package fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Position;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;

/**
 * Cette classe represente la perturbation courant qui deviera le bateau
 *
 * @author williamdandrea
 */


public class CourantVisibleDeckEntity extends VisibleDeckEntity{

    private double strength;

    public CourantVisibleDeckEntity(Position position, Shape shape, double strength) {
        super(VisibleDeckEntities.COURANT, position, shape);
        this.strength = strength;
    }
}
