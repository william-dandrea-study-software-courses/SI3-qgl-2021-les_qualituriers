package fr.unice.polytech.si3.qgl.qualituriers.game;

import fr.unice.polytech.si3.qgl.qualituriers.Deck;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.Wind;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.StreamVisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.VisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.game.goal.BattleGoal;
import fr.unice.polytech.si3.qgl.qualituriers.game.goal.RegattaGoal;
import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class GameInfoTest {

    GameInfo gameInfo;

    private VisibleDeckEntity streamEntity;
    private Map<Integer, VisibleDeckEntity> seaEntities;

    @BeforeEach
    public  void init() {

        streamEntity = new StreamVisibleDeckEntity(new Transform(0,0,0), new Rectangle(1,1,0), 0);
        seaEntities = Map.ofEntries(Map.entry(0, streamEntity));
        this.gameInfo = new GameInfo(
                new BattleGoal(),
                new Boat(100, new Transform(10, 10, 0), "lol", new Deck(4, 5), new BoatEntity[0], new Rectangle(2, 3, 0)),
                new Marin[]{new Marin(0, 1, 1, "sdfsdf")},
                2,
                new Wind(0, 0),
                seaEntities);

    }

    @Test
    public void testSetter() {
        Boat boat = new Boat(100, new Transform(10, 10, 0), "lol", new Deck(4, 5), new BoatEntity[0], new Rectangle(3, 2, 0));
        Marin[] sailors = new Marin[]{new Marin(0, 0, 0, "sous-chef")};
        int shipCount = 3;
        this.gameInfo.setShip(boat);
        assertEquals(gameInfo.getShip(), boat);
        this.gameInfo.setSailors(sailors);
        assertArrayEquals(sailors, this.gameInfo.getSailors());
        this.gameInfo.setShipCount(shipCount);
        assertEquals(shipCount, this.gameInfo.getShipCount());

    }

    @Test
    public void testEquals() {
        GameInfo gameInfo1 = new GameInfo(new BattleGoal(),
                new Boat(100, new Transform(10, 10, 0), "lol", new Deck(4, 5), new BoatEntity[0], new Rectangle(2, 3, 0)),
                new Marin[]{new Marin(0, 1, 1, "sdfsdf")},
                2,
                new Wind(0, 0),
                seaEntities);
        GameInfo gameInfo2 = new GameInfo(new BattleGoal(),
                new Boat(100, new Transform(10, 10, 0), "lol", new Deck(4, 5), new BoatEntity[0], new Rectangle(2, 3, 0)),
                new Marin[]{new Marin(0, 1, 1, "sdfsdf")},
                3,
                new Wind(0, 0),
                seaEntities);

        GameInfo gameInfo3 = new GameInfo(new BattleGoal(),
                new Boat(100, new Transform(10, 10, 0), "lol", new Deck(4, 5), new BoatEntity[0], new Rectangle(2, 3, 0)),
                new Marin[]{new Marin(1, 1, 1, "sdfsdf")},
                2,
                new Wind(0, 0),
                seaEntities);

        GameInfo gameInfo4 = new GameInfo(new BattleGoal(),
                new Boat(110, new Transform(10, 10, 0), "lol", new Deck(4, 5), new BoatEntity[0], new Rectangle(2, 3, 0)),
                new Marin[]{new Marin(0, 1, 1, "sdfsdf")},
                2,
                new Wind(0, 0),
                seaEntities);
        GameInfo gameInfo5 = new GameInfo(new RegattaGoal(new CheckPoint[]{new CheckPoint(new Transform(50, 50, 0), new Circle(50))}),
                new Boat(100, new Transform(10, 10, 0), "lol", new Deck(4, 5), new BoatEntity[0], new Rectangle(2, 3, 0)),
                new Marin[]{new Marin(0, 1, 1, "sdfsdf")},
                2,
                new Wind(0, 0),
                seaEntities);
        assertEquals(gameInfo1, this.gameInfo);
        assertNotEquals(gameInfo2, this.gameInfo);
        assertNotEquals(gameInfo3, this.gameInfo);
        assertNotEquals(gameInfo4, this.gameInfo);
        assertNotEquals(gameInfo5, this.gameInfo);
        assertNotEquals(gameInfo, null);
        assertNotEquals(gameInfo, "test");
    }

    @Test
    public void testHashcode() {
        GameInfo gameInfo1 = new GameInfo(new BattleGoal(),
                new Boat(100, new Transform(10, 10, 0), "lol", new Deck(4, 5), new BoatEntity[0], new Rectangle(2, 3, 0)),
                new Marin[]{new Marin(0, 1, 1, "sdfsdf")},
                2,
                new Wind(0, 0),
                seaEntities);
        GameInfo gameInfo2 = new GameInfo(new BattleGoal(),
                new Boat(100, new Transform(10, 10, 0), "lol", new Deck(4, 5), new BoatEntity[0], new Rectangle(2, 3, 0)),
                new Marin[]{new Marin(0, 1, 1, "sdfsdf")},
                3, new Wind(0, 0),
                seaEntities);
        GameInfo gameInfo3 = new GameInfo(new BattleGoal(),
                new Boat(100, new Transform(10, 10, 0), "lol", new Deck(4, 5), new BoatEntity[0], new Rectangle(2, 3, 0)),
                new Marin[]{new Marin(0, 2, 1, "sdfsdf")},
                2,
                new Wind(0, 0),
                seaEntities);
        GameInfo gameInfo4 = new GameInfo(new BattleGoal(),
                new Boat(110, new Transform(10, 10, 0), "lol", new Deck(4, 5), new BoatEntity[0], new Rectangle(2, 3, 0)),
                new Marin[]{new Marin(0, 1, 1, "sdfsdf")},
                2,
                new Wind(0, 0),
                seaEntities);
        GameInfo gameInfo5 = new GameInfo(new RegattaGoal(new CheckPoint[]{new CheckPoint(new Transform(50, 50, 0), new Circle(50))}),
                new Boat(100, new Transform(10, 10, 0), "lol", new Deck(4, 5), new BoatEntity[0], new Rectangle(2, 3, 0)),
                new Marin[]{new Marin(0, 1, 1, "sdfsdf")},
                2,
                new Wind(0, 0),
                seaEntities);
        assertEquals(gameInfo1.hashCode(), this.gameInfo.hashCode());
        assertNotEquals(gameInfo2.hashCode(), this.gameInfo.hashCode());
        assertNotEquals(gameInfo3.hashCode(), this.gameInfo.hashCode());
        assertNotEquals(gameInfo4.hashCode(), this.gameInfo.hashCode());
        assertNotEquals(gameInfo5.hashCode(), this.gameInfo.hashCode());
    }



}
