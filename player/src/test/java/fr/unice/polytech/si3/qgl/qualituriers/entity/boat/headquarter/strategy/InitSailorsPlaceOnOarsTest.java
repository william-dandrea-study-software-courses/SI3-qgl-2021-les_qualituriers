package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.strategy;

import fr.unice.polytech.si3.qgl.qualituriers.Deck;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.*;

import static fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.headquarterutils.HeadquarterUtil.*;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InitSailorsPlaceOnOarsTest {

    private Boat defaultBoat;
    private Boat completeBoat;
    private List<Marin> defaultSailors;

    @BeforeEach
    void setUp() {
        int defaultLife = 100;
        Transform defaultTransform = new Transform(0, 0, 0);
        String defaultName = "defaultName";
        Deck defaultDeck = new Deck(5, 12);
        BoatEntity[] defaultEntities = {
                new OarBoatEntity(2, 0),
                new OarBoatEntity(3, 0),
                new OarBoatEntity(4, 0),
                new OarBoatEntity(5, 0),
                new OarBoatEntity(6, 0),
                new OarBoatEntity(7, 0),
                new OarBoatEntity(8, 0),
                new OarBoatEntity(9, 0),
                new OarBoatEntity(2, 4),
                new OarBoatEntity(3, 4),
                new OarBoatEntity(4, 4),
                new OarBoatEntity(5, 4),
                new OarBoatEntity(6, 4),
                new OarBoatEntity(7, 4),
                new OarBoatEntity(8, 4),
                new OarBoatEntity(9, 4)
        };
        Shape defaultShape = new Rectangle(5, 12, 0);
        defaultBoat = new Boat(defaultLife, defaultTransform, defaultName, defaultDeck, defaultEntities, defaultShape);
        defaultSailors = new ArrayList<>() {{
            add(new Marin(0, 0, 0, "marin0"));
            add(new Marin(1, 0, 1, "marin1"));
            add(new Marin(2, 0, 2, "marin2"));
            add(new Marin(3, 0, 3, "marin3"));
            add(new Marin(4, 0, 4, "marin4"));
            add(new Marin(5, 1, 0, "marin5"));
            add(new Marin(6, 1, 1, "marin6"));
            add(new Marin(7, 1, 2, "marin7"));
            add(new Marin(8, 1, 3, "marin8"));
            add(new Marin(9, 1, 4, "marin9"));
            add(new Marin(10, 2, 0, "marin10"));
            add(new Marin(11, 2, 1, "marin11"));
            add(new Marin(12, 2, 2, "marin12"));
            add(new Marin(13, 2, 3, "marin13"));
            add(new Marin(14, 2, 4, "marin14"));
            add(new Marin(15, 3, 0, "marin15"));
            add(new Marin(16, 3, 1, "marin16"));
            add(new Marin(17, 3, 2, "marin17"));
            add(new Marin(18, 3, 3, "marin18"));
        }};





        String completeBoatName = "defaultName";
        Deck completeBoatDeck = new Deck(5, 12);
        BoatEntity[] completeBoatEntities = {
                new OarBoatEntity(2, 0),
                new OarBoatEntity(3, 0),
                new OarBoatEntity(4, 0),
                new OarBoatEntity(5, 0),
                new OarBoatEntity(6, 0),
                new OarBoatEntity(7, 0),
                new OarBoatEntity(8, 0),
                new OarBoatEntity(9, 0),
                new OarBoatEntity(2, 4),
                new OarBoatEntity(3, 4),
                new OarBoatEntity(4, 4),
                new OarBoatEntity(5, 4),
                new OarBoatEntity(6, 4),
                new OarBoatEntity(7, 4),
                new OarBoatEntity(8, 4),
                new OarBoatEntity(9, 4),
                new RudderBoatEntity(11,2),
                new SailBoatEntity(6,2, false),

        };
        Shape completeBoatShape = new Rectangle(5, 12, 0);
        completeBoat = new Boat(defaultLife, defaultTransform, completeBoatName, completeBoatDeck, completeBoatEntities, completeBoatShape);

    }

    @Test
    void initSailorsPlaceSailor0() {

        InitSailorsPlaceOnOars initSailorsPlaceOnOars = new InitSailorsPlaceOnOars(defaultBoat, defaultSailors);
        List<Action> finalListOfActions = initSailorsPlaceOnOars.initSailorsPlace();

        System.out.println(finalListOfActions);
        System.out.println(defaultSailors);

        assertEquals(16, finalListOfActions.size());


        assertEquals(5, getSailorByHisID(defaultSailors, 0).get().getX());
        assertEquals(4, getSailorByHisID(defaultSailors, 0).get().getY());

    }

    @Test
    void initSailorsPlaceSailor1() {

        InitSailorsPlaceOnOars initSailorsPlaceOnOars = new InitSailorsPlaceOnOars(defaultBoat, defaultSailors);
        List<Action> finalListOfActions = initSailorsPlaceOnOars.initSailorsPlace();



        assertEquals(5, getSailorByHisID(defaultSailors, 1).get().getX());
        assertEquals(0, getSailorByHisID(defaultSailors, 1).get().getY());

    }

    @Test
    void initSailorsPlaceSailor2() {

        InitSailorsPlaceOnOars initSailorsPlaceOnOars = new InitSailorsPlaceOnOars(defaultBoat, defaultSailors);
        List<Action> finalListOfActions = initSailorsPlaceOnOars.initSailorsPlace();




        assertEquals(4, getSailorByHisID(defaultSailors, 2).get().getX());
        assertEquals(4, getSailorByHisID(defaultSailors, 2).get().getY());

    }

    @Test
    void initSailorsPlaceSailor3() {

        InitSailorsPlaceOnOars initSailorsPlaceOnOars = new InitSailorsPlaceOnOars(defaultBoat, defaultSailors);
        List<Action> finalListOfActions = initSailorsPlaceOnOars.initSailorsPlace();




        assertEquals(4, getSailorByHisID(defaultSailors, 3).get().getX());
        assertEquals(0, getSailorByHisID(defaultSailors, 3).get().getY());

    }

    @Test
    void initSailorsPlaceSailor4() {

        InitSailorsPlaceOnOars initSailorsPlaceOnOars = new InitSailorsPlaceOnOars(defaultBoat, defaultSailors);
        List<Action> finalListOfActions = initSailorsPlaceOnOars.initSailorsPlace();



        assertEquals(3, getSailorByHisID(defaultSailors, 4).get().getX());
        assertEquals(4, getSailorByHisID(defaultSailors, 4).get().getY());


    }

    @Test
    void initSailorsPlaceSailor5() {

        InitSailorsPlaceOnOars initSailorsPlaceOnOars = new InitSailorsPlaceOnOars(defaultBoat, defaultSailors);
        List<Action> finalListOfActions = initSailorsPlaceOnOars.initSailorsPlace();




        assertEquals(6, getSailorByHisID(defaultSailors, 5).get().getX());
        assertEquals(4, getSailorByHisID(defaultSailors, 5).get().getY());


    }

    @Test
    void initSailorsPlaceSailor6() {

        InitSailorsPlaceOnOars initSailorsPlaceOnOars = new InitSailorsPlaceOnOars(defaultBoat, defaultSailors);
        List<Action> finalListOfActions = initSailorsPlaceOnOars.initSailorsPlace();



        assertEquals(6, getSailorByHisID(defaultSailors, 6).get().getX());
        assertEquals(0, getSailorByHisID(defaultSailors, 6).get().getY());


    }

    @Test
    void initSailorsPlaceSailor7() {

        InitSailorsPlaceOnOars initSailorsPlaceOnOars = new InitSailorsPlaceOnOars(defaultBoat, defaultSailors);
        List<Action> finalListOfActions = initSailorsPlaceOnOars.initSailorsPlace();



        assertEquals(6, getSailorByHisID(defaultSailors, 7).get().getX());
        assertEquals(3, getSailorByHisID(defaultSailors, 7).get().getY());


    }

    @Test
    void initSailorsPlaceSailor8() {

        InitSailorsPlaceOnOars initSailorsPlaceOnOars = new InitSailorsPlaceOnOars(defaultBoat, defaultSailors);
        List<Action> finalListOfActions = initSailorsPlaceOnOars.initSailorsPlace();

        assertEquals(6, getSailorByHisID(defaultSailors, 8).get().getX());
        assertEquals(2, getSailorByHisID(defaultSailors, 8).get().getY());


    }

    @Test
    void initSailorsPlaceSailor9() {

        InitSailorsPlaceOnOars initSailorsPlaceOnOars = new InitSailorsPlaceOnOars(defaultBoat, defaultSailors);
        List<Action> finalListOfActions = initSailorsPlaceOnOars.initSailorsPlace();



        assertEquals(6, getSailorByHisID(defaultSailors, 9).get().getX());
        assertEquals(1, getSailorByHisID(defaultSailors, 9).get().getY());


    }

    @Test
    void initSailorsPlaceSailor10() {

        InitSailorsPlaceOnOars initSailorsPlaceOnOars = new InitSailorsPlaceOnOars(defaultBoat, defaultSailors);
        List<Action> finalListOfActions = initSailorsPlaceOnOars.initSailorsPlace();



        assertEquals(2, getSailorByHisID(defaultSailors, 10).get().getX());
        assertEquals(0, getSailorByHisID(defaultSailors, 10).get().getY());


    }

    @Test
    void initSailorsPlaceSailor11() {

        InitSailorsPlaceOnOars initSailorsPlaceOnOars = new InitSailorsPlaceOnOars(defaultBoat, defaultSailors);
        List<Action> finalListOfActions = initSailorsPlaceOnOars.initSailorsPlace();



        assertEquals(7, getSailorByHisID(defaultSailors, 11).get().getX());
        assertEquals(4, getSailorByHisID(defaultSailors, 11).get().getY());


    }

    @Test
    void initSailorsPlaceSailor12() {

        InitSailorsPlaceOnOars initSailorsPlaceOnOars = new InitSailorsPlaceOnOars(defaultBoat, defaultSailors);
        List<Action> finalListOfActions = initSailorsPlaceOnOars.initSailorsPlace();



        assertEquals(7, getSailorByHisID(defaultSailors, 12).get().getX());
        assertEquals(0, getSailorByHisID(defaultSailors, 12).get().getY());


    }

    @Test
    void initSailorsPlaceSailor13() {

        InitSailorsPlaceOnOars initSailorsPlaceOnOars = new InitSailorsPlaceOnOars(defaultBoat, defaultSailors);
        List<Action> finalListOfActions = initSailorsPlaceOnOars.initSailorsPlace();



        assertEquals(7, getSailorByHisID(defaultSailors, 13).get().getX());
        assertEquals(3, getSailorByHisID(defaultSailors, 13).get().getY());


    }

    @Test
    void initSailorsPlaceSailor14() {

        InitSailorsPlaceOnOars initSailorsPlaceOnOars = new InitSailorsPlaceOnOars(defaultBoat, defaultSailors);
        List<Action> finalListOfActions = initSailorsPlaceOnOars.initSailorsPlace();



        assertEquals(2, getSailorByHisID(defaultSailors, 14).get().getX());
        assertEquals(4, getSailorByHisID(defaultSailors, 14).get().getY());


    }

    @Test
    void initSailorsPlaceSailor15() {

        InitSailorsPlaceOnOars initSailorsPlaceOnOars = new InitSailorsPlaceOnOars(defaultBoat, defaultSailors);
        List<Action> finalListOfActions = initSailorsPlaceOnOars.initSailorsPlace();



        assertEquals(3, getSailorByHisID(defaultSailors, 15).get().getX());
        assertEquals(0, getSailorByHisID(defaultSailors, 15).get().getY());

    }

    @Test
    void initSailorsPlaceSailor16() {

        InitSailorsPlaceOnOars initSailorsPlaceOnOars = new InitSailorsPlaceOnOars(defaultBoat, defaultSailors);
        List<Action> finalListOfActions = initSailorsPlaceOnOars.initSailorsPlace();



        assertEquals(8, getSailorByHisID(defaultSailors, 16).get().getX());
        assertEquals(4, getSailorByHisID(defaultSailors, 16).get().getY());


    }

    @Test
    void initSailorsPlaceSailor17() {

        InitSailorsPlaceOnOars initSailorsPlaceOnOars = new InitSailorsPlaceOnOars(defaultBoat, defaultSailors);
        List<Action> finalListOfActions = initSailorsPlaceOnOars.initSailorsPlace();




        assertEquals(8, getSailorByHisID(defaultSailors, 17).get().getX());
        assertEquals(0, getSailorByHisID(defaultSailors, 17).get().getY());


    }

    @Test
    void initSailorsPlaceSailor18() {

        InitSailorsPlaceOnOars initSailorsPlaceOnOars = new InitSailorsPlaceOnOars(defaultBoat, defaultSailors);
        List<Action> finalListOfActions = initSailorsPlaceOnOars.initSailorsPlace();



        assertEquals(8, getSailorByHisID(defaultSailors, 18).get().getX());
        assertEquals(3, getSailorByHisID(defaultSailors, 18).get().getY());

    }

    @Test
    void initSailorsPlaceSailorAll() {

        InitSailorsPlaceOnOars initSailorsPlaceOnOars = new InitSailorsPlaceOnOars(defaultBoat, defaultSailors);
        List<Action> finalListOfActions = initSailorsPlaceOnOars.initSailorsPlace();


        assertEquals(5, getSailorByHisID(defaultSailors, 0).get().getX());
        assertEquals(4, getSailorByHisID(defaultSailors, 0).get().getY());

        assertEquals(5, getSailorByHisID(defaultSailors, 1).get().getX());
        assertEquals(0, getSailorByHisID(defaultSailors, 1).get().getY());

        assertEquals(4, getSailorByHisID(defaultSailors, 2).get().getX());
        assertEquals(4, getSailorByHisID(defaultSailors, 2).get().getY());

        assertEquals(4, getSailorByHisID(defaultSailors, 3).get().getX());
        assertEquals(0, getSailorByHisID(defaultSailors, 3).get().getY());

        assertEquals(3, getSailorByHisID(defaultSailors, 4).get().getX());
        assertEquals(4, getSailorByHisID(defaultSailors, 4).get().getY());

        assertEquals(6, getSailorByHisID(defaultSailors, 5).get().getX());
        assertEquals(4, getSailorByHisID(defaultSailors, 5).get().getY());

        assertEquals(6, getSailorByHisID(defaultSailors, 6).get().getX());
        assertEquals(0, getSailorByHisID(defaultSailors, 6).get().getY());

        assertEquals(6, getSailorByHisID(defaultSailors, 7).get().getX());
        assertEquals(3, getSailorByHisID(defaultSailors, 7).get().getY());

        assertEquals(6, getSailorByHisID(defaultSailors, 8).get().getX());
        assertEquals(2, getSailorByHisID(defaultSailors, 8).get().getY());

        assertEquals(6, getSailorByHisID(defaultSailors, 9).get().getX());
        assertEquals(1, getSailorByHisID(defaultSailors, 9).get().getY());

        assertEquals(2, getSailorByHisID(defaultSailors, 10).get().getX());
        assertEquals(0, getSailorByHisID(defaultSailors, 10).get().getY());

        assertEquals(7, getSailorByHisID(defaultSailors, 11).get().getX());
        assertEquals(4, getSailorByHisID(defaultSailors, 11).get().getY());

        assertEquals(7, getSailorByHisID(defaultSailors, 12).get().getX());
        assertEquals(0, getSailorByHisID(defaultSailors, 12).get().getY());

        assertEquals(7, getSailorByHisID(defaultSailors, 13).get().getX());
        assertEquals(3, getSailorByHisID(defaultSailors, 13).get().getY());

        assertEquals(2, getSailorByHisID(defaultSailors, 14).get().getX());
        assertEquals(4, getSailorByHisID(defaultSailors, 14).get().getY());

        assertEquals(3, getSailorByHisID(defaultSailors, 15).get().getX());
        assertEquals(0, getSailorByHisID(defaultSailors, 15).get().getY());

        assertEquals(8, getSailorByHisID(defaultSailors, 16).get().getX());
        assertEquals(4, getSailorByHisID(defaultSailors, 16).get().getY());

        assertEquals(8, getSailorByHisID(defaultSailors, 17).get().getX());
        assertEquals(0, getSailorByHisID(defaultSailors, 17).get().getY());

        assertEquals(8, getSailorByHisID(defaultSailors, 18).get().getX());
        assertEquals(3, getSailorByHisID(defaultSailors, 18).get().getY());

    }

    @Test
    void initSailorsPlaceSailor2Rounds() {

        InitSailorsPlaceOnOars initSailorsPlaceOnOars = new InitSailorsPlaceOnOars(defaultBoat, defaultSailors);
        List<Action> finalListOfActions = initSailorsPlaceOnOars.initSailorsPlace();

        System.out.println(finalListOfActions);
        System.out.println(defaultSailors);

        List<Action> finalListOfActions2 = initSailorsPlaceOnOars.initSailorsPlace();
        System.out.println(finalListOfActions2);
        System.out.println(defaultSailors);

        System.out.println(getListOfSailorsOnOars(defaultSailors, defaultBoat).size());

    }






















    @Test
    void initSailorsPlaceSailorWithCompleteBoat() {

        // new RudderBoatEntity(11,2),
        // new SailBoatEntity(6,2, false),

        InitSailorsPlaceOnOars initSailorsPlaceOnOars = new InitSailorsPlaceOnOars(completeBoat, defaultSailors);
        List<Action> finalListOfActions = initSailorsPlaceOnOars.initSailorsPlace();
        finalListOfActions = initSailorsPlaceOnOars.initSailorsPlace();

        //System.out.println(defaultSailors);
        assertTrue(defaultSailors.stream().noneMatch(sailor -> sailor.getX() == 11 &&  sailor.getY() == 2));
        assertTrue(defaultSailors.stream().noneMatch(sailor -> sailor.getX() == 6 &&  sailor.getY() == 2));
    }
}
