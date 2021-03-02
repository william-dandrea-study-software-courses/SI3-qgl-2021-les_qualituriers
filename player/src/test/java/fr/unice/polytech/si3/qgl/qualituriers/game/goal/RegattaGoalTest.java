package fr.unice.polytech.si3.qgl.qualituriers.game.goal;

import fr.unice.polytech.si3.qgl.qualituriers.utils.CheckPoint;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class RegattaGoalTest {

    RegattaGoal regattaGoal;

    @BeforeEach
    public void init() {
        this.regattaGoal = new RegattaGoal(new CheckPoint[]{
                new CheckPoint(new Transform(10, 10, 0), new Circle(10)),
        new CheckPoint(new Transform(-10, -10, Math.PI / 2), new Circle(30))});
    }

    @Test
    public void testEquals() {
        RegattaGoal goal1 = new RegattaGoal(new CheckPoint[]{
                new CheckPoint(new Transform(10, 10, 0), new Circle(10)),
                new CheckPoint(new Transform(-10, -10, Math.PI / 2), new Circle(30))});
        RegattaGoal goal2 = new RegattaGoal(new CheckPoint[]{
                new CheckPoint(new Transform(10, 10, 0), new Circle(20)),
                new CheckPoint(new Transform(-10, -10, Math.PI / 2), new Circle(30))});
        assertEquals(Goals.REGATTA, this.regattaGoal.getMode());
        assertEquals(Goals.REGATTA.getType(), this.regattaGoal.getMode().getType());
        assertEquals(goal1, this.regattaGoal);
        assertNotEquals(goal2, this.regattaGoal);
        assertNotEquals(regattaGoal, null);
        assertNotEquals(regattaGoal, "test");
    }

    @Test
    public void testHashcode() {
        RegattaGoal goal1 = new RegattaGoal(new CheckPoint[]{
                new CheckPoint(new Transform(10, 10, 0), new Circle(10)),
                new CheckPoint(new Transform(-10, -10, Math.PI / 2), new Circle(30))});
        RegattaGoal goal2 = new RegattaGoal(new CheckPoint[]{
                new CheckPoint(new Transform(10, 10, 0), new Circle(20)),
                new CheckPoint(new Transform(-10, -10, Math.PI / 2), new Circle(30))});
        assertEquals(goal1.hashCode(), this.regattaGoal.hashCode());
        assertNotEquals(goal2.hashCode(), this.regattaGoal.hashCode());
    }

}
