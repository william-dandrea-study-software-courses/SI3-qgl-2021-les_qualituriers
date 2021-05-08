package fr.unice.polytech.si3.qgl.qualituriers.game.headquarter;

import fr.unice.polytech.si3.qgl.qualituriers.Deck;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.OarBoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.game.headquarter.strategy.InitSailorsPlaceOnOars;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class HeadQuarterTest {

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
    void playTurn() {

        InitSailorsPlaceOnOars initSailorsPlaceOnOars = new InitSailorsPlaceOnOars(defaultBoat, defaultSailors);
        initSailorsPlaceOnOars.initSailorsPlace();
        initSailorsPlaceOnOars.initSailorsPlace();


    }
}
