package fr.unice.polytech.si3.qgl.qualituriers.entity.boat;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.qualituriers.Deck;
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
    private BoatEntity[] entitys;
    private Shapes shapes;

    public Boat(int life, Position position, String name, Deck deck, BoatEntity[] entitys, Shapes shapes) {
        this.life = life;
        this.position = position;
        this.name = name;
        this.deck = deck;
        this.entitys = entitys;
        this.shapes = shapes;
    }

    @JsonCreator
    public Boat(@JsonProperty("life") int life,@JsonProperty("x") int posX,@JsonProperty("y") int posY,@JsonProperty("name") String name,@JsonProperty("width") int width,@JsonProperty("length") int length){
        this.life = life;
        this.position = new Position(posX, posY, 0);
        this.name = name;
        this.deck = new Deck(width, length);
    }

    public String getName() {
        return name;
    }
    public int getLife() { return life; }
    public Position getPosition() { return position; }
    public Deck getDeck() { return deck; }
    public BoatEntity[] getEntities() { return entitys; }
    public Shapes getShapes() { return shapes; }
}
