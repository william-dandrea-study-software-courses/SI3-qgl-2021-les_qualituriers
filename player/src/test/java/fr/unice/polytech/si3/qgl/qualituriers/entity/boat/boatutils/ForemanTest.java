package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatutils;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.unice.polytech.si3.qgl.qualituriers.Deck;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.*;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Actions;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Moving;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shapes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ForemanTest {

    Boat boat;
    OarBoatEntity oar;

    void createBoat() {
        oar = new OarBoatEntity(0,0) {};
        BoatEntity oar2 = new OarBoatEntity(1,0) {};
        BoatEntity oar3 = new OarBoatEntity(0,1) {};
        BoatEntity oar4 = new OarBoatEntity(1,1) {};
        BoatEntity oar5 = new OarBoatEntity(3,0) {};
        BoatEntity oar6 = new OarBoatEntity(3,1) {};


        Marin sailor1 = new Marin(1,0,1,"sailor1");
        Marin sailor2 = new Marin(2,1,0,"sailor2");
        Marin sailor3 = new Marin(3,0,1,"sailor3");
        Marin sailor4 = new Marin(4,1,1,"sailor4");

        int life = 100;
        Transform transform = new Transform(0,0,0);
        String name = "boatTest1";
        Deck deck = new Deck(2,4);
        BoatEntity[] entities = {oar6,oar5,oar3,oar4, oar2, oar};
        Shape shape = new Shape(Shapes.POLYGON) {
            @Override
            public boolean isIn(Point position) {
                return false;
            }
        };
        Marin[] sailors = {sailor4, sailor3,sailor2,sailor1};


        boat = new Boat(life, transform, name, deck, entities,shape);
        boat.setSailors(List.of(sailors));
    }

    @BeforeEach
    void init() throws JsonProcessingException, FileNotFoundException {
        /*ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        File f = new File("src/test/java/fr/unice/polytech/si3/qgl/qualituriers/parser/fichiersJsonTest/boat.json");
        Scanner scanner = new Scanner(f);
        String res = "";
        while(scanner.hasNextLine()) res += scanner.nextLine() + "\n";
        System.out.println(res);

        boat = mapper.readValue(res, Boat.class);

        System.out.println(boat.toString());*/
        createBoat();
    }

    @Test
    void assignSailorToPost() {
        boat.getForeman().assignSailorToOar(oar);
        var sailor = boat.getForeman().getSailorAtPost(oar);

        assertNotEquals(null, sailor);
        var actions = sailor.actionDoneDuringTurn();
        assertTrue(actions.stream().anyMatch(a -> a.getType() == Actions.OAR));
        assertTrue(sailor.getPosition().equals(oar.getPosition()) || actions.stream().anyMatch(a -> a.getType() == Actions.MOVING));

        var moving = actions.stream().filter(a -> a.getType() == Actions.MOVING).findAny();

        if(moving.isPresent()) {
            System.out.println("Sailor has moved");

            var neoPos = ((Moving)moving.get()).getDirection().add(sailor.getPosition());
            assertEquals(oar.getPosition(), neoPos);
        } else {
            System.out.println("Sailor hasn't move");
        }
    }

    @Test
    void unAssignedToPost() {
        boat.getForeman().assignSailorToOar(oar);
        var sailor = boat.getForeman().getSailorAtPost(oar);
        assertNotEquals(null, sailor);
        assertTrue(sailor.actionDoneDuringTurn().stream().anyMatch(a -> a.getType() == Actions.OAR));

        boat.getForeman().unAssignSailorToOar(oar);
        var sailor2 = boat.getForeman().getSailorAtPost(oar);
        assertEquals(null, sailor2);
        assertFalse(sailor.actionDoneDuringTurn().stream().anyMatch(a -> a.getType() == Actions.OAR));

    }

    @Test
    void setSpeedTest() {
        boat.getForeman().setSpeed(2);
        boat.getForeman().decide();

        // The sailor can't be on two place at the same time
        for(var post: boat.getEntities()) {
            for(var post2: boat.getEntities()) {
                if(post == post2) continue;
                var s1 = boat.getForeman().getSailorAtPost(post);
                var s2 = boat.getForeman().getSailorAtPost(post2);
                if(s1 == null || s2 == null) continue;

                assertNotEquals(boat.getForeman().getSailorAtPost(post), boat.getForeman().getSailorAtPost(post2));
            }
        }

        assertEquals(2, boat.getForeman().getNumberLeftRowingSailors());
        assertEquals(2, boat.getForeman().getNumberRightRowingSailors());
    }

    @Test
    void setBendLeftTest() {
        boat.getForeman().setSpeed(2);
        boat.getForeman().setBendLeft(2);
        boat.getForeman().decide();

        // The sailor can't be on two place at the same time
        for(var post: boat.getEntities()) {
            for(var post2: boat.getEntities()) {
                if(post == post2) continue;
                var s1 = boat.getForeman().getSailorAtPost(post);
                var s2 = boat.getForeman().getSailorAtPost(post2);
                if(s1 == null || s2 == null) continue;

                assertNotEquals(boat.getForeman().getSailorAtPost(post), boat.getForeman().getSailorAtPost(post2));
            }
        }

        assertEquals(0, boat.getForeman().getNumberLeftRowingSailors());
        assertEquals(2, boat.getForeman().getNumberRightRowingSailors());
    }

    @Test
    void setBendRightTest() {
        boat.getForeman().setSpeed(2);
        boat.getForeman().setBendRight(2);
        boat.getForeman().decide();

        // The sailor can't be on two place at the same time
        for(var post: boat.getEntities()) {
            for(var post2: boat.getEntities()) {
                if(post == post2) continue;
                var s1 = boat.getForeman().getSailorAtPost(post);
                var s2 = boat.getForeman().getSailorAtPost(post2);
                if(s1 == null || s2 == null) continue;

                assertNotEquals(boat.getForeman().getSailorAtPost(post), boat.getForeman().getSailorAtPost(post2));
            }
        }

        assertEquals(2, boat.getForeman().getNumberLeftRowingSailors());
        assertEquals(0, boat.getForeman().getNumberRightRowingSailors());
    }

    @Test
    void setBendRightTooLarge() {
        boat.getForeman().setSpeed(2);
        boat.getForeman().setBendRight(3);
        boat.getForeman().decide();

        // The sailor can't be on two place at the same time
        for(var post: boat.getEntities()) {
            for(var post2: boat.getEntities()) {
                if(post == post2) continue;
                var s1 = boat.getForeman().getSailorAtPost(post);
                var s2 = boat.getForeman().getSailorAtPost(post2);
                if(s1 == null || s2 == null) continue;

                assertNotEquals(boat.getForeman().getSailorAtPost(post), boat.getForeman().getSailorAtPost(post2));
            }
        }

        assertEquals(2, boat.getForeman().getNumberLeftRowingSailors());
        assertEquals(0, boat.getForeman().getNumberRightRowingSailors());
    }

    @Test
    void setBendLeftTooLarge() {
        boat.getForeman().setSpeed(2);
        boat.getForeman().setBendLeft(3);
        boat.getForeman().decide();

        // The sailor can't be on two place at the same time
        for(var post: boat.getEntities()) {
            for(var post2: boat.getEntities()) {
                if(post == post2) continue;
                var s1 = boat.getForeman().getSailorAtPost(post);
                var s2 = boat.getForeman().getSailorAtPost(post2);
                if(s1 == null || s2 == null) continue;

                assertNotEquals(boat.getForeman().getSailorAtPost(post), boat.getForeman().getSailorAtPost(post2));
            }
        }

        assertEquals(0, boat.getForeman().getNumberLeftRowingSailors());
        assertEquals(2, boat.getForeman().getNumberRightRowingSailors());
    }

    @Test
    void setBendRightNextLeft() {
        setBendLeftTest();
        setBendRightTest();
    }
}
