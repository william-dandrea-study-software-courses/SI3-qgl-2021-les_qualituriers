package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.headquarterutils;

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
                new RudderBoatEntity(11,2),
                new SailBoatEntity(6,2, false),

        };
        Shape completeBoatShape = new Rectangle(5, 12, 0);
        completeBoat = new Boat(defaultLife, defaultTransform, completeBoatName, completeBoatDeck, completeBoatEntities, completeBoatShape);
    }

    @Test
    void tryToMoveToFarPoint() {

        BoatPathFinding boatPathFinding = new BoatPathFinding(defaultSailors,completeBoat, 18, new Point(11,2));
        Point firstPoint = boatPathFinding.generateClosestPoint();
        HeadquarterUtil.getSailorByHisID(defaultSailors, 18).get().setPosition((int) firstPoint.getX(), (int) firstPoint.getY());

        assertEquals(new Point(11,2), boatPathFinding.generateClosestPoint());

    }

    @Test
    void tryToMoveToFarPointTopLeftToBottomRight() {

        int xStart = 0;
        int yStart = 0;
        int xFinal = 31;
        int yFinal = 10;


        Deck actualDeck = new Deck(11, 32);
        BoatEntity[] actualEntities = {
                new RudderBoatEntity(xFinal,yFinal),
                new SailBoatEntity(6,2, false)
        };
        Shape actualBoatShape = new Rectangle(11, 32, 0);
        Boat actualBoat = new Boat(defaultLife,defaultTransform, defaultName, actualDeck, actualEntities, actualBoatShape);

        List<Marin> actualSailors = new ArrayList<>() {{ add(new Marin(0, xStart, yStart, "marin0"));}};


        BoatPathFinding boatPathFinding = new BoatPathFinding(actualSailors,actualBoat, 0, new Point(xFinal,yFinal));
        Point firstPoint = boatPathFinding.generateClosestPoint();
        HeadquarterUtil.getSailorByHisID(actualSailors, 0).get().setPosition((int) firstPoint.getX(), (int) firstPoint.getY());

        firstPoint = boatPathFinding.generateClosestPoint(); HeadquarterUtil.getSailorByHisID(actualSailors, 0).get().setPosition((int) firstPoint.getX(), (int) firstPoint.getY());
        firstPoint = boatPathFinding.generateClosestPoint(); HeadquarterUtil.getSailorByHisID(actualSailors, 0).get().setPosition((int) firstPoint.getX(), (int) firstPoint.getY());
        firstPoint = boatPathFinding.generateClosestPoint(); HeadquarterUtil.getSailorByHisID(actualSailors, 0).get().setPosition((int) firstPoint.getX(), (int) firstPoint.getY());
        firstPoint = boatPathFinding.generateClosestPoint(); HeadquarterUtil.getSailorByHisID(actualSailors, 0).get().setPosition((int) firstPoint.getX(), (int) firstPoint.getY());
        firstPoint = boatPathFinding.generateClosestPoint(); HeadquarterUtil.getSailorByHisID(actualSailors, 0).get().setPosition((int) firstPoint.getX(), (int) firstPoint.getY());
        firstPoint = boatPathFinding.generateClosestPoint(); HeadquarterUtil.getSailorByHisID(actualSailors, 0).get().setPosition((int) firstPoint.getX(), (int) firstPoint.getY());

        assertEquals(new Point(xFinal,yFinal), boatPathFinding.generateClosestPoint());

    }


    @Test
    void generateClosestPointWhereWeCanMoveSailor() {
        defaultSailors.add(new Marin(30, 11, 4, "marinTest"));
        BoatPathFinding boatPathFinding = new BoatPathFinding(defaultSailors,completeBoat, 30, new Point(9,3));
        assertEquals(new Point(9,3), boatPathFinding.generateClosestPoint());
    }

    @Test
    void generateClosestPointWhereWeCantMoveSailorTopLeft() {

        defaultSailors.add(new Marin(30, 7, 4, "marinTest"));

        BoatPathFinding boatPathFinding = new BoatPathFinding(defaultSailors,completeBoat, 30, new Point(0,0));
        assertEquals(new Point(4,2), boatPathFinding.generateClosestPoint());


    }


    @Test
    void getTheInitialCloserPositionWithCloserPoint() {

        BoatEntity[] actualEntities = {};
        List<Marin> actualSailors = new ArrayList<>() {{ add(new Marin(0, 0, 0, "marin0")); }};
        Boat actualBoat = new Boat(defaultLife, defaultTransform, defaultName, defaultDeck, actualEntities , defaultShape);

        BoatPathFinding boatPathFinding = new BoatPathFinding(actualSailors, actualBoat, 0, new Point(2,2));
        assertEquals(new Point(2, 2), boatPathFinding.getTheInitialCloserPosition());

    }

    @Test
    void getTheInitialCloserPositionXPositiveYPositive() {

        BoatEntity[] actualEntities = {};
        List<Marin> actualSailors = new ArrayList<>() {{ add(new Marin(0, 0, 0, "marin0")); }};
        Boat actualBoat = new Boat(defaultLife, defaultTransform, defaultName, defaultDeck, actualEntities , defaultShape);

        BoatPathFinding boatPathFinding = new BoatPathFinding(actualSailors, actualBoat, 0, new Point(11,4));
        assertEquals(new Point(5, 4), boatPathFinding.getTheInitialCloserPosition());

    }

    @Test
    void getTheInitialCloserPositionXPositiveYNegative() {

        BoatEntity[] actualEntities = {};
        List<Marin> actualSailors = new ArrayList<>() {{ add(new Marin(0, 0, 4, "marin0")); }};
        Boat actualBoat = new Boat(defaultLife, defaultTransform, defaultName, defaultDeck, actualEntities , defaultShape);

        BoatPathFinding boatPathFinding = new BoatPathFinding(actualSailors, actualBoat, 0, new Point(11,0));
        assertEquals(new Point(5, 0), boatPathFinding.getTheInitialCloserPosition());

    }

    @Test
    void getTheInitialCloserPositionXNegativeYPositive() {

        BoatEntity[] actualEntities = {};
        List<Marin> actualSailors = new ArrayList<>() {{ add(new Marin(0, 11, 4, "marin0")); }};
        Boat actualBoat = new Boat(defaultLife, defaultTransform, defaultName, defaultDeck, actualEntities , defaultShape);

        BoatPathFinding boatPathFinding = new BoatPathFinding(actualSailors, actualBoat, 0, new Point(0,0));
        assertEquals(new Point(6, 0), boatPathFinding.getTheInitialCloserPosition());

    }

    @Test
    void getTheInitialCloserPositionXNegativeYNegative() {

        BoatEntity[] actualEntities = {};
        List<Marin> actualSailors = new ArrayList<>() {{ add(new Marin(0, 11, 0, "marin0")); }};
        Boat actualBoat = new Boat(defaultLife, defaultTransform, defaultName, defaultDeck, actualEntities , defaultShape);

        BoatPathFinding boatPathFinding = new BoatPathFinding(actualSailors, actualBoat, 0, new Point(0,4));
        assertEquals(new Point(6, 4), boatPathFinding.getTheInitialCloserPosition());

    }






    @Test
    void generatePotentialsPointsForSailorTopLeftTest() {
        BoatEntity[] actualEntities = {};
        List<Marin> actualSailors = new ArrayList<>() {{ add(new Marin(0, 0, 0, "marin0")); }};
        Boat actualBoat = new Boat(defaultLife, defaultTransform, defaultName, defaultDeck, actualEntities , defaultShape);

        BoatPathFinding boatPathFinding = new BoatPathFinding(actualSailors, actualBoat, 0, new Point(11,4));
        //System.out.println(boatPathFinding.generatePotentialsPointsForMovingInJustOneSide());

        List<Point> points = new ArrayList<>() {{
            add(new Point(1,0));
            add(new Point(1,1));
            add(new Point(0,1));
            add(new Point(0,2));
            add(new Point(1,2));
            add(new Point(2,2));
            add(new Point(2,1));
            add(new Point(2,0));
            add(new Point(3,0));
            add(new Point(3,1));
            add(new Point(3,2));
            add(new Point(3,3));
            add(new Point(2,3));
            add( new Point(1,3));
            add( new Point(0,3));
            add( new Point(0,4));
            add(new Point(1,4));
            add(new Point(2,4));
            add(new Point(3,4));
            add(new Point(4,4));
            add(new Point(4,3));
            add(new Point(4,2));
            add(new Point(4,1));
            add(new Point(4,0));
            add(new Point(5,0));
            add(new Point(5,1));
            add(new Point(5,2));
            add(new Point(5,3));
            add(new Point(5,4));
            add(new Point(5,5));
            add( new Point(4,5));
            add(new Point(3,5));
            add(new Point(2,5));
            add(new Point(1,5));
            add(new Point(0,5));
        }};

        assertEquals(points, boatPathFinding.generateTurningAroundPointCoefficients());
    }
}
