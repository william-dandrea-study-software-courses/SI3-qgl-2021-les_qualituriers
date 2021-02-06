package fr.unice.polytech.si3.qgl.qualituriers.entity.boat;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.qualituriers.Deck;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Moving;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Cette classe a pour objectif de décrire un bateau, le bateau pourra bouger mais aussi faire plusieurs actions
 * en fonction de l'emplacement des entity
 *
 * @author williamdandrea, Alexandre Arcil
 * TODO : 28/01/2020 : essayer de revoir le constructeur pour parser des json node plutot que chaques field un par un.
 */
public class Boat {

    private final int life;
    private final Transform transform;
    private final String name;
    private final Deck deck;
    private final BoatEntity[] entities;
    private final Shape shape;

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
    }

    public String getName() {
        return name;
    }
    public int getLife() { return life; }
    public Transform getTransform() { return transform; }
    public Deck getDeck() { return deck; }
    public BoatEntity[] getEntities() { return entities; }

    public Shape getShape() {
        return shape;
    }


    /**
     * Strategie :
     * - Regarder ou sont les marins
     * -
     * @param orientationValue
     */
    void turnBoat(double orientationValue) {

        List<BoatEntity> listOfSailors = new ArrayList<>();

        for (BoatEntity entity: entities) {

            if (entity.type.equals(BoatEntities.SAIL)) {
                listOfSailors.add(entity);
            }
        }
    }




    /**
     * This method move the sailor at a certain position, this method also verify if we can move the sailor at this
     * position. Template of the JSON output:
     * {
     *         "sailorId": 1,
     *         "type": "MOVING",
     *         "xdistance": 0,
     *         "ydistance": 0
     * }
     * @param sailor
     * @param xDistance
     * @param yDistance
     * @return
     */
    public Optional<String> moveSailorSomewhere(Marin sailor, int xDistance, int yDistance) {

        Deck boatDeck = deck;
        int deckY = boatDeck.getLength();   // Hauteur du bateau
        int deckX = boatDeck.getWidth();    // Largeur du bateau

        if (xDistance > deckX || yDistance > deckY) {
            return Optional.empty();
        }

        ObjectMapper objectMapper = new ObjectMapper();

        Moving moving = new Moving(sailor.getId() ,xDistance, yDistance);
        String stringMoving = null;

        try {
            stringMoving = objectMapper.writeValueAsString(moving);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        if (stringMoving != null) {
            return Optional.of(stringMoving);
        }
        return Optional.empty();
    }
}
