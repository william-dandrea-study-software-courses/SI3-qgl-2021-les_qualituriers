package fr.unice.polytech.si3.qgl.qualituriers.entity.boat;

import fr.unice.polytech.si3.qgl.qualituriers.Deck;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntities;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shapes;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

class MoveBoatTest {


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



        defaultTransform = new Transform(new Point(0,0), 0.0);


        BoatEntity defaultBoatEntity = new BoatEntity(BoatEntities.OAR, 0, 0) {};
        defaultListBoatEntities = new BoatEntity[]{
                defaultBoatEntity,
                new BoatEntity(BoatEntities.OAR, 0,1) {}
        };



        Marin defaultSailor = new Marin(0, 0, 0, "defaultSailor");
        defaultListSailors.add(defaultSailor);
        defaultListSailors.add(new Marin(1,0,1,"marin1"));

        defaultDeck = new Deck(2, 4);

        defaultBoat = new Boat(defaultLife, defaultTransform, defaultName, defaultDeck, defaultListBoatEntities, defaultShape);


    }


    /*@Test
    void moveBoatTestNormal() {

    }*/

}
