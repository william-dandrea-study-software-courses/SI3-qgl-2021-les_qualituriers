package fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;

import java.util.Objects;

/**
 * Cette classe représente la perturbation autre bateau qui sera un obstacle pour notre bateau
 *
 * @author williamdandrea, Alexandre Arcil
 * @author CLODONG Yann
 */

public class EnemyVisibleDeckEntity extends VisibleDeckEntity {

    private final int life;

    @JsonCreator
    public EnemyVisibleDeckEntity(@JsonProperty("position") Transform position,
                                  @JsonProperty("shape") Shape shape, @JsonProperty("life") int life) {
        super(VisibleDeckEntities.ENEMY, position, shape);
        this.life = life;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof EnemyVisibleDeckEntity)) return false;
        var castedObj = (EnemyVisibleDeckEntity)obj;
        return super.equals(obj) && castedObj.life == life;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), life);
    }

    @Override
    public String toString() {
        return "EnemyVisibleDeckEntity{" +
                "life=" + life +
                ", type=" + type +
                ", positionableShape=" + positionableShape +
                '}';
    }
}
