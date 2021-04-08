package fr.unice.polytech.si3.qgl.qualituriers.engine.graphics.Sea.futur;

import fr.unice.polytech.si3.qgl.qualituriers.utils.helpers.IDrawer;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionablePolygon;

import java.awt.*;

public class PolygonFuturDraw extends FuturDraw {

    private final PositionablePolygon polygon;
    private final Color color;

    public PolygonFuturDraw(PositionablePolygon polygon, Color color) {
        this.polygon = polygon;
        this.color = color;
    }

    @Override
    public void draw(IDrawer drawer, Graphics g) {
        drawer.drawPolygon(this.polygon, this.color, g);
    }
}
