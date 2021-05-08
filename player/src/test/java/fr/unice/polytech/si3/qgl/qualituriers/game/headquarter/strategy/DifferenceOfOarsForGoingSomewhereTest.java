package fr.unice.polytech.si3.qgl.qualituriers.game.headquarter.strategy;

import fr.unice.polytech.si3.qgl.qualituriers.Config;
import fr.unice.polytech.si3.qgl.qualituriers.Deck;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntities;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DifferenceOfOarsForGoingSomewhereTest {

    private int defaultLife = 100;
    private Transform defaultTransform = new Transform(0,0,0);
    private String defaultName = "defaultBoat";
    private Shape defaultShape = new Rectangle(2, 4, 0);
    private Boat defaultBoat;
    private Deck defaultDeck = new Deck(2,4);
    private List<Marin> defaultListSailors;


    @BeforeEach
    void setUp() {


        BoatEntity[] actualListBoatEntities = {
                new BoatEntity(BoatEntities.OAR, 0,0){},
                new BoatEntity(BoatEntities.OAR, 0,1){},
                new BoatEntity(BoatEntities.OAR, 1,0){},
                new BoatEntity(BoatEntities.OAR, 1,1){},
                new BoatEntity(BoatEntities.OAR, 3,0){},
                new BoatEntity(BoatEntities.OAR, 3,1){},
        };

        defaultBoat = new Boat(defaultLife, defaultTransform, defaultName, defaultDeck, actualListBoatEntities, defaultShape);



    }

    @Test
    void differenceOfOarsForGoingSomewherePId4() {

        Transform destination = new Transform(new Point(100,100), 0);
        DifferenceOfOarsForGoingSomewhere differenceOfOarsForGoingSomewhere = new DifferenceOfOarsForGoingSomewhere(defaultBoat, destination);

        assertEquals(1, differenceOfOarsForGoingSomewhere.differenceOfOarsForGoingSomewhere());
        assertEquals(Math.PI/12, differenceOfOarsForGoingSomewhere.differenceOfAngleForTheRudder(), Config.EPSILON);
    }

    @Test
    void differenceOfOarsForGoingSomewhereMinusPId4() {

        Transform destination = new Transform(new Point(100,-100), 0);
        DifferenceOfOarsForGoingSomewhere differenceOfOarsForGoingSomewhere = new DifferenceOfOarsForGoingSomewhere(defaultBoat, destination);

        assertEquals(-1, differenceOfOarsForGoingSomewhere.differenceOfOarsForGoingSomewhere());
        assertEquals(-Math.PI/12, differenceOfOarsForGoingSomewhere.differenceOfAngleForTheRudder(), Config.EPSILON);
    }

    @Test
    void differenceOfOarsForGoingSomewherePId2() {

        Transform destination = new Transform(new Point(0,100), 0);
        DifferenceOfOarsForGoingSomewhere differenceOfOarsForGoingSomewhere = new DifferenceOfOarsForGoingSomewhere(defaultBoat, destination);

        assertEquals(3, differenceOfOarsForGoingSomewhere.differenceOfOarsForGoingSomewhere());
        assertEquals(0, differenceOfOarsForGoingSomewhere.differenceOfAngleForTheRudder(), Config.EPSILON);
    }

    @Test
    void differenceOfOarsForGoingSomewhereMinusPId2() {

        Transform destination = new Transform(new Point(0,-100), 0);
        DifferenceOfOarsForGoingSomewhere differenceOfOarsForGoingSomewhere = new DifferenceOfOarsForGoingSomewhere(defaultBoat, destination);

        assertEquals(-3, differenceOfOarsForGoingSomewhere.differenceOfOarsForGoingSomewhere());
        assertEquals(0, differenceOfOarsForGoingSomewhere.differenceOfAngleForTheRudder(), Config.EPSILON);
    }

    @Test
    void differenceOfOarsForGoingSomewhere3PId4() {

        Transform destination = new Transform(new Point(-100,100), 0);
        DifferenceOfOarsForGoingSomewhere differenceOfOarsForGoingSomewhere = new DifferenceOfOarsForGoingSomewhere(defaultBoat, destination);

        assertEquals(3, differenceOfOarsForGoingSomewhere.differenceOfOarsForGoingSomewhere());
        assertEquals(Math.PI/4, differenceOfOarsForGoingSomewhere.differenceOfAngleForTheRudder(), Config.EPSILON);
    }

    @Test
    void differenceOfOarsForGoingSomewhereMinus3PId4() {

        Transform destination = new Transform(new Point(-100,-100), 0);
        DifferenceOfOarsForGoingSomewhere differenceOfOarsForGoingSomewhere = new DifferenceOfOarsForGoingSomewhere(defaultBoat, destination);

        assertEquals(-3, differenceOfOarsForGoingSomewhere.differenceOfOarsForGoingSomewhere());
        assertEquals(-Math.PI/4, differenceOfOarsForGoingSomewhere.differenceOfAngleForTheRudder(), Config.EPSILON);
    }

    @Test
    void differenceOfOarsForGoingSomewherePI() {

        Transform destination = new Transform(new Point(-100,0), 0);
        DifferenceOfOarsForGoingSomewhere differenceOfOarsForGoingSomewhere = new DifferenceOfOarsForGoingSomewhere(defaultBoat, destination);

        assertEquals(3, differenceOfOarsForGoingSomewhere.differenceOfOarsForGoingSomewhere());
        assertEquals(Math.PI/4, differenceOfOarsForGoingSomewhere.differenceOfAngleForTheRudder(), Config.EPSILON);
    }
}
