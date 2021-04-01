package fr.unice.polytech.si3.qgl.qualituriers.engine.graphics.Sea;

import fr.unice.polytech.si3.qgl.qualituriers.engine.races.Race;

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

        canvas.drawShape(race.getBoat().getPositionableShape().getCircumscribedPolygon(), Color.BLUE);
        //canvas.drawPin(race.getBoat().getPosition(), Color.BLUE);
    }
}
