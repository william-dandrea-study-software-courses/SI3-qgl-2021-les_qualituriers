package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.turnboat;

import fr.unice.polytech.si3.qgl.qualituriers.Deck;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.BoatEntities;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.turnboat.turnboatutils.BabordTribordAngle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shapes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TurnBoatTest {

    private Transform defaultTransform;
    private final List<Marin> defaultListSailors = new ArrayList<>();
    private BoatEntity[] defaultListBoatEntities;
    private Boat defaultBoat;
    private Deck defaultDeck;


    int defaultLife = 100;
    String defaultName = "defaultBoat";
    Shape defaultShape = new Shape(Shapes.POLYGON) {
        @Override
        public boolean isIn(Point position) {
            return false;
        }
    };

    @BeforeEach
    void setUp() {

        BoatEntity defaultBoatEntity = new BoatEntity(BoatEntities.OAR, 0, 0) {
        };
        Marin defaultSailor = new Marin(0, 0, 0, "defaultSailor");
        defaultTransform = new Transform(new Point(0,0), 0.0);

        defaultListBoatEntities = new BoatEntity[]{defaultBoatEntity, new BoatEntity(BoatEntities.OAR, 0,1) {} };



        defaultListSailors.add(defaultSailor);
        defaultListSailors.add(new Marin(1,0,1,"marin1"));

        defaultDeck = new Deck(2, 4);

        defaultBoat = new Boat(defaultLife, defaultTransform, defaultName, defaultDeck, defaultListBoatEntities, defaultShape);


    }

    @Test
    void turnBoatWhenAngleOfDeriveEqual0() {

        double finalOrientationBoat = 0.0;
        TurnBoat turnBoat = new TurnBoat(finalOrientationBoat, defaultBoat, defaultListSailors);

        assertTrue(turnBoat.turnBoat().isEmpty());
    }

    @Test
    void turnBoatWhenAngleOfDeriveEqualFloat1() {

        Deck actualDeck = new Deck(2,4);
        BoatEntity[] actualListBoatEntities = {
                new BoatEntity(BoatEntities.OAR, 0,0){},
                new BoatEntity(BoatEntities.OAR, 0,1){},
                new BoatEntity(BoatEntities.OAR, 1,0){},
                new BoatEntity(BoatEntities.OAR, 1,1){},
                new BoatEntity(BoatEntities.OAR, 3,0){},
                new BoatEntity(BoatEntities.OAR, 3,1){},
        };

        Boat actualBoat = new Boat(defaultLife, defaultTransform, defaultName, actualDeck, actualListBoatEntities, defaultShape);

        List<Marin> actualListSailors = new ArrayList<>() {{
            add(new Marin(1,0,0, "marin1"));
            add(new Marin(2,0,1, "marin2"));
            add(new Marin(3,2,0, "marin3"));
            add(new Marin(4,2,1, "marin4"));
        }};


        TurnBoat turnBoat = new TurnBoat(0.01, actualBoat, actualListSailors);
        turnBoat.turnBoat();
        assertEquals(0, turnBoat.getFinalDispositionOfOars().getAngle());

    }

    @Test
    void turnBoatWhenAngleOfDeriveEqualFloat2() {

        Deck actualDeck = new Deck(2,4);
        BoatEntity[] actualListBoatEntities = {
                new BoatEntity(BoatEntities.OAR, 0,0){},
                new BoatEntity(BoatEntities.OAR, 0,1){},
                new BoatEntity(BoatEntities.OAR, 1,0){},
                new BoatEntity(BoatEntities.OAR, 1,1){},
                new BoatEntity(BoatEntities.OAR, 3,0){},
                new BoatEntity(BoatEntities.OAR, 3,1){},
        };

        Boat actualBoat = new Boat(defaultLife, defaultTransform, defaultName, actualDeck, actualListBoatEntities, defaultShape);

        List<Marin> actualListSailors = new ArrayList<>() {{
            add(new Marin(1,0,0, "marin1"));
            add(new Marin(2,0,1, "marin2"));
            add(new Marin(3,2,0, "marin3"));
            add(new Marin(4,2,1, "marin4"));
        }};


        TurnBoat turnBoat = new TurnBoat(-0.01, actualBoat, actualListSailors);
        turnBoat.turnBoat();
        assertEquals(0, turnBoat.getFinalDispositionOfOars().getAngle());

    }

    @Test
    void turnBoatWhenAngleOfDeriveEqualFloat3() {

        Deck actualDeck = new Deck(2,4);
        BoatEntity[] actualListBoatEntities = {
                new BoatEntity(BoatEntities.OAR, 0,0){},
                new BoatEntity(BoatEntities.OAR, 0,1){},
                new BoatEntity(BoatEntities.OAR, 1,0){},
                new BoatEntity(BoatEntities.OAR, 1,1){},
                new BoatEntity(BoatEntities.OAR, 3,0){},
                new BoatEntity(BoatEntities.OAR, 3,1){},
        };

        Boat actualBoat = new Boat(defaultLife, defaultTransform, defaultName, actualDeck, actualListBoatEntities, defaultShape);

        List<Marin> actualListSailors = new ArrayList<>() {{
            add(new Marin(1,0,0, "marin1"));
            add(new Marin(2,0,1, "marin2"));
            add(new Marin(3,2,0, "marin3"));
            add(new Marin(4,2,1, "marin4"));
        }};


        TurnBoat turnBoat = new TurnBoat((Math.PI/6) - 0.01, actualBoat, actualListSailors);
        turnBoat.turnBoat();
        assertEquals((Math.PI/6), turnBoat.getFinalDispositionOfOars().getAngle());

        turnBoat = new TurnBoat((Math.PI/6) + 0.01, actualBoat, actualListSailors);

        turnBoat.turnBoat();
        assertEquals((Math.PI/6), turnBoat.getFinalDispositionOfOars().getAngle());

    }

    @Test
    void turnBoatWhenAngleOfDeriveEqualFloat4() {

        Deck actualDeck = new Deck(2,4);
        BoatEntity[] actualListBoatEntities = {
                new BoatEntity(BoatEntities.OAR, 0,0){},
                new BoatEntity(BoatEntities.OAR, 0,1){},
                new BoatEntity(BoatEntities.OAR, 1,0){},
                new BoatEntity(BoatEntities.OAR, 1,1){},
                new BoatEntity(BoatEntities.OAR, 3,0){},
                new BoatEntity(BoatEntities.OAR, 3,1){},
        };

        Boat actualBoat = new Boat(defaultLife, defaultTransform, defaultName, actualDeck, actualListBoatEntities, defaultShape);

        List<Marin> actualListSailors = new ArrayList<>() {{
            add(new Marin(1,0,0, "marin1"));
            add(new Marin(2,0,1, "marin2"));
            add(new Marin(3,2,0, "marin3"));
            add(new Marin(4,2,1, "marin4"));
        }};


        TurnBoat turnBoat = new TurnBoat(- (Math.PI/6) + 0.01, actualBoat, actualListSailors);
        turnBoat.turnBoat();
        assertEquals(-(Math.PI/6), turnBoat.getFinalDispositionOfOars().getAngle());

        turnBoat = new TurnBoat(- (Math.PI/6) - 0.01, actualBoat, actualListSailors);
        turnBoat.turnBoat();
        assertEquals(-(Math.PI/6), turnBoat.getFinalDispositionOfOars().getAngle());

    }


    @Test
    void turnBoatWhenAngleOfDeriveEqualFloat5() {

        Deck actualDeck = new Deck(2,4);
        BoatEntity[] actualListBoatEntities = {
                new BoatEntity(BoatEntities.OAR, 0,0){},
                new BoatEntity(BoatEntities.OAR, 0,1){},
                new BoatEntity(BoatEntities.OAR, 1,0){},
                new BoatEntity(BoatEntities.OAR, 1,1){},
                new BoatEntity(BoatEntities.OAR, 3,0){},
                new BoatEntity(BoatEntities.OAR, 3,1){},
        };

        Boat actualBoat = new Boat(defaultLife, defaultTransform, defaultName, actualDeck, actualListBoatEntities, defaultShape);

        List<Marin> actualListSailors = new ArrayList<>() {{
            add(new Marin(1,0,0, "marin1"));
            add(new Marin(2,0,1, "marin2"));
            add(new Marin(3,2,0, "marin3"));
            add(new Marin(4,2,1, "marin4"));
        }};


        TurnBoat turnBoat = new TurnBoat(- (Math.PI/2) + 0.01, actualBoat, actualListSailors);
        turnBoat.turnBoat();
        assertEquals(-(Math.PI/2), turnBoat.getFinalDispositionOfOars().getAngle());

        turnBoat = new TurnBoat( (Math.PI/2) - 0.01, actualBoat, actualListSailors);
        turnBoat.turnBoat();
        assertEquals((Math.PI/2), turnBoat.getFinalDispositionOfOars().getAngle());

    }

    @Test
    void actualizeDifferenceOfAngleWithAngleOf0() {

        double finalOrientationBoat = 0.0;

        TurnBoat turnBoat = new TurnBoat(finalOrientationBoat, defaultBoat, defaultListSailors);


        assertEquals(0.0, turnBoat.generateDifferenceOfAngle(false));
    }

    @Test
    void actualizeDifferenceOfAngleWithAngleOfPiDividedBy2() {

        double finalOrientationBoat = Math.PI/2;

        TurnBoat turnBoat = new TurnBoat(finalOrientationBoat, defaultBoat, defaultListSailors);


        assertEquals(Math.PI/2, turnBoat.generateDifferenceOfAngle(false));
    }

    @Test
    void actualizeDifferenceOfAngleWithAngleOfMinusPiDividedBy2() {

        double finalOrientationBoat = -Math.PI/2;

        TurnBoat turnBoat = new TurnBoat(finalOrientationBoat, defaultBoat, defaultListSailors);


        assertEquals(-Math.PI/2, turnBoat.generateDifferenceOfAngle(false));
    }

    @Test
    void actualizeDifferenceOfAngleWithSpecialAngleNegative() {

        Transform actualPosition = new Transform(new Point(0,0), -3*Math.PI / 4);
        double finalOrientationBoat = 3*Math.PI/4;

        Boat actualBoat = new Boat(defaultLife, actualPosition, defaultName, defaultDeck, defaultListBoatEntities, defaultShape);
        TurnBoat turnBoat = new TurnBoat(finalOrientationBoat, actualBoat, defaultListSailors);

        assertEquals(-2 * Math.PI/4, turnBoat.generateDifferenceOfAngle(false));
    }

    @Test
    void actualizeDifferenceOfAngleWithSpecialAnglePositive() {

        Transform actualPosition = new Transform(new Point(0,0), -Math.PI / 4);
        double finalOrientationBoat = Math.PI/4;

        Boat actualBoat = new Boat(defaultLife, actualPosition, defaultName, defaultDeck, defaultListBoatEntities, defaultShape);
        TurnBoat turnBoat = new TurnBoat(finalOrientationBoat, actualBoat, defaultListSailors);


        assertEquals(2 * Math.PI/4, turnBoat.generateDifferenceOfAngle(false));
    }

    @Test
    void actualizeDifferenceOfAngleWithOnePiDivided2() {

        Transform actualPosition = new Transform(new Point(0,0), -Math.PI/2);
        double finalOrientationBoat = Math.PI/4;

        Boat actualBoat = new Boat(defaultLife, actualPosition, defaultName, defaultDeck, defaultListBoatEntities, defaultShape);
        TurnBoat turnBoat = new TurnBoat(finalOrientationBoat, actualBoat, defaultListSailors);


        assertEquals(3 * Math.PI/4, turnBoat.generateDifferenceOfAngle(false));
    }

    @Test
    void actualizeDifferenceOfAngleWithTwoPiDivided2() {

        Transform actualPosition = new Transform(new Point(0,0), -Math.PI/2);
        double finalOrientationBoat = Math.PI/2;

        Boat actualBoat = new Boat(defaultLife, actualPosition, defaultName, defaultDeck, defaultListBoatEntities, defaultShape);
        TurnBoat turnBoat = new TurnBoat(finalOrientationBoat, actualBoat, defaultListSailors);

        assertTrue(Math.PI == turnBoat.generateDifferenceOfAngle(false) || -Math.PI == turnBoat.generateDifferenceOfAngle(false));
    }

    @Test
    void actualizeDifferenceOfAngleWithTwoPi() {

        Transform actualPosition = new Transform(new Point(0,0), -Math.PI);
        double finalOrientationBoat = Math.PI;

        Boat actualBoat = new Boat(defaultLife, actualPosition, defaultName, defaultDeck, defaultListBoatEntities, defaultShape);
        TurnBoat turnBoat = new TurnBoat(finalOrientationBoat, actualBoat, defaultListSailors);


        assertEquals(0.0, turnBoat.generateDifferenceOfAngle(false));
    }

    @Test
    void actualizeDifferenceOfAngleWithPiAnd0() {

        Transform actualPosition = new Transform(new Point(0,0), -Math.PI);
        double finalOrientationBoat = 0;

        Boat actualBoat = new Boat(defaultLife, actualPosition, defaultName, defaultDeck, defaultListBoatEntities, defaultShape);
        TurnBoat turnBoat = new TurnBoat(finalOrientationBoat, actualBoat, defaultListSailors);


        assertEquals(Math.PI, turnBoat.generateDifferenceOfAngle(false));
    }

    @Test
    void actualizeDifferenceOfAngleWithPiAnd0AndWantNegative() {

        Transform actualPosition = new Transform(new Point(0,0), -Math.PI);
        double finalOrientationBoat = 0;

        Boat actualBoat = new Boat(defaultLife, actualPosition, defaultName, defaultDeck, defaultListBoatEntities, defaultShape);
        TurnBoat turnBoat = new TurnBoat(finalOrientationBoat, actualBoat, defaultListSailors);
        turnBoat.generateDifferenceOfAngle(true);

        assertEquals(-Math.PI, turnBoat.generateDifferenceOfAngle(true));
    }

    @Test
    void generateListOfPossibleAnglesTestWithClassicInput() {

        double finalOrientationBoat = 0.0;
        TurnBoat turnBoat = new TurnBoat(finalOrientationBoat, defaultBoat, defaultListSailors);

        turnBoat.generateListOfPossibleAngles(0).forEach(eachValue -> assertTrue(eachValue.getAngle() >= -Math.PI / 2 && eachValue.getAngle() <= Math.PI));

        assertEquals(3, turnBoat.generateListOfPossibleAngles(0).size());

    }

    @Test
    void generateListOfPossibleAnglesTestWithUnpairInput() {

        BoatEntity[] actualListBoatEntities = {new BoatEntity(BoatEntities.OAR, 0,0) {}};


        Boat actualBoat = new Boat(defaultLife, defaultTransform, defaultName, defaultDeck, actualListBoatEntities, defaultShape);


        TurnBoat turnBoat = new TurnBoat(0.0,actualBoat, defaultListSailors);

        assertThrows(IllegalArgumentException.class, () -> turnBoat.generateListOfPossibleAngles(0));


    }

    @Test
    void generateListOfPossibleAnglesTestWith0Input() {

        BoatEntity[] actualListBoatEntities = {};

        Boat actualBoat = new Boat(defaultLife, defaultTransform, defaultName, defaultDeck, actualListBoatEntities, defaultShape);
        TurnBoat turnBoat = new TurnBoat(0.0,actualBoat, defaultListSailors);

        assertTrue(turnBoat.generateListOfPossibleAngles(0).isEmpty());


    }

    @Test
    void generateListOfPossibleAnglesTestWithClassicInputAndDifferentField() {

        //defaultListBoatEntities.add(new BoatEntity(BoatEntities.OAR, 2,2) {});
        //defaultListBoatEntities.add(new BoatEntity(BoatEntities.OAR, 2,1) {});

        TurnBoat turnBoat = new TurnBoat(0.0,defaultBoat, defaultListSailors);

        turnBoat.generateListOfPossibleAngles(1).forEach(eachValue -> assertTrue(eachValue.getAngle() >= -Math.PI / 2 && eachValue.getAngle() <= Math.PI));


        assertEquals(3, turnBoat.generateListOfPossibleAngles(0).size());

    }


    @Test
    void actualizeListSailorsOnOarTestWithClassicsValues() {


        TurnBoat turnBoat = new TurnBoat(0.0,defaultBoat, defaultListSailors);

        assertEquals(2, turnBoat.getSailorsOnOar().size());
        assertEquals(1, turnBoat.getSailorsOnOarAtBabord().size());
        assertEquals(1, turnBoat.getSailorsOnOarAtTribord().size());

    }

    @Test
    void actualizeListSailorsOnOarTestWith0Oar() {


        BoatEntity[] actualListBoatEntities = {};
        Boat actualBoat = new Boat(defaultLife, defaultTransform, defaultName, defaultDeck, actualListBoatEntities, defaultShape);

        TurnBoat turnBoat = new TurnBoat(0.0,actualBoat, defaultListSailors);

        assertEquals(0, turnBoat.getSailorsOnOar().size());
        assertEquals(0, turnBoat.getSailorsOnOarAtBabord().size());
        assertEquals(0, turnBoat.getSailorsOnOarAtTribord().size());

    }

    @Test
    void actualizeListSailorsOnOarTestWith0Sailors() {


        List<Marin> actualSailors = new ArrayList<>();

        TurnBoat turnBoat = new TurnBoat(0.0,defaultBoat, actualSailors);

        assertEquals(0, turnBoat.getSailorsOnOar().size());
        assertEquals(0, turnBoat.getSailorsOnOarAtBabord().size());
        assertEquals(0, turnBoat.getSailorsOnOarAtTribord().size());

    }

    @Test
    void actualizeListSailorsOnOarTestWithManySailors() {


        defaultListSailors.add(new Marin(4,2,2, "name3"));
        TurnBoat turnBoat = new TurnBoat(0.0,defaultBoat, defaultListSailors);

        assertEquals(2, turnBoat.getSailorsOnOar().size());
        assertEquals(1, turnBoat.getSailorsOnOarAtBabord().size());
        assertEquals(1, turnBoat.getSailorsOnOarAtTribord().size());

    }

    @Test
    void actualizeListSailorsOnOarTestWithManyOars() {


        BoatEntity[] actualListBoatEntities = {
                new BoatEntity(BoatEntities.OAR, 0,0){},
                new BoatEntity(BoatEntities.OAR, 0,1){},
                new BoatEntity(BoatEntities.OAR, 1,0){},
                new BoatEntity(BoatEntities.OAR, 1,1){},
        };
        Boat actualBoat = new Boat(defaultLife, defaultTransform, defaultName, defaultDeck, actualListBoatEntities, defaultShape);

        TurnBoat turnBoat = new TurnBoat(0.0,actualBoat, defaultListSailors);

        assertEquals(2, turnBoat.getSailorsOnOar().size());
        assertEquals(1, turnBoat.getSailorsOnOarAtBabord().size());
        assertEquals(1, turnBoat.getSailorsOnOarAtTribord().size());

    }

    @Test
    void sailorsOnAnyEntitiesTest() {

        // BoatEntities :
        //  - BoatEntity(BoatEntities.OAR, 0,0) {};
        //  - BoatEntity(BoatEntities.OAR, 0,1) {};

        // Sailors :
        //  - Marin(id : 0, 0, 0, "defaultSailor");
        //  - Marin(id : 1, 0, 1, "marin1")

        defaultListSailors.add(new Marin(2,1,1,"marin2"));
        TurnBoat turnBoat = new TurnBoat(Math.PI / 4,defaultBoat, defaultListSailors);

        System.out.println(turnBoat.getSailorsOnAnyEntities());
        assertEquals(1, turnBoat.getSailorsOnAnyEntities().size());
    }

    @Test
    void sailorsOnAnyEntitiesWithAnySailorsNotOnOarTest() {

        // BoatEntities :
        //  - BoatEntity(BoatEntities.OAR, 0,0) {};
        //  - BoatEntity(BoatEntities.OAR, 0,1) {};

        // Sailors :
        //  - Marin(id : 0, 0, 0, "defaultSailor");
        //  - Marin(id : 1, 0, 1, "marin1")

        TurnBoat turnBoat = new TurnBoat(Math.PI / 4,defaultBoat, defaultListSailors);

        System.out.println(turnBoat.getSailorsOnAnyEntities());
        assertEquals(0, turnBoat.getSailorsOnAnyEntities().size());
    }


    @Test
    void generateIdealRepartitionOfOarsTestLeftRotation() {

        Deck actualDeck = new Deck(3,6);
        BoatEntity[] actualListBoatEntities = {
                new BoatEntity(BoatEntities.OAR, 0,0){},
                new BoatEntity(BoatEntities.OAR, 0,2){},
                new BoatEntity(BoatEntities.OAR, 2,0){},
                new BoatEntity(BoatEntities.OAR, 2,2){},
                new BoatEntity(BoatEntities.OAR, 4,0){},
                new BoatEntity(BoatEntities.OAR, 4,2){}
        };

        Boat actualBoat = new Boat(defaultLife, defaultTransform, defaultName, actualDeck, actualListBoatEntities, defaultShape);

        List<Marin> actualListSailors = new ArrayList<>() {{
            add(new Marin(1,0,0, "marin1"));
            add(new Marin(2,0,2, "marin2"));
            add(new Marin(3,2,0, "marin3"));
            add(new Marin(4,3,1, "marin4"));
            add(new Marin(5,4,1, "marin5"));
            add(new Marin(6,4,2, "marin6"));
        }};


        TurnBoat turnBoat = new TurnBoat(2 * Math.PI / 6, actualBoat, actualListSailors);
        turnBoat.turnBoat();

        BabordTribordAngle finalRepartition = turnBoat.generateIdealRepartitionOfOars(2 * Math.PI / 6);

        assertEquals(new BabordTribordAngle(1, 3, 2 * Math.PI / 6), finalRepartition);
    }

    @Test
    void generateIdealRepartitionOfOarsTestRightRotation() {


        Deck actualDeck = new Deck(3,6);
        BoatEntity[] actualListBoatEntities = {
                new BoatEntity(BoatEntities.OAR, 0,0){},
                new BoatEntity(BoatEntities.OAR, 0,2){},
                new BoatEntity(BoatEntities.OAR, 2,0){},
                new BoatEntity(BoatEntities.OAR, 2,2){},
                new BoatEntity(BoatEntities.OAR, 4,0){},
                new BoatEntity(BoatEntities.OAR, 4,2){}
        };

        Boat actualBoat = new Boat(defaultLife, defaultTransform, defaultName, actualDeck, actualListBoatEntities, defaultShape);

        List<Marin> actualListSailors = new ArrayList<>() {{
            add(new Marin(1,0,0, "marin1"));
            add(new Marin(2,0,2, "marin2"));
            add(new Marin(3,2,0, "marin3"));
            add(new Marin(4,3,1, "marin4"));
            add(new Marin(5,4,1, "marin5"));
            add(new Marin(6,4,2, "marin6"));
        }};


        TurnBoat turnBoat = new TurnBoat(2 * Math.PI / 6, actualBoat, actualListSailors);
        turnBoat.turnBoat();

        BabordTribordAngle finalRepartition = turnBoat.generateIdealRepartitionOfOars(-2 * Math.PI / 6);

        assertEquals(new BabordTribordAngle(3, 1, -2 * Math.PI / 6), finalRepartition);
    }


    @Test
    void generateIdealRepartitionOfOarsTestWithAnyFreeSailor() {


        Deck actualDeck = new Deck(3,6);
        BoatEntity[] actualListBoatEntities = {
                new BoatEntity(BoatEntities.OAR, 0,0){},
                new BoatEntity(BoatEntities.OAR, 0,2){},
                new BoatEntity(BoatEntities.OAR, 2,0){},
                new BoatEntity(BoatEntities.OAR, 2,2){},
        };

        Boat actualBoat = new Boat(defaultLife, defaultTransform, defaultName, actualDeck, actualListBoatEntities, defaultShape);

        List<Marin> actualListSailors = new ArrayList<>() {{
            add(new Marin(1,0,0, "marin1"));
            add(new Marin(2,0,2, "marin2"));
            add(new Marin(3,2,0, "marin3"));
            add(new Marin(4,2,2, "marin4"));
        }};


        TurnBoat turnBoat = new TurnBoat(Math.PI / 2, actualBoat, actualListSailors);
        turnBoat.turnBoat();

        BabordTribordAngle finalRepartition = turnBoat.generateIdealRepartitionOfOars(Math.PI / 2);

        assertEquals(new BabordTribordAngle(0, 2, Math.PI / 2), finalRepartition);
    }



    @Test
    void generateIdealRepartitionOfOarsTestWithAnySailorOnOneSide() {


        Deck actualDeck = new Deck(3,6);
        BoatEntity[] actualListBoatEntities = {
                new BoatEntity(BoatEntities.OAR, 0,0){},
                new BoatEntity(BoatEntities.OAR, 0,2){},
                new BoatEntity(BoatEntities.OAR, 2,0){},
                new BoatEntity(BoatEntities.OAR, 2,2){},
                new BoatEntity(BoatEntities.OAR, 4,0){},
                new BoatEntity(BoatEntities.OAR, 4,2){},
                new BoatEntity(BoatEntities.OAR, 1,0){},
                new BoatEntity(BoatEntities.OAR, 1,2){}
        };

        Boat actualBoat = new Boat(defaultLife, defaultTransform, defaultName, actualDeck, actualListBoatEntities, defaultShape);

        List<Marin> actualListSailors = new ArrayList<>() {{
            add(new Marin(1,0,0, "marin1"));
            add(new Marin(2,2,0, "marin2"));
            add(new Marin(3,1,0, "marin3"));
            add(new Marin(4,4,0, "marin4"));
        }};


        TurnBoat turnBoat = new TurnBoat(-Math.PI / 2, actualBoat, actualListSailors);
        turnBoat.turnBoat();

        BabordTribordAngle finalRepartition = turnBoat.generateIdealRepartitionOfOars(-Math.PI / 2);

        assertEquals(new BabordTribordAngle(4, 0, -Math.PI / 2), finalRepartition);
    }

    @Test
    void generateIdealRepartitionOfOarsTestWithAnySailorOnTheOtherSide() {


        Deck actualDeck = new Deck(3,6);
        BoatEntity[] actualListBoatEntities = {
                new BoatEntity(BoatEntities.OAR, 0,0){},
                new BoatEntity(BoatEntities.OAR, 0,2){},
                new BoatEntity(BoatEntities.OAR, 2,0){},
                new BoatEntity(BoatEntities.OAR, 2,2){},
                new BoatEntity(BoatEntities.OAR, 4,0){},
                new BoatEntity(BoatEntities.OAR, 4,2){},
                new BoatEntity(BoatEntities.OAR, 1,0){},
                new BoatEntity(BoatEntities.OAR, 1,2){}
        };

        Boat actualBoat = new Boat(defaultLife, defaultTransform, defaultName, actualDeck, actualListBoatEntities, defaultShape);

        List<Marin> actualListSailors = new ArrayList<>() {{
            add(new Marin(1,0,0, "marin1"));
            add(new Marin(2,2,0, "marin2"));
            add(new Marin(3,1,0, "marin3"));
            add(new Marin(4,4,0, "marin4"));
        }};


        TurnBoat turnBoat = new TurnBoat(+Math.PI / 2, actualBoat, actualListSailors);
        turnBoat.turnBoat();

        BabordTribordAngle finalRepartition = turnBoat.generateIdealRepartitionOfOars(+Math.PI / 2);

        assertEquals(new BabordTribordAngle(0, 4, +Math.PI / 2), finalRepartition);
    }


    @Test
    void generateIdealRepartitionOfOarsTestWithExactAngle() {


        Deck actualDeck = new Deck(3,6);
        BoatEntity[] actualListBoatEntities = {
                new BoatEntity(BoatEntities.OAR, 0,0){},
                new BoatEntity(BoatEntities.OAR, 0,2){},
                new BoatEntity(BoatEntities.OAR, 2,0){},
                new BoatEntity(BoatEntities.OAR, 2,2){},
                new BoatEntity(BoatEntities.OAR, 4,0){},
                new BoatEntity(BoatEntities.OAR, 4,2){},
                new BoatEntity(BoatEntities.OAR, 1,0){},
                new BoatEntity(BoatEntities.OAR, 1,2){}
        };

        Boat actualBoat = new Boat(defaultLife, defaultTransform, defaultName, actualDeck, actualListBoatEntities, defaultShape);

        List<Marin> actualListSailors = new ArrayList<>() {{
            add(new Marin(1,0,0, "marin1"));
            add(new Marin(2,2,0, "marin2"));
            add(new Marin(3,1,0, "marin3"));
            add(new Marin(4,4,0, "marin4"));
        }};


        TurnBoat turnBoat = new TurnBoat(+Math.PI / 4, actualBoat, actualListSailors);
        turnBoat.turnBoat();

        BabordTribordAngle finalRepartition = turnBoat.generateIdealRepartitionOfOars(+Math.PI / 4);

        assertEquals(+Math.PI / 4, finalRepartition.getAngle());
    }


    @Test
    void generateIdealRepartitionOfOarsTestWithPositiveFloatAngle() {


        Deck actualDeck = new Deck(3,6);
        BoatEntity[] actualListBoatEntities = {
                new BoatEntity(BoatEntities.OAR, 0,0){},
                new BoatEntity(BoatEntities.OAR, 0,2){},
                new BoatEntity(BoatEntities.OAR, 2,0){},
                new BoatEntity(BoatEntities.OAR, 2,2){},
                new BoatEntity(BoatEntities.OAR, 4,0){},
                new BoatEntity(BoatEntities.OAR, 4,2){},
                new BoatEntity(BoatEntities.OAR, 1,0){},
                new BoatEntity(BoatEntities.OAR, 1,2){}
        };

        Boat actualBoat = new Boat(defaultLife, defaultTransform, defaultName, actualDeck, actualListBoatEntities, defaultShape);

        List<Marin> actualListSailors = new ArrayList<>() {{
            add(new Marin(1,0,0, "marin1"));
            add(new Marin(2,2,0, "marin2"));
            add(new Marin(3,1,0, "marin3"));
            add(new Marin(4,4,0, "marin4"));
        }};


        TurnBoat turnBoat = new TurnBoat(0.85, actualBoat, actualListSailors);
        turnBoat.turnBoat();

        BabordTribordAngle finalRepartition = turnBoat.generateIdealRepartitionOfOars(0.85);

        assertEquals(+Math.PI / 4, finalRepartition.getAngle());
    }


    @Test
    void generateIdealRepartitionOfOarsTestWithNegativeFloatAngle() {


        Deck actualDeck = new Deck(3,6);
        BoatEntity[] actualListBoatEntities = {
                new BoatEntity(BoatEntities.OAR, 0,0){},
                new BoatEntity(BoatEntities.OAR, 0,2){},
                new BoatEntity(BoatEntities.OAR, 2,0){},
                new BoatEntity(BoatEntities.OAR, 2,2){},
                new BoatEntity(BoatEntities.OAR, 4,0){},
                new BoatEntity(BoatEntities.OAR, 4,2){},
                new BoatEntity(BoatEntities.OAR, 1,0){},
                new BoatEntity(BoatEntities.OAR, 1,2){}
        };

        Boat actualBoat = new Boat(defaultLife, defaultTransform, defaultName, actualDeck, actualListBoatEntities, defaultShape);

        List<Marin> actualListSailors = new ArrayList<>() {{
            add(new Marin(1,0,0, "marin1"));
            add(new Marin(2,2,0, "marin2"));
            add(new Marin(3,1,0, "marin3"));
            add(new Marin(4,4,0, "marin4"));
        }};


        TurnBoat turnBoat = new TurnBoat(-0.85, actualBoat, actualListSailors);
        turnBoat.turnBoat();

        BabordTribordAngle finalRepartition = turnBoat.generateIdealRepartitionOfOars(-0.85);

        assertEquals(-Math.PI / 4, finalRepartition.getAngle());
    }


    @Test
    void generateIdealRepartitionOfOarsTestWithSmallAngle() {


        Deck actualDeck = new Deck(3,6);
        BoatEntity[] actualListBoatEntities = {
                new BoatEntity(BoatEntities.OAR, 0,0){},
                new BoatEntity(BoatEntities.OAR, 0,2){},
                new BoatEntity(BoatEntities.OAR, 2,0){},
                new BoatEntity(BoatEntities.OAR, 2,2){},
                new BoatEntity(BoatEntities.OAR, 4,0){},
                new BoatEntity(BoatEntities.OAR, 4,2){},
                new BoatEntity(BoatEntities.OAR, 1,0){},
                new BoatEntity(BoatEntities.OAR, 1,2){}
        };

        Boat actualBoat = new Boat(defaultLife, defaultTransform, defaultName, actualDeck, actualListBoatEntities, defaultShape);

        List<Marin> actualListSailors = new ArrayList<>() {{
            add(new Marin(1,0,0, "marin1"));
            add(new Marin(2,2,0, "marin2"));
            add(new Marin(3,1,0, "marin3"));
            add(new Marin(4,4,0, "marin4"));
        }};


        TurnBoat turnBoat = new TurnBoat(0.01, actualBoat, actualListSailors);
        turnBoat.turnBoat();

        BabordTribordAngle finalRepartition = turnBoat.generateIdealRepartitionOfOars(0.01);

        assertEquals(0.0, finalRepartition.getAngle());
    }
}
