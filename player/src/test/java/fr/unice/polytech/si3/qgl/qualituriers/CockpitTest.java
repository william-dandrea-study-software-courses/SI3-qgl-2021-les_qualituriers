package fr.unice.polytech.si3.qgl.qualituriers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Oar;
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

    @Test @DisplayName("Les marins ne doivent qu'avancer")
    void testAll() throws IOException {
        File from = new File("src/test/java/fr/unice/polytech/si3/qgl/qualituriers/parser/fichiersJsonTest/ParserInInitExempleGithub.JSON");
        JsonNode inputNode = om.readTree(from);
        cockpit.initGame(inputNode.toString());

        File from2 = new File("src/test/java/fr/unice/polytech/si3/qgl/qualituriers/parser/fichiersJsonTest/nextRoundInitGithub.JSON");
        JsonNode inputNode2 = om.readTree(from2);

        for (int i = 0; i < 8; i++) {
            String response = cockpit.nextRound(inputNode2.toString());
            assertDoesNotThrow(() -> om.readValue(response, Oar[].class));
        }
    }
}
