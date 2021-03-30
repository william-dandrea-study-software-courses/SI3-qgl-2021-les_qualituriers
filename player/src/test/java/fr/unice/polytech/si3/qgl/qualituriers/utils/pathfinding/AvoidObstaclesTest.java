package fr.unice.polytech.si3.qgl.qualituriers.utils.pathfinding;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
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
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableShape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class AvoidObstaclesTest {
    boolean showDetails = false;
    AvoidObstacles pathfinder;
    Boat boat;

    @BeforeEach
    void init() {
        pathfinder = new AvoidObstacles();
        boat = new Boat(10, Transform.ZERO, "", null, null, new Rectangle(10, 30, 0));
    }

    @Disabled
    @Test
    void RunRace6() {
        TestRace("race1");
    }

    @Disabled
    @Test
    void RunRace7() {
        TestRace("race2");
    }

    @Disabled
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

            TestBetweenTwoCheckpoints(map.getStartPosition().getPoint(), map.getCheckPoints()[0].getPosition().getPoint(), obstacles);
            for(int i = 1; i < map.getCheckPoints().length; i++) {
                TestBetweenTwoCheckpoints(map.getCheckPoints()[i - 1].getPosition(), map.getCheckPoints()[i].getPosition().getPoint(), obstacles);
            }
        });
    }

    void TestBetweenTwoCheckpoints(Point start, Point end, List<PositionableShape<? extends Shape>> obstacles) {
        MainPathfinding pathfinding = new MainPathfinding();
        var context = new PathfindingContext(new Boat(0, new Transform(start, 0), "", null, null, new Circle(100)), obstacles, new CheckPoint(new Transform(end, 0), new Circle(100)), new PathfindingStore());
        var nextCheckpoint = pathfinding.getNextCheckpoint(context);
        while(!end.equals(nextCheckpoint.getPosition().getPoint())) {
            context = new PathfindingContext(new Boat(0, nextCheckpoint.getPosition(), "", null, null, new Circle(100)), obstacles, new CheckPoint(new Transform(end, 0), new Circle(100)), new PathfindingStore());
            nextCheckpoint = pathfinding.getNextCheckpoint(context);
        }
    }

    MyCustomRace GetMap(String map) throws FileNotFoundException, JsonProcessingException {
        File file = new File("./pseudomaps/" + map + ".json");
        Scanner scanner = new Scanner(file);
        String fileContent = "";
        while(scanner.hasNextLine())
            fileContent += scanner.nextLine() + "\n";
        var om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return om.readValue(fileContent, MyCustomRace.class);
    }
}
