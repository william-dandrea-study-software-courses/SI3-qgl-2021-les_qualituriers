package fr.unice.polytech.si3.qgl.qualituriers.game.headquarter.headquarterutils;

import fr.unice.polytech.si3.qgl.qualituriers.Deck;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.*;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoatPathFindingTest {

    private Boat defaultBoat;
    private Boat completeBoat;
    private List<Marin> defaultSailors;
    private int defaultLife;
    private Transform defaultTransform;
    private String defaultName;
    private Deck defaultDeck;
    private Shape defaultShape;

    @BeforeEach
    void setUp() {
        defaultLife = 100;
        defaultTransform = new Transform(0, 0, 0);
        defaultName = "defaultName";
        defaultDeck = new Deck(5, 12);
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
        defaultShape = new Rectangle(5, 12, 0);
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
                new RudderBoatEntity(11, 2),
                new SailBoatEntity(6, 2, true),

        };
        Shape completeBoatShape = new Rectangle(5, 12, 0);
        completeBoat = new Boat(defaultLife, defaultTransform, completeBoatName, completeBoatDeck, completeBoatEntities, completeBoatShape);
    }

    @Test
    void generateTurningAroundPointCoefficientsTest() {
        BoatPathFinding boatPathFinding = new BoatPathFinding(defaultSailors, defaultBoat, 18, new Point(11,2));
        List<Point> points = boatPathFinding.generateTurningAroundPointCoefficients();

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


        Boat actualBoat = new Boat(defaultLife, defaultTransform, defaultName, new Deck(10,10), new BoatEntity[]{}, new Rectangle(10,10, 0));
        List<Marin> actualSailors = new ArrayList<>() {{add(new Marin(0,0,0, "marinTest"));}};

        BoatPathFinding boatPathFinding = new BoatPathFinding(actualSailors, actualBoat, 0, new Point(9,9));

        assertEquals(new Point(3,2), boatPathFinding.generateTheNearestPoint());

        actualSailors.add(new Marin(1,3,2,"MarinRelou"));

        assertEquals(new Point(2,3), boatPathFinding.generateTheNearestPoint());

    }

    @Test
    void generateTheNearestPointTestBottomLeftToTopRight() {


        Boat actualBoat = new Boat(defaultLife, defaultTransform, defaultName, new Deck(10,10), new BoatEntity[]{}, new Rectangle(10,10, 0));
        List<Marin> actualSailors = new ArrayList<>() {{add(new Marin(0,9,0, "marinTest"));}};

        BoatPathFinding boatPathFinding = new BoatPathFinding(actualSailors, actualBoat, 0, new Point(0,9));


        assertEquals(new Point(6,2), boatPathFinding.generateTheNearestPoint());

        actualSailors.add(new Marin(1,6,2,"MarinRelou"));
        boatPathFinding = new BoatPathFinding(actualSailors, actualBoat, 0, new Point(0,9));

        assertEquals(new Point(7,3), boatPathFinding.generateTheNearestPoint());


    }

    @Test
    void generateTheNearestPointTestBottomRightToTopLeft() {


        Boat actualBoat = new Boat(defaultLife, defaultTransform, defaultName, new Deck(10,10), new BoatEntity[]{}, new Rectangle(10,10, 0));
        List<Marin> actualSailors = new ArrayList<>() {{add(new Marin(0,9,9, "marinTest"));}};

        BoatPathFinding boatPathFinding = new BoatPathFinding(actualSailors, actualBoat, 0, new Point(0,0));

        System.out.println(boatPathFinding.generateTheNearestPoint());
        assertEquals(new Point(6,7), boatPathFinding.generateTheNearestPoint());


        actualSailors.add(new Marin(1,6,7,"MarinRelou"));
        boatPathFinding = new BoatPathFinding(actualSailors, actualBoat, 0, new Point(0,0));

        assertEquals(new Point(7,6), boatPathFinding.generateTheNearestPoint());


    }

}
