package fr.unice.polytech.si3.qgl.qualituriers.game.headquarterboat;

import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.Deck;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.*;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.Wind;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.VisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.game.goal.Goal;
import fr.unice.polytech.si3.qgl.qualituriers.game.goal.RegattaGoal;
import fr.unice.polytech.si3.qgl.qualituriers.game.headquarterboat.decisions.SailorMission;
import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.LiftSail;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.LowerSail;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.UseWatch;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author D'Andréa William
 */
class NewHeadQuarterTest {

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
        defaultDeckPoints = new CheckPoint[]{new CheckPoint(new Transform(1000, 1000, 0), new Circle(50))};
        defaultGoal = new RegattaGoal(defaultDeckPoints);

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
    void playTurn() {

        NewHeadQuarter newHeadQuarter = new NewHeadQuarter(gameInfo);

        List<Action> actions = newHeadQuarter.playTurn(new CheckPoint(new Transform(1000,1000,0),new Circle(10)));
        List<Action> actions2 = newHeadQuarter.playTurn(new CheckPoint(new Transform(1000,1000,0),new Circle(10)));
        List<Action> actions3 = newHeadQuarter.playTurn(new CheckPoint(new Transform(1000,1000,0),new Circle(10)));

    }







    @Test
    void desactivateSailTest() {

        sailor1.setPosition(8,2);
        sailor1.setSailorMission(SailorMission.SAIL_SAILOR);
        boatEntity10.setOpened(true);

        NewHeadQuarter headQuarter = new NewHeadQuarter(gameInfo);

        assertEquals(Optional.of(new LowerSail(sailor1.getId())), headQuarter.desactivateSailIn());

        boatEntity10.setOpened(false);
        assertEquals(Optional.empty(), headQuarter.desactivateSailIn());
    }


    @Test
    void activateSailTest() {

        sailor1.setPosition(8,2);
        sailor1.setSailorMission(SailorMission.SAIL_SAILOR);
        boatEntity10.setOpened(false);

        NewHeadQuarter headQuarter = new NewHeadQuarter(gameInfo);

        assertEquals(Optional.of(new LiftSail(sailor1.getId())), headQuarter.activateSailIn());

        boatEntity10.setOpened(true);
        assertEquals(Optional.empty(), headQuarter.activateSailIn());
    }


    /**
     *
     *
     *
     *
     *
     */


    @Test
    void activateWatchTest() {


        NewHeadQuarter headQuarter = new NewHeadQuarter(gameInfo);
        assertEquals(Optional.empty(), headQuarter.activateWatch());

        sailor1.setPosition(0,2);
        sailor1.setSailorMission(SailorMission.WATCH_SAILOR);
        assertEquals(Optional.of(new UseWatch(sailor1.getId())), headQuarter.activateWatch());



    }


    @Test
    void switchBetweenWatchAndSailTest() {


        NewHeadQuarter headQuarter = new NewHeadQuarter(gameInfo);
        assertEquals(SailorMission.WATCH_SAILOR, headQuarter.switchBetweenWatchAndSail());

        headQuarter.playTurn(new CheckPoint(new Transform(1000,1000,0),new Circle(10)));

        assertEquals(SailorMission.SAIL_SAILOR, headQuarter.switchBetweenWatchAndSail());


    }


    @Test
    void activateSailAllTest() {

        NewHeadQuarter headQuarter = new NewHeadQuarter(gameInfo);
        assertEquals(Optional.empty(), headQuarter.activateSail());

        defaultWind = new Wind(0, 100);

        for (int i = 0; i < 20; i++) { headQuarter.playTurn(new CheckPoint(new Transform(100000,0,0),new Circle(10))); }

        assertEquals(Optional.empty(), headQuarter.activateSail());


        Optional<Marin> sailSailorOp = Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.SAIL_SAILOR).findFirst();
        assertEquals(boatEntity10.getPosition()  , sailSailorOp.get().getPosition());

    }


    @Test
    void activateSailAll_TestWhenDistanceIsFar() {

        NewHeadQuarter headQuarter = new NewHeadQuarter(gameInfo);
        assertEquals(Optional.empty(), headQuarter.activateSail());

        defaultWind = new Wind(0, 100);
        gameInfo.setWind(defaultWind);

        headQuarter.playTurn(new CheckPoint(new Transform(100000,0,0),new Circle(10)));

        Optional<Action> action = headQuarter.activateSail();
        assertEquals(Optional.empty(), action);

        headQuarter.playTurn(new CheckPoint(new Transform(100000,0,0),new Circle(10)));
        headQuarter.playTurn(new CheckPoint(new Transform(100000,0,0),new Circle(10)));
        headQuarter.playTurn(new CheckPoint(new Transform(100000,0,0),new Circle(10)));

        action = headQuarter.activateSail();
        assertEquals(Optional.empty(), action);

        assertEquals(sailor6.getPosition(), boatEntity10.getPosition());
        assertTrue(((SailBoatEntity) boatEntity10).isOpened());
    }



    @Test
    void activateSailAll_TestWhenDistanceIsNotFar() {

        NewHeadQuarter headQuarter = new NewHeadQuarter(gameInfo);
        assertEquals(Optional.empty(), headQuarter.activateSail());

        defaultWind = new Wind(0, 100);
        gameInfo.setWind(defaultWind);

        headQuarter.playTurn(new CheckPoint(new Transform(299,0,0),new Circle(10)));

        Optional<Action> action = headQuarter.activateSail();
        assertEquals(Optional.empty(), action);

        headQuarter.playTurn(new CheckPoint(new Transform(299,0,0),new Circle(10)));
        headQuarter.playTurn(new CheckPoint(new Transform(299,0,0),new Circle(10)));
        headQuarter.playTurn(new CheckPoint(new Transform(299,0,0),new Circle(10)));

        action = headQuarter.activateSail();
        assertEquals(Optional.empty(), action);

        assertEquals(sailor6.getPosition(), boatEntity10.getPosition());
        assertFalse(((SailBoatEntity) boatEntity10).isOpened());
    }
}
