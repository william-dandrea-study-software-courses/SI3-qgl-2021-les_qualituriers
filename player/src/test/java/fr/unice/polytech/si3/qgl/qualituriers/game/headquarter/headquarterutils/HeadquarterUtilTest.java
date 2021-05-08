package fr.unice.polytech.si3.qgl.qualituriers.game.headquarter.headquarterutils;

import fr.unice.polytech.si3.qgl.qualituriers.Config;
import fr.unice.polytech.si3.qgl.qualituriers.Deck;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.*;
import fr.unice.polytech.si3.qgl.qualituriers.exceptions.MaxAngleRudderException;
import fr.unice.polytech.si3.qgl.qualituriers.exceptions.MovingSailorException;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Moving;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Oar;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Turn;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static fr.unice.polytech.si3.qgl.qualituriers.game.headquarter.headquarterutils.HeadquarterUtil.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class HeadquarterUtilTest {

    private Boat defaultBoat;
    private Boat crashBoat;
    private List<Marin> defaultSailors;
    private List<Marin> crashSailors;

    private int defaultLife;
    private Transform defaultTransform;
    private String defaultName;
    private Deck defaultDeck;
    private Shape defaultShape;


    @BeforeEach
    void setUp() {

        defaultLife = 100;
        defaultTransform = new Transform(0,0,0);
        defaultName = "defaultName";
        defaultDeck = new Deck(5,12);
        BoatEntity[] defaultEntities = {
                new OarBoatEntity(2,0),
                new OarBoatEntity(3,0),
                new OarBoatEntity(4,0),
                new OarBoatEntity(5,0),
                new OarBoatEntity(6,0),
                new OarBoatEntity(2,4),
                new OarBoatEntity(3,4),
                new OarBoatEntity(4,4),
                new OarBoatEntity(5,4),
                new OarBoatEntity(6,4),
                new RudderBoatEntity(12, 3),
                new SailBoatEntity(12,2, false)
        };
        BoatEntity[] crashEntities = {};

        defaultShape = new Rectangle(5,12,0);

        defaultBoat = new Boat(defaultLife, defaultTransform,defaultName,defaultDeck,defaultEntities,defaultShape);
        crashBoat = new Boat(defaultLife, defaultTransform,defaultName,defaultDeck,crashEntities,defaultShape);

         defaultSailors = new ArrayList<>() {{
            add(new Marin(1,2,0,"marin1"));
            add(new Marin(2,3,0,"marin2"));
            add(new Marin(3,2,4,"marin3"));
            add(new Marin(4,3,4,"marin4"));
            add(new Marin(5,2,2,"marin5"));
            add(new Marin(6,3,2,"marin6"));
        }};

         crashSailors = new ArrayList<>();
    }

    @Test
    void getSailorByHisIdTest() {
        assertEquals(Optional.of(new Marin(1,2,0,"marin1")), HeadquarterUtil.getSailorByHisID(defaultSailors, 1));
        assertEquals(Optional.empty(), HeadquarterUtil.getSailorByHisID(defaultSailors, 20));
    }

    @Test
    void getSailorByHisPositionTest() {
        assertEquals(Optional.empty(), HeadquarterUtil.getSailorByHisPosition(defaultSailors, 6,4));
        assertEquals(Optional.of(new Marin(6,3,2,"marin6")), HeadquarterUtil.getSailorByHisPosition(defaultSailors, 3,2));
    }

    @Test
    void generateMovingActionTest() {

        // Cas ou la distance est nul
        assertEquals(Optional.empty(),HeadquarterUtil.generateMovingAction(1, 0,0,0,0));

        // Cas ou la distance est valide
        assertEquals(Optional.of(new Moving(1,1,1)),HeadquarterUtil.generateMovingAction(1, 0,0,1,1));
        assertEquals(Optional.of(new  Moving(1,4,4)),HeadquarterUtil.generateMovingAction(1, -2,-2,2,2));

        // Cas ou la distance est invalide : supérieur a 5
        assertThrows(MovingSailorException.class, () -> HeadquarterUtil.generateMovingAction(1, 0,0,6,6));
        assertThrows(MovingSailorException.class, () -> HeadquarterUtil.generateMovingAction(1, -5,-5,1,1));

    }

    @Test
    void placeIsFreeTest() {
        assertTrue(HeadquarterUtil.placeIsFree(new Point(4,0), defaultSailors));
        assertFalse(HeadquarterUtil.placeIsFree(new Point(3,2), defaultSailors));
    }

    @Test
    void getListOfOarsTest() {
        assertEquals(10, HeadquarterUtil.getListOfOars(defaultBoat).size());
        assertEquals(0, HeadquarterUtil.getListOfOars(crashBoat).size());
        assertTrue(HeadquarterUtil.getListOfOars(defaultBoat).contains(new OarBoatEntity(6,4)));
        assertFalse(HeadquarterUtil.getListOfOars(defaultBoat).contains( new RudderBoatEntity(12, 3)));
        assertFalse(HeadquarterUtil.getListOfOars(defaultBoat).contains( new SailBoatEntity(12,2, false)));
    }

    @Test
    void getListOfBabordOarsTest() {
        assertEquals(5, HeadquarterUtil.getListOfBabordOars(defaultBoat).size());
        assertEquals(0, HeadquarterUtil.getListOfBabordOars(crashBoat).size());
        assertFalse(HeadquarterUtil.getListOfBabordOars(defaultBoat).contains(new OarBoatEntity(6,4)));
        assertTrue(HeadquarterUtil.getListOfBabordOars(defaultBoat).contains(new OarBoatEntity(2,0)));
        assertFalse(HeadquarterUtil.getListOfBabordOars(defaultBoat).contains( new RudderBoatEntity(12, 3)));
        assertFalse(HeadquarterUtil.getListOfBabordOars(defaultBoat).contains( new SailBoatEntity(12,2, false)));
    }

    @Test
    void getListOfTribordOarsTest() {
        assertEquals(5, HeadquarterUtil.getListOfTribordOars(defaultBoat).size());
        assertEquals(0, HeadquarterUtil.getListOfTribordOars(crashBoat).size());
        assertTrue(HeadquarterUtil.getListOfTribordOars(defaultBoat).contains(new OarBoatEntity(6,4)));
        assertFalse(HeadquarterUtil.getListOfTribordOars(defaultBoat).contains(new OarBoatEntity(2,0)));
        assertFalse(HeadquarterUtil.getListOfTribordOars(defaultBoat).contains( new RudderBoatEntity(12, 3)));
        assertFalse(HeadquarterUtil.getListOfTribordOars(defaultBoat).contains( new SailBoatEntity(12,2, false)));
    }

    @Test
    void getListOfPlaceWithAnyEntitiesOnItTest() {
        assertEquals(50, HeadquarterUtil.getListOfPlaceWithAnyEntitiesOnIt(defaultBoat).size());
        assertTrue(HeadquarterUtil.getListOfPlaceWithAnyEntitiesOnIt(defaultBoat).contains(new Point(6,3)));
        assertFalse(HeadquarterUtil.getListOfPlaceWithAnyEntitiesOnIt(defaultBoat).contains(new Point(12,2)));
        assertFalse(HeadquarterUtil.getListOfPlaceWithAnyEntitiesOnIt(defaultBoat).contains(new Point(12,3)));
    }

    @Test
    void getListOfSailorsOnBabordOarsTest() {
        assertEquals(2, HeadquarterUtil.getListOfSailorsOnBabordOars(defaultSailors, defaultBoat).size());
        assertTrue(HeadquarterUtil.getListOfSailorsOnBabordOars(defaultSailors, defaultBoat).contains(new Marin(1,2,0,"marin1")));
        assertFalse(HeadquarterUtil.getListOfSailorsOnBabordOars(defaultSailors, defaultBoat).contains(new Marin(3,2,4,"marin3")));
        assertEquals(0, HeadquarterUtil.getListOfSailorsOnBabordOars(crashSailors, defaultBoat).size());
        assertEquals(0, HeadquarterUtil.getListOfSailorsOnBabordOars(defaultSailors, crashBoat).size());
    }

    @Test
    void getListOfSailorsOnTribordOarsTest() {
        assertEquals(2, HeadquarterUtil.getListOfSailorsOnTribordOars(defaultSailors, defaultBoat).size());
        assertTrue(HeadquarterUtil.getListOfSailorsOnTribordOars(defaultSailors, defaultBoat).contains(new Marin(3,2,4,"marin3")));
        assertFalse(HeadquarterUtil.getListOfSailorsOnTribordOars(defaultSailors, defaultBoat).contains(new Marin(1,2,0,"marin1")));
        assertEquals(0, HeadquarterUtil.getListOfSailorsOnTribordOars(crashSailors, defaultBoat).size());
        assertEquals(0, HeadquarterUtil.getListOfSailorsOnTribordOars(defaultSailors, crashBoat).size());
    }

    @Test
    void getListOfSailorsOnOarsTest() {
        assertTrue(HeadquarterUtil.getListOfSailorsOnOars(defaultSailors, defaultBoat).contains(new Marin(3,2,4,"marin3")));
        assertTrue(HeadquarterUtil.getListOfSailorsOnOars(defaultSailors, defaultBoat).contains(new Marin(1,2,0,"marin1")));
        assertFalse(HeadquarterUtil.getListOfSailorsOnOars(defaultSailors, defaultBoat).contains(new Marin(5,2,2,"marin5")));
        assertFalse(HeadquarterUtil.getListOfSailorsOnOars(defaultSailors, defaultBoat).contains(new Marin(6,3,2,"marin5")));
        assertEquals(4, HeadquarterUtil.getListOfSailorsOnOars(defaultSailors, defaultBoat).size());
        assertEquals(0, HeadquarterUtil.getListOfSailorsOnOars(crashSailors, defaultBoat).size());
        assertEquals(0, HeadquarterUtil.getListOfSailorsOnOars(defaultSailors, crashBoat).size());
    }

    @Test
    void getListOfSailorsOnAnyOarTest() {
        assertFalse(HeadquarterUtil.getListOfSailorsOnAnyOar(defaultSailors, defaultBoat).contains(new Marin(3,2,4,"marin3")));
        assertFalse(HeadquarterUtil.getListOfSailorsOnAnyOar(defaultSailors, defaultBoat).contains(new Marin(1,2,0,"marin1")));
        assertTrue(HeadquarterUtil.getListOfSailorsOnAnyOar(defaultSailors, defaultBoat).contains(new Marin(5,2,2,"marin5")));
        assertTrue(HeadquarterUtil.getListOfSailorsOnAnyOar(defaultSailors, defaultBoat).contains(new Marin(6,3,2,"marin5")));
        assertEquals(2, HeadquarterUtil.getListOfSailorsOnAnyOar(defaultSailors, defaultBoat).size());
        assertEquals(0, HeadquarterUtil.getListOfSailorsOnAnyOar(crashSailors, defaultBoat).size());
        assertEquals(6, HeadquarterUtil.getListOfSailorsOnAnyOar(defaultSailors, crashBoat).size());
    }

    @Test
    void getListOfOarWithAnySailorsOnItTest() {
        assertTrue(HeadquarterUtil.getListOfOarWithAnySailorsOnIt(defaultSailors, defaultBoat).contains(new OarBoatEntity(6,4)));
        assertTrue(HeadquarterUtil.getListOfOarWithAnySailorsOnIt(defaultSailors, defaultBoat).contains(new OarBoatEntity(6,0)));
        assertFalse(HeadquarterUtil.getListOfOarWithAnySailorsOnIt(defaultSailors, defaultBoat).contains(new OarBoatEntity(3,0)));
        assertFalse(HeadquarterUtil.getListOfOarWithAnySailorsOnIt(defaultSailors, defaultBoat).contains(new OarBoatEntity(3,4)));
        assertEquals(6, HeadquarterUtil.getListOfOarWithAnySailorsOnIt(defaultSailors, defaultBoat).size());
        assertEquals(10, HeadquarterUtil.getListOfOarWithAnySailorsOnIt(crashSailors, defaultBoat).size());
        assertEquals(0, HeadquarterUtil.getListOfOarWithAnySailorsOnIt(defaultSailors, crashBoat).size());
    }

    @Test
    void getListOfBabordOarWithAnySailorsOnItTest() {
        assertTrue(HeadquarterUtil.getListOfBabordOarWithAnySailorsOnIt(defaultSailors, defaultBoat).contains(new OarBoatEntity(6,4)));
        assertTrue(HeadquarterUtil.getListOfBabordOarWithAnySailorsOnIt(defaultSailors, defaultBoat).contains(new OarBoatEntity(6,0)));
        assertFalse(HeadquarterUtil.getListOfBabordOarWithAnySailorsOnIt(defaultSailors, defaultBoat).contains(new OarBoatEntity(3,0)));
        assertTrue(HeadquarterUtil.getListOfBabordOarWithAnySailorsOnIt(defaultSailors, defaultBoat).contains(new OarBoatEntity(3,4)));
        assertEquals(8, HeadquarterUtil.getListOfBabordOarWithAnySailorsOnIt(defaultSailors, defaultBoat).size());
        assertEquals(10, HeadquarterUtil.getListOfBabordOarWithAnySailorsOnIt(crashSailors, defaultBoat).size());
        assertEquals(0, HeadquarterUtil.getListOfBabordOarWithAnySailorsOnIt(defaultSailors, crashBoat).size());
    }

    @Test
    void getListOfTribordOarWithAnySailorsOnIt() {
        assertTrue(HeadquarterUtil.getListOfTribordOarWithAnySailorsOnIt(defaultSailors, defaultBoat).contains(new OarBoatEntity(6,4)));
        assertTrue(HeadquarterUtil.getListOfTribordOarWithAnySailorsOnIt(defaultSailors, defaultBoat).contains(new OarBoatEntity(6,0)));
        assertTrue(HeadquarterUtil.getListOfTribordOarWithAnySailorsOnIt(defaultSailors, defaultBoat).contains(new OarBoatEntity(3,0)));
        assertFalse(HeadquarterUtil.getListOfTribordOarWithAnySailorsOnIt(defaultSailors, defaultBoat).contains(new OarBoatEntity(3,4)));
        assertEquals(8, HeadquarterUtil.getListOfTribordOarWithAnySailorsOnIt(defaultSailors, defaultBoat).size());
        assertEquals(10, HeadquarterUtil.getListOfTribordOarWithAnySailorsOnIt(crashSailors, defaultBoat).size());
        assertEquals(0, HeadquarterUtil.getListOfTribordOarWithAnySailorsOnIt(defaultSailors, crashBoat).size());
    }


    @Test
    void generateOarTest() {
        assertTrue(HeadquarterUtil.generateOar(1, defaultSailors, defaultBoat).isPresent());
        assertEquals(Optional.of(new Oar(1)), HeadquarterUtil.generateOar(1, defaultSailors, defaultBoat));

    }


    @Test
    void getSailorOnRudderTest() {

        BoatEntity[] rudderEntities = {
                new OarBoatEntity(2,0),
                new OarBoatEntity(3,0),
                new OarBoatEntity(4,0),
                new OarBoatEntity(5,0),
                new OarBoatEntity(6,0),
                new OarBoatEntity(2,4),
                new OarBoatEntity(3,4),
                new OarBoatEntity(4,4),
                new OarBoatEntity(5,4),
                new OarBoatEntity(6,4),
                new RudderBoatEntity(12, 3)
        };

        List<Marin> rudderSailors = new ArrayList<>() {{
            add(new Marin(1,2,0,"marin1"));
            add(new Marin(2,3,0,"marin2"));
            add(new Marin(3,2,4,"marin3"));
            add(new Marin(4,3,4,"marin4"));
            add(new Marin(5,2,2,"marin5"));
            add(new Marin(6,3,2,"marin6"));
            add(new Marin(6,12,3,"marin6"));
        }};

        Boat rudderBoat = new Boat(defaultLife, defaultTransform,defaultName,defaultDeck,rudderEntities,defaultShape);

        var marin = HeadquarterUtil.getSailorOnRudder(rudderBoat, rudderSailors);

        assertTrue(marin.isPresent());
        assertEquals(new Marin(6,12,3,"marin6"), marin.get());
    }

    @Test
    void getRudderTest() {

        BoatEntity[] rudderEntities = {
                new OarBoatEntity(2,0),
                new OarBoatEntity(3,0),
                new OarBoatEntity(4,0),
                new OarBoatEntity(5,0),
                new OarBoatEntity(6,0),
                new OarBoatEntity(2,4),
                new OarBoatEntity(3,4),
                new OarBoatEntity(4,4),
                new OarBoatEntity(5,4),
                new OarBoatEntity(6,4),
                new RudderBoatEntity(12, 3)
        };

        List<Marin> rudderSailors = new ArrayList<>() {{
            add(new Marin(1,2,0,"marin1"));
            add(new Marin(2,3,0,"marin2"));
            add(new Marin(3,2,4,"marin3"));
            add(new Marin(4,3,4,"marin4"));
            add(new Marin(5,2,2,"marin5"));
            add(new Marin(6,3,2,"marin6"));
            add(new Marin(6,12,3,"marin6"));
        }};

        Boat rudderBoat = new Boat(defaultLife, defaultTransform,defaultName,defaultDeck,rudderEntities,defaultShape);

        var rudder = HeadquarterUtil.getRudder(rudderBoat);

        assertTrue(rudder.isPresent());
        assertEquals(new RudderBoatEntity(12, 3), rudder.get());
    }


    @Test
    void distanceBetweenTwoPointsTest() {
        assertEquals(Math.sqrt(8), HeadquarterUtil.distanceBetweenTwoPoints(new Point(1,1), new Point(3,3)), Config.EPSILON);
    }

    @Test
    void searchTheClosestSailorToAPointTest() {
        assertEquals(HeadquarterUtil.getSailorByHisID(defaultSailors, 6).get(),HeadquarterUtil.searchTheClosestSailorToAPoint(defaultSailors, new Point(11,3), new ArrayList<>()));
        assertEquals(HeadquarterUtil.getSailorByHisID(defaultSailors, 5).get(),HeadquarterUtil.searchTheClosestSailorToAPoint(defaultSailors, new Point(11,3), new ArrayList<>() {{add(6);}}));
    }



    @Test
    void generateRudderTest() {
        assertThrows(MaxAngleRudderException.class, () ->  HeadquarterUtil.generateRudder(10, Math.PI));
        assertThrows(MaxAngleRudderException.class, () ->  HeadquarterUtil.generateRudder(10, -Math.PI));
        assertEquals(Optional.of(new Turn(10, Math.PI/4)), HeadquarterUtil.generateRudder(10, Math.PI/4));
        assertEquals(Optional.of(new Turn(10, -Math.PI/4)), HeadquarterUtil.generateRudder(10, -Math.PI/4));
        assertEquals(Optional.of(new Turn(10, 0)), HeadquarterUtil.generateRudder(10, 0));
    }

    @Test
    void sailorIsOnOarTest() {
        assertTrue(sailorIsOnOar(defaultBoat, new Marin(100,6,4,"Marin100")));
        assertFalse(sailorIsOnOar(defaultBoat, new Marin(100,39,1,"Marin100")));
    }

    @Test
    void placesIsFreeTest() {
        assertTrue(placeIsFree(new Point(12,2), defaultSailors));
        assertFalse(placeIsFree(new Point(3,2), defaultSailors));
    }

    @Test
    void placeIsNotAnBoatEntityTest() {
        assertFalse(HeadquarterUtil.placeIsNotAnBoatEntity(new Point(12,2), defaultBoat));
        assertFalse(HeadquarterUtil.placeIsNotAnBoatEntity(new Point(12,3), defaultBoat));
        assertFalse(HeadquarterUtil.placeIsNotAnBoatEntity(new Point(2,0), defaultBoat));
        assertTrue(HeadquarterUtil.placeIsNotAnBoatEntity(new Point(12,1), defaultBoat));
    }

    @Test
    void getSailTest() {
        Boat currentBoat = new Boat(10, defaultTransform, "", new Deck(2,2), new BoatEntity[]{new SailBoatEntity(0,0,false)}, new Rectangle(2,2,0));
        assertEquals(Optional.of(new SailBoatEntity(0,0,false)), HeadquarterUtil.getSail(currentBoat));

        Boat secondBoat = new Boat(10, defaultTransform, "", new Deck(2,2), new BoatEntity[]{}, new Rectangle(2,2,0));
        assertEquals(Optional.empty(), HeadquarterUtil.getSail(secondBoat));

    }

    @Test
    void getSailorOnSailTest() {
        Boat currentBoat = new Boat(10, defaultTransform, "", new Deck(2,2), new BoatEntity[]{new SailBoatEntity(0,0,false)}, new Rectangle(2,2,0));
        List<Marin> sailorsOnSail = new ArrayList<>(){{add(new Marin(100,0,0,""));}};
        assertEquals(Optional.of(new Marin(100,0,0,"")), HeadquarterUtil.getSailorOnSail(currentBoat, sailorsOnSail));

        Boat secondBoat = new Boat(10, defaultTransform, "", new Deck(2,2), new BoatEntity[]{new SailBoatEntity(2,2,false)}, new Rectangle(2,2,0));
        assertEquals(Optional.empty(), HeadquarterUtil.getSailorOnSail(secondBoat, sailorsOnSail));
    }











}
