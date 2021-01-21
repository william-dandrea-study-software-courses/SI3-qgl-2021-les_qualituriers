package fr.unice.polytech.si3.qgl.qualituriers.deckentities.deckvisibleentities;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Position;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;

/**
 * Cette classe represente la perturbation autre bateau qui sera un obstacle pour notre bateau
 *
 * @author williamdandrea
 */

public class AutreBateauVisibleDeckEntity extends VisibleDeckEntity {

    private int life;

    public AutreBateauVisibleDeckEntity(Position position, Shape shape, int life) {
        super(VisibleDeckEntities.AUTREBATEAU, position, shape);
        this.life = life;
    }
}
