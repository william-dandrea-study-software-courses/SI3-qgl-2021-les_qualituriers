package fr.unice.polytech.si3.qgl.qualituriers.engine;

import fr.unice.polytech.si3.qgl.qualituriers.Deck;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.*;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.ReefVisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.StreamVisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.VisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.game.goal.RegattaGoal;
import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/*
{"length":7,"width":3},"entities":[
{"type":"rudder","x":6,"y":1},
{"type":"sail","x":3,"y":1},
{"type":"oar","x":1,"y":2},
{"type":"oar","x":2,"y":2},
{"type":"oar","x":3,"y":2},
{"type":"oar","x":5,"y":2},
{"type":"oar","x":4,"y":2},
{"type":"oar","x":1,"y":0},
{"type":"oar","x":2,"y":0},
{"type":"oar","x":4,"y":0},
{"type":"oar","x":3,"y":0},
{"type":"oar","x":5,"y":0}],"life":1050},"wind":{"orientation":0,"strength":50}
 */

public class TurnConfig {


    public static final BoatEntity[] boatEntities = {
            new RudderBoatEntity(6, 1),
            new SailBoatEntity(3,1, false),
            new OarBoatEntity(1, 2),
            new OarBoatEntity(2, 2),
            new OarBoatEntity(3, 2),
            new OarBoatEntity(5, 2),
            new OarBoatEntity(4, 2),
            new OarBoatEntity(1, 0),
            new OarBoatEntity(2, 0),
            new OarBoatEntity(4, 0),
            new OarBoatEntity(3, 0),
            new OarBoatEntity(5, 0),

    };

    public static final VisibleDeckEntity[] seaEntities = {
            new ReefVisibleDeckEntity(new Transform(5416.415681234441, 211.04307432432563, 0.6981317007977318), new Rectangle(250,1200, 0.6981317007977318)),
            new ReefVisibleDeckEntity(new Transform(3567.708333333334, 1126.3020833333342, 0), new Circle(350)),
            new ReefVisibleDeckEntity(new Transform(4500, 2300, Math.PI / 4), new Rectangle(200, 300, 0)),
            new ReefVisibleDeckEntity(new Transform(4000, 3500, Math.PI / 4), new Rectangle(200, 300, 0)),
            new ReefVisibleDeckEntity(new Transform(4500, 2800, -Math.PI / 4), new Rectangle(200, 300, 0)),
            new StreamVisibleDeckEntity(new Transform(5144.039698840838, 499.8240427927923, 0.7330382858376184), new Rectangle(400,1350, 0.7330382858376184), 10)
    };

    private static final int boatLife = 1050;

    private static final Transform boatTransform = new Transform(2852.173913043478,1978.827361563518,-1.0297442586766543);

    private static final String boatName = "superFregate";

    private static final Deck boatDeck = new Deck(3, 7);

    public static final List<Marin> boatSailors = new ArrayList<>() {{
        add(new Marin(0, 0, 0, "marin0"));
        add(new Marin(1, 0, 1, "marin1"));
        add(new Marin(2, 0, 2, "marin2"));
        add(new Marin(3, 1, 0, "marin3"));
        add(new Marin(4, 1, 1, "marin4"));
        add(new Marin(5, 1, 2, "marin4"));
        add(new Marin(6, 2, 0, "marin4"));
        add(new Marin(7, 2, 1, "marin4"));
        add(new Marin(8, 2, 2, "marin4"));
        add(new Marin(9, 3, 0, "marin4"));
        add(new Marin(10, 3, 1, "marin4"));
        add(new Marin(11, 3, 2, "marin4"));

        /*add(new Marin(6, 1, 1, "marin6"));
        add(new Marin(7, 1, 2, "marin7"));
        add(new Marin(8, 1, 3, "marin8"));

        add(new Marin(10, 2, 0, "marin10"));
        add(new Marin(11, 2, 1, "marin11"));*/
    }};


    private static final Shape boatShape = new Rectangle(5, 12, 0);

    public static final Boat boat = new Boat(boatLife, boatTransform, boatName, boatDeck, boatEntities, boatShape);

    public static final RegattaGoal goal = new RegattaGoal(new CheckPoint[] {
            // WEEK4
            new CheckPoint(new Transform(new Point(4036.4115963579497, 264.956362612613), 0), new Circle(100)),
            new CheckPoint(new Transform(new Point(6866.105430167673, 277.9420045045067), 0), new Circle(100)),
            new CheckPoint(new Transform(new Point(6261.605476524839, 937.2184684684704), 0), new Circle(100)),
            new CheckPoint(new Transform(new Point(4024.9876946036743, -1002.8153153153128), 0), new Circle(100)),
            new CheckPoint(new Transform(new Point(3460.8684297910377, 2243.1024774774787), 0), new Circle(100)),
    });

    public static final Random random = new Random();

    /**
     * Probabilité de ne pas avoir de vent durant un tour
     */
    public static final double noWind = 0.4;

    /**
     * La force maximal du vent
     */
    public static final double maxStrength = 20;
    /**
     * La force minimal du vent
     */
    public static final double minStrength = 1;

    /**
     * Champ de vision sans le vigie d'activé
     */
    public static final double FIELD_VISION = 1000;

    /**
     * Champ de vision avec le vigie d'activé
     */
    public static final double FIELD_VISION_ENLARGE = 5000;


}
