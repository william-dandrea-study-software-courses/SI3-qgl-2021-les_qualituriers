package fr.unice.polytech.si3.qgl.qualituriers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.qualituriers.parser.ParserIn;
import org.junit.jupiter.api.BeforeEach;
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
    void nextRoundTest() {

        assertEquals("[]", this.cockpit.nextRound("{}"));


    }

    @Test
    void testAll() throws IOException {
        File from = new File("src/test/java/fr/unice/polytech/si3/qgl/qualituriers/parser/fichiersJsonTest/ParserInInitExempleGithub.JSON");
        JsonNode inputNode = om.readTree(from);

        //System.out.println(inputNode.toString());
        cockpit.initGame(inputNode.toString());

        File from2 = new File("src/test/java/fr/unice/polytech/si3/qgl/qualituriers/parser/fichiersJsonTest/nextRoundInitGithub.JSON");
        JsonNode inputNode2 = om.readTree(from2);

        //System.out.println(cockpit.nextRound(inputNode2.toString()));

        assertTrue(cockpit.nextRound(inputNode2.toString()).contains("0"));
        assertTrue(cockpit.nextRound(inputNode2.toString()).contains("1"));
        assertTrue(cockpit.nextRound(inputNode2.toString()).contains("2"));
        assertTrue(cockpit.nextRound(inputNode2.toString()).contains("3"));
        assertTrue(cockpit.nextRound(inputNode2.toString()).contains("4"));
        assertTrue(cockpit.nextRound(inputNode2.toString()).contains("5"));
        assertFalse(cockpit.nextRound(inputNode2.toString()).contains("8"));
    }
}
