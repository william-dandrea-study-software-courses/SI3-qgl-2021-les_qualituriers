package fr.unice.polytech.si3.qgl.qualituriers.deckentities.deckvisibleentities;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Position;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;

/**
 * Cette classe represente la perturbation recif qui sera un obstacle pour notre bateau
 *
 * @author williamdandrea
 */

public class RecifVisibleDeckEntity extends VisibleDeckEntity {

    public RecifVisibleDeckEntity(Position position, Shape shape) {
        super(VisibleDeckEntities.RECIF, position, shape);
    }
}
