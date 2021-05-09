package fr.unice.polytech.si3.qgl.qualituriers.game.headquarterboat.sailorsmission;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author D'Andréa William
 */
class GiveMissionToSailorsTest {

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

        gameInfo = new GameInfo(defaultGoal, defaultBoat, defaultSailors, defaultShipCount, defaultWind, defaultVisibleDeckEntities);
    }



    @Test
    void affectMissionForOneSailor_TestOnRudderWhenSailorIsOnFreePlaceWhenAnyAvoidMission() {

        GiveMissionToSailors giveMissionToSailors = new GiveMissionToSailors(gameInfo, SailorMission.NONE_SAILOR);
        giveMissionToSailors.affectMissionForOneSailor(BoatEntities.RUDDER, SailorMission.RUDDER_SAILOR, new ArrayList<>());

        assertEquals(sailor5.getSailorMission(), SailorMission.RUDDER_SAILOR);
    }



    @Test
    void affectMissionForOneSailor_TestOnRudderWhenSailorIsOnRudderWhenAnyAvoidMission() {

        GiveMissionToSailors giveMissionToSailors = new GiveMissionToSailors(gameInfo, SailorMission.NONE_SAILOR);
        sailor1.setPosition(11,2);
        giveMissionToSailors.affectMissionForOneSailor(BoatEntities.RUDDER, SailorMission.RUDDER_SAILOR, new ArrayList<>());

        assertEquals(new Point(11,2), sailor1.getPosition());
        assertEquals(sailor1.getSailorMission(), SailorMission.RUDDER_SAILOR);
    }



    @Test
    void affectMissionForOneSailor_TestOnRudderWhenAllSailorsAreOnOarWhenAnyAvoidMission() {


        GiveMissionToSailors giveMissionToSailors = new GiveMissionToSailors(gameInfo, SailorMission.NONE_SAILOR);
        sailor1.setPosition(4,0);
        sailor2.setPosition(5,0);
        sailor3.setPosition(6,0);
        sailor4.setPosition(4,4);
        sailor5.setPosition(5,4);
        sailor6.setPosition(7,4);

        giveMissionToSailors.affectMissionForOneSailor(BoatEntities.RUDDER, SailorMission.RUDDER_SAILOR, new ArrayList<>());
        assertEquals(sailor6.getSailorMission(), SailorMission.RUDDER_SAILOR);
    }


    @Test
    void affectMissionForOneSailor_TestOnRudderWhenSailorIsOnSailWhenAnyAvoidMission() {

        GiveMissionToSailors giveMissionToSailors = new GiveMissionToSailors(gameInfo, SailorMission.NONE_SAILOR);
        sailor1.setPosition(8,2);

        giveMissionToSailors.affectMissionForOneSailor(BoatEntities.RUDDER, SailorMission.RUDDER_SAILOR, new ArrayList<>());
        assertEquals(sailor1.getSailorMission(), SailorMission.RUDDER_SAILOR);

    }



    @Test
    void affectMissionForOneSailor_TestOnRudderWhenSailorIsOnWatchWhenAnyAvoidMission() {

        sailor1.setPosition(0,2);
        defaultSailors = new Marin[]{sailor1};
        gameInfo = new GameInfo(defaultGoal, defaultBoat, defaultSailors, defaultShipCount, defaultWind, defaultVisibleDeckEntities);

        GiveMissionToSailors giveMissionToSailors = new GiveMissionToSailors(gameInfo, SailorMission.NONE_SAILOR);
        giveMissionToSailors.affectMissionForOneSailor(BoatEntities.RUDDER, SailorMission.RUDDER_SAILOR, new ArrayList<>());

        System.out.println(Arrays.toString(defaultSailors));

        assertEquals(sailor1.getSailorMission(), SailorMission.RUDDER_SAILOR);

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
    void affectMissionForOneSailor_TestOnRudderWhenSailorIsOnFreePlaceWithOarAsAvoidMission() {

        List<SailorMission> avoidMissions = new ArrayList<SailorMission>() {{
            add(SailorMission.BABORDOAR_SAILOR);
            add(SailorMission.TRIBORDOAR_SAILOR);
        }};

        GiveMissionToSailors giveMissionToSailors = new GiveMissionToSailors(gameInfo, SailorMission.NONE_SAILOR);
        giveMissionToSailors.affectMissionForOneSailor(BoatEntities.RUDDER, SailorMission.RUDDER_SAILOR, avoidMissions);

        assertEquals(sailor5.getSailorMission(), SailorMission.RUDDER_SAILOR);
    }


    @Test
    void affectMissionForOneSailor_TestOnRudderWhenSailorIsOnRudderWithOarAsAvoidMission() {

        List<SailorMission> avoidMissions = new ArrayList<SailorMission>() {{
            add(SailorMission.BABORDOAR_SAILOR);
            add(SailorMission.TRIBORDOAR_SAILOR);
        }};

        GiveMissionToSailors giveMissionToSailors = new GiveMissionToSailors(gameInfo, SailorMission.NONE_SAILOR);
        sailor1.setPosition(11,2);
        giveMissionToSailors.affectMissionForOneSailor(BoatEntities.RUDDER, SailorMission.RUDDER_SAILOR, avoidMissions);

        assertEquals(new Point(11,2), sailor1.getPosition());
        assertEquals(sailor1.getSailorMission(), SailorMission.RUDDER_SAILOR);
    }


    @Test
    void affectMissionForOneSailor_TestOnRudderWhenAllSailorsAreOnOarWithOarAsAvoidMission() {

        List<SailorMission> avoidMissions = new ArrayList<SailorMission>() {{
            add(SailorMission.BABORDOAR_SAILOR);
            add(SailorMission.TRIBORDOAR_SAILOR);
        }};


        sailor1.setPosition(4,0);
        sailor1.setSailorMission(SailorMission.BABORDOAR_SAILOR);
        sailor2.setPosition(5,0);
        sailor2.setSailorMission(SailorMission.BABORDOAR_SAILOR);
        sailor3.setPosition(6,0);
        sailor3.setSailorMission(SailorMission.BABORDOAR_SAILOR);
        sailor4.setPosition(4,4);
        sailor4.setSailorMission(SailorMission.TRIBORDOAR_SAILOR);
        sailor5.setPosition(5,4);
        sailor5.setSailorMission(SailorMission.TRIBORDOAR_SAILOR);
        sailor6.setPosition(7,4);
        sailor6.setSailorMission(SailorMission.TRIBORDOAR_SAILOR);

        GiveMissionToSailors giveMissionToSailors = new GiveMissionToSailors(gameInfo, SailorMission.NONE_SAILOR);
        giveMissionToSailors.affectMissionForOneSailor(BoatEntities.RUDDER, SailorMission.RUDDER_SAILOR, avoidMissions);

        System.out.println(Arrays.toString(defaultSailors));

        assertEquals(sailor1.getSailorMission(), SailorMission.BABORDOAR_SAILOR);
        assertEquals(sailor2.getSailorMission(), SailorMission.BABORDOAR_SAILOR);
        assertEquals(sailor3.getSailorMission(), SailorMission.BABORDOAR_SAILOR);
        assertEquals(sailor4.getSailorMission(), SailorMission.TRIBORDOAR_SAILOR);
        assertEquals(sailor5.getSailorMission(), SailorMission.TRIBORDOAR_SAILOR);
        assertEquals(sailor6.getSailorMission(), SailorMission.TRIBORDOAR_SAILOR);
    }


    @Test
    void affectMissionForOneSailor_TestOnRudderWhenAllSailorsAreOnOarWithoutOneWithOarAsAvoidMission() {

        List<SailorMission> avoidMissions = new ArrayList<SailorMission>() {{
            add(SailorMission.BABORDOAR_SAILOR);
            add(SailorMission.TRIBORDOAR_SAILOR);
        }};


        sailor1.setPosition(4,0);
        sailor1.setSailorMission(SailorMission.BABORDOAR_SAILOR);
        sailor2.setPosition(5,0);
        sailor2.setSailorMission(SailorMission.BABORDOAR_SAILOR);
        sailor3.setPosition(6,0);
        sailor3.setSailorMission(SailorMission.BABORDOAR_SAILOR);
        sailor4.setPosition(4,4);
        sailor5.setPosition(5,4);
        sailor5.setSailorMission(SailorMission.TRIBORDOAR_SAILOR);
        sailor6.setPosition(7,4);
        sailor6.setSailorMission(SailorMission.TRIBORDOAR_SAILOR);

        GiveMissionToSailors giveMissionToSailors = new GiveMissionToSailors(gameInfo, SailorMission.NONE_SAILOR);
        giveMissionToSailors.affectMissionForOneSailor(BoatEntities.RUDDER, SailorMission.RUDDER_SAILOR, avoidMissions);

        System.out.println(Arrays.toString(defaultSailors));

        assertEquals(sailor1.getSailorMission(), SailorMission.BABORDOAR_SAILOR);
        assertEquals(sailor2.getSailorMission(), SailorMission.BABORDOAR_SAILOR);
        assertEquals(sailor3.getSailorMission(), SailorMission.BABORDOAR_SAILOR);
        assertEquals(sailor4.getSailorMission(), SailorMission.RUDDER_SAILOR);
        assertEquals(sailor5.getSailorMission(), SailorMission.TRIBORDOAR_SAILOR);
        assertEquals(sailor6.getSailorMission(), SailorMission.TRIBORDOAR_SAILOR);
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
    void affectAllTheSailorsWithAnyMissionOnOar_TestWhenAnySailorsOnOar() {

        gameInfo.reinitializeAllSailorsMissions();

        GiveMissionToSailors giveMissionToSailors = new GiveMissionToSailors(gameInfo, SailorMission.NONE_SAILOR);
        giveMissionToSailors.affectAllTheSailorsWithAnyMissionOnOar();

        assertEquals(SailorMission.TRIBORDOAR_SAILOR, sailor1.getSailorMission());
        assertEquals(SailorMission.TRIBORDOAR_SAILOR, sailor2.getSailorMission());
        assertEquals(SailorMission.TRIBORDOAR_SAILOR, sailor3.getSailorMission());
        assertEquals(SailorMission.BABORDOAR_SAILOR, sailor4.getSailorMission());
        assertEquals(SailorMission.BABORDOAR_SAILOR, sailor5.getSailorMission());
        assertEquals(SailorMission.BABORDOAR_SAILOR, sailor6.getSailorMission());
    }


    @Test
    void affectAllTheSailorsWithAnyMissionOnOar_TestWhenOneSailorIsOnTribordOar() {

        Marin sailor7 =  new Marin(7, 7,4, "marin7");
        defaultSailors = new Marin[] {sailor1, sailor2, sailor3, sailor4, sailor5, sailor6, sailor7};

        gameInfo = new GameInfo(defaultGoal, defaultBoat, defaultSailors, defaultShipCount, defaultWind, defaultVisibleDeckEntities);
        gameInfo.reinitializeAllSailorsMissions();

        GiveMissionToSailors giveMissionToSailors = new GiveMissionToSailors(gameInfo, SailorMission.NONE_SAILOR);
        giveMissionToSailors.affectAllTheSailorsWithAnyMissionOnOar();


        assertEquals(SailorMission.TRIBORDOAR_SAILOR, sailor1.getSailorMission());
        assertEquals(SailorMission.TRIBORDOAR_SAILOR, sailor2.getSailorMission());
        assertEquals(SailorMission.TRIBORDOAR_SAILOR, sailor3.getSailorMission());
        assertEquals(SailorMission.TRIBORDOAR_SAILOR, sailor4.getSailorMission());
        assertEquals(SailorMission.BABORDOAR_SAILOR, sailor5.getSailorMission());
        assertEquals(SailorMission.BABORDOAR_SAILOR, sailor6.getSailorMission());
        assertEquals(SailorMission.BABORDOAR_SAILOR, sailor7.getSailorMission());
    }



    @Test
    void affectAllTheSailorsWithAnyMissionOnOar_TestWhenOneSailorOnTribordOarAndOneSailorOnBabordOar() {

        Marin sailor7 =  new Marin(7, 7,4, "marin7");
        sailor6.setPosition(6,0);
        defaultSailors = new Marin[] {sailor1, sailor2, sailor3, sailor4, sailor5, sailor6, sailor7};

        gameInfo = new GameInfo(defaultGoal, defaultBoat, defaultSailors, defaultShipCount, defaultWind, defaultVisibleDeckEntities);
        gameInfo.reinitializeAllSailorsMissions();


        GiveMissionToSailors giveMissionToSailors = new GiveMissionToSailors(gameInfo, SailorMission.NONE_SAILOR);
        giveMissionToSailors.affectAllTheSailorsWithAnyMissionOnOar();

        System.out.println(Arrays.toString(gameInfo.getSailors()));

        assertEquals(SailorMission.TRIBORDOAR_SAILOR, sailor1.getSailorMission());
        assertEquals(SailorMission.TRIBORDOAR_SAILOR, sailor2.getSailorMission());
        assertEquals(SailorMission.TRIBORDOAR_SAILOR, sailor3.getSailorMission());
        assertEquals(SailorMission.TRIBORDOAR_SAILOR, sailor4.getSailorMission());
        assertEquals(SailorMission.BABORDOAR_SAILOR, sailor5.getSailorMission());
        assertEquals(SailorMission.BABORDOAR_SAILOR, sailor6.getSailorMission());
        assertEquals(SailorMission.BABORDOAR_SAILOR, sailor7.getSailorMission());
    }


    @Test
    void affectAllTheSailorsWithAnyMissionOnOar_TestWhenThreeSailorOnBabordOar() {

        Marin sailor7 =  new Marin(7, 7,4, "marin7");
        sailor1.setPosition(6,0);
        sailor2.setPosition(7,0);
        sailor3.setPosition(5,0);
        defaultSailors = new Marin[] {sailor1, sailor2, sailor3, sailor4, sailor5, sailor6, sailor7};

        gameInfo = new GameInfo(defaultGoal, defaultBoat, defaultSailors, defaultShipCount, defaultWind, defaultVisibleDeckEntities);
        gameInfo.reinitializeAllSailorsMissions();


        System.out.println(Arrays.toString(gameInfo.getSailors()));
        GiveMissionToSailors giveMissionToSailors = new GiveMissionToSailors(gameInfo, SailorMission.NONE_SAILOR);
        giveMissionToSailors.affectAllTheSailorsWithAnyMissionOnOar();

        System.out.println(Arrays.toString(gameInfo.getSailors()));

        assertEquals(SailorMission.BABORDOAR_SAILOR, sailor1.getSailorMission());
        assertEquals(SailorMission.BABORDOAR_SAILOR, sailor2.getSailorMission());
        assertEquals(SailorMission.BABORDOAR_SAILOR, sailor3.getSailorMission());
        assertEquals(SailorMission.TRIBORDOAR_SAILOR, sailor4.getSailorMission());
        assertEquals(SailorMission.TRIBORDOAR_SAILOR, sailor5.getSailorMission());
        assertEquals(SailorMission.TRIBORDOAR_SAILOR, sailor6.getSailorMission());
        assertEquals(SailorMission.TRIBORDOAR_SAILOR, sailor7.getSailorMission());
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
    void launch_TestWithTwoSailors() {


        defaultSailors = new Marin[] {sailor1, sailor2};
        gameInfo = new GameInfo(defaultGoal, defaultBoat, defaultSailors, defaultShipCount, defaultWind, defaultVisibleDeckEntities);

        GiveMissionToSailors giveMissionToSailors = new GiveMissionToSailors(gameInfo, SailorMission.NONE_SAILOR);
        giveMissionToSailors.launch();

        assertEquals(SailorMission.TRIBORDOAR_SAILOR, sailor1.getSailorMission());
        assertEquals(SailorMission.BABORDOAR_SAILOR, sailor2.getSailorMission());
    }


    @Test
    void launch_TestWithTwoSailorsWhenSailorsAreAlreadyInOars() {

        sailor1.setPosition(4,0);
        sailor2.setPosition(4,4);

        defaultSailors = new Marin[] {sailor1, sailor2};
        gameInfo = new GameInfo(defaultGoal, defaultBoat, defaultSailors, defaultShipCount, defaultWind, defaultVisibleDeckEntities);

        GiveMissionToSailors giveMissionToSailors = new GiveMissionToSailors(gameInfo, SailorMission.NONE_SAILOR);
        giveMissionToSailors.launch();

        assertEquals(SailorMission.BABORDOAR_SAILOR, sailor1.getSailorMission());
        assertEquals(SailorMission.TRIBORDOAR_SAILOR, sailor2.getSailorMission());
    }


    @Test
    void launch_TestWithThreeSailorsOnAnything() {


        defaultSailors = new Marin[] {sailor1, sailor2, sailor3};
        gameInfo.setSailors(defaultSailors);

        GiveMissionToSailors giveMissionToSailors = new GiveMissionToSailors(gameInfo, SailorMission.NONE_SAILOR);
        giveMissionToSailors.launch();

        System.out.println(Arrays.toString(gameInfo.getSailors()));

        assertEquals(SailorMission.TRIBORDOAR_SAILOR, sailor1.getSailorMission());
        assertEquals(SailorMission.RUDDER_SAILOR, sailor2.getSailorMission());
        assertEquals(SailorMission.BABORDOAR_SAILOR, sailor3.getSailorMission());
    }


    @Test
    void launch_TestWithOneSailorOnRudder() {

        sailor1.setPosition(11,2);

        defaultSailors = new Marin[] {sailor1, sailor2, sailor3};
        gameInfo.setSailors(defaultSailors);

        GiveMissionToSailors giveMissionToSailors = new GiveMissionToSailors(gameInfo, SailorMission.NONE_SAILOR);
        giveMissionToSailors.launch();

        System.out.println(Arrays.toString(gameInfo.getSailors()));

        assertEquals(SailorMission.RUDDER_SAILOR, sailor1.getSailorMission());
        assertEquals(SailorMission.TRIBORDOAR_SAILOR, sailor2.getSailorMission());
        assertEquals(SailorMission.BABORDOAR_SAILOR, sailor3.getSailorMission());
    }


    @Test
    void launch_TestWithOneSailorOnRudderAndOneOnTribordOar() {

        sailor1.setPosition(11,2);
        sailor3.setPosition(6,4);

        defaultSailors = new Marin[] {sailor1, sailor2, sailor3};
        gameInfo.setSailors(defaultSailors);

        GiveMissionToSailors giveMissionToSailors = new GiveMissionToSailors(gameInfo, SailorMission.NONE_SAILOR);
        giveMissionToSailors.launch();

        System.out.println(Arrays.toString(gameInfo.getSailors()));

        assertEquals(SailorMission.RUDDER_SAILOR, sailor1.getSailorMission());
        assertEquals(SailorMission.TRIBORDOAR_SAILOR, sailor2.getSailorMission());
        assertEquals(SailorMission.BABORDOAR_SAILOR, sailor3.getSailorMission());
    }

    @Test
    void launch_TestWithOneSailorOnRudderAndOneOnBabordOar() {

        sailor1.setPosition(11,2);
        sailor3.setPosition(6,0);

        defaultSailors = new Marin[] {sailor1, sailor2, sailor3};
        gameInfo.setSailors(defaultSailors);

        GiveMissionToSailors giveMissionToSailors = new GiveMissionToSailors(gameInfo, SailorMission.NONE_SAILOR);
        giveMissionToSailors.launch();

        System.out.println(Arrays.toString(gameInfo.getSailors()));

        assertEquals(SailorMission.RUDDER_SAILOR, sailor1.getSailorMission());
        assertEquals(SailorMission.TRIBORDOAR_SAILOR, sailor2.getSailorMission());
        assertEquals(SailorMission.BABORDOAR_SAILOR, sailor3.getSailorMission());
    }

    @Test
    void launch_TestWithThreeSailorsOnAnythingAndNoRudderOnBoat() {

        sailor1.setPosition(11,2);
        sailor3.setPosition(6,0);

        defaultSailors = new Marin[] {sailor1, sailor2, sailor3};
        gameInfo.setSailors(defaultSailors);

        defaultBoatEntities = new BoatEntity[]{
                new OarBoatEntity(4,0),
                new OarBoatEntity(4,4),
                new OarBoatEntity(5,0),
                new OarBoatEntity(5,4),
                new OarBoatEntity(6,0),
                new OarBoatEntity(6,4),
                new OarBoatEntity(7,0),
                new OarBoatEntity(7,4),
                new SailBoatEntity(8, 2, false),
                new WatchBoatEntity(0,2)
        };

        gameInfo.getShip().setEntities(defaultBoatEntities);

        GiveMissionToSailors giveMissionToSailors = new GiveMissionToSailors(gameInfo, SailorMission.NONE_SAILOR);
        giveMissionToSailors.launch();

        assertEquals(SailorMission.TRIBORDOAR_SAILOR, sailor1.getSailorMission());
        assertEquals(SailorMission.TRIBORDOAR_SAILOR, sailor2.getSailorMission());
        assertEquals(SailorMission.BABORDOAR_SAILOR, sailor3.getSailorMission());
    }



    @Test
    void launch_TestWithAllSailorsWhenWatch() {

        GiveMissionToSailors giveMissionToSailors = new GiveMissionToSailors(gameInfo, SailorMission.WATCH_SAILOR);
        giveMissionToSailors.launch();

        System.out.println(Arrays.toString(gameInfo.getSailors()));

        assertEquals(2, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.TRIBORDOAR_SAILOR).count());
        assertEquals(2, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.BABORDOAR_SAILOR).count());

        assertEquals(1, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.WATCH_SAILOR).count());
        assertEquals(1, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.RUDDER_SAILOR).count());
    }



    @Test
    void launch_TestWithAllSailorsWhenSail() {

        GiveMissionToSailors giveMissionToSailors = new GiveMissionToSailors(gameInfo, SailorMission.SAIL_SAILOR);
        giveMissionToSailors.launch();

        System.out.println(Arrays.toString(gameInfo.getSailors()));

        assertEquals(2, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.TRIBORDOAR_SAILOR).count());
        assertEquals(2, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.BABORDOAR_SAILOR).count());

        assertEquals(1, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.SAIL_SAILOR).count());
        assertEquals(1, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.RUDDER_SAILOR).count());
    }



    /**
     *
     *
     *
     *
     *
     *
     *
     *
     */

    @Test
    void launch_TestWithAllSailorsOnAnythingAndNoWatchOnBoat() {


        defaultSailors = new Marin[]{sailor1, sailor2, sailor3, sailor4, sailor5, sailor6,};

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
                // new WatchBoatEntity(0,2)
        };

        gameInfo.getShip().setEntities(defaultBoatEntities);

        GiveMissionToSailors giveMissionToSailors = new GiveMissionToSailors(gameInfo, SailorMission.WATCH_SAILOR);
        giveMissionToSailors.launch();


        System.out.println(Arrays.toString(gameInfo.getSailors()));

        assertEquals(3, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.TRIBORDOAR_SAILOR).count());
        assertEquals(2, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.BABORDOAR_SAILOR).count());

        assertEquals(0, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.SAIL_SAILOR).count());
        assertEquals(0, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.WATCH_SAILOR).count());

        assertEquals(1, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.RUDDER_SAILOR).count());

    }

    @Test
    void launch_TestWithAllSailorsOnAnythingAndNoSailOnBoat() {


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
                // new SailBoatEntity(8, 2, false),
                new WatchBoatEntity(0,2)
        };

        gameInfo.getShip().setEntities(defaultBoatEntities);

        GiveMissionToSailors giveMissionToSailors = new GiveMissionToSailors(gameInfo, SailorMission.SAIL_SAILOR);
        giveMissionToSailors.launch();


        System.out.println(Arrays.toString(gameInfo.getSailors()));

        assertEquals(3, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.TRIBORDOAR_SAILOR).count());
        assertEquals(2, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.BABORDOAR_SAILOR).count());

        assertEquals(0, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.SAIL_SAILOR).count());
        assertEquals(0, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.WATCH_SAILOR).count());

        assertEquals(1, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.RUDDER_SAILOR).count());

    }

    @Test
    void launch_TestWithAllSailorsOnAnythingAndNoRudderOnBoat() {


        defaultBoatEntities = new BoatEntity[]{
                new OarBoatEntity(4,0),
                new OarBoatEntity(4,4),
                new OarBoatEntity(5,0),
                new OarBoatEntity(5,4),
                new OarBoatEntity(6,0),
                new OarBoatEntity(6,4),
                new OarBoatEntity(7,0),
                new OarBoatEntity(7,4),
                //new RudderBoatEntity(11, 2),
                new SailBoatEntity(8, 2, false),
                new WatchBoatEntity(0,2)
        };

        gameInfo.getShip().setEntities(defaultBoatEntities);

        GiveMissionToSailors giveMissionToSailors = new GiveMissionToSailors(gameInfo, SailorMission.SAIL_SAILOR);
        giveMissionToSailors.launch();


        System.out.println(Arrays.toString(gameInfo.getSailors()));

        assertEquals(3, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.TRIBORDOAR_SAILOR).count());
        assertEquals(2, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.BABORDOAR_SAILOR).count());

        assertEquals(1, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.SAIL_SAILOR).count());
        assertEquals(0, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.WATCH_SAILOR).count());

        assertEquals(0, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.RUDDER_SAILOR).count());

    }

    @Test
    void launch_TestWithAllSailorsOnAnythingAndNoRudderOnBoatAndWatchMission() {


        defaultBoatEntities = new BoatEntity[]{
                new OarBoatEntity(4,0),
                new OarBoatEntity(4,4),
                new OarBoatEntity(5,0),
                new OarBoatEntity(5,4),
                new OarBoatEntity(6,0),
                new OarBoatEntity(6,4),
                new OarBoatEntity(7,0),
                new OarBoatEntity(7,4),
                //new RudderBoatEntity(11, 2),
                new SailBoatEntity(8, 2, false),
                new WatchBoatEntity(0,2)
        };

        gameInfo.getShip().setEntities(defaultBoatEntities);

        GiveMissionToSailors giveMissionToSailors = new GiveMissionToSailors(gameInfo, SailorMission.WATCH_SAILOR);
        giveMissionToSailors.launch();


        System.out.println(Arrays.toString(gameInfo.getSailors()));

        assertEquals(3, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.TRIBORDOAR_SAILOR).count());
        assertEquals(2, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.BABORDOAR_SAILOR).count());

        assertEquals(0, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.SAIL_SAILOR).count());
        assertEquals(1, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.WATCH_SAILOR).count());

        assertEquals(0, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.RUDDER_SAILOR).count());
    }




    /**
     *
     *
     *
     *
     *
     *
     *
     *
     */

    @Test
    void launch_TestWithImpairSailorsOnAnythingAndWatchMission() {


        Marin sailor7 = new Marin(7,0,0, "Marin7");

        defaultSailors = new Marin[]{sailor1, sailor2, sailor3, sailor4, sailor5, sailor6,sailor7};
        gameInfo.setSailors(defaultSailors);


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

        gameInfo.getShip().setEntities(defaultBoatEntities);

        GiveMissionToSailors giveMissionToSailors = new GiveMissionToSailors(gameInfo, SailorMission.WATCH_SAILOR);
        giveMissionToSailors.launch();


        System.out.println(Arrays.toString(gameInfo.getSailors()));

        assertEquals(3, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.TRIBORDOAR_SAILOR).count());
        assertEquals(2, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.BABORDOAR_SAILOR).count());

        assertEquals(0, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.SAIL_SAILOR).count());
        assertEquals(1, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.WATCH_SAILOR).count());

        assertEquals(1, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.RUDDER_SAILOR).count());

    }


    @Test
    void launch_TestWithImpairSailorsOnAnythingAndSailMission() {


        Marin sailor7 = new Marin(7,0,0, "Marin7");

        defaultSailors = new Marin[]{sailor1, sailor2, sailor3, sailor4, sailor5, sailor6,sailor7};
        gameInfo.setSailors(defaultSailors);


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

        gameInfo.getShip().setEntities(defaultBoatEntities);

        GiveMissionToSailors giveMissionToSailors = new GiveMissionToSailors(gameInfo, SailorMission.SAIL_SAILOR);
        giveMissionToSailors.launch();


        System.out.println(Arrays.toString(gameInfo.getSailors()));

        assertEquals(3, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.TRIBORDOAR_SAILOR).count());
        assertEquals(2, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.BABORDOAR_SAILOR).count());

        assertEquals(1, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.SAIL_SAILOR).count());
        assertEquals(0, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.WATCH_SAILOR).count());

        assertEquals(1, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.RUDDER_SAILOR).count());

    }

    @Test
    void launch_TestWithImpairSailorsOnAnythingAndNoWatchOnBoat() {


        Marin sailor7 = new Marin(7,0,0, "Marin7");

        defaultSailors = new Marin[]{sailor1, sailor2, sailor3, sailor4, sailor5, sailor6,sailor7};
        gameInfo.setSailors(defaultSailors);


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
                // new WatchBoatEntity(0,2)
        };

        gameInfo.getShip().setEntities(defaultBoatEntities);

        GiveMissionToSailors giveMissionToSailors = new GiveMissionToSailors(gameInfo, SailorMission.WATCH_SAILOR);
        giveMissionToSailors.launch();


        System.out.println(Arrays.toString(gameInfo.getSailors()));

        assertEquals(3, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.TRIBORDOAR_SAILOR).count());
        assertEquals(3, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.BABORDOAR_SAILOR).count());

        assertEquals(0, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.SAIL_SAILOR).count());
        assertEquals(0, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.WATCH_SAILOR).count());

        assertEquals(1, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.RUDDER_SAILOR).count());

    }

    @Test
    void launch_TestWithImpairOnAnythingAndNoSailOnBoat() {

        Marin sailor7 = new Marin(7,0,0, "Marin7");

        defaultSailors = new Marin[]{sailor1, sailor2, sailor3, sailor4, sailor5, sailor6,sailor7};
        gameInfo.setSailors(defaultSailors);



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
                // new SailBoatEntity(8, 2, false),
                new WatchBoatEntity(0,2)
        };

        gameInfo.getShip().setEntities(defaultBoatEntities);

        GiveMissionToSailors giveMissionToSailors = new GiveMissionToSailors(gameInfo, SailorMission.SAIL_SAILOR);
        giveMissionToSailors.launch();


        System.out.println(Arrays.toString(gameInfo.getSailors()));

        assertEquals(3, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.TRIBORDOAR_SAILOR).count());
        assertEquals(3, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.BABORDOAR_SAILOR).count());

        assertEquals(0, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.SAIL_SAILOR).count());
        assertEquals(0, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.WATCH_SAILOR).count());

        assertEquals(1, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.RUDDER_SAILOR).count());

    }

    @Test
    void launch_TestWithImpairOnAnythingAndNoRudderOnBoat() {


        Marin sailor7 = new Marin(7,0,0, "Marin7");

        defaultSailors = new Marin[]{sailor1, sailor2, sailor3, sailor4, sailor5, sailor6,sailor7};
        gameInfo.setSailors(defaultSailors);


        defaultBoatEntities = new BoatEntity[]{
                new OarBoatEntity(4,0),
                new OarBoatEntity(4,4),
                new OarBoatEntity(5,0),
                new OarBoatEntity(5,4),
                new OarBoatEntity(6,0),
                new OarBoatEntity(6,4),
                new OarBoatEntity(7,0),
                new OarBoatEntity(7,4),
                //new RudderBoatEntity(11, 2),
                new SailBoatEntity(8, 2, false),
                new WatchBoatEntity(0,2)
        };

        gameInfo.getShip().setEntities(defaultBoatEntities);

        GiveMissionToSailors giveMissionToSailors = new GiveMissionToSailors(gameInfo, SailorMission.SAIL_SAILOR);
        giveMissionToSailors.launch();


        System.out.println(Arrays.toString(gameInfo.getSailors()));

        assertEquals(3, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.TRIBORDOAR_SAILOR).count());
        assertEquals(3, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.BABORDOAR_SAILOR).count());

        assertEquals(1, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.SAIL_SAILOR).count());
        assertEquals(0, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.WATCH_SAILOR).count());

        assertEquals(0, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.RUDDER_SAILOR).count());

    }

    @Test
    void launch_TestWithImpairOnAnythingAndNoRudderOnBoatAndWatchMission() {

        Marin sailor7 = new Marin(7,0,0, "Marin7");

        defaultSailors = new Marin[]{sailor1, sailor2, sailor3, sailor4, sailor5, sailor6,sailor7};
        gameInfo.setSailors(defaultSailors);



        defaultBoatEntities = new BoatEntity[]{
                new OarBoatEntity(4,0),
                new OarBoatEntity(4,4),
                new OarBoatEntity(5,0),
                new OarBoatEntity(5,4),
                new OarBoatEntity(6,0),
                new OarBoatEntity(6,4),
                new OarBoatEntity(7,0),
                new OarBoatEntity(7,4),
                //new RudderBoatEntity(11, 2),
                new SailBoatEntity(8, 2, false),
                new WatchBoatEntity(0,2)
        };

        gameInfo.getShip().setEntities(defaultBoatEntities);

        GiveMissionToSailors giveMissionToSailors = new GiveMissionToSailors(gameInfo, SailorMission.WATCH_SAILOR);
        giveMissionToSailors.launch();


        System.out.println(Arrays.toString(gameInfo.getSailors()));

        assertEquals(3, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.TRIBORDOAR_SAILOR).count());
        assertEquals(3, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.BABORDOAR_SAILOR).count());

        assertEquals(0, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.SAIL_SAILOR).count());
        assertEquals(1, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.WATCH_SAILOR).count());

        assertEquals(0, Arrays.stream(gameInfo.getSailors()).filter(marin -> marin.getSailorMission() == SailorMission.RUDDER_SAILOR).count());
    }

}
