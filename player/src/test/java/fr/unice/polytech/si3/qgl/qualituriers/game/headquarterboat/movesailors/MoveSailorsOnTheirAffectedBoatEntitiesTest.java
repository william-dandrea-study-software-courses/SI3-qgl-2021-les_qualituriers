package fr.unice.polytech.si3.qgl.qualituriers.game.headquarterboat.movesailors;

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
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Moving;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author D'Andréa William
 */
class MoveSailorsOnTheirAffectedBoatEntitiesTest {

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
        gameInfo.initializeActionsToDoDuringOneTurn();
    }

    @Test
    void launch() {
    }

    @Test
    void moveSailorsForOneMission_TestForWatchWhenSailorIsOnWatch() {

        sailor2.setPosition(0,2);
        sailor2.setSailorMission(SailorMission.WATCH_SAILOR);
        boatEntity11.setSailorAffected(sailor2);


        MoveSailorsOnTheirAffectedBoatEntities moveSailorsOnTheirAffectedBoatEntities = new MoveSailorsOnTheirAffectedBoatEntities(gameInfo);


        List<Action> actions =  moveSailorsOnTheirAffectedBoatEntities.moveSailorsForOneMission(SailorMission.WATCH_SAILOR);

        assertEquals(0, actions.size());
    }



    @Test
    void moveSailorsForOneMission_TestForWatchWhenSailorIsNotOnWatchButIn5Blocs() {


        sailor6.setSailorMission(SailorMission.WATCH_SAILOR);
        boatEntity11.setSailorAffected(sailor6);

        MoveSailorsOnTheirAffectedBoatEntities moveSailorsOnTheirAffectedBoatEntities = new MoveSailorsOnTheirAffectedBoatEntities(gameInfo);
        List<Action> actions = moveSailorsOnTheirAffectedBoatEntities.moveSailorsForOneMission(SailorMission.WATCH_SAILOR);



        assertEquals(1, actions.size());
        assertEquals(-2, ((Moving) actions.get(0)).getDistanceX());
        assertEquals(-1, ((Moving) actions.get(0)).getDistanceY());
        assertEquals(0, sailor6.getX());
        assertEquals(2, sailor6.getY());
    }




    @Test
    void moveSailorsForOneMission_TestForWatchWhenSailorIsNotOnWatchButTooFarForAtteignGoal() {


        sailor5.setSailorMission(SailorMission.WATCH_SAILOR);
        sailor5.setPosition(6, 2);
        boatEntity11.setSailorAffected(sailor5);

        MoveSailorsOnTheirAffectedBoatEntities moveSailorsOnTheirAffectedBoatEntities = new MoveSailorsOnTheirAffectedBoatEntities(gameInfo);
        List<Action> actions = moveSailorsOnTheirAffectedBoatEntities.moveSailorsForOneMission(SailorMission.WATCH_SAILOR);



        assertEquals(1, actions.size());
        assertEquals(-4, ((Moving) actions.get(0)).getDistanceX());
        assertEquals(0, ((Moving) actions.get(0)).getDistanceY());
        assertEquals(2, sailor5.getX());
        assertEquals(2, sailor5.getY());
    }


    /**
     * Ce test degroupe un peut tout les test liées aux oar car on a teste les 3 cas :
     * -> le marin est deja sur sa destination
     * -> le marin n'est pas sur sa destination et a moins de 5 blocs
     * -> le marin n'est pas sur sa destination et il est a plus de 5 blocs
     */
    @Test
    void moveSailorsForOneMission_TestForOarWhenSailorIsOnOar() {


        sailor4.setSailorMission(SailorMission.BABORDOAR_SAILOR);
        sailor5.setSailorMission(SailorMission.BABORDOAR_SAILOR);
        sailor3.setSailorMission(SailorMission.BABORDOAR_SAILOR);

        sailor4.setPosition(4, 0);
        boatEntity1.setSailorAffected(sailor4);
        boatEntity3.setSailorAffected(sailor5);
        boatEntity7.setSailorAffected(sailor3);

        MoveSailorsOnTheirAffectedBoatEntities moveSailorsOnTheirAffectedBoatEntities = new MoveSailorsOnTheirAffectedBoatEntities(gameInfo);
        List<Action> actions = moveSailorsOnTheirAffectedBoatEntities.moveSailorsForOneMission(SailorMission.BABORDOAR_SAILOR);



        assertEquals(2, actions.size());

        assertEquals(4, sailor4.getX());
        assertEquals(0, sailor4.getY());

        assertEquals(5, sailor5.getX());
        assertEquals(0, sailor5.getY());

        assertEquals(5, sailor3.getX());
        assertEquals(2, sailor3.getY());
    }





    /**
     *
     *
     *
     *
     *
     */


    @Test
    void launch_TestWhenAllSailorsAreAllFree() {

        sailor1.setSailorMission(SailorMission.BABORDOAR_SAILOR);
        boatEntity7.setSailorAffected(sailor1);

        sailor2.setSailorMission(SailorMission.TRIBORDOAR_SAILOR);
        boatEntity8.setSailorAffected(sailor2);

        sailor3.setSailorMission(SailorMission.RUDDER_SAILOR);
        boatEntity9.setSailorAffected(sailor3);

        sailor4.setSailorMission(SailorMission.SAIL_SAILOR);
        boatEntity10.setSailorAffected(sailor4);

        sailor5.setSailorMission(SailorMission.WATCH_SAILOR);
        boatEntity11.setSailorAffected(sailor5);

        sailor6.setSailorMission(SailorMission.BABORDOAR_SAILOR);
        boatEntity1.setSailorAffected(sailor6);

        MoveSailorsOnTheirAffectedBoatEntities moveSailorsOnTheirAffectedBoatEntities = new MoveSailorsOnTheirAffectedBoatEntities(gameInfo);
        List<Action> actions = moveSailorsOnTheirAffectedBoatEntities.launch();


        assertEquals(new Point(6,1), sailor1.getPosition());
        assertEquals(new Point(6,2), sailor2.getPosition());
        assertEquals(new Point(6,3), sailor3.getPosition());
        assertEquals(new Point(7,1), sailor4.getPosition());
        assertEquals(new Point(0,2), sailor5.getPosition());
        assertEquals(new Point(4,0), sailor6.getPosition());

        List<Action> actionsSecondRound = moveSailorsOnTheirAffectedBoatEntities.launch();


        assertEquals(new Point(7,0), sailor1.getPosition());
        assertEquals(new Point(7,4), sailor2.getPosition());
        assertEquals(new Point(11,3), sailor3.getPosition());
        assertEquals(new Point(8,2), sailor4.getPosition());
        assertEquals(new Point(0,2), sailor5.getPosition());
        assertEquals(new Point(4,0), sailor6.getPosition());

        List<Action> actionsLastRound = moveSailorsOnTheirAffectedBoatEntities.launch();


        assertEquals(new Point(7,0), sailor1.getPosition());
        assertEquals(new Point(7,4), sailor2.getPosition());
        assertEquals(new Point(11,2), sailor3.getPosition());
        assertEquals(new Point(8,2), sailor4.getPosition());
        assertEquals(new Point(0,2), sailor5.getPosition());
        assertEquals(new Point(4,0), sailor6.getPosition());

    }


    @Test
    void launch_TestWhenAllSailorsAreAlreadyOnTheirEmplacement() {

    }


}
