package fr.unice.polytech.si3.qgl.qualituriers.engine.graphics.Sea.futur;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.helpers.IDrawer;

import java.awt.*;

public class PinFuturDraw extends FuturDraw {

    private final Point position;
    private final Color color;

    public PinFuturDraw(Point position, Color color) {
        this.position = position;
        this.color = color;
    }

    @Override
    public void draw(IDrawer drawer, Graphics g) {
        drawer.drawPin(this.position, this.color, g);
    }
}
