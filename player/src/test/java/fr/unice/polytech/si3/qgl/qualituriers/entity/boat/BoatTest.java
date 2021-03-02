package fr.unice.polytech.si3.qgl.qualituriers.entity.boat;

import fr.unice.polytech.si3.qgl.qualituriers.Deck;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.CanonBoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.OarBoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


class BoatTest {

    Boat boat;
    Transform transform;
    BoatEntity[] boatEntities;
    Shape shape;

    @BeforeEach
    void setup() {
        this.transform = new Transform(5, 4, 0);
        this.boatEntities = new BoatEntity[]{new OarBoatEntity(0, 1), new OarBoatEntity(1, 0)};
        this.shape = new Rectangle(4, 5, 0);
        this.boat = new Boat(100, transform, "bateau", new Deck(4, 5),
                boatEntities, shape);
    }

    @Test
    public void testGetter(){
        assertEquals(100, boat.getLife());
        assertEquals("bateau", this.boat.getName());
        assertEquals(shape, boat.getShape());
        assertEquals(0, boat.getSailors().size());
        List<Marin> marins = Arrays.asList(new Marin(0, 1, 1, "toto"), new Marin(1, 2, 2, "tata"));
        boat.sailors = marins;
        assertEquals(marins, boat.getSailors());
    }

    @Test
    public void testSetter(){
        boat.setLife(50);
        Transform transform = new Transform(1, 2, 0);
        boat.setPosition(transform);
        assertEquals(50, boat.getLife());
        assertEquals(transform, boat.getPosition());
    }

    @Test
    public void testEquals() {
        Boat boat2 = new Boat(100, transform, "bateau", new Deck(4, 5),
                boatEntities, shape);
        Boat boat3 = new Boat(100, transform, "bateau", new Deck(4, 5),
                boatEntities, new Circle(50));
        Boat boat4 = new Boat(100, transform, "bateau", new Deck(4, 5),
                new BoatEntity[] {new CanonBoatEntity(1, 1, false, 0)}, shape);
        Boat boat5 = new Boat(100, transform, "bateau", new Deck(4, 6),
                boatEntities, shape);
        Boat boat6 = new Boat(100, transform, "bato", new Deck(4, 5),
                boatEntities, shape);
        Boat boat7 = new Boat(99, transform, "bateau", new Deck(4, 5),
                boatEntities, shape);
        assertEquals(boat2, this.boat);
        assertNotEquals(boat3, this.boat);
        assertNotEquals(boat4, this.boat);
        assertNotEquals(boat5, this.boat);
        assertNotEquals(boat6, this.boat);
        assertNotEquals(boat7, this.boat);
        assertNotEquals(boat, null);
        assertNotEquals(boat2, "test");
    }

    @Test
    public void testHashcode() {
        Boat boat2 = new Boat(100, transform, "bateau", new Deck(4, 5),
                boatEntities, shape);
        Boat boat3 = new Boat(100, transform, "bateau", new Deck(4, 5),
                boatEntities, new Circle(50));
        Boat boat4 = new Boat(100, transform, "bateau", new Deck(4, 5),
                new BoatEntity[] {new CanonBoatEntity(1, 1, false, 0)}, shape);
        Boat boat5 = new Boat(100, transform, "bateau", new Deck(4, 6),
                boatEntities, shape);
        Boat boat6 = new Boat(100, transform, "bato", new Deck(4, 5),
                boatEntities, shape);
        Boat boat7 = new Boat(99, transform, "bateau", new Deck(4, 5),
                boatEntities, shape);
        assertEquals(boat2.hashCode(), this.boat.hashCode());
        assertNotEquals(boat3.hashCode(), this.boat.hashCode());
        assertNotEquals(boat4.hashCode(), this.boat.hashCode());
        assertNotEquals(boat5.hashCode(), this.boat.hashCode());
        assertNotEquals(boat6.hashCode(), this.boat.hashCode());
        assertNotEquals(boat7.hashCode(), this.boat.hashCode());
    }


}
