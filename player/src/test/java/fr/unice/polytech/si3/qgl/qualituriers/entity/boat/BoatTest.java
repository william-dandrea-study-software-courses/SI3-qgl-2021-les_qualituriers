package fr.unice.polytech.si3.qgl.qualituriers.entity.boat;

import fr.unice.polytech.si3.qgl.qualituriers.Deck;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shapes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


@Disabled
class BoatTest {



    @BeforeEach
    void setup() {

    }

    @Test
    void turnBoatTest() {

        BoatEntity oar1 = new OarBoatEntity(0,0);
        BoatEntity oar2 = new OarBoatEntity(1,0);
        BoatEntity oar3 = new OarBoatEntity(0,1);
        BoatEntity oar4 = new OarBoatEntity(1,1);
        BoatEntity oar5 = new OarBoatEntity(3,0);
        BoatEntity oar6 = new OarBoatEntity(3,1);
        BoatEntity oar7 = new OarBoatEntity(2,1);


        Marin sailor1 = new Marin(1,0,0,"sailor1");
        Marin sailor2 = new Marin(2,1,0,"sailor2");
        Marin sailor3 = new Marin(3,0,1,"sailor3");
        Marin sailor4 = new Marin(4,1,1,"sailor4");

        int life = 100;
        Transform transform = new Transform(0,0,0);
        String name = "boatTest1";
        Deck deck = new Deck(2,4);
        BoatEntity[] entities = {oar6,oar5,oar3,oar4, oar2, oar1};
        Shape shape = new Shape(Shapes.POLYGON) {
            @Override
            public boolean isIn(Point position) {
                return false;
            }
        };
        Marin[] sailors = {sailor4, sailor3,sailor2,sailor1};


        Boat boat = new Boat(life, transform, name, deck, entities,shape);

        boat.setSailors(Arrays.asList(sailors.clone()));
        boat.moveBoatToAPoint(new Transform(new Point(0,0), 0));
        System.out.println(boat.getActionsToDo());

        //boat.turnBoat( - ((Math.PI)/2) - ((Math.PI)/2) - ((Math.PI)/6));
        //boat.turnBoat(  ((Math.PI)/2) + ((Math.PI)/2) + ((Math.PI)/6));
        //boat.turnBoat(   -((Math.PI)/2) + ((Math.PI)/8));
        //boat.turnBoat(   0);
        // ((Math.PI)/6)




    }

    @Test
    void moveSailorSomewhereTest() {
    }
}
