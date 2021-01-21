package fr.unice.polytech.si3.qgl.qualituriers.deckentities.deckvisibleentities;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Position;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;


/**
 * Cette classe represente les elements commun aux différentes perturbations visibles que nous aurons sur le deck
 *
 * @author williamdandrea
 */
public class VisibleDeckEntity {

    protected VisibleDeckEntities type;
    protected Position position;
    protected Shape shape;

    public VisibleDeckEntity(VisibleDeckEntities type, Position position, Shape shape) {
        this.type = type;
        this.position = position;
        this.shape = shape;
    }
}
