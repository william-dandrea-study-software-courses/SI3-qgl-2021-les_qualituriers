package fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class EnemyTest {

    EnemyVisibleDeckEntity enemy;

    @BeforeEach
    public void init() {
        this.enemy = new EnemyVisibleDeckEntity(new Transform(50, 40, 0), new Rectangle(2, 3, 0), 100);
    }

    @Test
    public void testEquals() {
        assertEquals(new EnemyVisibleDeckEntity(new Transform(50, 40, 0), new Rectangle(2, 3, 0), 100), enemy);
        assertNotEquals(new EnemyVisibleDeckEntity(new Transform(50, 40, 0), new Rectangle(2, 3, 0), 110), enemy);
        assertNotEquals(new EnemyVisibleDeckEntity(new Transform(50, 40, 0), new Rectangle(3, 3, 0), 100), enemy);
        assertNotEquals(new EnemyVisibleDeckEntity(new Transform(60, 40, 0), new Rectangle(2, 3, 0), 100), enemy);
        assertNotEquals(enemy, null);
        assertNotEquals(enemy, "test");
    }

    @Test
    public void testHashcode() {
        assertEquals(new EnemyVisibleDeckEntity(new Transform(50, 40, 0), new Rectangle(2, 3, 0), 100).hashCode(), enemy.hashCode());
        assertNotEquals(new EnemyVisibleDeckEntity(new Transform(50, 40, 0), new Rectangle(2, 3, 0), 110).hashCode(), enemy.hashCode());
        assertNotEquals(new EnemyVisibleDeckEntity(new Transform(50, 40, 0), new Rectangle(3, 3, 0), 100).hashCode(), enemy.hashCode());
        assertNotEquals(new EnemyVisibleDeckEntity(new Transform(60, 40, 0), new Rectangle(2, 3, 0), 100).hashCode(), enemy.hashCode());
    }

}
