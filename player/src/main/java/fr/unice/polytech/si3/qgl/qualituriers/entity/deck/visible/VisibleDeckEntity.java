package fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.unice.polytech.si3.qgl.qualituriers.game.goal.BattleGoal;
import fr.unice.polytech.si3.qgl.qualituriers.game.goal.RegattaGoal;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;


/**
 * Cette classe represente les elements commun aux différentes perturbations visibles que nous aurons sur le deck
 *
 * @author williamdandrea
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = Void.class)
@JsonSubTypes({
        @JsonSubTypes.Type(name = "REGATTA", value = RegattaGoal.class),
        @JsonSubTypes.Type(name = "BATTLE", value = BattleGoal.class)
})



public class VisibleDeckEntity {

    protected String type;
    protected Transform position;
    protected Shape shape;

    /*public VisibleDeckEntity(VisibleDeckEntities type, Transform position, Shape shape) {
        this.type = type;
        this.position = position;
        this.shape = shape;
    }*/
}
