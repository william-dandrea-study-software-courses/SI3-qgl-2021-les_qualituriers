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

public class Race6 {

    static RegattaGoal goal = new RegattaGoal(new CheckPoint[] {
        new CheckPoint(new Transform(4036.4115963579497, 264.956362612613, 0), new Circle(100)),
        new CheckPoint(new Transform(6866.105430167673, 277.9420045045067, 0), new Circle(100)),
        new CheckPoint(new Transform(8019.417976524843, 1210.6559684684694, 0), new Circle(100)),
        new CheckPoint(new Transform(8100.5085279370105, 2245.8826013513526, 0), new Circle(100)),
        new CheckPoint(new Transform(4652.274679791037, 2802.9983108108113, 0), new Circle(100)),
    });

    static VisibleDeckEntity[] entities = new VisibleDeckEntity[] {
        new ReefVisibleDeckEntity(new Transform(8153.389030097254, 351.1753941441404, -0.4537856055185257), new Rectangle(400, 1350, 0)),
        new ReefVisibleDeckEntity(new Transform(6186.903137789905, 2006.7567567567567, -0.17453292519943295), new Rectangle(1750, 500, 0)),
        new ReefVisibleDeckEntity(new Transform(6630.286493860851, 3209.4594594594596, -0.24434609527920614), new Rectangle(1750, 500, 0)),
        new ReefVisibleDeckEntity(new Transform(7174.22818123444, 1102.9701576576585, -0.6981317007977318), new Rectangle(250, 1200, 0)),
        new ReefVisibleDeckEntity(new Transform(7409.664698840839, 701.646959459459, -0.7330382858376184), new Rectangle(400, 1000, 0)),
        new ReefVisibleDeckEntity(new Transform(6384.720327421553, 2581.0810810810804, 3.0019663134302466), new Rectangle(1000, 200, 0)),
    };

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
