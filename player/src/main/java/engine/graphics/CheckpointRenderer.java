package engine.graphics;

import engine.races.Race;
import fr.unice.polytech.si3.qgl.qualituriers.game.goal.RegattaGoal;

import java.awt.*;

public class CheckpointRenderer {

    private final Race race;
    public CheckpointRenderer(Race race) {
        this.race = race;
    }

    public void draw(MyCanvas canvas) {
        if(race.getGoal() instanceof RegattaGoal) {
            RegattaGoal g = (RegattaGoal)race.getGoal();
            for(var checkpoint : g.getCheckPoints()) {
                canvas.drawPin(checkpoint.getPosition().getPoint(), Color.RED);
            }
        }
    }
}
