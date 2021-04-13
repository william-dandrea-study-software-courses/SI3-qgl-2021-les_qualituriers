package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.strategy;

import fr.unice.polytech.si3.qgl.qualituriers.Deck;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.OarBoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.headquarterutils.HeadquarterUtil;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.Wind;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.StreamVisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.VisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.game.goal.RegattaGoal;
import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Oar;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OarTheGoodAmountOfSailorsTest {

    private Boat defaultBoat;
    private List<Marin> defaultSailors;
    private VisibleDeckEntity streamEntity;
    private VisibleDeckEntity[] seaEntities;
    private GameInfo gameInfo;

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
                new OarBoatEntity(2,4),
                new OarBoatEntity(3,4),
                new OarBoatEntity(4,4),
                new OarBoatEntity(5,4),
                new OarBoatEntity(6,4)
        };
        Shape defaultShape = new Rectangle(5,12,0);
        defaultBoat = new Boat(defaultLife, defaultTransform,defaultName,defaultDeck,defaultEntities,defaultShape);
        defaultSailors = new ArrayList<>() {{
            add(new Marin(1,2,0,"marin1"));
            add(new Marin(2,3,0,"marin2"));
            add(new Marin(3,2,4,"marin3"));
            add(new Marin(4,3,4,"marin4"));
            add(new Marin(5,2,2,"marin4"));
            add(new Marin(6,3,2,"marin4"));
        }};

        streamEntity = new StreamVisibleDeckEntity(new Transform(0,0,0), new Rectangle(1,1,0), 0);
        seaEntities = new VisibleDeckEntity[] {
                streamEntity,
                streamEntity,
        };

        gameInfo = new GameInfo(new RegattaGoal(new CheckPoint[]{new CheckPoint(new Transform(50, 50, 0), new Circle(50))}),
                new Boat(100, new Transform(10, 10, 0), "lol", new Deck(4, 5), new BoatEntity[0], new Rectangle(2, 3, 0)),
                new Marin[]{new Marin(0, 1, 1, "sdfsdf")},
                2,
                new Wind(0, 0),
                seaEntities);
    }


    @Test
    void oarTheGoodAmountOfSailorsTest() {
        Oar[] oars = {new Oar(1), new Oar(3), new Oar(2), new Oar(4)};
        OarTheGoodAmountOfSailors oarTheGoodAmountOfSailors = new OarTheGoodAmountOfSailors(defaultBoat, defaultSailors, 0, new Transform(1000,0,0), gameInfo);
        var test = oarTheGoodAmountOfSailors.oarTheGoodAmountOfSailors();
        for(int j = 0; j<oars.length; j++)
        assertEquals(oars[j], test.get(j));

    }

    @Test
    void optimumNumberOfActiveOarsToBeOnTheCheckPointTest() {
        OarTheGoodAmountOfSailors oarTheGoodAmountOfSailors = new OarTheGoodAmountOfSailors(defaultBoat, defaultSailors, 0, new Transform(1000,0,0), gameInfo);
        assertEquals(10, oarTheGoodAmountOfSailors.optimumNumberOfActiveOarsToBeOnTheCheckPoint());


        oarTheGoodAmountOfSailors = new OarTheGoodAmountOfSailors(defaultBoat, defaultSailors, 0, new Transform(16.5,0,0), gameInfo);
        assertEquals(1, oarTheGoodAmountOfSailors.optimumNumberOfActiveOarsToBeOnTheCheckPoint());

        oarTheGoodAmountOfSailors = new OarTheGoodAmountOfSailors(defaultBoat, defaultSailors, 0, new Transform(33,0,0), gameInfo);
        assertEquals(2, oarTheGoodAmountOfSailors.optimumNumberOfActiveOarsToBeOnTheCheckPoint());

        oarTheGoodAmountOfSailors = new OarTheGoodAmountOfSailors(defaultBoat, defaultSailors, 0, new Transform(49.5,0,0), gameInfo);
        assertEquals(3, oarTheGoodAmountOfSailors.optimumNumberOfActiveOarsToBeOnTheCheckPoint());

        oarTheGoodAmountOfSailors = new OarTheGoodAmountOfSailors(defaultBoat, defaultSailors, 0, new Transform(66,0,0), gameInfo);
        assertEquals(4, oarTheGoodAmountOfSailors.optimumNumberOfActiveOarsToBeOnTheCheckPoint());

        oarTheGoodAmountOfSailors = new OarTheGoodAmountOfSailors(defaultBoat, defaultSailors, 0, new Transform(82.5,0,0), gameInfo);
        assertEquals(5, oarTheGoodAmountOfSailors.optimumNumberOfActiveOarsToBeOnTheCheckPoint());

        oarTheGoodAmountOfSailors = new OarTheGoodAmountOfSailors(defaultBoat, defaultSailors, 0, new Transform(99,0,0), gameInfo);
        assertEquals(6, oarTheGoodAmountOfSailors.optimumNumberOfActiveOarsToBeOnTheCheckPoint());
    }

    @Test
    void generateOarActionWhenDifferenceIsPositive2Test() {

        List<Marin> listOfBabordSailors = HeadquarterUtil.getListOfSailorsOnBabordOars(defaultSailors, defaultBoat);
        List<Marin> listOfTribordSailors = HeadquarterUtil.getListOfSailorsOnTribordOars(defaultSailors, defaultBoat);
        System.out.println(listOfBabordSailors);
        System.out.println(listOfTribordSailors);

        OarTheGoodAmountOfSailors oarTheGoodAmountOfSailors = new OarTheGoodAmountOfSailors(defaultBoat, defaultSailors, 2, new Transform(1000,0,0), gameInfo);
        System.out.println(oarTheGoodAmountOfSailors.generateOarActionWhenDifferenceIsPositive());

        defaultSailors.add(new Marin(7,5,4, "test"));
        defaultSailors.add(new Marin(8,6,4, "test"));

        listOfBabordSailors = HeadquarterUtil.getListOfSailorsOnBabordOars(defaultSailors, defaultBoat);
        listOfTribordSailors = HeadquarterUtil.getListOfSailorsOnTribordOars(defaultSailors, defaultBoat);
        System.out.println(listOfBabordSailors);
        System.out.println(listOfTribordSailors);
        System.out.println(oarTheGoodAmountOfSailors.generateOarActionWhenDifferenceIsPositive());

        defaultSailors.add(new Marin(9,6,0, "test"));
        defaultSailors.add(new Marin(10,5,0, "test"));

        listOfBabordSailors = HeadquarterUtil.getListOfSailorsOnBabordOars(defaultSailors, defaultBoat);
        listOfTribordSailors = HeadquarterUtil.getListOfSailorsOnTribordOars(defaultSailors, defaultBoat);
        System.out.println(listOfBabordSailors);
        System.out.println(listOfTribordSailors);
        System.out.println(oarTheGoodAmountOfSailors.generateOarActionWhenDifferenceIsPositive());


        oarTheGoodAmountOfSailors = new OarTheGoodAmountOfSailors(defaultBoat, defaultSailors, 2, new Transform(49.5,0,0), gameInfo);
        System.out.println(oarTheGoodAmountOfSailors.generateOarActionWhenDifferenceIsPositive());

    }


    @Test
    void generateOarActionWhenDifferenceIsNegative2Test() {

        List<Marin> listOfBabordSailors = HeadquarterUtil.getListOfSailorsOnBabordOars(defaultSailors, defaultBoat);
        List<Marin> listOfTribordSailors = HeadquarterUtil.getListOfSailorsOnTribordOars(defaultSailors, defaultBoat);
        System.out.println(listOfBabordSailors);
        System.out.println(listOfTribordSailors);

        OarTheGoodAmountOfSailors oarTheGoodAmountOfSailors = new OarTheGoodAmountOfSailors(defaultBoat, defaultSailors, 2, new Transform(1000,0,0), gameInfo);
        System.out.println(oarTheGoodAmountOfSailors.generateOarActionWhenDifferenceIsNegative());

        defaultSailors.add(new Marin(7,5,4, "test"));
        defaultSailors.add(new Marin(8,6,4, "test"));

        listOfBabordSailors = HeadquarterUtil.getListOfSailorsOnBabordOars(defaultSailors, defaultBoat);
        listOfTribordSailors = HeadquarterUtil.getListOfSailorsOnTribordOars(defaultSailors, defaultBoat);
        System.out.println(listOfBabordSailors);
        System.out.println(listOfTribordSailors);
        System.out.println(oarTheGoodAmountOfSailors.generateOarActionWhenDifferenceIsNegative());

        defaultSailors.add(new Marin(9,6,0, "test"));
        defaultSailors.add(new Marin(10,5,0, "test"));

        listOfBabordSailors = HeadquarterUtil.getListOfSailorsOnBabordOars(defaultSailors, defaultBoat);
        listOfTribordSailors = HeadquarterUtil.getListOfSailorsOnTribordOars(defaultSailors, defaultBoat);
        System.out.println(listOfBabordSailors);
        System.out.println(listOfTribordSailors);
        System.out.println(oarTheGoodAmountOfSailors.generateOarActionWhenDifferenceIsNegative());


        oarTheGoodAmountOfSailors = new OarTheGoodAmountOfSailors(defaultBoat, defaultSailors, 2, new Transform(49.5,0,0), gameInfo);
        System.out.println(oarTheGoodAmountOfSailors.generateOarActionWhenDifferenceIsNegative());

    }
}
