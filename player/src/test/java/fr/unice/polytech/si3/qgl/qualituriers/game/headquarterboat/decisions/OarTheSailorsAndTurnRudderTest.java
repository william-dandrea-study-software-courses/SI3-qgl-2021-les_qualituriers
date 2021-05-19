package fr.unice.polytech.si3.qgl.qualituriers.game.headquarterboat.decisions;

import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.Deck;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.*;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.Wind;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.StreamVisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.VisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.game.goal.Goal;
import fr.unice.polytech.si3.qgl.qualituriers.game.goal.RegattaGoal;
import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Oar;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Turn;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

/**
 * @author D'Andréa William
 */
class OarTheSailorsAndTurnRudderTest {

    // Default CheckPoint
    private CheckPoint[] defaultCheckPoints;
    private Goal defaultGoal;

    // Default boat
    private int defaultBoatLife;
    private Transform defaultBoatPosition;
    private String defaultBoatName;
    private Deck defaultDeck;

    private OarBoatEntity boatEntity1 =  new OarBoatEntity(4,0);
    private OarBoatEntity boatEntity2 =  new OarBoatEntity(4,4);
    private OarBoatEntity boatEntity3 =  new OarBoatEntity(5,0);
    private OarBoatEntity boatEntity4 =  new OarBoatEntity(5,4);
    private OarBoatEntity boatEntity5 =  new OarBoatEntity(6,0);
    private OarBoatEntity boatEntity6 =  new OarBoatEntity(6,4);
    private OarBoatEntity boatEntity7 =  new OarBoatEntity(7,0);
    private OarBoatEntity boatEntity8 =  new OarBoatEntity(7,4);
    private RudderBoatEntity boatEntity9 = new RudderBoatEntity(11, 2);
    private SailBoatEntity boatEntity10 = new SailBoatEntity(8, 2, false);
    private WatchBoatEntity boatEntity11 = new WatchBoatEntity(0,2);

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
    private GameInfo gameInfo;


    @BeforeEach
    void setUp() {

        // Default CheckPoint
        defaultCheckPoints = new CheckPoint[]{new CheckPoint(new Transform(1000, 1000, 0), new Circle(50))};
        defaultGoal = new RegattaGoal(defaultCheckPoints);

        // Default boat
        defaultBoatLife = 100;
        defaultBoatPosition = new Transform(0,0,0);
        defaultBoatName = "default boat";
        defaultDeck = new Deck(5, 12);
        defaultBoatEntities = new BoatEntity[]{
                boatEntity1,
                boatEntity2,
                boatEntity3,
                boatEntity4,
                boatEntity5,
                boatEntity6,
                boatEntity7,
                boatEntity8,
                boatEntity9,
                boatEntity10,
                boatEntity11
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

        gameInfo = new GameInfo(defaultGoal, defaultBoat, defaultSailors, defaultShipCount, defaultWind, defaultVisibleDeckEntities);
    }

    @Test
    void launch() {
    }

    @Test
    void getDifferenceOfOarsForGoingToDestination() {

        OarTheSailorsAndTurnRudder oarTheSailorsAndTurnRudder = new OarTheSailorsAndTurnRudder(gameInfo, new CheckPoint(new Transform(100,0, 0), new Circle(10)));
        assertEquals(0, oarTheSailorsAndTurnRudder.getDifferenceOfOarsForGoingToDestination());

        oarTheSailorsAndTurnRudder = new OarTheSailorsAndTurnRudder(gameInfo, new CheckPoint(new Transform(100,100, 0), new Circle(10)));
        assertEquals(2, oarTheSailorsAndTurnRudder.getDifferenceOfOarsForGoingToDestination());

        oarTheSailorsAndTurnRudder = new OarTheSailorsAndTurnRudder(gameInfo, new CheckPoint(new Transform(100,-100, 0), new Circle(10)));
        assertEquals(-2, oarTheSailorsAndTurnRudder.getDifferenceOfOarsForGoingToDestination());

        oarTheSailorsAndTurnRudder = new OarTheSailorsAndTurnRudder(gameInfo, new CheckPoint(new Transform(-10,-100, 0), new Circle(10)));
        assertEquals(-4, oarTheSailorsAndTurnRudder.getDifferenceOfOarsForGoingToDestination());

        oarTheSailorsAndTurnRudder = new OarTheSailorsAndTurnRudder(gameInfo, new CheckPoint(new Transform(-10,100, 0), new Circle(10)));
        assertEquals(4, oarTheSailorsAndTurnRudder.getDifferenceOfOarsForGoingToDestination());

        oarTheSailorsAndTurnRudder = new OarTheSailorsAndTurnRudder(gameInfo, new CheckPoint(new Transform(1000,0, 0), new Circle(10)));
        assertEquals(0, oarTheSailorsAndTurnRudder.getDifferenceOfOarsForGoingToDestination());

        oarTheSailorsAndTurnRudder = new OarTheSailorsAndTurnRudder(gameInfo, new CheckPoint(new Transform(-1000,0, 0), new Circle(10)));
        assertEquals(4, oarTheSailorsAndTurnRudder.getDifferenceOfOarsForGoingToDestination());
    }


    /**
     *
     *
     *
     *
     *
     *
     */



    @Test
    void generateBoatSpeed_TestWhenNoStreamAndNoWind() {

        gameInfo.setWind(null);
        gameInfo.setSeaEntities(new VisibleDeckEntity[]{});

        OarTheSailorsAndTurnRudder oarTheSailorsAndTurnRudder = new OarTheSailorsAndTurnRudder(gameInfo, new CheckPoint(new Transform(-1000,0, 0), new Circle(10)));

        double distance = oarTheSailorsAndTurnRudder.generateBoatSpeed(0);
        assertEquals(0.0, distance);

        distance = oarTheSailorsAndTurnRudder.generateBoatSpeed(1);
        assertEquals(165.0 * 1 / 8, distance);

        distance = oarTheSailorsAndTurnRudder.generateBoatSpeed(9);
        assertEquals(165.0, distance);

    }


    @Test
    void generateBoatSpeed_TestWhenNoStreamAndOnWind() {

        gameInfo.setWind(new Wind(Math.PI / 4, 125));
        boatEntity10.setOpened(true);
        gameInfo.setSeaEntities(new VisibleDeckEntity[]{});

        OarTheSailorsAndTurnRudder oarTheSailorsAndTurnRudder = new OarTheSailorsAndTurnRudder(gameInfo, new CheckPoint(new Transform(-1000,0, 0), new Circle(10)));

        double distance = oarTheSailorsAndTurnRudder.generateBoatSpeed(0);
        double speedOar = 0.0;
        double speedWind = 125 * Math.cos(-Math.PI / 4);
        double speedStream = 0.0;
        assertEquals(speedOar + speedWind + speedStream, distance);

        distance = oarTheSailorsAndTurnRudder.generateBoatSpeed(1);
        speedOar = (165.0 * 1 / 8);
        speedWind = (125.0 * Math.cos(-Math.PI / 4));
        speedStream = 0;
        assertEquals(speedOar + speedWind + speedStream, distance);

        gameInfo.setWind(new Wind(Math.PI, 500));
        distance = oarTheSailorsAndTurnRudder.generateBoatSpeed(9);
        speedOar = 165.0;
        speedWind = 500 * -1;
        speedStream = 0;
        assertEquals(speedOar + speedWind + speedStream, distance);

    }


    @Test
    void generateBoatSpeed_TestWhenStreamAndWindWhenNegative() {

        gameInfo.setWind(new Wind(Math.PI / 4, 125));
        boatEntity10.setOpened(true);
        gameInfo.setSeaEntities(new VisibleDeckEntity[]{new StreamVisibleDeckEntity(new Transform(0,0,3*Math.PI/4), new Rectangle(100, 500,3*Math.PI/4), 1050)});

        OarTheSailorsAndTurnRudder oarTheSailorsAndTurnRudder = new OarTheSailorsAndTurnRudder(gameInfo, new CheckPoint(new Transform(-1000,0, 0), new Circle(10)));


        double distance = oarTheSailorsAndTurnRudder.generateBoatSpeed(0);
        double speedOar = 0.0;
        double speedWind = 125 * Math.cos(-Math.PI / 4);
        double speedStream = -1050;
        assertEquals(speedOar + speedWind + speedStream, distance);

        distance = oarTheSailorsAndTurnRudder.generateBoatSpeed(1);
        speedOar = (165.0 * 1 / 8);
        speedWind = (125.0 * Math.cos(-Math.PI / 4));
        speedStream = -1050;
        assertEquals(speedOar + speedWind + speedStream, distance);

        gameInfo.setWind(new Wind(Math.PI, 500));
        distance = oarTheSailorsAndTurnRudder.generateBoatSpeed(9);
        speedOar = 165.0;
        speedWind = 500 * -1;
        speedStream = -1050;

        assertEquals(speedOar + speedWind + speedStream, distance);

    }

    @Test
    void generateBoatSpeed_TestWhenStreamAndWindWhenPositive() {

        gameInfo.setWind(new Wind(Math.PI / 4, 125));
        boatEntity10.setOpened(true);
        gameInfo.setSeaEntities(new VisibleDeckEntity[]{new StreamVisibleDeckEntity(new Transform(0,0,0), new Rectangle(100, 500,0), 1050)});

        OarTheSailorsAndTurnRudder oarTheSailorsAndTurnRudder = new OarTheSailorsAndTurnRudder(gameInfo, new CheckPoint(new Transform(-1000,0, 0), new Circle(10)));


        double distance = oarTheSailorsAndTurnRudder.generateBoatSpeed(0);
        double speedOar = 0.0;
        double speedWind = 125 * Math.cos(-Math.PI / 4);
        double speedStream = 1050;
        assertEquals(speedOar + speedWind + speedStream, distance);

        distance = oarTheSailorsAndTurnRudder.generateBoatSpeed(1);
        speedOar = (165.0 * 1 / 8);
        speedWind = (125.0 * Math.cos(-Math.PI / 4));
        speedStream = 1050;
        assertEquals(speedOar + speedWind + speedStream, distance);

        gameInfo.setWind(new Wind(Math.PI, 500));
        distance = oarTheSailorsAndTurnRudder.generateBoatSpeed(9);
        speedOar = 165.0;
        speedWind = 500 * -1;
        speedStream = 1050;

        assertEquals(speedOar + speedWind + speedStream, distance);
    }


    /**
     *
     *
     *
     *
     *
     *
     */


    @Test
    void numberOfOarWeNeedToActivateForGoingToCheckpoint_TestWhenDistancePositive() {


        OarTheSailorsAndTurnRudder oarTheSailorsAndTurnRudder = new OarTheSailorsAndTurnRudder(gameInfo, new CheckPoint(new Transform(1000,0, 0), new Circle(10)));
        assertEquals(8, oarTheSailorsAndTurnRudder.numberOfOarWeNeedToActivateForGoingToCheckpoint());


        oarTheSailorsAndTurnRudder = new OarTheSailorsAndTurnRudder(gameInfo, new CheckPoint(new Transform(82.5,0, 0), new Circle(10)));
        assertEquals(4, oarTheSailorsAndTurnRudder.numberOfOarWeNeedToActivateForGoingToCheckpoint());

        oarTheSailorsAndTurnRudder = new OarTheSailorsAndTurnRudder(gameInfo, new CheckPoint(new Transform(1,0, 0), new Circle(10)));
        assertEquals(0, oarTheSailorsAndTurnRudder.numberOfOarWeNeedToActivateForGoingToCheckpoint());

    }


    /**
     *
     *
     *
     *
     *
     *
     *
     */

    @Test
    void generateOarActionWhenDifference_TestForBabord() {

        sailor1.setPosition(4,0);
        sailor3.setPosition(5,0);
        sailor5.setPosition(6,0);
        sailor2.setPosition(4,4);
        sailor4.setPosition(5,4);
        sailor6.setPosition(6,4);

        OarTheSailorsAndTurnRudder oarTheSailorsAndTurnRudder = new OarTheSailorsAndTurnRudder(gameInfo, new CheckPoint(new Transform(1000,0, 0), new Circle(10)));

        List<Action> actions = oarTheSailorsAndTurnRudder.generateOarAction(1, 0, 6);
        assertEquals(5, actions.size());
        assertTrue(actions.contains(new Oar(1)));
        assertTrue(actions.contains(new Oar(3)));
        assertTrue(actions.contains(new Oar(5)));

        assertTrue(actions.contains(new Oar(2)));
        assertTrue(actions.contains(new Oar(4)));
        assertFalse(actions.contains(new Oar(6)));


        actions = oarTheSailorsAndTurnRudder.generateOarAction(3, 0, 6);
        assertEquals(3, actions.size());
        assertTrue(actions.contains(new Oar(1)));
        assertTrue(actions.contains(new Oar(3)));
        assertTrue(actions.contains(new Oar(5)));

        assertFalse(actions.contains(new Oar(2)));
        assertFalse(actions.contains(new Oar(4)));
        assertFalse(actions.contains(new Oar(6)));

        actions = oarTheSailorsAndTurnRudder.generateOarAction(4, 0, 6);
        assertEquals(3, actions.size());
        assertTrue(actions.contains(new Oar(1)));
        assertTrue(actions.contains(new Oar(3)));
        assertTrue(actions.contains(new Oar(5)));

        assertFalse(actions.contains(new Oar(2)));
        assertFalse(actions.contains(new Oar(4)));
        assertFalse(actions.contains(new Oar(6)));

        actions = oarTheSailorsAndTurnRudder.generateOarAction(1, 0, 0);
        assertEquals(1, actions.size());
        assertTrue(actions.contains(new Oar(1)));
        assertFalse(actions.contains(new Oar(3)));
        assertFalse(actions.contains(new Oar(5)));

        assertFalse(actions.contains(new Oar(2)));
        assertFalse(actions.contains(new Oar(4)));
        assertFalse(actions.contains(new Oar(6)));
    }


    @Test
    void generateOarActionWhenDifference_TestForTribord() {

        sailor1.setPosition(4,0);
        sailor3.setPosition(5,0);
        sailor5.setPosition(6,0);
        sailor2.setPosition(4,4);
        sailor4.setPosition(5,4);
        sailor6.setPosition(6,4);

        OarTheSailorsAndTurnRudder oarTheSailorsAndTurnRudder = new OarTheSailorsAndTurnRudder(gameInfo, new CheckPoint(new Transform(1000,0, 0), new Circle(10)));

        List<Action> actions = oarTheSailorsAndTurnRudder.generateOarAction(0, 1, 6);
        assertEquals(5, actions.size());
        assertTrue(actions.contains(new Oar(1)));
        assertTrue(actions.contains(new Oar(3)));
        assertFalse(actions.contains(new Oar(5)));

        assertTrue(actions.contains(new Oar(2)));
        assertTrue(actions.contains(new Oar(4)));
        assertTrue(actions.contains(new Oar(6)));


        actions = oarTheSailorsAndTurnRudder.generateOarAction(0, 3, 6);
        assertEquals(3, actions.size());
        assertFalse(actions.contains(new Oar(1)));
        assertFalse(actions.contains(new Oar(3)));
        assertFalse(actions.contains(new Oar(5)));

        assertTrue(actions.contains(new Oar(2)));
        assertTrue(actions.contains(new Oar(4)));
        assertTrue(actions.contains(new Oar(6)));

        actions = oarTheSailorsAndTurnRudder.generateOarAction(0, 4, 6);
        assertEquals(3, actions.size());
        assertFalse(actions.contains(new Oar(1)));
        assertFalse(actions.contains(new Oar(3)));
        assertFalse(actions.contains(new Oar(5)));

        assertTrue(actions.contains(new Oar(2)));
        assertTrue(actions.contains(new Oar(4)));
        assertTrue(actions.contains(new Oar(6)));
    }


    @Test
    void generateOarActionWhenDifference_TestForSpecial() {

        sailor1.setPosition(4,0);
        sailor3.setPosition(5,0);
        sailor5.setPosition(6,0);
        sailor2.setPosition(4,4);
        sailor4.setPosition(5,4);
        sailor6.setPosition(6,4);

        OarTheSailorsAndTurnRudder oarTheSailorsAndTurnRudder = new OarTheSailorsAndTurnRudder(gameInfo, new CheckPoint(new Transform(1000,0, 0), new Circle(10)));

        List<Action> actions = oarTheSailorsAndTurnRudder.generateOarAction(0, 0, 6);
        assertEquals(6, actions.size());
        assertTrue(actions.contains(new Oar(1)));
        assertTrue(actions.contains(new Oar(3)));
        assertTrue(actions.contains(new Oar(5)));

        assertTrue(actions.contains(new Oar(2)));
        assertTrue(actions.contains(new Oar(4)));
        assertTrue(actions.contains(new Oar(6)));


        actions = oarTheSailorsAndTurnRudder.generateOarAction(0, 3, 10);
        assertEquals(3, actions.size());
        assertFalse(actions.contains(new Oar(1)));
        assertFalse(actions.contains(new Oar(3)));
        assertFalse(actions.contains(new Oar(5)));

        assertTrue(actions.contains(new Oar(2)));
        assertTrue(actions.contains(new Oar(4)));
        assertTrue(actions.contains(new Oar(6)));


        actions = oarTheSailorsAndTurnRudder.generateOarAction(0, 0, 2);
        assertEquals(2, actions.size());
        assertTrue(actions.contains(new Oar(1)));
        assertFalse(actions.contains(new Oar(3)));
        assertFalse(actions.contains(new Oar(5)));

        assertTrue(actions.contains(new Oar(2)));
        assertFalse(actions.contains(new Oar(4)));
        assertFalse(actions.contains(new Oar(6)));


        actions = oarTheSailorsAndTurnRudder.generateOarAction(0, 0, 3);
        assertEquals(2, actions.size());
        assertTrue(actions.contains(new Oar(1)));
        assertFalse(actions.contains(new Oar(3)));
        assertFalse(actions.contains(new Oar(5)));

        assertTrue(actions.contains(new Oar(2)));
        assertFalse(actions.contains(new Oar(4)));
        assertFalse(actions.contains(new Oar(6)));
    }


    /**
     *
     *
     *
     *
     *
     *
     */

    @Test
    void launch_TestClassic() {

        OarTheSailorsAndTurnRudder oarTheSailorsAndTurnRudder = new OarTheSailorsAndTurnRudder(gameInfo, new CheckPoint(new Transform(1000,0, 0), new Circle(10)));
        List<Action> actions = oarTheSailorsAndTurnRudder.launch();
        assertTrue(actions.isEmpty());


        sailor1.setPosition(4,0);
        sailor3.setPosition(5,0);
        sailor5.setPosition(6,0);
        sailor2.setPosition(4,4);
        sailor4.setPosition(5,4);
        sailor6.setPosition(6,4);

        actions = oarTheSailorsAndTurnRudder.launch();


        assertEquals(6, actions.size());
        assertTrue(actions.contains(new Oar(1)));
        assertTrue(actions.contains(new Oar(3)));
        assertTrue(actions.contains(new Oar(5)));

        assertTrue(actions.contains(new Oar(2)));
        assertTrue(actions.contains(new Oar(4)));
        assertTrue(actions.contains(new Oar(6)));

    }


    @Test
    void launch_TestWhenCheckpointNotFar() {

        OarTheSailorsAndTurnRudder oarTheSailorsAndTurnRudder = new OarTheSailorsAndTurnRudder(gameInfo, new CheckPoint(new Transform(41.25,41.25, 0), new Circle(10)));

        sailor1.setPosition(4,0);
        sailor3.setPosition(5,0);
        sailor5.setPosition(6,0);
        sailor2.setPosition(4,4);
        sailor4.setPosition(5,4);
        sailor6.setPosition(6,4);

        List<Action> actions = oarTheSailorsAndTurnRudder.launch();


        assertEquals(2, actions.size());
        assertFalse(actions.contains(new Oar(1)));
        assertFalse(actions.contains(new Oar(3)));
        assertFalse(actions.contains(new Oar(5)));

        assertTrue(actions.contains(new Oar(2)));
        assertTrue(actions.contains(new Oar(4)));
        assertFalse(actions.contains(new Oar(6)));

    }


    @Test
    void launch_TestWhenCheckpointNotFar2() {

        OarTheSailorsAndTurnRudder oarTheSailorsAndTurnRudder = new OarTheSailorsAndTurnRudder(gameInfo, new CheckPoint(new Transform(41.25,-41.25, 0), new Circle(10)));

        sailor1.setPosition(4,0);
        sailor3.setPosition(5,0);
        sailor5.setPosition(6,0);
        sailor2.setPosition(4,4);
        sailor4.setPosition(5,4);
        sailor6.setPosition(6,4);

        List<Action> actions = oarTheSailorsAndTurnRudder.launch();


        assertEquals(2, actions.size());
        assertTrue(actions.contains(new Oar(1)));
        assertTrue(actions.contains(new Oar(3)));
        assertFalse(actions.contains(new Oar(5)));

        assertFalse(actions.contains(new Oar(2)));
        assertFalse(actions.contains(new Oar(4)));
        assertFalse(actions.contains(new Oar(6)));

    }


    @Test
    void launch_TestWhenCheckpointFar() {

        OarTheSailorsAndTurnRudder oarTheSailorsAndTurnRudder = new OarTheSailorsAndTurnRudder(gameInfo, new CheckPoint(new Transform(1000,0, 0), new Circle(10)));

        sailor1.setPosition(4,0);
        sailor3.setPosition(5,0);
        sailor5.setPosition(6,0);
        sailor2.setPosition(4,4);
        sailor4.setPosition(5,4);
        sailor6.setPosition(6,4);

        List<Action> actions = oarTheSailorsAndTurnRudder.launch();


        assertEquals(6, actions.size());
        assertTrue(actions.contains(new Oar(1)));
        assertTrue(actions.contains(new Oar(3)));
        assertTrue(actions.contains(new Oar(5)));

        assertTrue(actions.contains(new Oar(2)));
        assertTrue(actions.contains(new Oar(4)));
        assertTrue(actions.contains(new Oar(6)));

    }

    @Test
    void launch_TestWhenCheckpointAtTheOpposite() {

        OarTheSailorsAndTurnRudder oarTheSailorsAndTurnRudder = new OarTheSailorsAndTurnRudder(gameInfo, new CheckPoint(new Transform(0,-1000, 0), new Circle(10)));

        sailor1.setPosition(4,0);
        sailor3.setPosition(5,0);
        sailor5.setPosition(6,0);
        sailor2.setPosition(4,4);
        sailor4.setPosition(5,4);
        sailor6.setPosition(6,4);

        List<Action> actions = oarTheSailorsAndTurnRudder.launch();


        assertEquals(3, actions.size());
        assertTrue(actions.contains(new Oar(1)));
        assertTrue(actions.contains(new Oar(3)));
        assertTrue(actions.contains(new Oar(5)));

        assertFalse(actions.contains(new Oar(2)));
        assertFalse(actions.contains(new Oar(4)));
        assertFalse(actions.contains(new Oar(6)));

    }



    @Test
    void launch_TestWhenCheckpointAtTheOppositeWithRudder() {

        Marin sailor7 = new Marin(7,11,2,"sailor7");
        defaultSailors = new Marin[] {sailor1, sailor2, sailor3, sailor4, sailor5, sailor6, sailor7};
        gameInfo.setSailors(defaultSailors);

        OarTheSailorsAndTurnRudder oarTheSailorsAndTurnRudder = new OarTheSailorsAndTurnRudder(gameInfo, new CheckPoint(new Transform(0,-1000, 0), new Circle(10)));

        sailor1.setPosition(4,0);
        sailor3.setPosition(5,0);
        sailor5.setPosition(6,0);
        sailor2.setPosition(4,4);
        sailor4.setPosition(5,4);
        sailor6.setPosition(6,4);

        List<Action> actions = oarTheSailorsAndTurnRudder.launch();

        assertEquals(4, actions.size());
        assertTrue(actions.contains(new Oar(1)));
        assertTrue(actions.contains(new Oar(3)));
        assertTrue(actions.contains(new Oar(5)));

        assertFalse(actions.contains(new Oar(2)));
        assertFalse(actions.contains(new Oar(4)));
        assertFalse(actions.contains(new Oar(6)));
        assertTrue(actions.contains(new Turn(7, 0.0)));
    }






}
