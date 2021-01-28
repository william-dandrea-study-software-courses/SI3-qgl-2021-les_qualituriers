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
 * TODO : 28/01/2020 : essayer de revoir le constructeur pour parser des json node plutot que chaques field un par un.
 */
public class Boat {

    private final int life;
    private final Position position;
    private final String name;
    private final Deck deck;
    private final BoatEntity[] entities;
    private final Shapes shapes;


    public Boat(int life, Position position, String name, Deck deck, BoatEntity[] entities, Shapes shapes) {
        this.life = life;
        this.position = position;
        this.name = name;
        this.deck = deck;
        this.entities = entities;
        this.shapes = shapes;
    }


    @JsonCreator
    public Boat(@JsonProperty("life") int life,@JsonProperty("x") int posX,@JsonProperty("y") int posY,@JsonProperty("name") String name,@JsonProperty("width") int width,@JsonProperty("length") int length){
        this.life = life;
        this.position = new Position(posX, posY, 0);
        this.name = name;
        this.deck = new Deck(width, length);
        this.entities = new BoatEntity[0];
        this.shapes = Shapes.RECTANGLE;
    }

    public String getName() {
        return name;
    }
    public int getLife() { return life; }
    public Position getPosition() { return position; }
    public Deck getDeck() { return deck; }
    public BoatEntity[] getEntities() { return entities; }
    public Shapes getShapes() { return shapes; }
}
