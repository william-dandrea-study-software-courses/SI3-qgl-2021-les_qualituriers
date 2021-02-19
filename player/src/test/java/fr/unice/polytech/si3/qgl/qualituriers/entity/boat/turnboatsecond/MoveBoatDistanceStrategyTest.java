package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.turnboatsecond;

import fr.unice.polytech.si3.qgl.qualituriers.Deck;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.BoatEntities;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shapes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoveBoatDistanceStrategyTest {


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

        System.out.println(sortList);
        MoveBoatDistanceStrategy moveBoat = new MoveBoatDistanceStrategy(actualBoat, sortList, actualListSailors.toArray(new Marin[0]));
        System.out.println(moveBoat.moveBoat());
    }
}