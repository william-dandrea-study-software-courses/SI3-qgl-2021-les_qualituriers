package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.turnboat;

import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.BoatEntities;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TurnBoatTest {

    private BoatEntity defaultBoatEntity;
    private Marin defaultSailor;
    private Transform defaultTransform;
    private List<Marin> defaultListSailors = new ArrayList<>();
    private List<BoatEntity> defaultListBoatEntities = new ArrayList<>();

    @BeforeEach
    void setUp() {

        defaultBoatEntity = new BoatEntity(BoatEntities.OAR, 0,0) {};
        defaultSailor = new Marin(0,0,0,"defaultSailor");
        defaultTransform = new Transform(new Point(0,0), 0.0);

        defaultListBoatEntities.add(defaultBoatEntity);
        defaultListBoatEntities.add(new BoatEntity(BoatEntities.OAR, 1,1) {});

        defaultListSailors.add(defaultSailor);
        defaultListSailors.add(new Marin(1,1,1,"marin1"));
    }

    @Test
    void turnBoatWhenAngleOfDeriveEqual0() {

        double finalOrientationBoat = 0.0;
        TurnBoat turnBoat = new TurnBoat(finalOrientationBoat, defaultListSailors, defaultListBoatEntities,defaultTransform);

        assertTrue(turnBoat.turnBoat().isEmpty());
    }

    @Test
    void actualizeDifferenceOfAngleWithAngleOf0() {

        double finalOrientationBoat = 0.0;

        TurnBoat turnBoat = new TurnBoat(finalOrientationBoat, defaultListSailors, defaultListBoatEntities,defaultTransform);


        assertEquals(0.0, turnBoat.generateDifferenceOfAngle(false));
    }

    @Test
    void actualizeDifferenceOfAngleWithAngleOfPiDividedBy2() {

        double finalOrientationBoat = Math.PI/2;

        TurnBoat turnBoat = new TurnBoat(finalOrientationBoat, defaultListSailors, defaultListBoatEntities,defaultTransform);


        assertEquals(Math.PI/2, turnBoat.generateDifferenceOfAngle(false));
    }

    @Test
    void actualizeDifferenceOfAngleWithAngleOfMinusPiDividedBy2() {

        double finalOrientationBoat = -Math.PI/2;

        TurnBoat turnBoat = new TurnBoat(finalOrientationBoat, defaultListSailors, defaultListBoatEntities,defaultTransform);


        assertEquals(-Math.PI/2, turnBoat.generateDifferenceOfAngle(false));
    }

    @Test
    void actualizeDifferenceOfAngleWithSpecialAngleNegative() {

        Transform actualPosition = new Transform(new Point(0,0), -3*Math.PI / 4);
        double finalOrientationBoat = 3*Math.PI/4;

        TurnBoat turnBoat = new TurnBoat(finalOrientationBoat, defaultListSailors, defaultListBoatEntities,actualPosition);

        assertEquals(-2 * Math.PI/4, turnBoat.generateDifferenceOfAngle(false));
    }

    @Test
    void actualizeDifferenceOfAngleWithSpecialAnglePositive() {

        Transform actualPosition = new Transform(new Point(0,0), -Math.PI / 4);
        double finalOrientationBoat = Math.PI/4;

        TurnBoat turnBoat = new TurnBoat(finalOrientationBoat, defaultListSailors, defaultListBoatEntities,actualPosition);


        assertEquals(2 * Math.PI/4, turnBoat.generateDifferenceOfAngle(false));
    }

    @Test
    void actualizeDifferenceOfAngleWithOnePiDivided2() {

        Transform actualPosition = new Transform(new Point(0,0), -Math.PI/2);
        double finalOrientationBoat = Math.PI/4;

        TurnBoat turnBoat = new TurnBoat(finalOrientationBoat, defaultListSailors, defaultListBoatEntities,actualPosition);


        assertEquals(3 * Math.PI/4, turnBoat.generateDifferenceOfAngle(false));
    }

    @Test
    void actualizeDifferenceOfAngleWithTwoPiDivided2() {

        Transform actualPosition = new Transform(new Point(0,0), -Math.PI/2);
        double finalOrientationBoat = Math.PI/2;

        TurnBoat turnBoat = new TurnBoat(finalOrientationBoat, defaultListSailors, defaultListBoatEntities,actualPosition);

        assertTrue(Math.PI == turnBoat.generateDifferenceOfAngle(false) || -Math.PI == turnBoat.generateDifferenceOfAngle(false));
    }

    @Test
    void actualizeDifferenceOfAngleWithTwoPi() {

        Transform actualPosition = new Transform(new Point(0,0), -Math.PI);
        double finalOrientationBoat = Math.PI;

        TurnBoat turnBoat = new TurnBoat(finalOrientationBoat, defaultListSailors, defaultListBoatEntities,actualPosition);


        assertEquals(0.0, turnBoat.generateDifferenceOfAngle(false));
    }

    @Test
    void actualizeDifferenceOfAngleWithPiAnd0() {

        Transform actualPosition = new Transform(new Point(0,0), -Math.PI);
        double finalOrientationBoat = 0;

        TurnBoat turnBoat = new TurnBoat(finalOrientationBoat, defaultListSailors, defaultListBoatEntities,actualPosition);


        assertEquals(Math.PI, turnBoat.generateDifferenceOfAngle(false));
    }

    @Test
    void actualizeDifferenceOfAngleWithPiAnd0AndWantNegative() {

        Transform actualPosition = new Transform(new Point(0,0), -Math.PI);
        double finalOrientationBoat = 0;

        TurnBoat turnBoat = new TurnBoat(finalOrientationBoat, defaultListSailors, defaultListBoatEntities,actualPosition);
        turnBoat.generateDifferenceOfAngle(true);

        assertEquals(-Math.PI, turnBoat.generateDifferenceOfAngle(true));
    }

    @Test
    void generateListOfPossibleAnglesTestWithClassicInput() {

        TurnBoat turnBoat = new TurnBoat(0.0, defaultListSailors, defaultListBoatEntities,defaultTransform);

        turnBoat.generateListOfPossibleAngles(0).stream().forEach(eachValue -> {
            assertTrue(eachValue.getAngle() >= -Math.PI / 2 && eachValue.getAngle() <= Math.PI);
        });

        assertEquals(3, turnBoat.generateListOfPossibleAngles(0).size());

    }

    @Test
    void generateListOfPossibleAnglesTestWithUnpairInput() {

        List<BoatEntity> actualListBoatEntities = new ArrayList<>();
        actualListBoatEntities.add(new BoatEntity(BoatEntities.OAR, 0,0) {});


        TurnBoat turnBoat = new TurnBoat(0.0, defaultListSailors, actualListBoatEntities,defaultTransform);

        assertThrows(IllegalArgumentException.class, () -> turnBoat.generateListOfPossibleAngles(0));


    }

    @Test
    void generateListOfPossibleAnglesTestWith0Input() {

        List<BoatEntity> actualListBoatEntities = new ArrayList<>();

        TurnBoat turnBoat = new TurnBoat(0.0, defaultListSailors, actualListBoatEntities,defaultTransform);
        assertTrue(turnBoat.generateListOfPossibleAngles(0).isEmpty());


    }

    @Test
    void generateListOfPossibleAnglesTestWithClassicInputAndDifferentField() {

        //defaultListBoatEntities.add(new BoatEntity(BoatEntities.OAR, 2,2) {});
        //defaultListBoatEntities.add(new BoatEntity(BoatEntities.OAR, 2,1) {});

        TurnBoat turnBoat = new TurnBoat(0.0, defaultListSailors, defaultListBoatEntities,defaultTransform);

        turnBoat.generateListOfPossibleAngles(1).stream().forEach(eachValue -> {
            assertTrue(eachValue.getAngle() >= -Math.PI / 2 && eachValue.getAngle() <= Math.PI);
        });


        assertEquals(3, turnBoat.generateListOfPossibleAngles(0).size());

    }




}
