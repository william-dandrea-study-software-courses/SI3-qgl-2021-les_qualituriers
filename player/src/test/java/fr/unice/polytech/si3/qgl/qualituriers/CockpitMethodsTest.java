package fr.unice.polytech.si3.qgl.qualituriers;

import fr.unice.polytech.si3.qgl.qualituriers.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.boat.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.boat.boatentities.BoatEntities;
import fr.unice.polytech.si3.qgl.qualituriers.boat.boatentities.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Position;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Oar;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shapes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CockpitMethodsTest {

    CockpitMethods cockpit;

    @BeforeEach
    void setUp() {
        this.cockpit = new CockpitMethods();
    }

    @Test
    void moveSailorSomewhereTest() {
        //assertEquals("[]", this.cockpit.nextRound("{}"));

        BoatEntity boatEntitys[] = {new BoatEntity(BoatEntities.RAME, 0, 0)};

        Boat boat = new Boat(
                1000,
                new Position(0,0,0),
                "Bateau",
                new Deck(5,5),
                boatEntitys,
                Shapes.RECTANGLE
        );

        Marin sailor = new Marin(1,0,0,"Marin");

        System.out.println(cockpit.moveSailorSomewhere(boat, sailor, 4,4));


    }


}
