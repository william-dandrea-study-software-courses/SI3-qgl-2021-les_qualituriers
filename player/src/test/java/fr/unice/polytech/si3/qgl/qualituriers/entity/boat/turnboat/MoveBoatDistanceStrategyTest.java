package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.turnboat;

import fr.unice.polytech.si3.qgl.qualituriers.Deck;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.BoatEntities;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.turnboat.Disposition;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.turnboat.MoveBoatDistanceStrategy;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.turnboat.SortedDispositionDistanceStrategy;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Polygon;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class MoveBoatDistanceStrategyTest {


    private Transform defaultTransform;
    private final List<Marin> defaultListSailors = new ArrayList<>();
    private BoatEntity[] defaultListBoatEntities;
    private Boat defaultBoat;
    private Deck defaultDeck;


    int defaultLife = 100;
    String defaultName = "defaultBoat";
    Shape defaultShape = new Polygon(0, new Point[] {new Point(0, 0)}) {
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
    void moveBoat() {



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
            add(new Marin(1,0,0, "marin1")); // babord
            add(new Marin(2,0,1, "marin2")); // tribord
            add(new Marin(3,1,0, "marin3")); // babord
            add(new Marin(4,1,1, "marin4")); // tribord
        }};

        SortedDispositionDistanceStrategy sortedDispositionDistanceStrategy = new SortedDispositionDistanceStrategy();
        var sortList = sortedDispositionDistanceStrategy.getIdealDisposition(new Transform(1000,1000,0), actualBoat);

        //System.out.println(sortList);

        List<Disposition> ourDisposition = new ArrayList<>() {{add(new Disposition(1,3));}};
        MoveBoatDistanceStrategy moveBoat = new MoveBoatDistanceStrategy(actualBoat, ourDisposition, actualListSailors.toArray(new Marin[0]));
        System.out.println(moveBoat.moveBoat());
    }

    @Test
    void moveBoatComplex() {



        Deck actualDeck = new Deck(3,6);
        BoatEntity[] actualListBoatEntities = {
                new BoatEntity(BoatEntities.OAR, 0,0){},
                new BoatEntity(BoatEntities.OAR, 0,2){},
                new BoatEntity(BoatEntities.OAR, 2,0){},
                new BoatEntity(BoatEntities.OAR, 2,2){},
                new BoatEntity(BoatEntities.OAR, 4,0){},
                new BoatEntity(BoatEntities.OAR, 4,2){},
        };

        Boat actualBoat = new Boat(defaultLife, defaultTransform, defaultName, actualDeck, actualListBoatEntities, defaultShape);

        List<Marin> actualListSailors = new ArrayList<>() {{
            add(new Marin(1,0,0, "marin1")); // babord
            add(new Marin(2,0,2, "marin2")); // tribord
            add(new Marin(3,2,0, "marin3")); // babord
            add(new Marin(4,3,1, "marin4")); // tribord
            //add(new Marin(5,4,1, "marin5")); // tribord
            //add(new Marin(6,4,2, "marin6")); // tribord
        }};

        SortedDispositionDistanceStrategy sortedDispositionDistanceStrategy = new SortedDispositionDistanceStrategy();
        var sortList = sortedDispositionDistanceStrategy.getIdealDisposition(new Transform(1000,1000,0), actualBoat);

        //System.out.println(sortList);

        List<Disposition> ourDisposition = new ArrayList<>() {{add(new Disposition(6,0));}};
        MoveBoatDistanceStrategy moveBoat = new MoveBoatDistanceStrategy(actualBoat, sortList, actualListSailors.toArray(new Marin[0]));
        System.out.println(moveBoat.moveBoat());
    }
}
