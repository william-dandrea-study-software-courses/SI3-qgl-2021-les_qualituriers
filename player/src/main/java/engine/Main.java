package engine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import engine.graphics.Sea.Sea;
import engine.mechanics.Mechanic;
import engine.mechanics.MovingMechanic;
import engine.mechanics.OarMechanic;
import engine.races.Race;
import engine.serializers.RectangleSerializer;
import fr.unice.polytech.si3.qgl.qualituriers.Cockpit;
import fr.unice.polytech.si3.qgl.qualituriers.Deck;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.*;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.VisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.game.RoundInfo;
import fr.unice.polytech.si3.qgl.qualituriers.game.goal.RegattaGoal;
import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {

    // "checkpoints":
    // [{"position":{"x":10.0,"y":-350.0,"orientation":0.0},"shape":{"type":"circle","radius":85.0}},
    // {"position":{"x":-150.0,"y":1250.0,"orientation":0.0},"shape":{"type":"circle","radius":85.0}}]}
    static Boat createBoat() {
        BoatEntity oar = new OarBoatEntity(0,0);
        BoatEntity oar2 = new OarBoatEntity(1,0);
        BoatEntity oar3 = new OarBoatEntity(0,1);
        BoatEntity oar4 = new OarBoatEntity(1,1);
        BoatEntity oar5 = new RudderBoatEntity(2,0);
        BoatEntity oar6 = new SailBoatEntity(2,1, false);
        BoatEntity oar7 = new OarBoatEntity(3,0);
        BoatEntity oar8 = new OarBoatEntity(3,1);

        int life = 100;
        Transform transform = new Transform(0,0,0);
        String name = "boatTest1";
        Deck deck = new Deck(2,4);
        BoatEntity[] entities = {oar8, oar7, oar6,oar5,oar3,oar4, oar2, oar};
        Shape shape = new Rectangle(5, 3, 0);

        return new Boat(life, transform, name, deck, entities,shape);
    }
    static Marin[] createSailors() {

        Marin sailor1 = new Marin(1,0,1,"sailor1");
        Marin sailor2 = new Marin(2,1,0,"sailor2");
        Marin sailor3 = new Marin(3,0,0,"sailor3");
        Marin sailor4 = new Marin(4,1,1,"sailor4");
        return new Marin[] {sailor4, sailor3,sailor2,sailor1};
    }

    static Race createRace() {
        var goal = new RegattaGoal(new CheckPoint[] {
                //WEEK3
                //new CheckPoint(new Transform(new Point(10.0, -350.0), 0.0), new Circle(85.0)),
                //new CheckPoint(new Transform(new Point(-150.0, 1250.0), 0.0), new Circle(85.0))

                // WEEK4
                new CheckPoint(new Transform(new Point(450.20463847203257, 932.4324324324338), 0), new Circle(100)),
                new CheckPoint(new Transform(new Point(1084.5839017735332, -54.05405405405337), 0), new Circle(100)),
                new CheckPoint(new Transform(new Point(738.7448840381987, -382.4324324324324), 0), new Circle(50)),
                new CheckPoint(new Transform(new Point(477.4897680763976, -54.054054054053665), 0), new Circle(100)),
                new CheckPoint(new Transform(new Point(90.9276944065494, -398.6486486486496), 0), new Circle(50)),
                new CheckPoint(new Transform(new Point(-199.86357435197826, 1.545430450278218e-13), 0), new Circle(60)),
        });
        return new Race(goal, createBoat(), createSailors(), new Mechanic[] {
                new MovingMechanic(),
                new OarMechanic()
        });
    }

    static void RunRace(Race race) throws JsonProcessingException, InterruptedException {


        ObjectMapper om = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Rectangle.class, new RectangleSerializer());
        om.registerModule(module);

        // Init game
        var gameInfo = new GameInfo(race.getGoal(), race.getBoat(), race.getSailors(), 1);


        Cockpit cockpit = new Cockpit();
        var ignitionString = om.writeValueAsString(gameInfo);
        cockpit.initGame(ignitionString);
        // Loop

        Action[] actionsDone;

        Sea renderer = new Sea(race);
        engine.graphics.Deck.Deck deckRenderer = new engine.graphics.Deck.Deck(race.getBoat(), createSailors());

        int compteurMax = 200;
        do {
            RoundInfo rInfo = new RoundInfo(race.getBoat(), null, new VisibleDeckEntity[] {});
            var roundString = om.writeValueAsString(rInfo);

            var actionString = cockpit.nextRound(roundString);

            actionsDone = om.readValue(actionString, Action[].class);

            List<Action> finalActionsDone = List.of(actionsDone);
            Arrays.stream(race.getMechanics()).forEach(m -> {
                m.execute(finalActionsDone, race);
            });
            deckRenderer.setSailor(race.getSailors());

            renderer.draw();
            //deckRenderer.draw();

            TimeUnit.MILLISECONDS.sleep(800);
            compteurMax--;

        } while(actionsDone.length != 0 && compteurMax >=0);
        //      Run game
        //      Execute action
    }

    public static void main(String... args) throws IOException, InterruptedException {

        RunRace(createRace());

    }
}
