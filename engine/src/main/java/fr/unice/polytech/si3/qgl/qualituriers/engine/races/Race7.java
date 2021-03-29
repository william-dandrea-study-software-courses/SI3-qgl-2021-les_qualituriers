package fr.unice.polytech.si3.qgl.qualituriers.engine.races;

import fr.unice.polytech.si3.qgl.qualituriers.Deck;
import fr.unice.polytech.si3.qgl.qualituriers.engine.TurnConfig;
import fr.unice.polytech.si3.qgl.qualituriers.engine.mechanics.*;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.*;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.Wind;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.ReefVisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.StreamVisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.VisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.game.goal.RegattaGoal;
import fr.unice.polytech.si3.qgl.qualituriers.utils.AngleUtil;
import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;

import java.util.ArrayList;
import java.util.List;

/**
 * @author D'Andréa William
 */
public class Race7 {

    static List<Marin> sailors = new ArrayList<>() {{
        add(new Marin(0,0,0,""));
        add(new Marin(1,0,1,""));
        add(new Marin(2,0,2,""));
        add(new Marin(3,1,0,""));
        add(new Marin(4,1,1,""));
        add(new Marin(5,1,2,""));
        add(new Marin(6,2,0,""));
        add(new Marin(7,2,1,""));
        add(new Marin(8,2,2,""));
        add(new Marin(9,3,0,""));
        add(new Marin(10,3,1,""));
        add(new Marin(11,3,2,""));
    }};

    static List<BoatEntity> boatEntities = new ArrayList<>() {{
        add(new RudderBoatEntity(6,1));
        add(new SailBoatEntity(3,1, false));
        add(new OarBoatEntity(1,2));
        add(new OarBoatEntity(2,2));
        add(new OarBoatEntity(3,2));
        add(new OarBoatEntity(5,2));
        add(new OarBoatEntity(4,2));
        add(new OarBoatEntity(1,0));
        add(new OarBoatEntity(2,0));
        add(new OarBoatEntity(4,0));
        add(new OarBoatEntity(3,0));
        add(new OarBoatEntity(5,0));
    }};

    static int life = 1050;

    static Transform positionBoat = new Transform(2852.173913043478,1978.827361563518,  -1.0297442586766543);

    static Deck boatDeck = new Deck(3,7);

    static Shape boatShape = new Rectangle(3,7, -1.0297442586766543);

    static Boat boat = new Boat(life, positionBoat, "race7", boatDeck, boatEntities.toArray(new BoatEntity[0]), boatShape);

    static RegattaGoal goal = new RegattaGoal(new CheckPoint[] {
            new CheckPoint(new Transform(4138.730832374324, 258.1996058558562, 0), new Circle(100)),
            new CheckPoint(new Transform(1722.858499744753, 136.05011261261518, 0), new Circle(100)),
            new CheckPoint(new Transform(4199.4998319136575, 3839.034346846844, 0), new Circle(100)),
            new CheckPoint(new Transform(1681.681788509998, 3874.260979729731, 0), new Circle(100)),
            new CheckPoint(new Transform(2858.2774083039967, 1998.9442567567567, 0), new Circle(100)),
    });

    static VisibleDeckEntity[] entities = new VisibleDeckEntity[] {
            new ReefVisibleDeckEntity(new Transform(2946.7939972714894, 682.4324324324299, 0), new Circle(250)),
            new ReefVisibleDeckEntity(new Transform(4234.2554663640385, 2440.8079954954965, 0.6981317007977318), new Rectangle(250, 1200, 0.6981317007977318)),
            new StreamVisibleDeckEntity(new Transform(3432.8570590045492, 2911.106418918915, -2.0594885173533086), new Rectangle(400, 1000, -2.0594885173533086), 10),
            new StreamVisibleDeckEntity(new Transform(3472.0327421555185, 1074.3243243243223, 2.0245819323134224), new Rectangle(200, 1000, 2.0245819323134224), 10),
            new ReefVisibleDeckEntity(new Transform(3001.364256480219, 4466.216216216216, 0), new Rectangle(500, 1750, 0)),
            new ReefVisibleDeckEntity(new Transform(2953.615279672578, -459.45945945945897, 0), new Rectangle(500, 1750, 0)),
            new ReefVisibleDeckEntity(new Transform(1461.7109946265862, 1513.3375563063044, -2.356194490192345), new Rectangle(400, 1350, -2.356194490192345)),
            new ReefVisibleDeckEntity(new Transform(2905.8663028649435, 3074.3243243243232, 0), new Circle(100)),
    };

    public static Race race = new Race(goal, boat, sailors.toArray(new Marin[0]), generateWind(), new Mechanic[] {
            new MovingMechanic(),
            new OarMechanic(),
            new LiftMechanic(),
            new RudderMechanic()
    }, entities);

    private static Wind generateWind() {
        if(TurnConfig.random.nextDouble() >= TurnConfig.noWind) {
            double orientation = 0;
            orientation = AngleUtil.modAngle(orientation);
            double strength = 100;
            return new Wind(orientation, strength);
        } else
            return null;
    }


}
