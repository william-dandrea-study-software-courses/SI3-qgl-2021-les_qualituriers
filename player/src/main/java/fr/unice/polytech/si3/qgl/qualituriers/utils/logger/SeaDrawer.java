package fr.unice.polytech.si3.qgl.qualituriers.utils.logger;

import fr.unice.polytech.si3.qgl.qualituriers.render.TempoRender;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.helpers.IDrawer;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableCircle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionablePolygon;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableShape;

import java.awt.*;

public class SeaDrawer {

    public static void drawPolygon(PositionablePolygon polygon, Color color) {
        if(TempoRender.SeaDrawer != null) {
            TempoRender.SeaDrawer.drawPolygon(polygon, color);
        }
    }

    public static void drawLine(Point start, Point end, Color color) {
        if(TempoRender.SeaDrawer != null) {
            TempoRender.SeaDrawer.drawLine(start, end, color);
        }
    }

    public static void drawCircle(PositionableCircle circle, Color color) {
        if(TempoRender.SeaDrawer != null) {
            TempoRender.SeaDrawer.drawCircle(circle, color);
        }
    }

    public static void drawShape(PositionableShape<? extends Shape> shape, Color color) {
        if(TempoRender.SeaDrawer != null) {
            TempoRender.SeaDrawer.drawShape(shape, color);
        }
    }

    public static void drawPin(Point position, Color color) {
        if(TempoRender.SeaDrawer != null) {
            TempoRender.SeaDrawer.drawPin(position, color);
        }
    }

    public static void waitIfDebugMode(int time) {
        if(TempoRender.SeaDrawer != null) {
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {}
        }
    }
}