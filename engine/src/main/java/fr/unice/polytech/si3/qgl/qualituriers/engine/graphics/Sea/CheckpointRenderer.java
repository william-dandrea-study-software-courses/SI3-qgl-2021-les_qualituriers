package fr.unice.polytech.si3.qgl.qualituriers.engine.graphics.Sea;

import fr.unice.polytech.si3.qgl.qualituriers.engine.races.Race;
import fr.unice.polytech.si3.qgl.qualituriers.game.goal.RegattaGoal;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;

public class CheckpointRenderer {

    private final Race race;
    public CheckpointRenderer(Race race) {
        this.race = race;
    }

    public void draw(MyCanvas canvas, Graphics g) {
        if(race.getGoal() instanceof RegattaGoal) {
            RegattaGoal goal = (RegattaGoal)race.getGoal();
            for(var checkpoint : goal.getCheckPoints()) {
                canvas.drawShape(checkpoint.getPositionableShape(), Color.RED, g);
            }
        }
    }

    public Rectangle2D.Double getBounds() {
        if(race.getGoal() instanceof RegattaGoal) {
            RegattaGoal g = (RegattaGoal)race.getGoal();


            double minx = Arrays.stream(g.getCheckPoints()).map(p -> p.getPosition().getX()).reduce(Double.MAX_VALUE, (old, neo) -> {
                if(neo < old) return neo;
                else return old;
            });
            double miny = Arrays.stream(g.getCheckPoints()).map(p -> p.getPosition().getY()).reduce(Double.MAX_VALUE, (old, neo) -> {
                if(neo < old) return neo;
                else return old;
            });
            double maxx = Arrays.stream(g.getCheckPoints()).map(p -> p.getPosition().getX()).reduce(-Double.MAX_VALUE, (old, neo) -> {
                if(neo > old) return neo;
                else return old;
            });
            double maxy = Arrays.stream(g.getCheckPoints()).map(p -> p.getPosition().getY()).reduce(-Double.MAX_VALUE, (old, neo) -> {
                if(neo > old) return neo;
                else return old;
            });

            return new Rectangle2D.Double(minx, miny, maxx - minx, maxy - miny);
        }

        return null;
    }
}
