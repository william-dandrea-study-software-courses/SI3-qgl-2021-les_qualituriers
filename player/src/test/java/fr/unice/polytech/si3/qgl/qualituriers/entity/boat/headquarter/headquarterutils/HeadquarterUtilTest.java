package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.headquarterutils;

import fr.unice.polytech.si3.qgl.qualituriers.Config;
import fr.unice.polytech.si3.qgl.qualituriers.Deck;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.OarBoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.RudderBoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.exceptions.MovingSailorException;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Moving;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
                new OarBoatEntity(6,4)
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
    }

    @Test
    void getListOfBabordOarsTest() {
        assertEquals(5, HeadquarterUtil.getListOfBabordOars(defaultBoat).size());
        assertEquals(0, HeadquarterUtil.getListOfBabordOars(crashBoat).size());
    }

    @Test
    void getListOfTribordOarsTest() {
        assertEquals(5, HeadquarterUtil.getListOfTribordOars(defaultBoat).size());
        assertEquals(0, HeadquarterUtil.getListOfTribordOars(crashBoat).size());
    }

    @Test
    void getListOfPlaceWithAnyEntitiesOnItTest() {
        assertEquals(50, HeadquarterUtil.getListOfPlaceWithAnyEntitiesOnIt(defaultBoat).size());
    }

    @Test
    void getListOfSailorsOnBabordOarsTest() {
        assertEquals(2, HeadquarterUtil.getListOfSailorsOnBabordOars(defaultSailors, defaultBoat).size());
        assertEquals(0, HeadquarterUtil.getListOfSailorsOnBabordOars(crashSailors, defaultBoat).size());
        assertEquals(0, HeadquarterUtil.getListOfSailorsOnBabordOars(defaultSailors, crashBoat).size());
    }

    @Test
    void getListOfSailorsOnTribordOarsTest() {
        assertEquals(2, HeadquarterUtil.getListOfSailorsOnTribordOars(defaultSailors, defaultBoat).size());
        assertEquals(0, HeadquarterUtil.getListOfSailorsOnTribordOars(crashSailors, defaultBoat).size());
        assertEquals(0, HeadquarterUtil.getListOfSailorsOnTribordOars(defaultSailors, crashBoat).size());
    }

    @Test
    void getListOfSailorsOnOarsTest() {
        assertEquals(4, HeadquarterUtil.getListOfSailorsOnOars(defaultSailors, defaultBoat).size());
        assertEquals(0, HeadquarterUtil.getListOfSailorsOnOars(crashSailors, defaultBoat).size());
        assertEquals(0, HeadquarterUtil.getListOfSailorsOnOars(defaultSailors, crashBoat).size());
    }

    @Test
    void getListOfSailorsOnAnyOarTest() {
        assertEquals(2, HeadquarterUtil.getListOfSailorsOnAnyOar(defaultSailors, defaultBoat).size());
        assertEquals(0, HeadquarterUtil.getListOfSailorsOnAnyOar(crashSailors, defaultBoat).size());
        assertEquals(6, HeadquarterUtil.getListOfSailorsOnAnyOar(defaultSailors, crashBoat).size());
    }

    @Test
    void getListOfOarWithAnySailorsOnItTest() {
        assertEquals(6, HeadquarterUtil.getListOfOarWithAnySailorsOnIt(defaultSailors, defaultBoat).size());
        assertEquals(10, HeadquarterUtil.getListOfOarWithAnySailorsOnIt(crashSailors, defaultBoat).size());
        assertEquals(0, HeadquarterUtil.getListOfOarWithAnySailorsOnIt(defaultSailors, crashBoat).size());
    }


    @Test
    void generateOarTest() {
        assertTrue(HeadquarterUtil.generateOar(1, defaultSailors, defaultBoat).isPresent());
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




}
