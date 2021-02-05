package fr.unice.polytech.si3.qgl.qualituriers;

import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;

import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.BoatEntities;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;

import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shapes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



public class CockpitMethodsTest {

    CockpitMethods cockpit;

    @BeforeEach
    void setUp() {
        this.cockpit = new CockpitMethods();
    }

    // TODO : implementer ce test et le completer
    @Test
    void moveSailorSomewhereTest() {
        //assertEquals("[]", this.cockpit.nextRound("{}"));

        /*BoatEntity[] boatEntities = {new BoatEntity(BoatEntities.RAME.getType(), 0, 0)};

        Boat boat = new Boat(
                1000,
                new Transform(0,0,0),
                "Bateau",
                new Deck(5,5),
                boatEntities,
                Shapes.RECTANGLE
        );*/

        //Marin sailor = new Marin(1,0,0,"Marin");

        //System.out.println(cockpit.moveSailorSomewhere(boat, sailor, 4,4));


    }


}
