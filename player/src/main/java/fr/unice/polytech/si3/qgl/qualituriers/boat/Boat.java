package fr.unice.polytech.si3.qgl.qualituriers.boat;

import fr.unice.polytech.si3.qgl.qualituriers.Deck;
import fr.unice.polytech.si3.qgl.qualituriers.boat.boatentities.BoatEntities;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Position;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shapes;

/**
 * Cette classe a pour objectif de décrire un bateau, le bateau pourra bouger mais aussi faire plusieurs actions
 * en fonction de l'emplacement des entity
 *
 * @author williamdandrea
 */
public class Boat {

    private int life;
    private Position position;
    private String name;
    private Deck deck;
    private BoatEntities[] entities;
    private Shapes shapes;

    public Boat(int life, Position position, String name, Deck deck, BoatEntities[] entities, Shapes shapes) {
        this.life = life;
        this.position = position;
        this.name = name;
        this.deck = deck;
        this.entities = entities;
        this.shapes = shapes;
    }
}
