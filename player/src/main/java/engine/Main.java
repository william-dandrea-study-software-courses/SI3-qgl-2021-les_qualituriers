package engine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import engine.mechanics.MovingMechanic;
import engine.mechanics.OarMechanic;
import engine.mechanics.Mechanic;
import engine.races.Race;
import fr.unice.polytech.si3.qgl.qualituriers.Cockpit;
import fr.unice.polytech.si3.qgl.qualituriers.Deck;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.OarBoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.VisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.game.RoundInfo;
import fr.unice.polytech.si3.qgl.qualituriers.game.goal.RegattaGoal;
import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.*;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.serialization.Deserializer;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    static Boat createBoat() {
        BoatEntity oar = new OarBoatEntity(0,0) {};
        BoatEntity oar2 = new OarBoatEntity(1,0) {};
        BoatEntity oar3 = new OarBoatEntity(0,1) {};
        BoatEntity oar4 = new OarBoatEntity(1,1) {};
        BoatEntity oar5 = new OarBoatEntity(3,0) {};
        BoatEntity oar6 = new OarBoatEntity(3,1) {};

        int life = 100;
        Transform transform = new Transform(0,0,0);
        String name = "boatTest1";
        Deck deck = new Deck(2,4);
        BoatEntity[] entities = {oar6,oar5,oar3,oar4, oar2, oar};
        Shape shape = new Rectangle(5, 3, 0);


        return new Boat(life, transform, name, deck, entities,shape);
    }
    static Marin[] createSailors() {

        Marin sailor1 = new Marin(1,0,1,"sailor1");
        Marin sailor2 = new Marin(2,1,0,"sailor2");
        Marin sailor3 = new Marin(3,0,1,"sailor3");
        Marin sailor4 = new Marin(4,1,1,"sailor4");
        return new Marin[] {sailor4, sailor3,sailor2,sailor1};
    }

    static Race createRace() {
        var goal = new RegattaGoal(new CheckPoint[] { new CheckPoint(new Transform(new Point(-100, 10), 0), new Circle(5))} );
        return new Race(goal, createBoat(), createSailors(), new Mechanic[] {
                new MovingMechanic(),
                new OarMechanic()
        });
    }

    static void RunRace(Race race) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Action.class, new Deserializer());
        om.registerModule(module);


        // Init game
        var gameInfo = new GameInfo(race.getGoal(), race.getBoat(), race.getSailors(), 1);

        Cockpit cockpit = new Cockpit();
        var ignitionString = om.writeValueAsString(gameInfo);
        cockpit.initGame(ignitionString);
        // Loop

        List<Action> actionsDone = new ArrayList<>();

        do {
            RoundInfo rInfo = new RoundInfo(race.getBoat(), null, new VisibleDeckEntity[] {});
            var roundString = om.writeValueAsString(rInfo);

            var actionString = cockpit.nextRound(roundString);

            actionsDone = om.readValue(actionString, List.class);

            List<Action> finalActionsDone = actionsDone;
            Arrays.stream(race.getMechanics()).forEach(m -> m.Execute(finalActionsDone, race));
        } while(actionsDone.size() != 0);
        //      Run game
        //      Execute action
    }

    public static void main(String... args) throws JsonProcessingException {
        RunRace(createRace());
    }
}
