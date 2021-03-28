package fr.unice.polytech.si3.qgl.qualituriers.engine.races;

import fr.unice.polytech.si3.qgl.qualituriers.engine.TurnConfig;
import fr.unice.polytech.si3.qgl.qualituriers.engine.mechanics.*;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.Wind;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.ReefVisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.VisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.game.goal.RegattaGoal;
import fr.unice.polytech.si3.qgl.qualituriers.utils.AngleUtil;
import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;

public class TestPathfinding {
    static RegattaGoal goal = new RegattaGoal(new CheckPoint[] {
            new CheckPoint(new Transform(1500, 3000, 0), new Circle(100)),
            new CheckPoint(new Transform(4500, 4000, 0), new Circle(100)),
            new CheckPoint(new Transform(1500, 5000, 0), new Circle(100)),
            new CheckPoint(new Transform(4500, 6000, 0), new Circle(100)),
            new CheckPoint(new Transform(1500, 7000, 0), new Circle(100)),
    });

    static VisibleDeckEntity[] entities = new VisibleDeckEntity[] {
            new ReefVisibleDeckEntity(new Transform(3000, 3000, 0), new Rectangle(2000, 500, 0)),
            new ReefVisibleDeckEntity(new Transform(3000, 4000, 0), new Rectangle(2000, 500, 0)),
            new ReefVisibleDeckEntity(new Transform(3000, 4500, 0), new Rectangle(500, 500, 0)),
            new ReefVisibleDeckEntity(new Transform(3000, 5000, 0), new Rectangle(2000, 500, 0)),
            new ReefVisibleDeckEntity(new Transform(3000, 6000, 0), new Rectangle(2000, 500, 0)),
            new ReefVisibleDeckEntity(new Transform(3000, 7000, 0), new Rectangle(2000, 500, 0)),
    };

    //  boatTransform = new Transform(2852.173913043478,1978.827361563518,-1.0297442586766543);
    public static Race race = new Race(goal, TurnConfig.boat, TurnConfig.boatSailors.toArray(new Marin[0]), generateWind(), new Mechanic[] {
            new MovingMechanic(),
            new OarMechanic(),
            new LiftMechanic(),
            new RudderMechanic()
    }, entities);

    private static Wind generateWind() {
        if(TurnConfig.random.nextDouble() >= TurnConfig.noWind) {
            double orientation = TurnConfig.random.nextDouble() * Math.PI; //Techniquement, elle peut être de 0, mais on ne va pas le prendre en compte
            if(TurnConfig.random.nextBoolean())
                orientation *= -1;
            orientation = AngleUtil.modAngle(orientation);
            double strength = TurnConfig.random.nextDouble() * (TurnConfig.maxStrength - TurnConfig.minStrength) + TurnConfig.minStrength;
            return new Wind(orientation, strength);
        } else
            return null;
    }
}
