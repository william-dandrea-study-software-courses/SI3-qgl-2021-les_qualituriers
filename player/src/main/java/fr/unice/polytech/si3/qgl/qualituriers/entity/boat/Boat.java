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
    private List<Marin> sailors;


    // Interne a la methode
    private List<BoatEntity> listOfOars;
    private int numberOfOars;
    private List<BoatEntity> listOfOarsAtBabord;
    private List<BoatEntity> listOfOarsAtTribord;
    private List<Marin> sailorsOnOarAtTribord;
    private List<Marin> sailorsOnOarAtBabord;
    private List<Marin> sailorsOnOar;

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

        numberOfOars = 0;

        sailors = new ArrayList<>();
        sailorsOnOar = new ArrayList<>();
        sailorsOnOarAtTribord = new ArrayList<>();
        sailorsOnOarAtBabord = new ArrayList<>();
        listOfOarsAtBabord = new ArrayList<>();
        listOfOarsAtTribord = new ArrayList<>();
        listOfOars = new ArrayList<>();
        sailorsOnOarAtTribord = new ArrayList<>();
        sailorsOnOarAtBabord = new ArrayList<>();
        sailorsOnOar = new ArrayList<>();

        List<BoatEntity> listOfOars = Arrays.stream(entities).collect(Collectors.toList()).stream().filter(boatEntity -> boatEntity.getType().equals(BoatEntities.OAR)).collect(Collectors.toList());
        numberOfOars = listOfOars.size();
        listOfOarsAtBabord = listOfOars.stream().filter(boatEntity -> boatEntity.getY() == 0).collect(Collectors.toList());
        listOfOarsAtTribord = listOfOars.stream().filter(boatEntity -> boatEntity.getY() == getDeck().getWidth()-1).collect(Collectors.toList());

        generateListSailorsOnOar();

        //this.captain = new Captain(this);
        //this.foreman = new Foreman(this);
    }

    public List<Action> moveBoatInLine() {
        MoveBoat moveBoat = new MoveBoat(getLife(), getTransform(), getName(),getDeck(),getEntities(),getShape());
        return moveBoat.moveBoatInLine();
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

        for(var sailor : sailors) {
            actions.addAll(sailor.actionDoneDuringTurn());
        }

        return actions;
    }







    /**
     * Cette méthode va actualiser les variables sailorsOnOar / sailorsOnOarAtBabord / sailorsOnOarAtTribord en
     * recherchant le nombre de marin qui sont actuellement positionnés sur les rames
     */
    void generateListSailorsOnOar() {

        if (!listOfOarsAtBabord.isEmpty()) { listOfOarsAtBabord.clear(); }
        if (!listOfOarsAtTribord.isEmpty()) { listOfOarsAtTribord.clear(); }
        if (!listOfOars.isEmpty()) { listOfOars.clear(); }



        for (Marin sailor: sailors) {

            for(BoatEntity rightEntity : listOfOarsAtTribord) {
                if (rightEntity.getX() == sailor.getX() && rightEntity.getY() == sailor.getY()) {

                    sailorsOnOarAtTribord.add(sailor);
                    sailorsOnOar.add(sailor);
                }
            }


            for(BoatEntity leftEntity : listOfOarsAtBabord) {
                if (leftEntity.getX() == sailor.getX() && leftEntity.getY() == sailor.getY()) {

                    sailorsOnOarAtBabord.add(sailor);
                    sailorsOnOar.add(sailor);
                }
            }
        }
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
    public Transform getTransform() { return transform; }
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


    public List<BoatEntity> getListOfOars() {
        return listOfOars;
    }

    public int getNumberOfOars() {
        return numberOfOars;
    }

    public List<BoatEntity> getListOfOarsAtBabord() {
        return listOfOarsAtBabord;
    }

    public List<BoatEntity> getListOfOarsAtTribord() {
        return listOfOarsAtTribord;
    }

    public List<Marin> getSailorsOnOarAtTribord() {
        return sailorsOnOarAtTribord;
    }

    public List<Marin> getSailorsOnOarAtBabord() {
        return sailorsOnOarAtBabord;
    }

    public List<Marin> getSailorsOnOar() {
        return sailorsOnOar;
    }
}
