package fr.unice.polytech.si3.qgl.qualituriers.engine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import fr.unice.polytech.si3.qgl.qualituriers.Cockpit;
import fr.unice.polytech.si3.qgl.qualituriers.engine.graphics.Deck.DeckRenderer;
import fr.unice.polytech.si3.qgl.qualituriers.engine.graphics.Sea.Sea;
import fr.unice.polytech.si3.qgl.qualituriers.engine.mechanics.*;
import fr.unice.polytech.si3.qgl.qualituriers.engine.races.Race;
import fr.unice.polytech.si3.qgl.qualituriers.engine.races.Race6;
import fr.unice.polytech.si3.qgl.qualituriers.engine.races.Race7;
import fr.unice.polytech.si3.qgl.qualituriers.engine.races.TestPathfinding;
import fr.unice.polytech.si3.qgl.qualituriers.engine.serializers.RectangleSerializer;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.Wind;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.ReefVisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.game.RoundInfo;
import fr.unice.polytech.si3.qgl.qualituriers.render.TempoRender;
import fr.unice.polytech.si3.qgl.qualituriers.utils.AngleUtil;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Collisions;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.*;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
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

    static Race createRaceFromFile(String path) throws FileNotFoundException, JsonProcessingException {
        File file = new File(path);
        Scanner scanner = new Scanner(file);
        StringBuilder json = new StringBuilder();
        while(scanner.hasNextLine())
            json.append(scanner.nextLine()).append("\n");
        return null;
    }

    static void RunRace(Race race) throws JsonProcessingException, InterruptedException {
        TempoRender.ON_ENGINE = true;


        ObjectMapper om = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Rectangle.class, new RectangleSerializer());
        om.registerModule(module);

        // Init game
        var gameInfo = new GameInfo(race.getGoal(), race.getBoat(), race.getSailors(), 1, new Wind(0, 50), race.getEntities());


        Cockpit cockpit = new Cockpit();
        var ignitionString = om.writeValueAsString(gameInfo);
        cockpit.initGame(ignitionString);
        // Loop

        Action[] actionsDone;

        Sea renderer = new Sea(race);
        //DeckRenderer deckRenderer = new DeckRenderer(race.getBoat(), createSailors());

        int compteurMax = 500;
        do {
            Wind wind = generateWind();
            RoundInfo rInfo = new RoundInfo(race.getBoat(), wind, race.getEntities());
            var roundString = om.writeValueAsString(rInfo);

            var actionString = cockpit.nextRound(roundString);

            race.setWind(wind);

            actionsDone = om.readValue(actionString, Action[].class);


            List<Action> finalActionsDone = List.of(actionsDone);

            checkMultipleActions(finalActionsDone);

            Transform oldPosition = race.getBoat().getPosition();
            Arrays.stream(race.getMechanics()).forEach(m -> m.execute(finalActionsDone, race));

            collisions(race);


            collisions(race);
            renderer.draw();
            //deckRenderer.draw();

            TimeUnit.MILLISECONDS.sleep(200);
            compteurMax--;
            //System.out.println(cockpit.getLogs());
            cockpit.getLogs().clear();

        } while(actionsDone.length != 0 && compteurMax >= 0 && race.getBoat().getLife() > 0);

        //Pour pouvoir continuer à faire des trucs
        while (true) {
            //deckRenderer.setSailor(race.getSailors());
            renderer.draw();
            TimeUnit.MILLISECONDS.sleep(2000);
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
        if(race.getEntities() != null) {
            for (ReefVisibleDeckEntity reef : Arrays.stream(race.getEntities())
                    .filter(entity -> entity instanceof ReefVisibleDeckEntity)
                    .map(entity -> (ReefVisibleDeckEntity) entity)
                    .collect(Collectors.toList())) {

                if (Collisions.isColliding(race.getBoat().getPositionableShape(), reef.getPositionableShape()))
                    race.getBoat().setLife(0);
            }
        }
    }

    private static void checkMultipleActions(List<Action> actions) {
        Map<Integer, Integer> sailorsMoving = new HashMap<>();
        Map<Integer, List<Actions>> sailorsActions = new HashMap<>();
        for (Action action : actions) {
            int sailorId = action.getSailorId();
            if(action.getType() == Actions.MOVING) {
                if(sailorsMoving.containsKey(sailorId))
                    sailorsMoving.put(sailorId, sailorsMoving.get(sailorId) + 1);
                else
                    sailorsMoving.put(sailorId, 1);
            } else {
                if (sailorsActions.containsKey(sailorId))
                    sailorsActions.get(sailorId).add(action.getType());
                else {
                    ArrayList<Actions> sailorActions = new ArrayList<>();
                    sailorActions.add(action.getType());
                    sailorsActions.put(sailorId, sailorActions);
                }
            }
        }

        List<Map.Entry<Integer, Integer>> tooManyMoves = sailorsMoving.entrySet().stream()
                .filter(entry -> entry.getValue() >= 2)
                .collect(Collectors.toList());
        List<Map.Entry<Integer, List<Actions>>> tooManyActions = sailorsActions.entrySet().stream()
                .filter(entry -> entry.getValue().size() >= 2)
                .collect(Collectors.toList());
        //if(!tooManyActions.isEmpty())
        //    throw new IllegalStateException("Plusieurs actions ont été donné à un/plusieurs marin(s): " + tooManyActions);
        //else if(!tooManyMoves.isEmpty())
         //   throw new IllegalStateException("Plusieurs déplacements à été donné à un/plusieurs marin(s): " + tooManyMoves);
    }

    private static Transform[] calculateMiddlePosition(Transform oldPosition, Transform speed) {
        Transform[] positions = new Transform[TurnConfig.STEP];
        Point vector = speed.getPoint().scalar(1d/TurnConfig.STEP);
        double angle = speed.getOrientation() / TurnConfig.STEP;
        for (int i = 0; i < TurnConfig.STEP; i++) {
            if(i == 0)
                positions[i] = oldPosition.translate(vector.rotate(oldPosition.getOrientation())).rotate(angle);
            else
                positions[i] = positions[i - 1].translate(vector.rotate(positions[i - 1].getOrientation())).rotate(angle);
        }
        return positions;
    }

    static Race loadRace(String name) throws FileNotFoundException, JsonProcessingException {
        File file = new File("engine/src/main/java/fr/unice/polytech/si3/qgl/qualituriers/engine/races/webrunnerjsonraces/" + name + ".json");
        Scanner scanner = new Scanner(file);
        String json = "";
        while(scanner.hasNextLine())
            json += scanner.nextLine() + "\n";



        return new Race(json, new Mechanic[] {
                new MovingMechanic(),
                new OarMechanic(),
                new LiftMechanic(),
                new RudderMechanic()
        });
    }

    public static void main(String... args) throws IOException, InterruptedException {
        RunRace(loadRace("WEEK8_PREVIEW2"));
        //RunRace(TestPathfinding.race);
    }
}
