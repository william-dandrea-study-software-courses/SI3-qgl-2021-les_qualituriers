package engine.graphics;

import engine.races.Race;

import java.awt.*;

public class BoatRenderer {

    private final Race race;
    public BoatRenderer(Race race) {
        this.race = race;
    }

    void render(MyCanvas canvas) {
        canvas.drawPin(race.getBoat().getPosition().getPoint(), Color.BLUE);
    }
}
