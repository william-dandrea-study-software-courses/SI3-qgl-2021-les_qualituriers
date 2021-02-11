package fr.unice.polytech.si3.qgl.qualituriers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.*;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.Wind;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.EnemyVisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.ReefVisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.StreamVisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.game.goal.BattleGoal;
import fr.unice.polytech.si3.qgl.qualituriers.game.goal.RegattaGoal;
import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Polygon;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @author Alexandre Arcil
 */
public class DeserializationTest {

    static ObjectMapper om;

    @BeforeAll
    public static void init() {
        om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Test
    public void deserializePoint() {
        Point shape = new Point(1, 6);
        String json = "{\"x\":1.0,\"y\":6.0}";
        this.testDeserialization(shape, json);
    }

    @Test
    public void deserializeTransform() {
        Transform transform = new Transform(4.4, -7.7, Math.PI/2);
        String json = "{\"x\":4.4,\"y\":-7.7,\"orientation\":1.5707963267948966}";
        this.testDeserialization(transform, json);
    }

    @Test
    public void deserializeDeck()   {
        Deck deck = new Deck(4, 5);
        String json = "{\"width\":4,\"length\":5}";
        this.testDeserialization(deck, json);
    }

    @Test
    public void deserializeOarBoatEntity() {
        OarBoatEntity ent = new OarBoatEntity(1, 2);
        String json = "{\"type\":\"oar\",\"x\":1,\"y\":2}";
        this.testDeserialization(ent, json);
    }

    @Test
    public void deserializeSailBoatEntity() {
        SailBoatEntity ent = new SailBoatEntity(1, 2, true);
        String json = "{\"type\":\"sail\",\"x\":1,\"y\":2, \"opened\": true}";
        this.testDeserialization(ent, json);
    }

    @Test
    public void deserializeRudderBoatEntity() {
        RudderBoatEntity ent = new RudderBoatEntity(1, 2);
        String json = "{\"type\":\"rudder\",\"x\":1,\"y\":2}";
        this.testDeserialization(ent, json);
    }

    @Test
    public void deserializeWatchBoatEntity() {
        WatchBoatEntity ent = new WatchBoatEntity(1, 2);
        String json = "{\"type\":\"watch\",\"x\":1,\"y\":2}";
        this.testDeserialization(ent, json);
    }

    @Test
    public void deserializeCanonBoatEntity() {
        CanonBoatEntity ent = new CanonBoatEntity(1, 2, true, Math.PI/4);
        System.out.println(Math.PI/4);
        String json = "{\"type\":\"canon\",\"x\":1,\"y\":2, \"loaded\":true, \"angle\":0.7853981633974483}";
        this.testDeserialization(ent, json);
    }

    @Test
    public void deserializeCircle() {
        Circle shape = new Circle(20);
        String json = "{\"type\":\"circle\",\"radius\":20.0}";
        this.testDeserialization(shape, json);
    }

    @Test
    public void deserializeRectangle() {
        Rectangle shape = new Rectangle(20, 20, Math.PI/2);
        String json = "{\"type\":\"rectangle\",\"width\":20.0,\"height\":20.0,\"orientation\":1.5707963267948966}";
        this.testDeserialization(shape, json);
    }

    @Test
    public void deserializePolygon() {
        Polygon shape = new Polygon(Math.PI/3, new Point[] {new Point(1, 0), new Point(2, 1), new Point(1, 2), new Point(0, 1)});
        String json = "{\"type\":\"polygon\",\"orientation\":1.0471975511965976,\"vertices\":[{\"x\":1.0,\"y\":0.0,\"orientation\":0.0},{\"x\":2.0,\"y\":1.0,\"orientation\":0.4636476090008061},{\"x\":1.0,\"y\":2.0,\"orientation\":1.1071487177940904},{\"x\":0.0,\"y\":1.0,\"orientation\":1.5707963267948966}]}";
        this.testDeserialization(shape, json);
    }

    @Test
    public void deserializeWind() {
        Wind wind = new Wind(5.8, 2.6);
        String json = "{\"orientation\":5.8, \"strength\":2.6}";
        this.testDeserialization(wind, json);
    }

    @Test
    public void deserializeStreamVisibleDeckEntity() {
        Transform transform = new Transform(2.3, 4.1, Math.PI/2);
        Circle circle = new Circle(34);
        StreamVisibleDeckEntity ent = new StreamVisibleDeckEntity(transform, circle, 3.5);
        String json = "{\"type\":\"stream\", \"position\":{\"x\":2.3,\"y\":4.1,\"orientation\":1.5707963267948966}, \"shape\":{\"type\":\"circle\",\"radius\":34.0}, \"strength\":3.5}";
        this.testDeserialization(ent, json);
    }

    @Test
    public void deserializeReefVisibleDeckEntity() {
        Transform transform = new Transform(2.3, 4.1, Math.PI/2);
        Circle circle = new Circle(34);
        ReefVisibleDeckEntity ent = new ReefVisibleDeckEntity(transform, circle);
        String json = "{\"type\":\"reef\", \"position\":{\"x\":2.3,\"y\":4.1,\"orientation\":1.5707963267948966}, \"shape\":{\"type\":\"circle\",\"radius\":34.0}}";
        this.testDeserialization(ent, json);
    }

    @Test
    public void deserializeEnemyVisibleDeckEntity() {
        Transform transform = new Transform(2.3, 4.1, Math.PI/2);
        Circle circle = new Circle(34);
        EnemyVisibleDeckEntity ent = new EnemyVisibleDeckEntity(transform, circle, 50);
        String json = "{\"type\":\"ship\", \"position\":{\"x\":2.3,\"y\":4.1,\"orientation\":1.5707963267948966}, \"shape\":{\"type\":\"circle\",\"radius\":34.0}, \"life\":50}";
        this.testDeserialization(ent, json);
    }

    @Test
    public void deserializeCheckPoint() {
        CheckPoint checkPoint = new CheckPoint(new Transform(0, 50, 0), new Circle(98));
        String json = "{\"position\":{\"x\":0.0,\"y\":50.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":98.0}}";
        this.testDeserialization(checkPoint, json);
    }

    @Test
    public void deserializeRegattaGoal() {
        CheckPoint[] checkPoints = new CheckPoint[3];
        checkPoints[0] = new CheckPoint(new Transform(0, 50, 0), new Circle(98));
        checkPoints[1] = new CheckPoint(new Transform(10, 70, Math.PI), new Circle(83));
        checkPoints[2] = new CheckPoint(new Transform(20, 90, 0), new Circle(20));
        RegattaGoal regattaGoal = new RegattaGoal(checkPoints);
        String json = "{\"mode\":\"REGATTA\",\"checkpoints\":[{\"position\":{\"x\":0.0,\"y\":50.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":98.0}},{\"position\":{\"x\":10.0,\"y\":70.0,\"orientation\":3.141592653589793},\"shape\":{\"type\":\"circle\",\"radius\":83.0}},{\"position\":{\"x\":20.0,\"y\":90.0,\"orientation\":0.0},\"shape\":{\"type\":\"circle\",\"radius\":20.0}}]}";
        this.testDeserialization(regattaGoal, json);
    }

    @Test
    public void deserializeBattleGoal() {
        BattleGoal goal = new BattleGoal();
        String json = "{\"mode\":\"BATTLE\"}";
        this.testDeserialization(goal, json);
    }

    private void testDeserialization(Object obj, String json) {
        Object deser = assertDoesNotThrow(() -> om.readValue(json, obj.getClass()));
        assertEquals(obj, deser);
    }




}
