package fr.unice.polytech.si3.qgl.qualituriers.entity.boat;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.qualituriers.Deck;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.HeadQuarter;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
import fr.unice.polytech.si3.qgl.qualituriers.utils.logger.ILogger;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableShape;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableShapeFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

    public List<Action> moveBoatDistanceStrategy(Transform checkPoint, ILogger logger) {
        /*SortedDispositionDistanceStrategy distanceStrategy = new SortedDispositionDistanceStrategy();
        List<Disposition> listOfDispositions = distanceStrategy.getIdealDisposition(checkPoint, this);
        MoveBoatDistanceStrategy moveBoatStrategy = new MoveBoatDistanceStrategy(this, listOfDispositions, sailors.toArray(new Marin[0]));
        List<Action> actions = moveBoatStrategy.moveBoat(logger);
        setSailors(Arrays.asList(moveBoatStrategy.getSailors().clone()));*/
        return new ArrayList<>();
    }

    public List<Action> moveBoatDistanceStrategy2(Transform checkPoint,  ILogger logger) {

        HeadQuarter headQuarter = new HeadQuarter(this, getSailors(), checkPoint);
        List<Action> actions = headQuarter.playTurn();
        setSailors(headQuarter.getSailors());


        return actions;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof Boat)) return false;
        var castedObj = (Boat)obj;
        return castedObj.name.equals(name);
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
        if(sailors == null) return new ArrayList<>();
        return List.copyOf(sailors);
    }

}
