package fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.ReefVisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.VisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Collisions;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding.pseudomaps.MyCustomRace;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableCircle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionablePolygon;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableShape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class MainPathfindingTest {
    boolean showDetails = false;
    MainPathfinding pathfinder;
    Boat boat;

    @BeforeEach
    void init() {
        pathfinder = new MainPathfinding();
        boat = new Boat(10, Transform.ZERO, "", null, null, new Rectangle(10, 30, 0));
    }


    private List<PositionableShape<? extends Shape>> getBasicMap() {
        return new ArrayList<>() {{
            add(new PositionablePolygon(new Rectangle(100, 100, 0), new Transform(new Point(1000, 0), 0)));
        }};
    }

    private Boat createBoatAt(Point position) {
        return new Boat(0, new Transform(position, 0), "", null, null, new Circle(10));
    }

    private CheckPoint createChecpointAt(Point position) {
        return new CheckPoint(new Transform(position, 0), new Circle(100));
    }


    private class NoFindPath extends RuntimeException {
        NoFindPath() {
            super("Can't find a path here !");
        }
    }
    private class CustomPathfinder extends MainPathfinding {
        private boolean canFindPath = false;

        void allowFindPath() {
            canFindPath = true;
        }

        void denyFindPath() {
            canFindPath = false;
        }

        @Override
        void FindANewPath(PathfindingContext context, List<PositionablePolygon> obstacles) {
            if(canFindPath) super.FindANewPath(context, obstacles);
            else throw new NoFindPath();
        }
    }

    @Test
    void TestDirectRoad() {
        var context = new PathfindingContext(Point.ZERO, new ArrayList<>(), new Point(300, 0), new PathfindingStore());

        CustomPathfinder pathfinder = new CustomPathfinder();
        assertDoesNotThrow(() -> pathfinder.getNextCheckpoint(context));
    }

    @Test
    void TestAtTheBegining() {
        var context = new PathfindingContext(Point.ZERO, new ArrayList<>() {{
            add(new ReefVisibleDeckEntity(new Transform(new Point(150, 0), 0), new Circle(50)));
        }}, new Point(300, 0), new PathfindingStore());

        CustomPathfinder pathfinder = new CustomPathfinder();
        assertThrows(NoFindPath.class, () -> pathfinder.getNextCheckpoint(context));
    }

    @Test
    void TestInvalidPath() {
        var store = new PathfindingStore();
        var context1 = new PathfindingContext(Point.ZERO, new ArrayList<>(), new Point(300, 0), store);
        var context2 = new PathfindingContext(Point.ZERO, new ArrayList<>() {{
            add(new ReefVisibleDeckEntity(new Transform(new Point(150, 0), 0), new Circle(50)));
        }}, new Point(300, 0), store);
        CustomPathfinder pathfinder = new CustomPathfinder();
        pathfinder.getNextCheckpoint(context1);

        // Check to don't  recalculate path
        assertDoesNotThrow(() -> pathfinder.getNextCheckpoint(context1));

        // Test recalculate if new obstacle
        assertThrows(NoFindPath.class, () -> pathfinder.getNextCheckpoint(context2));
    }

    @Test
    void CheckPointChanged() {
        var store = new PathfindingStore();
        var context1 = new PathfindingContext(Point.ZERO, new ArrayList<>() {{
            add(new ReefVisibleDeckEntity(new Transform(new Point(150, 0), 0), new Circle(50)));
        }}, new Point(300, 0), store);
        var context2 = new PathfindingContext(Point.ZERO, new ArrayList<>() {{
            add(new ReefVisibleDeckEntity(new Transform(new Point(150, 0), 0), new Circle(50)));
        }}, new Point(600, 0), store);
        CustomPathfinder pathfinder = new CustomPathfinder();

        pathfinder.allowFindPath();
        pathfinder.getNextCheckpoint(context1);
        pathfinder.denyFindPath();

        // Check to don't  recalculate path
        assertDoesNotThrow(() -> pathfinder.getNextCheckpoint(context1));

        // Test recalculate if new obstacle
        assertThrows(NoFindPath.class, () -> pathfinder.getNextCheckpoint(context2));
    }

    @Test
    void TestWaypointReached() {
        var store = new PathfindingStore();
        var context1 = new PathfindingContext(Point.ZERO, new ArrayList<>() {{
            add(new ReefVisibleDeckEntity(new Transform(new Point(150, 0), 0), new Circle(50)));
        }}, new Point(300, 0), store);

        CustomPathfinder pathfinder = new CustomPathfinder();

        pathfinder.allowFindPath();
        pathfinder.getNextCheckpoint(context1);
        pathfinder.denyFindPath();

        var context2 = new PathfindingContext(store.getCalculatedPath().get(store.getCurrentNodeToReach()).getPosition(), new ArrayList<>() {{
            add(new ReefVisibleDeckEntity(new Transform(new Point(150, 0), 0), new Circle(50)));
        }}, new Point(300, 0), store);

        // Test recalculate if new obstacle
        assertDoesNotThrow(() -> pathfinder.getNextCheckpoint(context2));
    }


    @Test
    void RunRace6() {
        TestRace("race1");
    }

    @Test
    void RunRace7() {
        TestRace("race2");
    }

    @Test
    void RunRace8() {
        TestRace("race3");
    }

    void TestRace(String name) {
        assertDoesNotThrow(() -> {
            var map = GetMap(name);
            List<PositionableShape<? extends Shape>> obstacles = new ArrayList<>();
            Arrays.stream(map.getObstacles())
                    .map(VisibleDeckEntity::getPositionableShape)
                    .forEach(obstacles::add);

            PathfindingStore store = new PathfindingStore();
            TestBetweenTwoCheckpoints(map.getStartPosition().getPoint(), map.getCheckPoints()[0].getPosition().getPoint(), obstacles, store);
            for(int i = 1; i < map.getCheckPoints().length; i++) {
                TestBetweenTwoCheckpoints(map.getCheckPoints()[i - 1].getPosition(), map.getCheckPoints()[i].getPosition().getPoint(), obstacles, store);
            }
        });
    }

    void TestBetweenTwoCheckpoints(Point start, Point end, List<PositionableShape<? extends Shape>> obstacles, PathfindingStore store) {
        MainPathfinding pathfinding = new MainPathfinding();
        var reefs = obstacles.stream()
                .map(obs -> new ReefVisibleDeckEntity(obs.getTransform(), obs.getShape()))
                .map(reef -> (VisibleDeckEntity)reef)
                .collect(Collectors.toList());

        var context = new PathfindingContext(start, reefs, end, store);
        var nextCheckpoint = pathfinding.getNextCheckpoint(context);
        while(!end.equals(nextCheckpoint)) {
            context = new PathfindingContext(nextCheckpoint, reefs, end, store);
            nextCheckpoint = pathfinding.getNextCheckpoint(context);
        }
    }

    MyCustomRace GetMap(String map) throws FileNotFoundException, JsonProcessingException {
        File file = new File("src/test/java/fr/unice/polytech/si3/qgl/qualituriers/utils/pathfinding/pseudomaps/" + map + ".json");
        Scanner scanner = new Scanner(file);
        String fileContent = "";
        while(scanner.hasNextLine())
            fileContent += scanner.nextLine() + "\n";
        var om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return om.readValue(fileContent, MyCustomRace.class);
    }
}
