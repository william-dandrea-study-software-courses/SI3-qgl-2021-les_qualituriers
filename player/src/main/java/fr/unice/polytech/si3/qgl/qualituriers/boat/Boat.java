package fr.unice.polytech.si3.qgl.qualituriers.boat;

import fr.unice.polytech.si3.qgl.qualituriers.Deck;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Position;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Shape;

public class Boat {

    private int life;
    private Position position;
    private String name;
    private Deck deck;
    private Entities entities;
    private Shape shape;

    public Boat(int life, Position position, String name, Deck deck, Entities entities, Shape shape) {
        this.life = life;
        this.position = position;
        this.name = name;
        this.deck = deck;
        this.entities = entities;
        this.shape = shape;
    }
}
