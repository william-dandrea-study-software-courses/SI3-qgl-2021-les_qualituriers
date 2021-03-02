package engine;

import fr.unice.polytech.si3.qgl.qualituriers.Deck;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.*;
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
            new RudderBoatEntity(0, 4),
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

    private static final int boatLife = 100;

    private static final Transform boatTransform = new Transform(0,0,0);

    private static final String boatName = "superFregate";

    private static final Deck boatDeck = new Deck(5, 12);

    public static final List<Marin> boatSailors = new ArrayList<>() {{
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


    private static final Shape boatShape = new Rectangle(5, 12, 0);

    public static final Boat boat = new Boat(boatLife, boatTransform, boatName, boatDeck, boatEntities, boatShape);

    public static final RegattaGoal goal = new RegattaGoal(new CheckPoint[] {
            // WEEK4
            new CheckPoint(new Transform(new Point(450.20463847203257, 932.4324324324338), 0), new Circle(100)),
            new CheckPoint(new Transform(new Point(1084.5839017735332, -54.05405405405337), 0), new Circle(100)),
            new CheckPoint(new Transform(new Point(738.7448840381987, -382.4324324324324), 0), new Circle(50)),
            new CheckPoint(new Transform(new Point(477.4897680763976, -54.054054054053665), 0), new Circle(100)),
            new CheckPoint(new Transform(new Point(90.9276944065494, -398.6486486486496), 0), new Circle(50)),
            new CheckPoint(new Transform(new Point(-199.86357435197826, 1.545430450278218e-13), 0), new Circle(60)),
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


}
