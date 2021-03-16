package fr.unice.polytech.si3.qgl.qualituriers.entity.boat;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.qualituriers.Deck;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.HeadQuarter;
import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
import fr.unice.polytech.si3.qgl.qualituriers.utils.logger.ILogger;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableShape;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableShapeFactory;

import java.util.*;

/**
 * Cette classe a pour objectif de décrire un bateau, le bateau pourra bouger mais aussi faire plusieurs actions
 * en fonction de l'emplacement des entity
 *
 * @author williamdandrea, Alexandre Arcil
 * @author CLODONG Yann
 */
public class Boat {

    private int life;
    private final String name;
    private final Deck deck;
    private BoatEntity[] entities;
    private final List<Action> actionsToDo;

    @JsonIgnore
    private final PositionableShape<? extends Shape> positionableShape;

    List<Marin> sailors;

    @JsonCreator
    public Boat(@JsonProperty("life") int life, @JsonProperty("position") Transform position,
                @JsonProperty("name") String name, @JsonProperty("deck") Deck deck,
                @JsonProperty("entities") BoatEntity[] entities, @JsonProperty("shape") Shape shape) {
        this.life = life;
        this.name = name;
        this.deck = deck;
        this.entities = entities;
        this.actionsToDo = new ArrayList<>();
        this.positionableShape = PositionableShapeFactory.getPositionable(shape, position);
    }



    public List<Action> moveBoatDistanceStrategy2(Transform checkPoint, GameInfo gameInfo,  ILogger logger) {

        HeadQuarter headQuarter = new HeadQuarter(this, getSailors(), checkPoint, gameInfo);
        List<Action> actions = headQuarter.playTurn();
        setSailors(headQuarter.getSailors());


        return actions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Boat boat = (Boat) o;
        return life == boat.life && Objects.equals(name, boat.name) && Objects.equals(deck, boat.deck)
                && Arrays.equals(entities, boat.entities) && Objects.equals(actionsToDo, boat.actionsToDo)
                && Objects.equals(positionableShape, boat.positionableShape) && Objects.equals(sailors, boat.sailors);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(life, name, deck, actionsToDo, positionableShape, sailors);
        result = 31 * result + Arrays.hashCode(entities);
        return result;
    }

    public String getName() { return name; }
    public int getLife() { return life; }
    public Transform getPosition() { return this.positionableShape.getTransform(); }
    public Deck getDeck() { return deck; }
    public BoatEntity[] getEntities() { return entities; }
    public Shape getShape() { return this.positionableShape.getShape(); }
    public void setLife(int life) { this.life = life; }
    public void setPosition(Transform position) { this.positionableShape.setTransform(position);}
    public void setEntities(BoatEntity[] entities) { this.entities = entities; }

    public void setSailors(List<Marin> sailors) {
        this.sailors = sailors;
    }

    @JsonIgnore
    public PositionableShape<? extends Shape> getPositionableShape() {
        return positionableShape;
    }

    @JsonIgnore
    public List<Marin> getSailors() {
        if(sailors == null) return Collections.emptyList();
        return List.copyOf(sailors);
    }

}
