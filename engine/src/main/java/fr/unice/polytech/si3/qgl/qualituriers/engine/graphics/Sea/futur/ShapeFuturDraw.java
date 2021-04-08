package fr.unice.polytech.si3.qgl.qualituriers.engine.graphics.Sea.futur;

import fr.unice.polytech.si3.qgl.qualituriers.utils.helpers.IDrawer;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableShape;

import java.awt.*;

public class ShapeFuturDraw extends FuturDraw {

    private final PositionableShape<? extends Shape> shape;
    private final Color color;

    public ShapeFuturDraw(PositionableShape<? extends Shape> shape, Color color) {
        this.shape = shape;
        this.color = color;
    }

    @Override
    public void draw(IDrawer drawer, Graphics g) {
        drawer.drawShape(this.shape, this.color, g);
    }
}
