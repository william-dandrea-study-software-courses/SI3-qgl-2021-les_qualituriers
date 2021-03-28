package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.strategy;

import fr.unice.polytech.si3.qgl.qualituriers.Deck;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.*;

import static fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.headquarterutils.HeadquarterUtil.*;

import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.headquarterutils.HeadquarterUtil;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class InitSailorsPlaceOnOarsTest {

    private Boat defaultBoat;
    private Boat completeBoat;
    private List<Marin> defaultSailors;

    @BeforeEach
    void setUp() {
        int defaultLife = 100;
        Transform defaultTransform = new Transform(0, 0, 0);
        String defaultName = "defaultName";
        Deck defaultDeck = new Deck(5, 12);
        BoatEntity[] defaultEntities = {
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
        Shape defaultShape = new Rectangle(5, 12, 0);
        defaultBoat = new Boat(defaultLife, defaultTransform, defaultName, defaultDeck, defaultEntities, defaultShape);
        defaultSailors = new ArrayList<>() {{
            add(new Marin(0, 0, 0, "marin0"));
            add(new Marin(1, 0, 1, "marin1"));
            add(new Marin(2, 0, 2, "marin2"));
            add(new Marin(3, 0, 3, "marin3"));
            add(new Marin(4, 0, 4, "marin4"));
            add(new Marin(5, 1, 0, "marin5"));
            add(new Marin(6, 1, 1, "marin6"));
            add(new Marin(7, 1, 2, "marin7"));
            add(new Marin(8, 1, 3, "marin8"));
            add(new Marin(9, 1, 4, "marin9"));
            add(new Marin(10, 2, 0, "marin10"));
            add(new Marin(11, 2, 1, "marin11"));
            add(new Marin(12, 2, 2, "marin12"));
            add(new Marin(13, 2, 3, "marin13"));
            add(new Marin(14, 2, 4, "marin14"));
            add(new Marin(15, 3, 0, "marin15"));
            add(new Marin(16, 3, 1, "marin16"));
            add(new Marin(17, 3, 2, "marin17"));
            add(new Marin(18, 3, 3, "marin18"));
        }};





        String completeBoatName = "defaultName";
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
                new RudderBoatEntity(11,2),
                new SailBoatEntity(6,2, false),

        };
        Shape completeBoatShape = new Rectangle(5, 12, 0);
        completeBoat = new Boat(defaultLife, defaultTransform, completeBoatName, completeBoatDeck, completeBoatEntities, completeBoatShape);

    }

    @Test
    void moveSailorsInTest() {

        List<Marin> sailorsWeWantToMove = new ArrayList<>() {{
            add(HeadquarterUtil.getSailorByHisID(defaultSailors, 0).get());
            add(HeadquarterUtil.getSailorByHisID(defaultSailors, 1).get());
            add(HeadquarterUtil.getSailorByHisID(defaultSailors, 2).get());
            add(HeadquarterUtil.getSailorByHisID(defaultSailors, 3).get());
        }};

        List<BoatEntity> destinations = new ArrayList<>() {{
            add(new OarBoatEntity(7, 0));
            add(new OarBoatEntity(8, 0));
            add(new OarBoatEntity(9, 0));
        }};


        InitSailorsPlaceOnOars initSailorsPlaceOnRudder = new InitSailorsPlaceOnOars(completeBoat, defaultSailors);

        assertEquals(4, initSailorsPlaceOnRudder.moveSailorsIn(4, sailorsWeWantToMove, destinations).size());

        assertEquals(new Point(5,0), HeadquarterUtil.getSailorByHisID(sailorsWeWantToMove, 0).get().getPosition());
        assertEquals(new Point(5,1), HeadquarterUtil.getSailorByHisID(sailorsWeWantToMove, 1).get().getPosition());
        assertEquals(new Point(5,2), HeadquarterUtil.getSailorByHisID(sailorsWeWantToMove, 2).get().getPosition());
        assertEquals(new Point(5,3), HeadquarterUtil.getSailorByHisID(sailorsWeWantToMove, 3).get().getPosition());
    }


    @Test
    void generateCorrespondanceSailorWithPotentialEmplacementTest() {

        List<Marin> marins = new ArrayList<>() {{
            add(HeadquarterUtil.getSailorByHisID(defaultSailors, 18).get());
            add(HeadquarterUtil.getSailorByHisID(defaultSailors, 17).get());
            add(HeadquarterUtil.getSailorByHisID(defaultSailors, 16).get());
        }};

        List<BoatEntity> entities = new ArrayList<>() {{
            add(new OarBoatEntity(4, 0));
            add(new OarBoatEntity(5, 0));
            add(new OarBoatEntity(6, 0));
        }};

        InitSailorsPlaceOnOars initSailorsPlaceOnRudder = new InitSailorsPlaceOnOars(completeBoat, defaultSailors);
        assertEquals(3, initSailorsPlaceOnRudder.generateCorrespondanceSailorWithPotentialEmplacement(marins, entities).size());
    }

    @Test
    void generateCorrespondanceSailorWithPotentialEmplacementWhenPlaceIsNotFreeTest() {

        List<Marin> marins = new ArrayList<>() {{
            add(HeadquarterUtil.getSailorByHisID(defaultSailors, 18).get());
            add(HeadquarterUtil.getSailorByHisID(defaultSailors, 17).get());
            add(HeadquarterUtil.getSailorByHisID(defaultSailors, 16).get());
        }};

        List<BoatEntity> entities = new ArrayList<>() {{
            add(new OarBoatEntity(2, 0));
            add(new OarBoatEntity(3, 0));
        }};

        InitSailorsPlaceOnOars initSailorsPlaceOnRudder = new InitSailorsPlaceOnOars(completeBoat, defaultSailors);
        assertEquals(0, initSailorsPlaceOnRudder.generateCorrespondanceSailorWithPotentialEmplacement(marins, entities).size());
    }

    @Test
    void generateCorrespondanceSailorWithPotentialEmplacementWhithDisparitiesOfSize1Test() {

        List<Marin> marins = new ArrayList<>() {{
            add(HeadquarterUtil.getSailorByHisID(defaultSailors, 18).get());
            add(HeadquarterUtil.getSailorByHisID(defaultSailors, 17).get());
            add(HeadquarterUtil.getSailorByHisID(defaultSailors, 16).get());
        }};

        List<BoatEntity> entities = new ArrayList<>() {{
            add(new OarBoatEntity(4, 0));
        }};


        InitSailorsPlaceOnOars initSailorsPlaceOnRudder = new InitSailorsPlaceOnOars(completeBoat, defaultSailors);
        var result = initSailorsPlaceOnRudder.generateCorrespondanceSailorWithPotentialEmplacement(marins, entities);
        assertEquals(1, result.size());

        System.out.println(result);
    }

    @Test
    void generateCorrespondanceSailorWithPotentialEmplacementWhithDisparitiesOfSize2Test() {

        List<Marin> marins = new ArrayList<>() {{
            add(HeadquarterUtil.getSailorByHisID(defaultSailors, 18).get());
        }};

        List<BoatEntity> entities = new ArrayList<>() {{
            add(new OarBoatEntity(4, 0));
            add(new OarBoatEntity(5, 0));
            add(new OarBoatEntity(6, 0));
        }};


        InitSailorsPlaceOnOars initSailorsPlaceOnRudder = new InitSailorsPlaceOnOars(completeBoat, defaultSailors);
        var result = initSailorsPlaceOnRudder.generateCorrespondanceSailorWithPotentialEmplacement(marins, entities);
        assertEquals(1, result.size());

        System.out.println(result);
    }

    @Test
    void generateCorrespondanceSailorWithPotentialEmplacementCrashTest() {

        List<Marin> marins = new ArrayList<>() {{ }};
        List<BoatEntity> entities = new ArrayList<>() {{ }};


        InitSailorsPlaceOnOars initSailorsPlaceOnRudder = new InitSailorsPlaceOnOars(completeBoat, defaultSailors);
        var result = initSailorsPlaceOnRudder.generateCorrespondanceSailorWithPotentialEmplacement(marins, entities);
        assertEquals(0, result.size());

        marins = new ArrayList<>() {{ add(HeadquarterUtil.getSailorByHisID(defaultSailors, 18).get());}};
        entities = new ArrayList<>() {{ }};


        initSailorsPlaceOnRudder = new InitSailorsPlaceOnOars(completeBoat, defaultSailors);
        result = initSailorsPlaceOnRudder.generateCorrespondanceSailorWithPotentialEmplacement(marins, entities);
        assertEquals(0, result.size());

        marins = new ArrayList<>() {{ }};
        entities = new ArrayList<>() {{ add(new OarBoatEntity(4, 0));}};


        initSailorsPlaceOnRudder = new InitSailorsPlaceOnOars(completeBoat, defaultSailors);
        result = initSailorsPlaceOnRudder.generateCorrespondanceSailorWithPotentialEmplacement(marins, entities);
        assertEquals(0, result.size());


    }




    @Test
    void affectMarinToBoatEntityTest() {
        Map<Marin, Point> map = new HashMap<>() {{
            put(HeadquarterUtil.getSailorByHisID(defaultSailors, 18).get(), new Point(6, 4));
            put(HeadquarterUtil.getSailorByHisID(defaultSailors, 17).get(), new Point(7, 4));
            put(HeadquarterUtil.getSailorByHisID(defaultSailors, 16).get(), new Point(8, 4));
        }};

        InitSailorsPlaceOnOars initSailorsPlaceOnRudder = new InitSailorsPlaceOnOars(completeBoat, defaultSailors);

        assertEquals(2, initSailorsPlaceOnRudder.affectMarinToBoatEntity(map, 2).size());

        assertEquals(new Point(3,3),HeadquarterUtil.getSailorByHisID(defaultSailors, 18).get().getPosition());
        assertEquals(new Point(7,4),HeadquarterUtil.getSailorByHisID(defaultSailors, 17).get().getPosition());
        assertEquals(new Point(8,4),HeadquarterUtil.getSailorByHisID(defaultSailors, 16).get().getPosition());

    }

    @Test
    void affectMarinToBoatEntityTestWhenSailorOnEmplacement() {
        Map<Marin, Point> map = new HashMap<>() {{
            put(HeadquarterUtil.getSailorByHisID(defaultSailors, 18).get(), new Point(3, 3));
        }};

        InitSailorsPlaceOnOars initSailorsPlaceOnRudder = new InitSailorsPlaceOnOars(completeBoat, defaultSailors);
        assertEquals(0, initSailorsPlaceOnRudder.affectMarinToBoatEntity(map, 2).size());
        assertEquals(new Point(3,3),HeadquarterUtil.getSailorByHisID(defaultSailors, 18).get().getPosition());

    }

    @Test
    void affectMarinToBoatEntityCrashTest() {
        Map<Marin, Point> map = new HashMap<>() {{ }};

        InitSailorsPlaceOnOars initSailorsPlaceOnRudder = new InitSailorsPlaceOnOars(completeBoat, defaultSailors);
        assertEquals(0, initSailorsPlaceOnRudder.affectMarinToBoatEntity(map, 2).size());
        assertEquals(0, initSailorsPlaceOnRudder.affectMarinToBoatEntity(map, 0).size());

    }
}
