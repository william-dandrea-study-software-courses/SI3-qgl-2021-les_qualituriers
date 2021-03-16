package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.headquarter.strategy;

import fr.unice.polytech.si3.qgl.qualituriers.Deck;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.OarBoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.Wind;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.StreamVisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.VisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.game.goal.RegattaGoal;
import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        defaultBoatEntities = new BoatEntity[] {new OarBoatEntity(0,0), new OarBoatEntity(0,3)};
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
    void detectWind() {

        WindStrategy windStrategy = new WindStrategy(defaultBoat, defaultGoal, defaultSailors, defaultGameInfo);
        System.out.println(windStrategy.detectWind());

    }
}
