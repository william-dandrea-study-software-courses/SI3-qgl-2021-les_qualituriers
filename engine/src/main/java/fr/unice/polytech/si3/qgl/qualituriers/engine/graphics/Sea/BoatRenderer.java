package fr.unice.polytech.si3.qgl.qualituriers.engine.graphics.Sea;

import fr.unice.polytech.si3.qgl.qualituriers.engine.TurnConfig;
import fr.unice.polytech.si3.qgl.qualituriers.engine.races.Race;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableCircle;

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

    void render(MyCanvas canvas, Graphics g) {
        canvas.drawShape(race.getBoat().getPositionableShape().getCircumscribedPolygon(), Color.BLUE, g);
        canvas.drawCircle(new PositionableCircle(new Circle(race.isUsingWatch() ? TurnConfig.FIELD_VISION_ENLARGE : TurnConfig.FIELD_VISION),
                race.getBoat().getPosition()), Color.PINK, g);
        //canvas.drawPin(race.getBoat().getPosition(), Color.BLUE);
    }
}
