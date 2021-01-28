package fr.unice.polytech.si3.qgl.qualituriers.parser;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.qualituriers.CockpitMethods;
import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Polygon;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shapes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * @author williamdandrea
 */
public class ParserInTest {

    ObjectMapper om;

    @BeforeEach
    void setUp() {
        om = new ObjectMapper();
    }

    @Test
    void createBoatTest() throws IOException{

    }

    @Test
    void createCheckpointTest() throws IOException {


        File from = new File("src/test/java/fr/unice/polytech/si3/qgl/qualituriers/parser/fichiersJsonTest/ParserInCreateCheckpointTestFile1.JSON");
        JsonNode inputNode = om.readTree(from);

        ParserIn p = new ParserIn(inputNode);

        assertEquals(2, p.createCheckpoint().size());
        assertFalse(p.createCheckpoint().contains(Optional.empty()));
    }

    @Test
    void createCheckpointWhenNullTest() throws IOException {

        ParserIn p = new ParserIn(null);
        assertTrue(p.createCheckpoint().isEmpty());

    }

    @Test
    void createCheckpointWhenCorruptFileTest() throws IOException {

        File from = new File("src/test/java/fr/unice/polytech/si3/qgl/qualituriers/parser/fichiersJsonTest/ParserInReturnTheGoodCheckpointTestCircle.JSON");
        JsonNode inputNode = om.readTree(from);
        ParserIn p = new ParserIn(inputNode);

        assertTrue(p.createCheckpoint().isEmpty());

    }

    @Test
    void returnTheGoodCheckpointTestCircle() throws IOException {

        File from = new File("src/test/java/fr/unice/polytech/si3/qgl/qualituriers/parser/fichiersJsonTest/ParserInReturnTheGoodCheckpointTestCircle.JSON");
        JsonNode inputNode = om.readTree(from);
        ParserIn p = new ParserIn(inputNode);

        CheckPoint checkPoint = p.returnTheGoodCheckpoint(inputNode).get();

        // Le delta correspond a l'erreur relative car on a des doubles (voir cours IT S5 - representation des nombres)
        assertEquals(1000.0, checkPoint.getPosition().getX(), 0);
        assertEquals(0.0, checkPoint.getPosition().getY(),0);
        assertEquals(0.0, checkPoint.getPosition().getOrientation(),0);

        assertEquals(Shapes.CIRCLE.getType(), checkPoint.getShape().getType());

        Circle circle = (Circle) checkPoint.getShape();
        assertEquals(50.0,circle.getRadius(), 0);

    }

    @Test
    void returnTheGoodCheckpointTestRectangle() throws IOException {

        File from = new File("src/test/java/fr/unice/polytech/si3/qgl/qualituriers/parser/fichiersJsonTest/ParserInReturnTheGoodCheckpointTestRectangle.JSON");
        JsonNode inputNode = om.readTree(from);
        ParserIn p = new ParserIn(inputNode);

        CheckPoint checkPoint = p.returnTheGoodCheckpoint(inputNode).get();

        // Le delta correspond a l'erreur relative car on a des doubles (voir cours IT S5 - representation des nombres)
        assertEquals(1000.0, checkPoint.getPosition().getX(), 0);
        assertEquals(0.0, checkPoint.getPosition().getY(),0);
        assertEquals(0.0, checkPoint.getPosition().getOrientation(),0);

        assertEquals(Shapes.RECTANGLE.getType(), checkPoint.getShape().getType());

        Rectangle rectangle = (Rectangle) checkPoint.getShape();
        assertEquals(100.0,rectangle.getHeight(), 0);
        assertEquals(50.0,rectangle.getWidth(), 0);
        assertEquals(150.0,rectangle.getOrientation(), 0);

    }

    @Test
    void returnTheGoodCheckpointTestPolygon() throws IOException {

        // TODO: 25/01/2021  Gerer les polygon quand on aura la structure exacte
        File from = new File("src/test/java/fr/unice/polytech/si3/qgl/qualituriers/parser/fichiersJsonTest/ParserInReturnTheGoodCheckpointTestPolygon.JSON");
        JsonNode inputNode = om.readTree(from);
        ParserIn p = new ParserIn(inputNode);

    }

    @Test
    void returnTheGoodCheckpointTestNULL() throws IOException {

        File from = new File("src/test/java/fr/unice/polytech/si3/qgl/qualituriers/parser/fichiersJsonTest/ParserInReturnTheGoodCheckpointTestCircle.JSON");
        JsonNode inputNode = om.readTree(from);
        ParserIn p = new ParserIn(inputNode);

        assertTrue(p.returnTheGoodCheckpoint(null).isEmpty());

        ParserIn p2 = new ParserIn(null);
        assertTrue(p2.returnTheGoodCheckpoint(inputNode).isEmpty());


    }
    @Test
    void returnTheGoodCheckpointTestWithACorruptFile() throws IOException {

        File from = new File("src/test/java/fr/unice/polytech/si3/qgl/qualituriers/parser/fichiersJsonTest/ParserInCreateCheckpointTestFile1.JSON");
        JsonNode inputNode = om.readTree(from);
        ParserIn p = new ParserIn(inputNode);

        assertTrue(p.returnTheGoodCheckpoint(inputNode).isEmpty());


    }

    @Test
    void returnTheGoodSailorTest() throws IOException {

        File from = new File("src/test/java/fr/unice/polytech/si3/qgl/qualituriers/parser/fichiersJsonTest/ParserInInitExempleGithub.JSON");
        JsonNode inputNode = om.readTree(from);
        ParserIn p = new ParserIn(inputNode);


        //System.out.println(p.createSailors());

    }


}
