package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.turnboat;

import fr.unice.polytech.si3.qgl.qualituriers.Deck;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.CanonBoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.OarBoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.turnboat.turnboatutils.DistanceDisposition;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Oar;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class SortedDispositionDistanceStrategyTest {

    @BeforeEach
    void setUp() {


    }

    @Disabled("il faut tester avec des valeurs attendus et non avec des valeurs retournée par la méthode (si elle fonctionne pas,  le test sera quand même juste)")
    @Test
    void getIdealRepartition() {
        SortedDispositionDistanceStrategy strategy = new SortedDispositionDistanceStrategy();
        Boat boat = new Boat(100, new Transform(0, 0, 0), "canard", new Deck(2, 2),
                new BoatEntity[]{new OarBoatEntity(1, 2), new OarBoatEntity(0, 0),
                        new CanonBoatEntity(0, 1, false, 0)}, new Rectangle(4, 4, 0));
        List<DistanceDisposition> idealRepartition = strategy.getIdealRepartition(new Transform(50, 0, 0), boat);
        List<DistanceDisposition> list = Arrays.asList(
                new DistanceDisposition(0, 1, new Point(1, 84.5 ), 59.92703897240376),
        new DistanceDisposition(1, 1, new Point(166.0, 2.0), 125.53883861180172),
        new DistanceDisposition(1, 0, new Point(1, -80.5), 139.3960185945065));
        //assertEquals(list, idealRepartition);
    }

    @Test
    void generateListOfDispositionsTest() {
        SortedDispositionDistanceStrategy sortedDispositionDistanceStrategy = new SortedDispositionDistanceStrategy();
        var unsortList = sortedDispositionDistanceStrategy.generateListOfDispositions(4, new Transform(0,0,0), new Transform(new Point(1000,1000),0));
        List<DistanceDisposition> dispositions = Arrays.asList(
                new DistanceDisposition(0, 1, new Point(29.168154723945086, 29.168154723945083), 1372.9635623730949),
                new DistanceDisposition(0, 2, new Point(0, 82.5), 1357.1316258933766)
, new DistanceDisposition(1, 0, new Point(29.168154723945086, -29.168154723945083), 1414.8150276626268)
, new DistanceDisposition(1, 1, new Point(82.5, 0.0), 1357.1316258933766)
, new DistanceDisposition(1, 2, new Point(87.50446417183527, 87.50446417183525), 1290.463562373095)
, new DistanceDisposition(2, 0, new Point(5.051668046482832E-15, -82.5), 1473.7049399387924)
, new DistanceDisposition(2, 1, new Point(87.50446417183527, -87.50446417183525), 1419.6175761450688)
, new DistanceDisposition(2, 2, new Point(165.0, 0.0 ), 1302.7758824909217)
        );
        assertEquals(dispositions, unsortList);
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
