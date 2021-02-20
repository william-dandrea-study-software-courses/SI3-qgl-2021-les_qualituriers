package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.turnboatsecond;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SortedDispositionDistanceStrategyTest {

    @BeforeEach
    void setUp() {


    }

    @Test
    void getIdealRepartition() {
    }

    @Test
    void generateListOfDispositionsTest() {

        SortedDispositionDistanceStrategy sortedDispositionDistanceStrategy = new SortedDispositionDistanceStrategy();
        var unsortList = sortedDispositionDistanceStrategy.generateListOfDispositions(4, new Transform(0,0,0), new Transform(new Point(1000,1000),0));
        //var sortList = distanceStrategy.sortTheDisposition(unsortList, new Transform(1000,1000,0));
        System.out.println(unsortList);
        assertEquals(8, unsortList.size());

    }

    @Test
    void sortListTest() {

        SortedDispositionDistanceStrategy sortedDispositionDistanceStrategy = new SortedDispositionDistanceStrategy();
        var unsortList = sortedDispositionDistanceStrategy.generateListOfDispositions(4, new Transform(0,0,0), new Transform(new Point(1000,1000),0));
        System.out.println(unsortList);
        var sortList = sortedDispositionDistanceStrategy.sortTheDisposition(unsortList, new Transform(1000,1000,0));
        System.out.println(sortList);
        assertNotEquals(sortList, unsortList);

    }


}
