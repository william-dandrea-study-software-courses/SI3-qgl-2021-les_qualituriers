package fr.unice.polytech.si3.qgl.qualituriers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.qualituriers.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.boat.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.boat.boatentities.BoatEntities;
import fr.unice.polytech.si3.qgl.qualituriers.boat.boatentities.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.parser.ParserIn;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Position;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shapes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;


/**
 * @author williamdandrea
 */
public class CockpitIntelligenceTest {



    CockpitIntelligence cockpitIntel;
    ObjectMapper om;

    @BeforeEach
    void setUp() {
        this.cockpitIntel = new CockpitIntelligence();
        om = new ObjectMapper();
    }

    @Test
    void premierRenduTest() throws IOException {
        File from = new File("src/test/java/fr/unice/polytech/si3/qgl/qualituriers/parser/fichiersJsonTest/ParserInInitExempleGithub.JSON");
        JsonNode inputNode = om.readTree(from);
        ParserIn p = new ParserIn(inputNode);

        //System.out.println(p.createSailors());
        //System.out.println(cockpitIntel.premierRendu(p.createSailors()));

    }





}
