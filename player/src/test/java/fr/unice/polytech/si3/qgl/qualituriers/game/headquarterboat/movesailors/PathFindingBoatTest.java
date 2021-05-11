package fr.unice.polytech.si3.qgl.qualituriers.game.headquarterboat.movesailors;

import fr.unice.polytech.si3.qgl.qualituriers.Deck;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.*;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.Wind;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.VisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.game.goal.Goal;
import fr.unice.polytech.si3.qgl.qualituriers.game.goal.RegattaGoal;
import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author D'Andréa William
 */
class PathFindingBoatTest {


    // Default CheckPoint
    private CheckPoint[] defaultDeckPoints;
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
    private Marin sailor7;
    private Marin sailor8;
    private Marin sailor9;
    private Marin sailor10;
    private Marin sailor11;
    private Marin sailor12;
    private Marin sailor13;
    private Marin sailor14;
    private Marin sailor15;
    private Marin sailor16;
    private Marin sailor17;
    private Marin sailor18;
    private Marin sailor19;
    private Marin[] defaultSailors;

    // Default ship count
    private int defaultShipCount;

    // Default wind
    private Wind defaultWind;

    // Default visible deck entities
    private VisibleDeckEntity[] defaultVisibleDeckEntities;

    // Default gameinfo
    private GameInfo gameInfo;

    private Boat completeBoat;


    @BeforeEach
    void setUp() {

        // Default CheckPoint
        defaultDeckPoints = new CheckPoint[]{new CheckPoint(new Transform(1000, 1000, 0), new Circle(50))};
        defaultGoal = new RegattaGoal(defaultDeckPoints);

        // Default boat
        defaultBoatLife = 100;
        defaultBoatPosition = new Transform(0,0,0);
        defaultBoatName = "default boat";
        defaultDeck = new Deck(10, 10);
        defaultBoatEntities = new BoatEntity[]{
                new OarBoatEntity(2, 0),
                new OarBoatEntity(3, 0),
                new OarBoatEntity(4, 0),
                new OarBoatEntity(5, 0),
                new OarBoatEntity(6, 0),
                new OarBoatEntity(7, 0),
                new OarBoatEntity(8, 0),
                new OarBoatEntity(9, 0),
                new OarBoatEntity(2, 4),
                new OarBoatEntity(3, 4),
                new OarBoatEntity(4, 4),
                new OarBoatEntity(5, 4),
                new OarBoatEntity(6, 4),
                new OarBoatEntity(7, 4),
                new OarBoatEntity(8, 4),
                new OarBoatEntity(9, 4)
        };
        defaultBoatShape = new Rectangle(10,10, 0);
        defaultBoat = new Boat(defaultBoatLife, defaultBoatPosition, defaultBoatName, defaultDeck, new BoatEntity[]{}, defaultBoatShape);

        // Default Sailors
        sailor1 = new Marin(0, 0, 0, "marin0");
        sailor2 = new Marin(1, 0, 1, "marin1");
        sailor3 = new Marin(2, 0, 2, "marin2");
        sailor4 = new Marin(3, 0, 3, "marin3");
        sailor5 = new Marin(4, 0, 4, "marin4");
        sailor6 = new Marin(5, 1, 0, "marin5");
        sailor7 = new Marin(6, 1, 1, "marin6");
        sailor8 = new Marin(7, 1, 2, "marin7");
        sailor9 = new Marin(8, 1, 3, "marin8");
        sailor10 = new Marin(9, 1, 4, "marin9");
        sailor11 = new Marin(10, 2, 0, "marin10");
        sailor12 = new Marin(11, 2, 1, "marin11");
        sailor13 = new Marin(12, 2, 2, "marin12");
        sailor14 = new Marin(13, 2, 3, "marin13");
        sailor15 = new Marin(14, 2, 4, "marin14");
        sailor16 = new Marin(15, 3, 0, "marin15");
        sailor17 = new Marin(16, 3, 1, "marin16");
        sailor18 = new Marin(17, 3, 2, "marin17");
        sailor19 = new Marin(18, 3, 3, "marin18");
        defaultSailors = new Marin[]{sailor1, sailor2, sailor3, sailor4, sailor5, sailor6, sailor7, sailor8, sailor9, sailor10, sailor11, sailor12, sailor13, sailor14, sailor15, sailor16, sailor17, sailor18, sailor19};

        // Default ship count
        defaultShipCount = 0;

        // Default wind
        defaultWind = new Wind(0, 0);

        // Default visible deck entities
        defaultVisibleDeckEntities = new VisibleDeckEntity[]{};

        gameInfo = new GameInfo(defaultGoal, defaultBoat, defaultSailors, defaultShipCount, defaultWind, defaultVisibleDeckEntities);





        Deck completeBoatDeck = new Deck(5, 12);

        BoatEntity[] completeBoatEntities = {
                new OarBoatEntity(2, 0),
                new OarBoatEntity(3, 0),
                new OarBoatEntity(4, 0),
                new OarBoatEntity(5, 0),
                new OarBoatEntity(6, 0),
                new OarBoatEntity(7, 0),
                new OarBoatEntity(8, 0),
                new OarBoatEntity(9, 0),
                new OarBoatEntity(2, 4),
                new OarBoatEntity(3, 4),
                new OarBoatEntity(4, 4),
                new OarBoatEntity(5, 4),
                new OarBoatEntity(6, 4),
                new OarBoatEntity(7, 4),
                new OarBoatEntity(8, 4),
                new OarBoatEntity(9, 4),
                new RudderBoatEntity(11, 2),
                new SailBoatEntity(6, 2, true),

        };
        Shape completeBoatShape = new Rectangle(5, 12, 0);
        completeBoat = new Boat(defaultBoatLife, defaultBoatPosition, defaultBoatName, completeBoatDeck, completeBoatEntities, completeBoatShape);

    }

    @Test
    void generateTurningAroundPointCoefficients() {

        PathFindingBoat pathFindingBoat = new PathFindingBoat(gameInfo, sailor1, new Point(0,0));
        List<Point> points = pathFindingBoat.generateTurningAroundPointCoefficients();

        assertEquals(21, points.size());
        assertTrue(points.contains(new Point(0,0)));
        assertTrue(points.contains(new Point(1,0)));
        assertTrue(points.contains(new Point(2,0)));
        assertTrue(points.contains(new Point(3,0)));
        assertTrue(points.contains(new Point(4,0)));
        assertTrue(points.contains(new Point(5,0)));
        assertTrue(points.contains(new Point(0,1)));
        assertTrue(points.contains(new Point(1,1)));
        assertTrue(points.contains(new Point(2,1)));
        assertTrue(points.contains(new Point(3,1)));
        assertTrue(points.contains(new Point(4,1)));
        assertTrue(points.contains(new Point(0,2)));
        assertTrue(points.contains(new Point(1,2)));
        assertTrue(points.contains(new Point(2,2)));
        assertTrue(points.contains(new Point(3,2)));
        assertTrue(points.contains(new Point(0,3)));
        assertTrue(points.contains(new Point(1,3)));
        assertTrue(points.contains(new Point(2,3)));
        assertTrue(points.contains(new Point(0,4)));
        assertTrue(points.contains(new Point(1,4)));
        assertTrue(points.contains(new Point(0,5)));

        assertFalse(points.contains(new Point(6,0)));
        assertFalse(points.contains(new Point(5,1)));
        assertFalse(points.contains(new Point(4,2)));
        assertFalse(points.contains(new Point(3,3)));
        assertFalse(points.contains(new Point(2,4)));
        assertFalse(points.contains(new Point(1,5)));
        assertFalse(points.contains(new Point(0,6)));


    }








    @Test
    void generateTheNearestPointTestTopLeftToBottomRight() {


        Boat actualBoat = new Boat(defaultBoatLife, defaultBoatPosition, defaultBoatName, new Deck(10,10), new BoatEntity[]{}, new Rectangle(10,10, 0));
        Marin currentMarin = new Marin(0,0,0, "marinTest");
        Marin[] actualSailors = new Marin[] {currentMarin};
        GameInfo currentGameInfo = new GameInfo(defaultGoal, actualBoat, actualSailors, defaultShipCount, defaultWind, defaultVisibleDeckEntities);
        PathFindingBoat boatPathFinding = new PathFindingBoat(currentGameInfo, currentMarin, new Point(9,9));

        assertEquals(new Point(3,2), boatPathFinding.generateTheNearestPoint());

        Marin marinRelou = new Marin(1,3,2,"MarinRelou");
        actualSailors = new Marin[] {currentMarin, marinRelou};
        currentGameInfo = new GameInfo(defaultGoal, actualBoat, actualSailors, defaultShipCount, defaultWind, defaultVisibleDeckEntities);
        boatPathFinding = new PathFindingBoat(currentGameInfo, currentMarin, new Point(9,9));

        assertEquals(new Point(2,3), boatPathFinding.generateTheNearestPoint());

    }

    @Test
    void generateTheNearestPointTestBottomLeftToTopRight() {

        Boat actualBoat = new Boat(defaultBoatLife, defaultBoatPosition, defaultBoatName, new Deck(10,10), new BoatEntity[]{}, new Rectangle(10,10, 0));
        Marin currentMarin = new Marin(0,9,0, "marinTest");
        Marin[] actualSailors = new Marin[] {currentMarin};
        GameInfo currentGameInfo = new GameInfo(defaultGoal, actualBoat, actualSailors, defaultShipCount, defaultWind, defaultVisibleDeckEntities);
        PathFindingBoat boatPathFinding = new PathFindingBoat(currentGameInfo, currentMarin, new Point(0,9));

        assertEquals(new Point(6,2), boatPathFinding.generateTheNearestPoint());

        Marin marinRelou = new Marin(1,6,2,"MarinRelou");
        actualSailors = new Marin[] {currentMarin, marinRelou};
        currentGameInfo = new GameInfo(defaultGoal, actualBoat, actualSailors, defaultShipCount, defaultWind, defaultVisibleDeckEntities);
        boatPathFinding = new PathFindingBoat(currentGameInfo, currentMarin, new Point(0,9));

        assertEquals(new Point(7,3), boatPathFinding.generateTheNearestPoint());

    }

    @Test
    void generateTheNearestPointTestBottomRightToTopLeft() {

        Boat actualBoat = new Boat(defaultBoatLife, defaultBoatPosition, defaultBoatName, new Deck(10,10), new BoatEntity[]{}, new Rectangle(10,10, 0));
        Marin currentMarin = new Marin(0,9,9, "marinTest");
        Marin[] actualSailors = new Marin[] {currentMarin};
        GameInfo currentGameInfo = new GameInfo(defaultGoal, actualBoat, actualSailors, defaultShipCount, defaultWind, defaultVisibleDeckEntities);
        PathFindingBoat boatPathFinding = new PathFindingBoat(currentGameInfo, currentMarin, new Point(0,0));

        assertEquals(new Point(6,7), boatPathFinding.generateTheNearestPoint());

        Marin marinRelou = new Marin(1,6,7,"MarinRelou");
        actualSailors = new Marin[] {currentMarin, marinRelou};
        currentGameInfo = new GameInfo(defaultGoal, actualBoat, actualSailors, defaultShipCount, defaultWind, defaultVisibleDeckEntities);
        boatPathFinding = new PathFindingBoat(currentGameInfo, currentMarin, new Point(0,0));

        assertEquals(new Point(7,6), boatPathFinding.generateTheNearestPoint());

    }
}
