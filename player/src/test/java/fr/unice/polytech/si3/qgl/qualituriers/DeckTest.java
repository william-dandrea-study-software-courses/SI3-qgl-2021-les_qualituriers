package fr.unice.polytech.si3.qgl.qualituriers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {

    Deck deck;

    @BeforeEach
    public void init() {
        this.deck = new Deck(5, 4);
    }

    @Test
    public void testGetter() {
        assertEquals(5, deck.getWidth());
        assertEquals(4, deck.getLength());
    }

    @Test
    public void testEquals() {
        Deck deck2 = new Deck(5, 4);
        Deck deck3 = new Deck(5, 5);
        Deck deck4 = new Deck(4, 4);
        assertEquals(deck2, deck);
        assertNotEquals(deck3, deck);
        assertNotEquals(deck4, deck);
        assertNotEquals(deck, null);
        assertNotEquals(deck, "test");
    }

    @Test
    public void testHashcode() {
        Deck deck2 = new Deck(5, 4);
        Deck deck3 = new Deck(5, 5);
        Deck deck4 = new Deck(4, 4);
        assertEquals(deck2.hashCode(), deck.hashCode());
        assertNotEquals(deck3.hashCode(), deck.hashCode());
        assertNotEquals(deck4.hashCode(), deck.hashCode());
    }


}
