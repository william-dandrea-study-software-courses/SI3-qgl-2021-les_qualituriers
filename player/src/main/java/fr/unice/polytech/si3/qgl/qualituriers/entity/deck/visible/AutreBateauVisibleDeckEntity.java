package fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;

/**
 * Cette classe represente la perturbation autre bateau qui sera un obstacle pour notre bateau
 *
 * @author williamdandrea
 */

public class AutreBateauVisibleDeckEntity extends VisibleDeckEntity {

    private int life;

    public AutreBateauVisibleDeckEntity(Transform position, Shape shape, int life) {
        //super(VisibleDeckEntities.AUTREBATEAU, position, shape);
        this.life = life;
    }
}
