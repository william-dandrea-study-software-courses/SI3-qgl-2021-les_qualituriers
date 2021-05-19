package fr.unice.polytech.si3.qgl.qualituriers.engine.races;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.Deck;

public class WebRunnerBoat {
    private final String name;
    private final Deck deck;
    private final BoatEntity[] entities;
    private final int life;

    @JsonCreator
    public WebRunnerBoat(@JsonProperty("name") String name, @JsonProperty("deck") Deck deck, @JsonProperty("entities") BoatEntity[] entities, @JsonProperty("life") int life) {
        this.name = name;
        this.deck = deck;
        this.entities = entities;
        this.life = life;
    }

    public String getName() {
        return name;
    }

    public Deck getDeck() {
        return deck;
    }

    public BoatEntity[] getEntities() {
        return entities;
    }

    public int getLife() {
        return life;
    }
}
