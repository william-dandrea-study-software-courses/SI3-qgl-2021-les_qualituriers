package fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;

/**
 * Cette classe représente la perturbation autre bateau qui sera un obstacle pour notre bateau
 *
 * @author williamdandrea, Alexandre Arcil
 */

public class EnemyVisibleDeckEntity extends VisibleDeckEntity {

    private final int life;

    @JsonCreator
    public EnemyVisibleDeckEntity(@JsonProperty("position") Transform position,
                                  @JsonProperty("shape") Shape shape, @JsonProperty("life") int life) {
        super(VisibleDeckEntities.ENEMY, position, shape);
        this.life = life;
    }
}
