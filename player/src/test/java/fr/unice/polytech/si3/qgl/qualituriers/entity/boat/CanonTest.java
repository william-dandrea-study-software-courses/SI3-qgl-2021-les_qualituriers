package fr.unice.polytech.si3.qgl.qualituriers.entity.boat;

import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.CanonBoatEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CanonTest {

    CanonBoatEntity canon;

    @BeforeEach
    public void init() {
        this.canon = new CanonBoatEntity(1, 5, true, Math.PI / 4);
    }

    @Test
    public void testEquals() {
        CanonBoatEntity canon1 = new CanonBoatEntity(1, 5, true, Math.PI / 4);
        CanonBoatEntity canon2 = new CanonBoatEntity(1, 5, true, Math.PI / 2);
        CanonBoatEntity canon3 = new CanonBoatEntity(1, 5, false, Math.PI / 4);
        CanonBoatEntity canon4 = new CanonBoatEntity(1, 9, true, Math.PI / 4);
        CanonBoatEntity canon5 = new CanonBoatEntity(5, 5, true, Math.PI / 4);
        assertEquals(canon1, canon);
        assertNotEquals(canon2, canon);
        assertNotEquals(canon3, canon);
        assertNotEquals(canon4, canon);
        assertNotEquals(canon5, canon);
        assertNotEquals(canon, null);
        assertNotEquals(canon, "test");
    }

    @Test
    public void testHashcode() {
        CanonBoatEntity canon1 = new CanonBoatEntity(1, 5, true, Math.PI / 4);
        CanonBoatEntity canon2 = new CanonBoatEntity(1, 5, true, Math.PI / 2);
        CanonBoatEntity canon3 = new CanonBoatEntity(1, 5, false, Math.PI / 4);
        CanonBoatEntity canon4 = new CanonBoatEntity(1, 9, true, Math.PI / 4);
        CanonBoatEntity canon5 = new CanonBoatEntity(5, 5, true, Math.PI / 4);
        assertEquals(canon1.hashCode(), canon.hashCode());
        assertNotEquals(canon2.hashCode(), canon.hashCode());
        assertNotEquals(canon3.hashCode(), canon.hashCode());
        assertNotEquals(canon4.hashCode(), canon.hashCode());
        assertNotEquals(canon5.hashCode(), canon.hashCode());
    }

    @Test
    public void testToString(){
        assertEquals("CanonBoatEntity : loaded=true, angle=" + Math.PI/4 + ", type=BoatEntities{type='cannon', entity=class fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.CanonBoatEntity}, x=1, y=5\n", canon.toString());
    }


}
