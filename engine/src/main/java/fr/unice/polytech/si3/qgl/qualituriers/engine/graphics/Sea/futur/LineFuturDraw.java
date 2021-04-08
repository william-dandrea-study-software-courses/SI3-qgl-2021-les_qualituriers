package fr.unice.polytech.si3.qgl.qualituriers.engine.graphics.Sea.futur;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.helpers.IDrawer;

import java.awt.*;

public class LineFuturDraw extends FuturDraw{

    private final Point start;
    private final Point end;
    private final Color color;

    public LineFuturDraw(Point start, Point end, Color color) {
        this.start = start;
        this.end = end;
        this.color = color;
    }

    @Override
    public void draw(IDrawer drawer, Graphics g) {
        drawer.drawLine(this.start, this.end, this.color, g);
    }
}
