package fr.unice.polytech.si3.qgl.qualituriers.game;

import fr.unice.polytech.si3.qgl.qualituriers.Deck;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.*;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.Wind;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.StreamVisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.VisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.game.goal.BattleGoal;
import fr.unice.polytech.si3.qgl.qualituriers.game.goal.Goal;
import fr.unice.polytech.si3.qgl.qualituriers.game.goal.RegattaGoal;
import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Moving;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class GameInfoTest {

    GameInfo gameInfo;

    private VisibleDeckEntity streamEntity;
    private VisibleDeckEntity[] seaEntities;

    // Default CheckPoint
    private CheckPoint[] defaultDeckPoints;
    private Goal defaultGoal;

    // Default boat
    private int defaultBoatLife;
    private Transform defaultBoatPosition;
    private String defaultBoatName;
    private Deck defaultDeck;
    private BoatEntity[] defaultBoatEntities;
    private Shape defaultBoatShape;
    private Boat defaultBoat;

    // Default Sailors
    private Marin sailor1;
    private Marin sailor2;
    private Marin sailor3;
    private Marin sailor4;
    private Marin sailor5;
    private Marin sailor6;
    private Marin[] defaultSailors;

    // Default ship count
    private int defaultShipCount;

    // Default wind
    private Wind defaultWind;

    // Default visible deck entities
    private VisibleDeckEntity[] defaultVisibleDeckEntities;

    // Default gameinfo
    private GameInfo gameInfo2;





    @BeforeEach
    public  void init() {

        streamEntity = new StreamVisibleDeckEntity(new Transform(0,0,0), new Rectangle(1,1,0), 0);
        seaEntities = new VisibleDeckEntity[] {
                streamEntity,
                streamEntity,
        };
        this.gameInfo = new GameInfo(
                new BattleGoal(),
                new Boat(100, new Transform(10, 10, 0), "lol", new Deck(4, 5), new BoatEntity[0], new Rectangle(2, 3, 0)),
                new Marin[]{new Marin(0, 1, 1, "sdfsdf")},
                2,
                new Wind(0, 0),
                seaEntities);



        // Default CheckPoint
        defaultDeckPoints = new CheckPoint[]{new CheckPoint(new Transform(1000, 1000, 0), new Circle(50))};
        defaultGoal = new RegattaGoal(defaultDeckPoints);

        // Default boat
        defaultBoatLife = 100;
        defaultBoatPosition = new Transform(0,0,0);
        defaultBoatName = "default boat";
        defaultDeck = new Deck(5, 12);
        defaultBoatEntities = new BoatEntity[]{
                new OarBoatEntity(4,0),
                new OarBoatEntity(4,4),
                new OarBoatEntity(5,0),
                new OarBoatEntity(5,4),
                new OarBoatEntity(6,0),
                new OarBoatEntity(6,4),
                new OarBoatEntity(7,0),
                new OarBoatEntity(7,4),
                new RudderBoatEntity(11, 2),
                new SailBoatEntity(8, 2, false),
                new WatchBoatEntity(0,2)
        };
        defaultBoatShape = new Rectangle(5,12, 0);
        defaultBoat = new Boat(defaultBoatLife, defaultBoatPosition, defaultBoatName, defaultDeck, defaultBoatEntities, defaultBoatShape);

        // Default Sailors
        sailor1 = new Marin(1, 1,1,"marin1");
        sailor2 = new Marin(2, 1,2,"marin2");
        sailor3 = new Marin(3, 1,3,"marin3");
        sailor4 = new Marin(4, 2,1,"marin4");
        sailor5 = new Marin(5, 2,2,"marin5");
        sailor6 = new Marin(6, 2,3,"marin6");
        defaultSailors = new Marin[]{sailor1, sailor2, sailor3, sailor4, sailor5, sailor6,};

        // Default ship count
        defaultShipCount = 0;

        // Default wind
        defaultWind = new Wind(0, 0);

        // Default visible deck entities
        defaultVisibleDeckEntities = new VisibleDeckEntity[]{};

        gameInfo2 = new GameInfo(defaultGoal, defaultBoat, defaultSailors, defaultShipCount, defaultWind, defaultVisibleDeckEntities);

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
        this.gameInfo.setGoal(new RegattaGoal(new CheckPoint[]{new CheckPoint(new Transform(1, 2, 2), new Rectangle(1, 2, 2))}));
        assertEquals(gameInfo.getGoal(), new RegattaGoal(new CheckPoint[]{new CheckPoint(new Transform(1, 2, 2), new Rectangle(1, 2, 2))}));
        this.gameInfo.setShipCount(shipCount);
        assertEquals(shipCount, this.gameInfo.getShipCount());
        this.gameInfo.setWind(new Wind(2, 0));
        assertEquals(new Wind(2, 0), gameInfo.getWind());
        VisibleDeckEntity[] theEntities = new VisibleDeckEntity[] {new StreamVisibleDeckEntity(new Transform(2,0,0), new Rectangle(1,1,0), 0),
                new StreamVisibleDeckEntity(new Transform(2,0,0), new Rectangle(1,1,0), 0)};
        this.gameInfo.setSeaEntities(theEntities);
        assertEquals(theEntities, this.gameInfo.getSeaEntities());
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

    @Test
    void testGetNumberOfTurn(){
        assertEquals(gameInfo.getNumberOfTurn(), 0);
    }

    @Test
    void testDistanceTraveled(){
        assertEquals(gameInfo.getTraveledDistance(), 0);
    }

    @Test
    void testAddPointsWhereTheBoatMoved(){
        assertEquals(gameInfo.getPointsWhereTheBoatMoved(), new ArrayList<Point>());
        gameInfo.addPointsWhereTheBoatMoved(new Point(1, 1));
        List<Point> compare = new ArrayList<Point>();
        compare.add(new Point(1, 1));
        assertEquals(gameInfo.getPointsWhereTheBoatMoved(), compare);
    }




    @Test
    void getListOfSailorsOnBabordOars_TestWhenAnySailorsOnOars() {

        assertEquals(new ArrayList<>(), gameInfo2.getListOfSailorsOnBabordOars());

    }


    @Test
    void getListOfSailorsOnBabordOars_TestWhenOneSailorOnBabordOar() {

        sailor6.setPosition(4, 0);

        List<Marin> result = gameInfo2.getListOfSailorsOnBabordOars();
        assertEquals(1, result.size());
        assertTrue(result.contains(sailor6));

    }

    @Test
    void getListOfSailorsOnBabordOars_TestWhenTwoSailorOnBabordOar() {

        sailor6.setPosition(4, 0);
        sailor5.setPosition(5, 0);

        List<Marin> result = gameInfo2.getListOfSailorsOnBabordOars();
        assertEquals(2, result.size());
        assertTrue(result.contains(sailor6));
        assertTrue(result.contains(sailor5));

    }

    @Test
    void getListOfSailorsOnBabordOars_TestWhenOneSailorOnTribordOar() {
        sailor6.setPosition(4,4);

        List<Marin> result = gameInfo2.getListOfSailorsOnBabordOars();
        assertEquals(0, result.size());
    }

    @Test
    void getListOfSailorsOnBabordOars_TestWhenOneSailorOnOtherBoatEntities() {
        sailor6.setPosition(8,2);

        List<Marin> result = gameInfo2.getListOfSailorsOnBabordOars();
        assertEquals(0, result.size());
    }




    @Test
    void getListOfSailorsOnTribordOars_TestWhenAnySailorsOnOars() {

        assertEquals(new ArrayList<>(), gameInfo2.getListOfSailorsOnTribordOars());

    }


    @Test
    void getListOfSailorsOnTribordOars_TestWhenOneSailorOnTribordOar() {

        sailor6.setPosition(4, 4);

        List<Marin> result = gameInfo2.getListOfSailorsOnTribordOars();
        assertEquals(1, result.size());
        assertTrue(result.contains(sailor6));

    }

    @Test
    void getListOfSailorsOnTribordOars_TestWhenTwoSailorOnTribordOar() {

        sailor6.setPosition(4, 4);
        sailor5.setPosition(5, 4);


        List<Marin> result = gameInfo2.getListOfSailorsOnTribordOars();
        assertEquals(2, result.size());
        assertTrue(result.contains(sailor6));
        assertTrue(result.contains(sailor5));

    }

    @Test
    void getListOfSailorsOnTribordOars_TestWhenOneSailorOnBabordOar() {
        sailor6.setPosition(0,4);

        List<Marin> result = gameInfo2.getListOfSailorsOnTribordOars();
        assertEquals(0, result.size());
    }

    @Test
    void getListOfSailorsOnTribordOars_TestWhenOneSailorOnOtherBoatEntities() {
        sailor6.setPosition(8,2);

        List<Marin> result = gameInfo2.getListOfSailorsOnTribordOars();
        assertEquals(0, result.size());
    }

    @Test
    void getListOfOarWithAnySailorsOnItTest(){
        List<BoatEntity> result = gameInfo2.getListOfOarWithAnySailorsOnIt();
        assertEquals(8, result.size());

        sailor6.setPosition(7,4);
        result = gameInfo2.getListOfOarWithAnySailorsOnIt();
        assertEquals(7, result.size());
    }

    @Test
    void getListOfBabordOarWithAnySailorsOnItTest(){
        List<BoatEntity> result = gameInfo2.getListOfBabordOarWithAnySailorsOnIt();
        assertEquals(8, result.size());

        sailor6.setPosition(7,4);
        result = gameInfo2.getListOfBabordOarWithAnySailorsOnIt();
        assertEquals(8, result.size());

        sailor6.setPosition(4,0);
        result = gameInfo2.getListOfBabordOarWithAnySailorsOnIt();
        assertEquals(7, result.size());
    }

    @Test
    void getListOfTribordOarWithAnySailorsOnItTest(){
        List<BoatEntity> result = gameInfo2.getListOfTribordOarWithAnySailorsOnIt();
        assertEquals(8, result.size());

        sailor6.setPosition(7,4);
        result = gameInfo2.getListOfTribordOarWithAnySailorsOnIt();
        assertEquals(7, result.size());

        sailor6.setPosition(4,0);
        result = gameInfo2.getListOfTribordOarWithAnySailorsOnIt();
        assertEquals(8, result.size());
    }

    @Test
    void getSailorByHisIDTest(){
        var result = gameInfo2.getSailorByHisID(2);
        assertEquals(result.get(), sailor2);
    }

    @Test
    void getSailorByHisPositionTest(){
        var result = gameInfo2.getSailorByHisPosition(1, 2);
        assertEquals(result.get(), sailor2);
        assertEquals(gameInfo2.getSailorByHisPosition(0, 0), Optional.empty());
    }

    @Test
    void addActionsToDoDuringOneTurnTest(){
        gameInfo2.initializeActionsToDoDuringOneTurn();
        gameInfo2.addActionsToDoDuringOneTurn(new Moving(2, 1, 1));
        var result = gameInfo2.getActionsToDoDuringOneTurn();
        assertEquals(result.get(0), new Moving(2, 1, 1));
    }

    @Test
    void addAllActionsToDoDuringOneTurnTest(){
        List<Action> actions = new ArrayList<>();
        actions.add(new Moving(2, 1, 1));
        actions.add(new Moving(3, 2, 2));
        gameInfo2.initializeActionsToDoDuringOneTurn();
        gameInfo2.addAllActionsToDoDuringOneTurn(actions);
        var result = gameInfo2.getActionsToDoDuringOneTurn();
        assertEquals(result.get(0), new Moving(2, 1, 1));
        assertEquals(result.get(1), new Moving(3, 2, 2));
    }

    @Test
    void searchTheClosestSailorToAPointTest(){
        assertEquals(gameInfo2.searchTheClosestSailorToAPoint(new Point(1, 1)), sailor1);
        assertEquals(gameInfo2.searchTheClosestSailorToAPoint(new Point(4, 4)), sailor6);
    }

    @Test
    void getListOfPlaceWithAnyEntitiesOnIt(){
        assertEquals(49, gameInfo2.getListOfPlaceWithAnyEntitiesOnIt().size());
        assertEquals(20, gameInfo.getListOfPlaceWithAnyEntitiesOnIt().size());
    }

}
