package fr.unice.polytech.si3.qgl.qualituriers.entity.boat;

import fr.unice.polytech.si3.qgl.qualituriers.Deck;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MarinTest {

    Marin marin;

    @BeforeEach
    public void init() {
        this.marin = new Marin(3, 1, 2, "canard");
    }

    @Test
    public void testGetter() {
        assertEquals(new Point(1, 2), marin.getPosition());
        assertEquals(1, marin.getX());
        assertEquals(2, marin.getY());
        assertEquals(3, marin.getId());
        assertEquals("canard", marin.getName());
    }

    @Test
    public void testSetter(){
        marin.setX(3);
        assertEquals(3, marin.getX());
        marin.setY(4);
        assertEquals(4, marin.getY());
    }

    @Test
    public void testEquals() {
        assertEquals(new Marin(3, 1, 2, "canard"), this.marin);
        assertNotEquals(new Marin(1, 1, 2, "canard"), this.marin);
        assertNotEquals(marin, null);
        assertNotEquals(marin, "test");
    }

    @Test
    public void testHashcode() {
        assertEquals(new Marin(3, 1, 2, "canard").hashCode(), this.marin.hashCode());
        assertNotEquals(new Marin(1, 1, 2, "canard").hashCode(), this.marin.hashCode());
    }

    @Test
    void canMoveTo() {

        Boat actualBoat = new Boat(100, new Transform(0,0,0), "name", new Deck(10,10), new BoatEntity[]{}, new Rectangle(10,10, 0));
        Marin marin = new Marin(0,0,0, "MarinTest");

        assertFalse(marin.canMoveTo(9,9, actualBoat));
        assertFalse(marin.canMoveTo(3,3, actualBoat));
        assertTrue(marin.canMoveTo(3,2, actualBoat));
        assertFalse(marin.canMoveTo(-1,2, actualBoat));
        assertFalse(marin.canMoveTo(2,-2, actualBoat));
    }

}
