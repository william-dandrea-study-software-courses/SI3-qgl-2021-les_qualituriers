package fr.unice.polytech.si3.qgl.qualituriers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.*;
import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.game.goal.RegattaGoal;
import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CockpitTest {

    Cockpit cockpit;
    ObjectMapper om;

    @BeforeEach
    void setUp() {
        this.cockpit = new Cockpit();
        this.om = new ObjectMapper();
        this.om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


    @Test
    public void gameInfoCorrect() throws IOException {
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
    public void initGameRenderNotNull() throws IOException {
        File from = new File("src/test/java/fr/unice/polytech/si3/qgl/qualituriers/parser/fichiersJsonTest/ParserInInitExempleGithub.JSON");
        JsonNode inputNode = om.readTree(from);
        cockpit.initGame(inputNode.toString());
        assertNotNull(cockpit.render);
    }

    @Test
    void nextRoundEmpty() {
        assertEquals("[]", this.cockpit.nextRound("{}"));
    }

    //@Disabled
    @Test @DisplayName("Les marins ne doivent qu'avancer")
    void testAll() throws IOException {
        File from = new File("src/test/java/fr/unice/polytech/si3/qgl/qualituriers/parser/fichiersJsonTest/week2/initGame.json");
        JsonNode inputNode = om.readTree(from);
        cockpit.initGame(inputNode.toString());



        String src = "src/test/java/fr/unice/polytech/si3/qgl/qualituriers/parser/fichiersJsonTest/week2/week2_1_1.json";

        System.out.println(cockpit.nextRound(om.readTree(new File(src)).toString()));

//            for (int i = 1; i <= 12 ; i++) {
//                String str = "src/test/java/fr/unice/polytech/si3/qgl/qualituriers/parser/fichiersJsonTest/week2/nextRound" + i + ".json";
//                System.out.println(cockpit.nextRound(om.readTree(new File(str)).toString()));
//
//            }
    }


    @Test @DisplayName("WEEK3")
    void testWeek3() throws IOException {
        File from = new File("src/test/java/fr/unice/polytech/si3/qgl/qualituriers/jsonfiles/week3/init.json");
        JsonNode inputNode = om.readTree(from);
        cockpit.initGame(inputNode.toString());


        String src = "src/test/java/fr/unice/polytech/si3/qgl/qualituriers/jsonfiles/week3/nextRound.json";

        System.out.println(cockpit.nextRound(om.readTree(new File(src)).toString()));

//            for (int i = 1; i <= 12 ; i++) {
//                String str = "src/test/java/fr/unice/polytech/si3/qgl/qualituriers/parser/fichiersJsonTest/week2/nextRound" + i + ".json";
//                System.out.println(cockpit.nextRound(om.readTree(new File(str)).toString()));
//
//            }


    }
}
