package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.strategy;

import fr.unice.polytech.si3.qgl.qualituriers.Deck;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.*;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.headquarterutils.HeadquarterUtil;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Moving;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.text.Position;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InitSailorsPlaceOnRudderTest {

    private Boat defaultBoat;
    private Boat completeBoat;
    private List<Marin> defaultSailors;
    private int defaultLife;
    private Transform defaultTransform;
    private String defaultName;
    private Deck defaultDeck;
    private Shape defaultShape;



    @BeforeEach
    void setUp() {
        defaultLife = 100;
        defaultTransform = new Transform(0, 0, 0);
        defaultName = "defaultName";
        defaultDeck = new Deck(5, 12);
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
        defaultShape = new Rectangle(5, 12, 0);
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
    void initSailorsPlaceOnRudderWhenSailorOnRudder() {

        InitSailorsPlaceOnRudder initSailorsPlaceOnRudder = new InitSailorsPlaceOnRudder(completeBoat, 18 , defaultSailors);
        initSailorsPlaceOnRudder.initSailorsPlaceOnRudder();
        initSailorsPlaceOnRudder.initSailorsPlaceOnRudder();
        assertEquals(new Point(11,2), HeadquarterUtil.getSailorByHisID(defaultSailors, 18).get().getPosition());
    }

    @Test
    void initSailorsPlaceOnRudderWhenSailorOnRudderMoreComplex() {

        InitSailorsPlaceOnRudder initSailorsPlaceOnRudder = new InitSailorsPlaceOnRudder(completeBoat, 0 , defaultSailors);
        initSailorsPlaceOnRudder.initSailorsPlaceOnRudder();
        initSailorsPlaceOnRudder.initSailorsPlaceOnRudder();
        initSailorsPlaceOnRudder.initSailorsPlaceOnRudder();
        assertEquals(new Point(11,2), HeadquarterUtil.getSailorByHisID(defaultSailors, 0).get().getPosition());
    }

    @Test
    void initSailorsPlaceOnRudderWhenSailorNotOnRudderButInRangeOf5() {

        BoatEntity[] currentBoatEntities = {new RudderBoatEntity(11,3)};
        Marin currentSailor = new Marin(0, 9, 0, "marin0");
        List<Marin> currentSailors = new ArrayList<>() {{ add(currentSailor); }};
        Boat tempBoat = new Boat(defaultLife, defaultTransform, defaultName, defaultDeck,currentBoatEntities, defaultShape);

        InitSailorsPlaceOnRudder initSailorsPlaceOnRudder = new InitSailorsPlaceOnRudder(tempBoat, currentSailor.getId(), currentSailors);

        assertEquals(new Moving(0,2,3), initSailorsPlaceOnRudder.initSailorsPlaceOnRudder().get(0));
        assertEquals(currentSailor.getX(), 11);
        assertEquals(currentSailor.getY(), 3);
    }

}
