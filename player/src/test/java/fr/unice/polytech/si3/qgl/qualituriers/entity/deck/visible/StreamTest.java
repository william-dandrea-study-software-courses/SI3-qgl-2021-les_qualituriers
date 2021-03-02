package fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class StreamTest {

    StreamVisibleDeckEntity stream;

    @BeforeEach
    public void init() {
        this.stream = new StreamVisibleDeckEntity(new Transform(1, 2, 0), new Circle(10), 15);
    }

    @Test
    public void testEquals() {
        assertEquals(new StreamVisibleDeckEntity(new Transform(1, 2, 0), new Circle(10), 15), this.stream);
        assertNotEquals(new StreamVisibleDeckEntity(new Transform(1, 2, 0), new Circle(10), 16), this.stream);
        assertNotEquals(new StreamVisibleDeckEntity(new Transform(1, 2, 0), new Rectangle(10, 2, 0), 15), this.stream);
        assertNotEquals(new StreamVisibleDeckEntity(new Transform(4, 2, 0), new Circle(10), 15), this.stream);
        assertNotEquals(stream, null);
        assertNotEquals(stream, "test");
    }

    @Test
    public void testHashcode() {
        assertEquals(new StreamVisibleDeckEntity(new Transform(1, 2, 0), new Circle(10), 15).hashCode(), this.stream.hashCode());
        assertNotEquals(new StreamVisibleDeckEntity(new Transform(1, 2, 0), new Circle(10), 16).hashCode(), this.stream.hashCode());
        assertNotEquals(new StreamVisibleDeckEntity(new Transform(1, 2, 0), new Rectangle(10, 2, 0), 15).hashCode(), this.stream.hashCode());
        assertNotEquals(new StreamVisibleDeckEntity(new Transform(4, 2, 0), new Circle(10), 15).hashCode(), this.stream.hashCode());
    }

}
