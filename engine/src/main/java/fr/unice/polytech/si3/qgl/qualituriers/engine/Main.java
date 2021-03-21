package fr.unice.polytech.si3.qgl.qualituriers.engine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import fr.unice.polytech.si3.qgl.qualituriers.engine.graphics.Deck.DeckRenderer;
import fr.unice.polytech.si3.qgl.qualituriers.engine.graphics.Sea.Sea;
import fr.unice.polytech.si3.qgl.qualituriers.engine.mechanics.*;
import fr.unice.polytech.si3.qgl.qualituriers.engine.races.Race;
import fr.unice.polytech.si3.qgl.qualituriers.engine.serializers.RectangleSerializer;
import fr.unice.polytech.si3.qgl.qualituriers.Cockpit;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.*;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.Wind;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.ReefVisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.VisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.game.RoundInfo;
import fr.unice.polytech.si3.qgl.qualituriers.utils.AngleUtil;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Collisions;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Turn;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

        return new Race(TurnConfig.goal, createBoat(), createSailors(), generateWind(), new Mechanic[] {
                new MovingMechanic(),
                new OarMechanic(),
                new LiftMechanic(),
                new RudderMechanic()
        }, TurnConfig.seaEntities);
    }

    static void RunRace(Race race) throws JsonProcessingException, InterruptedException {

        ObjectMapper om = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Rectangle.class, new RectangleSerializer());
        om.registerModule(module);

        // Init game
        var gameInfo = new GameInfo(race.getGoal(), race.getBoat(), race.getSailors(), 1, new Wind(Math.PI, 20), race.getEntities());


        Cockpit cockpit = new Cockpit();
        var ignitionString = om.writeValueAsString(gameInfo);
        cockpit.initGame(ignitionString);
        // Loop

        Action[] actionsDone;

        Sea renderer = new Sea(race);
        DeckRenderer deckRenderer = new DeckRenderer(race.getBoat(), createSailors());

        int compteurMax = 500;
        do {
            Wind wind = generateWind();
            RoundInfo rInfo = new RoundInfo(race.getBoat(), wind, new VisibleDeckEntity[] {});
            var roundString = om.writeValueAsString(rInfo);

            var actionString = cockpit.nextRound(roundString);

            race.setWind(wind);

            actionsDone = om.readValue(actionString, Action[].class);

            List<Action> finalActionsDone = List.of(actionsDone);

            Arrays.stream(race.getMechanics()).forEach(m -> m.execute(finalActionsDone, race));

            collisions(race);

            deckRenderer.setSailor(race.getSailors());

            renderer.draw();
            //deckRenderer.draw();

            TimeUnit.MILLISECONDS.sleep(50);
            compteurMax--;
            //System.out.println(cockpit.getLogs());
            cockpit.getLogs().clear();

        } while(actionsDone.length != 0 && compteurMax >= 0 && race.getBoat().getLife() > 0);

        //Pour pouvoir continuer à faire des trucs
        while (true) {
            deckRenderer.setSailor(race.getSailors());
            renderer.draw();
            TimeUnit.MILLISECONDS.sleep(2);
        }
        //      Run game
        //      Execute action
    }

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

    private static void collisions(Race race) {
        for (ReefVisibleDeckEntity reef : Arrays.stream(race.getEntities())
                .filter(entity -> entity instanceof ReefVisibleDeckEntity)
                .map(entity -> (ReefVisibleDeckEntity) entity)
                .collect(Collectors.toList())) {
            if(Collisions.isColliding(race.getBoat().getPositionableShape(), reef.getPositionableShape()))
                race.getBoat().setLife(0);
        }
    }

    public static void main(String... args) throws IOException, InterruptedException {

        RunRace(createRace());

    }
}