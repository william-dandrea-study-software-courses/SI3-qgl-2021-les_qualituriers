package fr.unice.polytech.si3.qgl.qualituriers.entity.boat;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.qualituriers.Deck;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.turnboat.TurnBoat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.turnboat.turnboatutils.*;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Moving;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Oar;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import org.w3c.dom.ls.LSOutput;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Cette classe a pour objectif de décrire un bateau, le bateau pourra bouger mais aussi faire plusieurs actions
 * en fonction de l'emplacement des entity
 *
 * @author williamdandrea, Alexandre Arcil
 * @author CLODONG Yann
 */
public class Boat {

    private int life;
    private Transform transform;
    private final String name;
    private final Deck deck;
    private BoatEntity[] entities;
    private final Shape shape;
    private List<Action> actionsToDo;

    @JsonIgnore
    private Captain captain;
    @JsonIgnore
    private Foreman foreman;

    List<Marin> sailors;




    @JsonCreator
    public Boat(@JsonProperty("life") int life, @JsonProperty("position") Transform position,
                @JsonProperty("name") String name, @JsonProperty("deck") Deck deck,
                @JsonProperty("entities") BoatEntity[] entities, @JsonProperty("shape") Shape shape) {
        this.life = life;
        this.transform = position;
        this.name = name;
        this.deck = deck;
        this.entities = entities;
        this.shape = shape;
        this.actionsToDo = new ArrayList<>();


        this.captain = new Captain(this);
        this.foreman = new Foreman(this);
    }

    public List<Action> moveBoatInLine() {
        TurnBoat turnBoat = new TurnBoat(0.0, this, getSailors());
        return turnBoat.moveBoatInLine();
    }

    public List<Action> turnBoat(double angleWeWantToTurn) {
        TurnBoat turnBoat = new TurnBoat(angleWeWantToTurn, this, getSailors());
        return turnBoat.turnBoat();
    }




    /**
     * Donne la liste d'action a chaque tour permettant d'achever l'objectif fixe au capitaine
     * @return La liste d'action.
     */
    public List<Action> playTurn() {
        captain.decide();
        foreman.decide();

        List<Action> actions = new ArrayList<>();

        for(var sailor : getSailors()) {
            actions.addAll(sailor.actionDoneDuringTurn());
        }

        return actions;
    }











    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof Boat)) return false;
        var castedObj = (Boat)obj;
        return castedObj.name.equals(name);
    }


    public String getName() { return name; }
    public int getLife() { return life; }
    public Transform getPosition() { return transform; }
    public Deck getDeck() { return deck; }
    public BoatEntity[] getEntities() { return entities; }
    public List<Action> getActionsToDo() { return actionsToDo; }
    public Shape getShape() { return shape; }
    public void setLife(int life) { this.life = life; }
    public void setTransform(Transform transform) { this.transform = transform; }
    public void setEntities(BoatEntity[] entities) { this.entities = entities; }

    public void setSailors(List<Marin> sailors) {
        this.sailors = sailors;
        this.foreman.setSailors(sailors);
    }

    @JsonIgnore
    public List<Marin> getSailors() {
        if(sailors == null) return new ArrayList<>();
        return List.copyOf(sailors);
    }

    @JsonIgnore
    public Foreman getForeman() { return foreman; }

    @JsonIgnore
    public Captain getCaptain() { return captain; }




}
