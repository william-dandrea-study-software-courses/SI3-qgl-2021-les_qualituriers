package fr.unice.polytech.si3.qgl.qualituriers.game.headquarterboat.movesailors;

import fr.unice.polytech.si3.qgl.qualituriers.Deck;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.*;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.Wind;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.VisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.game.goal.Goal;
import fr.unice.polytech.si3.qgl.qualituriers.game.goal.RegattaGoal;
import fr.unice.polytech.si3.qgl.qualituriers.game.headquarterboat.sailorsmission.SailorMission;
import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author D'Andréa William
 */
class AffectSailorsWithObjectiveToTheirBoatEntitiesTest {

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
    void affectSailorsToBoatEntity_TestForWatchWhenAnySailorOnWatch() {

        sailor1.setSailorMission(SailorMission.WATCH_SAILOR);
        sailor2.setSailorMission(SailorMission.RUDDER_SAILOR);
        sailor3.setSailorMission(SailorMission.BABORDOAR_SAILOR);
        sailor4.setSailorMission(SailorMission.BABORDOAR_SAILOR);
        sailor5.setSailorMission(SailorMission.TRIBORDOAR_SAILOR);
        sailor6.setSailorMission(SailorMission.TRIBORDOAR_SAILOR);

        List<BoatEntity> watchBoatEntities = Arrays.stream(gameInfo.getShip().getEntities()).filter(boatEntity -> boatEntity.getType() == BoatEntities.WATCH).collect(Collectors.toList());

        AffectSailorsWithObjectiveToTheirBoatEntities affectSailorsWithObjectiveToTheirBoatEntities = new AffectSailorsWithObjectiveToTheirBoatEntities(gameInfo);
        affectSailorsWithObjectiveToTheirBoatEntities.affectSailorsToBoatEntity(SailorMission.WATCH_SAILOR, watchBoatEntities);

        System.out.println(Arrays.toString(gameInfo.getShip().getEntities()));

        assertEquals(boatEntity1.getSailorAffected(), null);
        assertEquals(boatEntity2.getSailorAffected(), null);
        assertEquals(boatEntity3.getSailorAffected(), null);
        assertEquals(boatEntity4.getSailorAffected(), null);
        assertEquals(boatEntity5.getSailorAffected(), null);
        assertEquals(boatEntity6.getSailorAffected(), null);
        assertEquals(boatEntity7.getSailorAffected(), null);
        assertEquals(boatEntity8.getSailorAffected(), null);
        assertEquals(boatEntity9.getSailorAffected(), null);
        assertEquals(boatEntity10.getSailorAffected(), null);
        assertEquals(boatEntity11.getSailorAffected(), sailor1);

    }

    @Test
    void affectSailorsToBoatEntity_TestForWatchWhenSailorOnWatch() {

        sailor2.setSailorMission(SailorMission.WATCH_SAILOR);
        sailor2.setPosition(0,2);
        sailor1.setSailorMission(SailorMission.RUDDER_SAILOR);
        sailor3.setSailorMission(SailorMission.BABORDOAR_SAILOR);
        sailor4.setSailorMission(SailorMission.BABORDOAR_SAILOR);
        sailor5.setSailorMission(SailorMission.TRIBORDOAR_SAILOR);
        sailor6.setSailorMission(SailorMission.TRIBORDOAR_SAILOR);

        List<BoatEntity> watchBoatEntities = Arrays.stream(gameInfo.getShip().getEntities()).filter(boatEntity -> boatEntity.getType() == BoatEntities.WATCH).collect(Collectors.toList());

        AffectSailorsWithObjectiveToTheirBoatEntities affectSailorsWithObjectiveToTheirBoatEntities = new AffectSailorsWithObjectiveToTheirBoatEntities(gameInfo);
        affectSailorsWithObjectiveToTheirBoatEntities.affectSailorsToBoatEntity(SailorMission.WATCH_SAILOR, watchBoatEntities);

        System.out.println(Arrays.toString(gameInfo.getShip().getEntities()));

        assertEquals(boatEntity1.getSailorAffected(), null);
        assertEquals(boatEntity2.getSailorAffected(), null);
        assertEquals(boatEntity3.getSailorAffected(), null);
        assertEquals(boatEntity4.getSailorAffected(), null);
        assertEquals(boatEntity5.getSailorAffected(), null);
        assertEquals(boatEntity6.getSailorAffected(), null);
        assertEquals(boatEntity7.getSailorAffected(), null);
        assertEquals(boatEntity8.getSailorAffected(), null);
        assertEquals(boatEntity9.getSailorAffected(), null);
        assertEquals(boatEntity10.getSailorAffected(), null);
        assertEquals(boatEntity11.getSailorAffected(), sailor2);

    }

    @Test
    void affectSailorsToBoatEntity_TestForRudder() {

        sailor1.setSailorMission(SailorMission.WATCH_SAILOR);
        sailor2.setSailorMission(SailorMission.RUDDER_SAILOR);
        sailor3.setSailorMission(SailorMission.BABORDOAR_SAILOR);
        sailor4.setSailorMission(SailorMission.BABORDOAR_SAILOR);
        sailor5.setSailorMission(SailorMission.TRIBORDOAR_SAILOR);
        sailor6.setSailorMission(SailorMission.TRIBORDOAR_SAILOR);

        List<BoatEntity> watchBoatEntities = Arrays.stream(gameInfo.getShip().getEntities()).filter(boatEntity -> boatEntity.getType() == BoatEntities.RUDDER).collect(Collectors.toList());

        AffectSailorsWithObjectiveToTheirBoatEntities affectSailorsWithObjectiveToTheirBoatEntities = new AffectSailorsWithObjectiveToTheirBoatEntities(gameInfo);
        affectSailorsWithObjectiveToTheirBoatEntities.affectSailorsToBoatEntity(SailorMission.RUDDER_SAILOR, watchBoatEntities);

        System.out.println(Arrays.toString(gameInfo.getShip().getEntities()));

        assertEquals(boatEntity1.getSailorAffected(), null);
        assertEquals(boatEntity2.getSailorAffected(), null);
        assertEquals(boatEntity3.getSailorAffected(), null);
        assertEquals(boatEntity4.getSailorAffected(), null);
        assertEquals(boatEntity5.getSailorAffected(), null);
        assertEquals(boatEntity6.getSailorAffected(), null);
        assertEquals(boatEntity7.getSailorAffected(), null);
        assertEquals(boatEntity8.getSailorAffected(), null);
        assertEquals(boatEntity9.getSailorAffected(), sailor2);
        assertEquals(boatEntity10.getSailorAffected(), null);
        assertEquals(boatEntity11.getSailorAffected(), null);

    }

    @Test
    void affectSailorsToBoatEntity_TestForSail() {

        sailor1.setSailorMission(SailorMission.SAIL_SAILOR);
        sailor2.setSailorMission(SailorMission.RUDDER_SAILOR);
        sailor3.setSailorMission(SailorMission.BABORDOAR_SAILOR);
        sailor4.setSailorMission(SailorMission.BABORDOAR_SAILOR);
        sailor5.setSailorMission(SailorMission.TRIBORDOAR_SAILOR);
        sailor6.setSailorMission(SailorMission.TRIBORDOAR_SAILOR);

        List<BoatEntity> watchBoatEntities = Arrays.stream(gameInfo.getShip().getEntities()).filter(boatEntity -> boatEntity.getType() == BoatEntities.SAIL).collect(Collectors.toList());

        AffectSailorsWithObjectiveToTheirBoatEntities affectSailorsWithObjectiveToTheirBoatEntities = new AffectSailorsWithObjectiveToTheirBoatEntities(gameInfo);
        affectSailorsWithObjectiveToTheirBoatEntities.affectSailorsToBoatEntity(SailorMission.SAIL_SAILOR, watchBoatEntities);

        System.out.println(Arrays.toString(gameInfo.getShip().getEntities()));

        assertEquals(boatEntity1.getSailorAffected(), null);
        assertEquals(boatEntity2.getSailorAffected(), null);
        assertEquals(boatEntity3.getSailorAffected(), null);
        assertEquals(boatEntity4.getSailorAffected(), null);
        assertEquals(boatEntity5.getSailorAffected(), null);
        assertEquals(boatEntity6.getSailorAffected(), null);
        assertEquals(boatEntity7.getSailorAffected(), null);
        assertEquals(boatEntity8.getSailorAffected(), null);
        assertEquals(boatEntity9.getSailorAffected(), null);
        assertEquals(boatEntity10.getSailorAffected(), sailor1);
        assertEquals(boatEntity11.getSailorAffected(), null);
    }



    @Test
    void affectSailorsToBoatEntity_TestForTwoBabordOarWhenAnySailorsOnBabordOar() {

        sailor1.setSailorMission(SailorMission.SAIL_SAILOR);
        sailor2.setSailorMission(SailorMission.RUDDER_SAILOR);
        sailor3.setSailorMission(SailorMission.BABORDOAR_SAILOR);
        sailor4.setSailorMission(SailorMission.BABORDOAR_SAILOR);
        sailor5.setSailorMission(SailorMission.TRIBORDOAR_SAILOR);
        sailor6.setSailorMission(SailorMission.TRIBORDOAR_SAILOR);

        List<BoatEntity> watchBoatEntities = gameInfo.getListOfBabordOars();


        AffectSailorsWithObjectiveToTheirBoatEntities affectSailorsWithObjectiveToTheirBoatEntities = new AffectSailorsWithObjectiveToTheirBoatEntities(gameInfo);
        affectSailorsWithObjectiveToTheirBoatEntities.affectSailorsToBoatEntity(SailorMission.BABORDOAR_SAILOR, watchBoatEntities);

        System.out.println(Arrays.toString(gameInfo.getShip().getEntities()));

        assertEquals(boatEntity1.getSailorAffected(), sailor3);
        assertEquals(boatEntity2.getSailorAffected(), null);
        assertEquals(boatEntity3.getSailorAffected(), sailor4);
        assertEquals(boatEntity4.getSailorAffected(), null);
        assertEquals(boatEntity5.getSailorAffected(), null);
        assertEquals(boatEntity6.getSailorAffected(), null);
        assertEquals(boatEntity7.getSailorAffected(), null);
        assertEquals(boatEntity8.getSailorAffected(), null);
        assertEquals(boatEntity9.getSailorAffected(), null);
        assertEquals(boatEntity10.getSailorAffected(), null);
        assertEquals(boatEntity11.getSailorAffected(), null);
    }

    @Test
    void affectSailorsToBoatEntity_TestForTwoBabordOarWhenSailorsOnBabordOar() {

        sailor1.setSailorMission(SailorMission.SAIL_SAILOR);
        sailor2.setSailorMission(SailorMission.RUDDER_SAILOR);
        sailor3.setSailorMission(SailorMission.BABORDOAR_SAILOR);
        sailor4.setSailorMission(SailorMission.BABORDOAR_SAILOR);
        sailor4.setPosition(4,0);
        sailor5.setSailorMission(SailorMission.TRIBORDOAR_SAILOR);
        sailor6.setSailorMission(SailorMission.TRIBORDOAR_SAILOR);

        List<BoatEntity> watchBoatEntities = gameInfo.getListOfBabordOars();


        AffectSailorsWithObjectiveToTheirBoatEntities affectSailorsWithObjectiveToTheirBoatEntities = new AffectSailorsWithObjectiveToTheirBoatEntities(gameInfo);
        affectSailorsWithObjectiveToTheirBoatEntities.affectSailorsToBoatEntity(SailorMission.BABORDOAR_SAILOR, watchBoatEntities);

        System.out.println(Arrays.toString(gameInfo.getShip().getEntities()));

        assertEquals(boatEntity1.getSailorAffected(), sailor4);
        assertEquals(boatEntity2.getSailorAffected(), null);
        assertEquals(boatEntity3.getSailorAffected(), sailor3);
        assertEquals(boatEntity4.getSailorAffected(), null);
        assertEquals(boatEntity5.getSailorAffected(), null);
        assertEquals(boatEntity6.getSailorAffected(), null);
        assertEquals(boatEntity7.getSailorAffected(), null);
        assertEquals(boatEntity8.getSailorAffected(), null);
        assertEquals(boatEntity9.getSailorAffected(), null);
        assertEquals(boatEntity10.getSailorAffected(), null);
        assertEquals(boatEntity11.getSailorAffected(), null);
    }


    @Test
    void affectSailorsToBoatEntity_TestForTwoTribordOarWhenSailorsOnTribordOar() {

        sailor1.setSailorMission(SailorMission.SAIL_SAILOR);
        sailor2.setSailorMission(SailorMission.RUDDER_SAILOR);
        sailor3.setSailorMission(SailorMission.BABORDOAR_SAILOR);
        sailor4.setSailorMission(SailorMission.BABORDOAR_SAILOR);
        sailor5.setSailorMission(SailorMission.TRIBORDOAR_SAILOR);
        sailor6.setSailorMission(SailorMission.TRIBORDOAR_SAILOR);
        sailor6.setPosition(8,4);

        List<BoatEntity> watchBoatEntities = gameInfo.getListOfTribordOars();


        AffectSailorsWithObjectiveToTheirBoatEntities affectSailorsWithObjectiveToTheirBoatEntities = new AffectSailorsWithObjectiveToTheirBoatEntities(gameInfo);
        affectSailorsWithObjectiveToTheirBoatEntities.affectSailorsToBoatEntity(SailorMission.TRIBORDOAR_SAILOR, watchBoatEntities);

        System.out.println(Arrays.toString(gameInfo.getShip().getEntities()));

        assertEquals(boatEntity1.getSailorAffected(), null);
        assertEquals(boatEntity2.getSailorAffected(), sailor5);
        assertEquals(boatEntity3.getSailorAffected(), null);
        assertEquals(boatEntity4.getSailorAffected(), null);
        assertEquals(boatEntity5.getSailorAffected(), null);
        assertEquals(boatEntity6.getSailorAffected(), null);
        assertEquals(boatEntity7.getSailorAffected(), null);
        assertEquals(boatEntity8.getSailorAffected(), sailor6);
        assertEquals(boatEntity9.getSailorAffected(), null);
        assertEquals(boatEntity10.getSailorAffected(), null);
        assertEquals(boatEntity11.getSailorAffected(), null);
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
    void launch_TestWhenAnySailorsOnBoatEntity() {

        sailor1.setSailorMission(SailorMission.SAIL_SAILOR);
        sailor2.setSailorMission(SailorMission.RUDDER_SAILOR);
        sailor3.setSailorMission(SailorMission.BABORDOAR_SAILOR);
        sailor4.setSailorMission(SailorMission.BABORDOAR_SAILOR);
        sailor5.setSailorMission(SailorMission.TRIBORDOAR_SAILOR);
        sailor6.setSailorMission(SailorMission.TRIBORDOAR_SAILOR);


        AffectSailorsWithObjectiveToTheirBoatEntities affectSailorsWithObjectiveToTheirBoatEntities = new AffectSailorsWithObjectiveToTheirBoatEntities(gameInfo);
        affectSailorsWithObjectiveToTheirBoatEntities.launch();

        System.out.println(Arrays.toString(gameInfo.getShip().getEntities()));

        assertEquals(boatEntity1.getSailorAffected(), sailor3);
        assertEquals(boatEntity2.getSailorAffected(), sailor5);
        assertEquals(boatEntity3.getSailorAffected(), sailor4);
        assertEquals(boatEntity4.getSailorAffected(), sailor6);
        assertEquals(boatEntity5.getSailorAffected(), null);
        assertEquals(boatEntity6.getSailorAffected(), null);
        assertEquals(boatEntity7.getSailorAffected(), null);
        assertEquals(boatEntity8.getSailorAffected(), null);
        assertEquals(boatEntity9.getSailorAffected(), sailor2);
        assertEquals(boatEntity10.getSailorAffected(), sailor1);
        assertEquals(boatEntity11.getSailorAffected(), null);


    }


    @Test
    void launch_TestWhenAnySailorsOnBoatEntityWithImpairOarMission() {

        sailor1.setSailorMission(SailorMission.SAIL_SAILOR);
        sailor2.setSailorMission(SailorMission.RUDDER_SAILOR);
        sailor3.setSailorMission(SailorMission.BABORDOAR_SAILOR);
        sailor4.setSailorMission(SailorMission.WATCH_SAILOR);
        sailor5.setSailorMission(SailorMission.TRIBORDOAR_SAILOR);
        sailor6.setSailorMission(SailorMission.TRIBORDOAR_SAILOR);


        AffectSailorsWithObjectiveToTheirBoatEntities affectSailorsWithObjectiveToTheirBoatEntities = new AffectSailorsWithObjectiveToTheirBoatEntities(gameInfo);
        affectSailorsWithObjectiveToTheirBoatEntities.launch();

        System.out.println(Arrays.toString(gameInfo.getShip().getEntities()));

        assertEquals(boatEntity1.getSailorAffected(), sailor3);
        assertEquals(boatEntity2.getSailorAffected(), sailor5);
        assertEquals(boatEntity3.getSailorAffected(), null);
        assertEquals(boatEntity4.getSailorAffected(), sailor6);
        assertEquals(boatEntity5.getSailorAffected(), null);
        assertEquals(boatEntity6.getSailorAffected(), null);
        assertEquals(boatEntity7.getSailorAffected(), null);
        assertEquals(boatEntity8.getSailorAffected(), null);
        assertEquals(boatEntity9.getSailorAffected(), sailor2);
        assertEquals(boatEntity10.getSailorAffected(), sailor1);
        assertEquals(boatEntity11.getSailorAffected(), sailor4);
    }


    @Test
    void launch_TestWhenSailorsOnOar() {

        sailor1.setSailorMission(SailorMission.SAIL_SAILOR);
        sailor2.setSailorMission(SailorMission.RUDDER_SAILOR);
        sailor3.setSailorMission(SailorMission.BABORDOAR_SAILOR);
        sailor4.setSailorMission(SailorMission.BABORDOAR_SAILOR);
        sailor4.setPosition(4,0);
        sailor5.setSailorMission(SailorMission.TRIBORDOAR_SAILOR);
        sailor6.setSailorMission(SailorMission.TRIBORDOAR_SAILOR);
        sailor6.setPosition(4,4);


        AffectSailorsWithObjectiveToTheirBoatEntities affectSailorsWithObjectiveToTheirBoatEntities = new AffectSailorsWithObjectiveToTheirBoatEntities(gameInfo);
        affectSailorsWithObjectiveToTheirBoatEntities.launch();

        System.out.println(Arrays.toString(gameInfo.getShip().getEntities()));

        assertEquals(boatEntity1.getSailorAffected(), sailor4);
        assertEquals(boatEntity2.getSailorAffected(), sailor6);
        assertEquals(boatEntity3.getSailorAffected(), sailor3);
        assertEquals(boatEntity4.getSailorAffected(), sailor5);
        assertEquals(boatEntity5.getSailorAffected(), null);
        assertEquals(boatEntity6.getSailorAffected(), null);
        assertEquals(boatEntity7.getSailorAffected(), null);
        assertEquals(boatEntity8.getSailorAffected(), null);
        assertEquals(boatEntity9.getSailorAffected(), sailor2);
        assertEquals(boatEntity10.getSailorAffected(), sailor1);
        assertEquals(boatEntity11.getSailorAffected(), null);
    }

}
