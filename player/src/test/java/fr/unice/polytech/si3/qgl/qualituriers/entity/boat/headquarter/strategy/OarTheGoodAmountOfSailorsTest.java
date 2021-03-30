package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.strategy;

import fr.unice.polytech.si3.qgl.qualituriers.Deck;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.OarBoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.headquarterutils.HeadquarterUtil;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Oar;
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
    }


    @Test
    void oarTheGoodAmountOfSailorsTest() {
        Oar[] oars = {new Oar(1), new Oar(3), new Oar(2), new Oar(4)};
        OarTheGoodAmountOfSailors oarTheGoodAmountOfSailors = new OarTheGoodAmountOfSailors(defaultBoat, defaultSailors, 0, new Transform(1000,0,0));
        var test = oarTheGoodAmountOfSailors.oarTheGoodAmountOfSailors();
        for(int j = 0; j<oars.length; j++)
        assertEquals(oars[j], test.get(j));


    }

    @Test
    void optimumNumberOfActiveOarsToBeOnTheCheckPointTest() {
        OarTheGoodAmountOfSailors oarTheGoodAmountOfSailors = new OarTheGoodAmountOfSailors(defaultBoat, defaultSailors, 0, new Transform(1000,0,0));
        assertEquals(10, oarTheGoodAmountOfSailors.optimumNumberOfActiveOarsToBeOnTheCheckPoint());


        oarTheGoodAmountOfSailors = new OarTheGoodAmountOfSailors(defaultBoat, defaultSailors, 0, new Transform(16.5,0,0));
        assertEquals(1, oarTheGoodAmountOfSailors.optimumNumberOfActiveOarsToBeOnTheCheckPoint());

        oarTheGoodAmountOfSailors = new OarTheGoodAmountOfSailors(defaultBoat, defaultSailors, 0, new Transform(33,0,0));
        assertEquals(2, oarTheGoodAmountOfSailors.optimumNumberOfActiveOarsToBeOnTheCheckPoint());

        oarTheGoodAmountOfSailors = new OarTheGoodAmountOfSailors(defaultBoat, defaultSailors, 0, new Transform(49.5,0,0));
        assertEquals(3, oarTheGoodAmountOfSailors.optimumNumberOfActiveOarsToBeOnTheCheckPoint());

        oarTheGoodAmountOfSailors = new OarTheGoodAmountOfSailors(defaultBoat, defaultSailors, 0, new Transform(66,0,0));
        assertEquals(4, oarTheGoodAmountOfSailors.optimumNumberOfActiveOarsToBeOnTheCheckPoint());

        oarTheGoodAmountOfSailors = new OarTheGoodAmountOfSailors(defaultBoat, defaultSailors, 0, new Transform(82.5,0,0));
        assertEquals(5, oarTheGoodAmountOfSailors.optimumNumberOfActiveOarsToBeOnTheCheckPoint());

        oarTheGoodAmountOfSailors = new OarTheGoodAmountOfSailors(defaultBoat, defaultSailors, 0, new Transform(99,0,0));
        assertEquals(6, oarTheGoodAmountOfSailors.optimumNumberOfActiveOarsToBeOnTheCheckPoint());
    }

    @Test
    void generateOarActionWhenDifferenceIsPositive2Test() {

        List<Marin> listOfBabordSailors = HeadquarterUtil.getListOfSailorsOnBabordOars(defaultSailors, defaultBoat);
        List<Marin> listOfTribordSailors = HeadquarterUtil.getListOfSailorsOnTribordOars(defaultSailors, defaultBoat);
        System.out.println(listOfBabordSailors);
        System.out.println(listOfTribordSailors);

        OarTheGoodAmountOfSailors oarTheGoodAmountOfSailors = new OarTheGoodAmountOfSailors(defaultBoat, defaultSailors, 2, new Transform(1000,0,0));
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


        oarTheGoodAmountOfSailors = new OarTheGoodAmountOfSailors(defaultBoat, defaultSailors, 2, new Transform(49.5,0,0));
        System.out.println(oarTheGoodAmountOfSailors.generateOarActionWhenDifferenceIsPositive());

    }


    @Test
    void generateOarActionWhenDifferenceIsNegative2Test() {

        List<Marin> listOfBabordSailors = HeadquarterUtil.getListOfSailorsOnBabordOars(defaultSailors, defaultBoat);
        List<Marin> listOfTribordSailors = HeadquarterUtil.getListOfSailorsOnTribordOars(defaultSailors, defaultBoat);
        System.out.println(listOfBabordSailors);
        System.out.println(listOfTribordSailors);

        OarTheGoodAmountOfSailors oarTheGoodAmountOfSailors = new OarTheGoodAmountOfSailors(defaultBoat, defaultSailors, 2, new Transform(1000,0,0));
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


        oarTheGoodAmountOfSailors = new OarTheGoodAmountOfSailors(defaultBoat, defaultSailors, 2, new Transform(49.5,0,0));
        System.out.println(oarTheGoodAmountOfSailors.generateOarActionWhenDifferenceIsNegative());

    }
}
