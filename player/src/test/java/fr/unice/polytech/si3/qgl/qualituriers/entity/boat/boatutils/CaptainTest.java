package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatutils;

import fr.unice.polytech.si3.qgl.qualituriers.Deck;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.OarBoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shapes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CaptainTest {
    Boat boat;
    OarBoatEntity oar;

    void createBoat() {
        oar = new OarBoatEntity(0,0) {};
        BoatEntity oar2 = new OarBoatEntity(1,0) {};
        BoatEntity oar3 = new OarBoatEntity(0,1) {};
        BoatEntity oar4 = new OarBoatEntity(1,1) {};
        BoatEntity oar5 = new OarBoatEntity(3,0) {};
        BoatEntity oar6 = new OarBoatEntity(3,1) {};


        Marin sailor1 = new Marin(1,0,1,"sailor1");
        Marin sailor2 = new Marin(2,1,0,"sailor2");
        Marin sailor3 = new Marin(3,0,1,"sailor3");
        Marin sailor4 = new Marin(4,1,1,"sailor4");

        int life = 100;
        Transform transform = new Transform(0,0,0);
        String name = "boatTest1";
        Deck deck = new Deck(2,4);
        BoatEntity[] entities = {oar6,oar5,oar3,oar4, oar2, oar};
        Shape shape = new Shape(Shapes.POLYGON) {
            @Override
            public boolean isIn(Point position) {
                return false;
            }
        };
        Marin[] sailors = {sailor4, sailor3,sailor2,sailor1};


        boat = new Boat(life, transform, name, deck, entities,shape);
        boat.setSailors(List.of(sailors));
    }
    @BeforeEach
    void init() {
        createBoat();
    }

    @Test
    void GoToTestByLeft() {
        boat.getCaptain().goTo(new Point(-10, 1));
        boat.getCaptain().decide();
        boat.getForeman().decide();

        assertTrue(boat.getForeman().getNumberLeftRowingSailors() < boat.getForeman().getNumberRightRowingSailors());
        assertEquals(0, boat.getForeman().getNumberLeftRowingSailors());
    }

    @Test
    void GoToByRight() {
        boat.getCaptain().goTo(new Point(-10, -1));
        boat.getCaptain().decide();
        boat.getForeman().decide();

        assertTrue(boat.getForeman().getNumberLeftRowingSailors() > boat.getForeman().getNumberRightRowingSailors());
        assertEquals(0, boat.getForeman().getNumberRightRowingSailors());
    }

    @Test
    void GoToForward() {
        boat.getCaptain().goTo(new Point(10, 0));
        boat.getCaptain().decide();
        boat.getForeman().decide();

        assertEquals(boat.getForeman().getNumberRightRowingSailors(), boat.getForeman().getNumberLeftRowingSailors());
        assertNotEquals(0, boat.getForeman().getNumberRightRowingSailors());
    }

}
