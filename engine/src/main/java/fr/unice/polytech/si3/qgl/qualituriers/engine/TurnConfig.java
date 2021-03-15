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

public class TurnConfig {


    public static final BoatEntity[] boatEntities = {
            new RudderBoatEntity(3, 2),
            new SailBoatEntity(3,1, false),
            new OarBoatEntity(1, 3),
            new OarBoatEntity(2, 3),
            new OarBoatEntity(3, 3),
            new OarBoatEntity(4, 3),
            new OarBoatEntity(1, 0),
            new OarBoatEntity(2, 0),
            new OarBoatEntity(3, 0),
            new OarBoatEntity(4, 0),

    };

    public static final VisibleDeckEntity[] seaEntities = {
            new ReefVisibleDeckEntity(new Transform(5000, 0, 0), new Circle(200)),
            new StreamVisibleDeckEntity(new Transform(4000, 0, Math.PI / 2), new Circle(200), 5)
    };

    private static final int boatLife = 100;

    private static final Transform boatTransform = new Transform(2852.173913043478,1978.827361563518,0);

    private static final String boatName = "superFregate";

    private static final Deck boatDeck = new Deck(4, 6);

    public static final List<Marin> boatSailors = new ArrayList<>() {{
        add(new Marin(0, 0, 0, "marin0"));
        add(new Marin(1, 0, 1, "marin1"));
        add(new Marin(2, 0, 2, "marin2"));
        add(new Marin(3, 0, 3, "marin3"));
        add(new Marin(4, 0, 4, "marin4"));

        add(new Marin(6, 1, 1, "marin6"));
        add(new Marin(7, 1, 2, "marin7"));
        add(new Marin(8, 1, 3, "marin8"));

        add(new Marin(10, 2, 0, "marin10"));
        add(new Marin(10, 2, 1, "marin10"));
    }};


    private static final Shape boatShape = new Rectangle(5, 12, 0);

    public static final Boat boat = new Boat(boatLife, boatTransform, boatName, boatDeck, boatEntities, boatShape);

    public static final RegattaGoal goal = new RegattaGoal(new CheckPoint[] {
            // WEEK4
            new CheckPoint(new Transform(new Point(5582.608695652169, 2092.833876221499), 0), new Circle(100)),
            new CheckPoint(new Transform(new Point(3869.565217391304, 3737.7850162866466), 0), new Circle(100)),
            new CheckPoint(new Transform(new Point(1634.7826086956427, 3884.3648208469035), 0), new Circle(100)),
            new CheckPoint(new Transform(new Point(-400.00000000000085, 1970.684039087947), 0), new Circle(100)),
            new CheckPoint(new Transform(new Point(5452.173913043472, -57.00325732899084), 0), new Circle(100)),
            new CheckPoint(new Transform(new Point(7965.21739130434, 2117.2638436482102), 0), new Circle(60)),
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
