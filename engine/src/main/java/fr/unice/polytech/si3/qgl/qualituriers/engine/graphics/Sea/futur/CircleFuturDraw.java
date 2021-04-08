package fr.unice.polytech.si3.qgl.qualituriers.engine.graphics.Sea.futur;

import fr.unice.polytech.si3.qgl.qualituriers.utils.helpers.IDrawer;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableCircle;

import java.awt.*;

public class CircleFuturDraw extends FuturDraw {

    private final PositionableCircle circle;
    private final Color color;

    public CircleFuturDraw(PositionableCircle circle, Color color) {
        this.circle = circle;
        this.color = color;
    }

    @Override
    public void draw(IDrawer drawer, Graphics g) {
        drawer.drawFilledCircle(this.circle, this.color, g);
    }
}
