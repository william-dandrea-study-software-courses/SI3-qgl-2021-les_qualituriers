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
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Actions;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Main {



    // "checkpoints":
    // [{"position":{"x":10.0,"y":-350.0,"orientation":0.0},"shape":{"type":"circle","radius":85.0}},
    // {"position":{"x":-150.0,"y":1250.0,"orientation":0.0},"shape":{"type":"circle","radius":85.0}}]}
    static Boat createBoat() {

        return TurnConfig.boat;
    }
    static Marin[] createSailors() {

        return TurnConfig.boatSailors.toArray(new Marin[0]);
    }

    static Race createRace() {

        return new Race(TurnConfig.goal, createBoat(), createSailors(), new Mechanic[] {
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



            Arrays.stream(race.getMechanics()).forEach(m -> {m.execute(finalActionsDone, race);});



            deckRenderer.setSailor(race.getSailors());

            renderer.draw();
            //deckRenderer.draw();

            TimeUnit.MILLISECONDS.sleep(200);
            compteurMax--;

        } while(actionsDone.length != 0 && compteurMax >=0);
        //      Run game
        //      Execute action
    }

    public static void main(String... args) throws IOException, InterruptedException {

        RunRace(createRace());

    }
}
