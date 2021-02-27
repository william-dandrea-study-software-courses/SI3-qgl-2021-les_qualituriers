package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.strategy;

import fr.unice.polytech.si3.qgl.qualituriers.Deck;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.OarBoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Moving;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InitSailorsPlaceTest {

    private Boat defaultBoat;
    private List<Marin> defaultSailors;

    @BeforeEach
    void setUp() {
        int defaultLife = 100;
        Transform defaultTransform = new Transform(0,0,0);
        String defaultName = "defaultName";
        Deck defaultDeck = new Deck(5,12);
        BoatEntity[] defaultEntities = {
                new OarBoatEntity(2,0),
                new OarBoatEntity(3,0),
                new OarBoatEntity(4,0),
                new OarBoatEntity(5,0),
                new OarBoatEntity(6,0),
                new OarBoatEntity(7,0),
                new OarBoatEntity(8,0),
                new OarBoatEntity(9,0),
                new OarBoatEntity(2,4),
                new OarBoatEntity(3,4),
                new OarBoatEntity(4,4),
                new OarBoatEntity(5,4),
                new OarBoatEntity(6,4),
                new OarBoatEntity(7,4),
                new OarBoatEntity(8,4),
                new OarBoatEntity(9,4)
        };
        Shape defaultShape = new Rectangle(5,12,0);
        defaultBoat = new Boat(defaultLife, defaultTransform,defaultName,defaultDeck,defaultEntities,defaultShape);
        defaultSailors = new ArrayList<>() {{
            add(new Marin(0,0,0,"marin0"));
            add(new Marin(1,0,1,"marin1"));
            add(new Marin(2,0,2,"marin2"));
            add(new Marin(3,0,3,"marin3"));
            add(new Marin(4,0,4,"marin4"));
            add(new Marin(5,1,0,"marin5"));
            add(new Marin(6,1,1,"marin6"));
            add(new Marin(7,1,2,"marin7"));
            add(new Marin(8,1,3,"marin8"));
            add(new Marin(9,1,4,"marin9"));
            add(new Marin(10,2,0,"marin10"));
            add(new Marin(11,2,1,"marin11"));
            add(new Marin(12,2,2,"marin12"));
            add(new Marin(13,2,3,"marin13"));
            add(new Marin(14,2,4,"marin14"));
            add(new Marin(15,3,0,"marin15"));
            add(new Marin(16,3,1,"marin16"));
            add(new Marin(17,3,2,"marin17"));
            add(new Marin(18,3,3,"marin18"));
        }};
    }

    @Test
    void initSailorsPlace() {
        System.out.println("============================ SAILORS BEFORE ============================");
        System.out.println(defaultSailors);
        System.out.println("========================================================================");

        InitSailorsPlace initSailorsPlace = new InitSailorsPlace(defaultBoat, defaultSailors);
        System.out.println(initSailorsPlace.initSailorsPlace());

        System.out.println("============================ SAILORS AFTER ============================");
        System.out.println(defaultSailors);
        System.out.println("========================================================================");

        System.out.println(initSailorsPlace.initSailorsPlace());

        System.out.println("============================ SAILORS AFTER ============================");
        System.out.println(defaultSailors);
        System.out.println("========================================================================");
    }


    @Test
    void moveSailorAtTheFarthestPositionTest() {

        InitSailorsPlace initSailorsPlace = new InitSailorsPlace(defaultBoat, defaultSailors);
        assertEquals(new Moving(0,5,4),initSailorsPlace.moveSailorAtTheFarthestPosition(0).get() );

    }
}
