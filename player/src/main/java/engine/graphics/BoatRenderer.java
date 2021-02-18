package engine.graphics;

import engine.races.Race;
import fr.unice.polytech.si3.qgl.qualituriers.utils.PositionableShape;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class BoatRenderer {

    private final Race race;
    public BoatRenderer(Race race) {
        this.race = race;
    }

    Rectangle2D.Double getBounds() {
        return new Rectangle2D.Double(race.getBoat().getPosition().getX(), race.getBoat().getPosition().getY(), 0, 0);
    }

    void render(MyCanvas canvas) {

        //canvas.drawShape(new PositionableShape<>(race.getBoat().getShape(), race.getBoat().getPosition()), Color.BLUE);
        canvas.drawPin(race.getBoat().getPosition(), Color.BLUE);
    }
}
