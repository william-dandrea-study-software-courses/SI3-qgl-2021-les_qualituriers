package fr.unice.polytech.si3.qgl.qualituriers.game;

import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.Deck;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.Wind;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.ReefVisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.VisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RoundInfoTest {

    RoundInfo roundInfo;

    @BeforeEach
    public void init() {
        this.roundInfo = new RoundInfo(
                new Boat(100, new Transform(10, 10, 0), "lol", new Deck(4, 5), new BoatEntity[0], new Rectangle(2, 3, 0)),
                new Wind(0, 10),
                new VisibleDeckEntity[]{new ReefVisibleDeckEntity(new Transform(10, 20, 0), new Circle(10))}
                );
    }

    @Test
    public void testGetter() {
        Boat boat = new Boat(100, new Transform(10, 10, 0), "lol", new Deck(4, 5), new BoatEntity[0], new Rectangle(2, 3, 0));
        Wind wind = new Wind(0, 10);
        VisibleDeckEntity[] visibleEntities = {new ReefVisibleDeckEntity(new Transform(10, 20, 0), new Circle(10))};
        assertEquals(boat, this.roundInfo.getShip());
        assertEquals(wind, this.roundInfo.getWind());
        assertArrayEquals(visibleEntities, this.roundInfo.getVisibleEntities());
    }

    @Test
    public void testEquals() {
        RoundInfo roundInfo1 = new RoundInfo(
                new Boat(100, new Transform(10, 10, 0), "lol", new Deck(4, 5), new BoatEntity[0], new Rectangle(2, 3, 0)),
                new Wind(0, 10),
                new VisibleDeckEntity[]{new ReefVisibleDeckEntity(new Transform(10, 20, 0), new Circle(10))}
        );
        RoundInfo roundInfo2 = new RoundInfo(
                new Boat(100, new Transform(10, 10, 0), "lol", new Deck(4, 5), new BoatEntity[0], new Rectangle(2, 3, 0)),
                new Wind(0, 10),
                new VisibleDeckEntity[]{new ReefVisibleDeckEntity(new Transform(10, 21, 0), new Circle(10))}
        );
        RoundInfo roundInfo3 = new RoundInfo(
                new Boat(100, new Transform(10, 10, 0), "lol", new Deck(4, 5), new BoatEntity[0], new Rectangle(2, 3, 0)),
                new Wind(Math.PI, 10),
                new VisibleDeckEntity[]{new ReefVisibleDeckEntity(new Transform(10, 20, 0), new Circle(10))}
        );
        RoundInfo roundInfo4 = new RoundInfo(
                new Boat(80, new Transform(10, 10, 0), "lol", new Deck(4, 5), new BoatEntity[0], new Rectangle(2, 3, 0)),
                new Wind(0, 10),
                new VisibleDeckEntity[]{new ReefVisibleDeckEntity(new Transform(10, 20, 0), new Circle(10))}
        );
        assertEquals(roundInfo1, this.roundInfo);
        assertNotEquals(roundInfo2, this.roundInfo);
        assertNotEquals(roundInfo3, this.roundInfo);
        assertNotEquals(roundInfo4, this.roundInfo);
        assertNotEquals(roundInfo, null);
        assertNotEquals(roundInfo, "test");
    }

    @Test
    public void testHashcode() {
        RoundInfo roundInfo1 = new RoundInfo(
                new Boat(100, new Transform(10, 10, 0), "lol", new Deck(4, 5), new BoatEntity[0], new Rectangle(2, 3, 0)),
                new Wind(0, 10),
                new VisibleDeckEntity[]{new ReefVisibleDeckEntity(new Transform(10, 20, 0), new Circle(10))}
        );
        RoundInfo roundInfo2 = new RoundInfo(
                new Boat(100, new Transform(10, 10, 0), "lol", new Deck(4, 5), new BoatEntity[0], new Rectangle(2, 3, 0)),
                new Wind(0, 10),
                new VisibleDeckEntity[]{new ReefVisibleDeckEntity(new Transform(10, 21, 0), new Circle(10))}
        );
        RoundInfo roundInfo3 = new RoundInfo(
                new Boat(100, new Transform(10, 10, 0), "lol", new Deck(4, 5), new BoatEntity[0], new Rectangle(2, 3, 0)),
                new Wind(Math.PI, 10),
                new VisibleDeckEntity[]{new ReefVisibleDeckEntity(new Transform(10, 20, 0), new Circle(10))}
        );
        RoundInfo roundInfo4 = new RoundInfo(
                new Boat(80, new Transform(10, 10, 0), "lol", new Deck(4, 5), new BoatEntity[0], new Rectangle(2, 3, 0)),
                new Wind(0, 10),
                new VisibleDeckEntity[]{new ReefVisibleDeckEntity(new Transform(10, 20, 0), new Circle(10))}
        );
        assertEquals(roundInfo1.hashCode(), this.roundInfo.hashCode());
        assertNotEquals(roundInfo2.hashCode(), this.roundInfo.hashCode());
        assertNotEquals(roundInfo3.hashCode(), this.roundInfo.hashCode());
        assertNotEquals(roundInfo4.hashCode(), this.roundInfo.hashCode());
    }

}
