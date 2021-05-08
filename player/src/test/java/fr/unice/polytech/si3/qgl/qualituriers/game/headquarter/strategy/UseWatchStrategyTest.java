package fr.unice.polytech.si3.qgl.qualituriers.game.headquarter.strategy;

import fr.unice.polytech.si3.qgl.qualituriers.Deck;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.WatchBoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.Wind;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.VisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.game.goal.RegattaGoal;
import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class UseWatchStrategyTest {

    GameInfo gameInfo;
    GameInfo gameInfo2;
    List<Marin> sailors;
    Marin marinWeChoose;
    List<Integer> idSailorsWeUsesMoving;
    Boat boat;
    Boat boat2;
    UseWatchStrategy useWatchStrategy;
    UseWatchStrategy useWatchStrategy2;

    @BeforeEach
    void init() {
        gameInfo = new GameInfo(new RegattaGoal(new CheckPoint[]{new CheckPoint(new Transform(50, 50, 0), new Circle(50))}),
                new Boat(100, new Transform(10, 10, 0), "lol", new Deck(4, 5), new BoatEntity[0], new Rectangle(2, 3, 0)),
                new Marin[]{new Marin(0, 1, 1, "sdfsdf"), new Marin(1, 2, 2, "tetetetet"), new Marin(2, 3, 3, "tytytytytytyt")},
                2,
                new Wind(0, 0),
                new VisibleDeckEntity[0]);


        sailors = new ArrayList<>();
        sailors.add(new Marin(0, 1, 1, "sdfsdf"));
        sailors.add(new Marin(1, 2, 2, "tetetetet"));
        sailors.add(new Marin(2, 3, 3, "tytytytytytyt"));
        boat = new Boat(100, new Transform(10, 10, 0), "lol", new Deck(4, 5), new BoatEntity[0], new Rectangle(2, 3, 0));
        idSailorsWeUsesMoving = new ArrayList<>();
        idSailorsWeUsesMoving.add(1);
        idSailorsWeUsesMoving.add(0);
        marinWeChoose = sailors.get(2);
        useWatchStrategy = new UseWatchStrategy(gameInfo, sailors, boat, idSailorsWeUsesMoving, marinWeChoose);


        boat2 = new Boat(100, new Transform(10, 10, 0), "lol", new Deck(4, 5), new BoatEntity[]{
                new WatchBoatEntity(2, 2)
        }, new Rectangle(2, 3, 0));
        gameInfo2 = new GameInfo(new RegattaGoal(new CheckPoint[]{new CheckPoint(new Transform(50, 50, 0), new Circle(50))}),
                new Boat(100, new Transform(10, 10, 0), "lol", new Deck(4, 5), new BoatEntity[]{
                    new WatchBoatEntity(2, 2)
                }, new Rectangle(2, 3, 0)),
                new Marin[]{new Marin(0, 1, 1, "sdfsdf"), new Marin(1, 2, 2, "tetetetet"), new Marin(2, 3, 3, "tytytytytytyt")},
                2,
                new Wind(0, 0),
                new VisibleDeckEntity[0]);
        useWatchStrategy2 = new UseWatchStrategy(gameInfo2, sailors, boat2, idSailorsWeUsesMoving, marinWeChoose);
    }

    @Test
    void isSmartToUseWatchTest(){
        assertEquals(false, useWatchStrategy.isSmartToUseUseWatch());
        assertEquals(true, useWatchStrategy2.isSmartToUseUseWatch());
    }

    @Test
    void useWatchStrategyTest(){
        assertEquals("Action{type=USE_WATCH, sailorId=2}", useWatchStrategy2.useWatchStrategy().get(0).toString());
    }

}
