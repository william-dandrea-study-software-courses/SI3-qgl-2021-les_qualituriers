package fr.unice.polytech.si3.qgl.qualituriers.game.headquarter.strategy;

import fr.unice.polytech.si3.qgl.qualituriers.Deck;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.OarBoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.SailBoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.game.headquarter.headquarterutils.HeadquarterUtil;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.Wind;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.StreamVisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.VisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.game.goal.RegattaGoal;
import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Moving;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class WindStrategyTest {


    private Transform defaultBoatPosition;
    private Deck defaultBoatDeck;
    private BoatEntity[] defaultBoatEntities;
    private Shape defaultBoatShape;

    private CheckPoint[] defaultGameInfoCheckpoints;
    private Wind defaultGameInfoWind;
    private VisibleDeckEntity[] defaultGameInfoSeaEntities;


    private Boat defaultBoat;
    private Transform defaultGoal;
    private List<Marin> defaultSailors;
    private GameInfo defaultGameInfo;





    @BeforeEach
    void setup() {

        defaultBoatPosition = new Transform(new Point(0,0), 0);
        defaultBoatDeck = new Deck(4,12);
        defaultBoatEntities = new BoatEntity[] {new OarBoatEntity(0,0), new OarBoatEntity(0,3), new SailBoatEntity(11,3, false)};
        defaultBoatShape = new Rectangle(4, 12, 0);

        defaultBoat = new Boat(10, defaultBoatPosition, "boatName1", defaultBoatDeck, defaultBoatEntities, defaultBoatShape);
        defaultGoal = new Transform(new Point(1000,1000), 0);
        defaultSailors = new ArrayList<>() {{
            add(new Marin(0,0,0,"marin0"));
            add(new Marin(1,1,0,"marin1"));
            add(new Marin(2,2,0,"marin2"));
            add(new Marin(3,3,0,"marin3"));
        }};

        defaultGameInfoCheckpoints = new CheckPoint[] {new CheckPoint(defaultGoal, new Circle(50))};
        defaultGameInfoWind = new Wind(0, 100);
        defaultGameInfoSeaEntities = new VisibleDeckEntity[] {
                new StreamVisibleDeckEntity(
                        new Transform(new Point(100,100),
                                Math.PI/6),
                        new Circle(30),
                        30.0
                ),
        };


        defaultGameInfo = new GameInfo(
                new RegattaGoal(defaultGameInfoCheckpoints),
                defaultBoat,
                defaultSailors.toArray(new Marin[0]),
                1,
                defaultGameInfoWind,
                defaultGameInfoSeaEntities
                );


    }

    @Test
    void setupWindCrashTest() {

        Optional<Marin> sailorForSail = HeadquarterUtil.getSailorByHisID(defaultSailors, 3);
        BoatEntity sail = HeadquarterUtil.getSail(defaultBoat).get();
        List<Integer> sailorsWeCantMove = new ArrayList<>() {{add(0); add(1); add(2);}};

        WindStrategy windStrategy = new WindStrategy(defaultBoat, defaultSailors, defaultGameInfo, sailorForSail, sail, sailorsWeCantMove );
        System.out.println(windStrategy.setupWind());
        assertTrue(windStrategy.setupWind().isEmpty());
    }

    @Test
    void setupWindClassicTest() {

        Optional<Marin> sailorForSail = HeadquarterUtil.getSailorByHisID(defaultSailors, 3);
        BoatEntity sail = HeadquarterUtil.getSail(defaultBoat).get();
        List<Integer> sailorsWeCantMove = new ArrayList<>() {{add(0);}};

        WindStrategy windStrategy = new WindStrategy(defaultBoat, defaultSailors, defaultGameInfo, sailorForSail, sail, sailorsWeCantMove );

        assertTrue(windStrategy.setupWind().contains(new Moving(2,5,0)));
    }
}
