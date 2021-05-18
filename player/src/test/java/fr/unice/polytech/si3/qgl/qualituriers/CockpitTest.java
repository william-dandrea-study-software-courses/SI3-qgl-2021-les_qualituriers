package fr.unice.polytech.si3.qgl.qualituriers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.*;
import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.game.RoundInfo;
import fr.unice.polytech.si3.qgl.qualituriers.game.goal.RegattaGoal;
import fr.unice.polytech.si3.qgl.qualituriers.render.TempoRender;
import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Moving;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CockpitTest {

    Cockpit cockpit;
    ObjectMapper om;
    JsonNode initGame;
    JsonNode nextRound;

    @BeforeEach
    void setUp() throws IOException {
        this.cockpit = new Cockpit();
        this.om = new ObjectMapper();
        this.om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        File from = new File("src/test/java/fr/unice/polytech/si3/qgl/qualituriers/parser/fichiersJsonTest/ParserInInitExempleGithub.JSON");
        this.initGame = om.readTree(from);
        File from2 = new File("src/test/java/fr/unice/polytech/si3/qgl/qualituriers/parser/fichiersJsonTest/week2/nextRound1.json");
        this.nextRound = om.readTree(from2);
    }


    @Test
    void gameInfoCorrect() throws IOException {
        CheckPoint[] checkPoints = new CheckPoint[2];
        checkPoints[0] = new CheckPoint(new Transform(10, 10, 0), new Circle(50));
        checkPoints[1] = new CheckPoint(new Transform(0, 0, 0), new Circle(50));
        RegattaGoal goal = new RegattaGoal(checkPoints);
        BoatEntity[] entities = new BoatEntity[8];
        entities[0] = new OarBoatEntity(1, 0);
        entities[1] = new OarBoatEntity(1, 2);
        entities[2] = new OarBoatEntity(3, 0);
        entities[3] = new OarBoatEntity(3, 2);
        entities[4] = new OarBoatEntity(4, 0);
        entities[5] = new OarBoatEntity(4, 2);
        entities[6] = new SailBoatEntity(2, 1, false);
        entities[7] = new RudderBoatEntity(5, 0);
        Boat boat = new Boat( 100, new Transform(0, 0, 0), "Les copaings d'abord!", new Deck(3, 6),
                entities, new Rectangle(3, 6, 0));
        Marin[] marins = new Marin[6];
        marins[0] = new Marin(0, 0, 0, "Edward Teach");
        marins[1] = new Marin(1, 0, 1, "Edward Pouce");
        marins[2] = new Marin(2, 0, 2, "Tom Pouce");
        marins[3] = new Marin(3, 1, 0, "Jack Teach");
        marins[4] = new Marin(4, 1, 1, "Jack Teach");
        marins[5] = new Marin(5, 2, 1, "Tom Pouce");
        File from = new File("src/test/java/fr/unice/polytech/si3/qgl/qualituriers/parser/fichiersJsonTest/ParserInInitExempleGithub.JSON");
        GameInfo gameInfo = om.readValue(from, GameInfo.class);
        assertEquals(goal, gameInfo.getGoal());
        assertEquals(1, gameInfo.getShipCount());
        assertArrayEquals(marins, gameInfo.getSailors());
        assertEquals(boat, gameInfo.getShip());
    }

    @Test
    void initGameRenderNotNull() {
        cockpit.initGame(this.initGame.toString());
        assertNotNull(cockpit.render);
    }

    @Test
    void initGameCrash() {
        cockpit.initGame("{\"goal\":\"mode\":\"test\"}");
        assertTrue(cockpit.getLogs().contains("com.fasterxml.jackson.core.JsonParseException: Unexpected character (':' (code 58)): was expecting comma to separate Object entries\n" +
                " at [Source: (String)\"{\"goal\":\"mode\":\"test\"}\"; line: 1, column: 16]"));
    }

    @Test
    void nextRoundRenderNull() {
        cockpit.initGame(this.initGame.toString());
        cockpit.render = null;
        assertEquals("[]", this.cockpit.nextRound(this.nextRound.toString()));
    }

    @Test
    void nextRoundRenderNoAction() throws IOException {
        File from = new File("src/test/java/fr/unice/polytech/si3/qgl/qualituriers/parser/fichiersJsonTest/week2/nextRound1.json");
        RoundInfo roundInfo = om.readValue(from, RoundInfo.class);
        cockpit.initGame(this.initGame.toString());
        cockpit.render = mock(TempoRender.class);
        when(cockpit.render.nextRound(roundInfo)).thenReturn(null);
        assertEquals("[]", this.cockpit.nextRound(this.nextRound.toString()));
    }

    @Test
    void nextRoundCrash() {
        cockpit.initGame(this.initGame.toString());
        this.cockpit.nextRound("{\"ship\":\"type\":\"ship\"}");
        assertTrue(cockpit.getLogs().contains("com.fasterxml.jackson.databind.exc.MismatchedInputException: Cannot construct instance of `fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat` (although at least one Creator exists): no String-argument constructor/factory method to deserialize from String value ('type')\n" +
                " at [Source: (String)\"{\"ship\":\"type\":\"ship\"}\"; line: 1, column: 9] (through reference chain: fr.unice.polytech.si3.qgl.qualituriers.game.RoundInfo[\"ship\"])"));
    }

    @Test
    void nextRoundNotGoodReturn() throws IOException {
        File from = new File("src/test/java/fr/unice/polytech/si3/qgl/qualituriers/parser/fichiersJsonTest/week2/nextRound1.json");
        cockpit.initGame(this.initGame.toString());
        cockpit.render = mock(TempoRender.class);
        RoundInfo roundInfo = om.readValue(from, RoundInfo.class);
        Moving moving = new Moving(1, 1, 1);
        when(cockpit.render.nextRound(roundInfo)).thenReturn(Collections.singletonList(moving));
        String result = this.cockpit.nextRound(this.nextRound.toString());
        assertNotNull(result);
        assertDoesNotThrow(() -> this.om.readValue(result, new TypeReference<List<Action>>(){}));
        List<Action> list = this.om.readValue(result, new TypeReference<List<Action>>(){});
        assertEquals(1, list.size());
        assertEquals(moving, list.get(0));
    }


}
