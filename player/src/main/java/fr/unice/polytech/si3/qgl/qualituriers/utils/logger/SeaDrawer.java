package fr.unice.polytech.si3.qgl.qualituriers.utils.logger;

import fr.unice.polytech.si3.qgl.qualituriers.render.TempoRender;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.helpers.IShapeDraw;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableCircle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionablePolygon;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableShape;

import java.awt.*;

public class SeaDrawer {

    public static void drawPolygon(PositionablePolygon polygon, Color color) {
        if(TempoRender.SeaDrawer != null) {
            TempoRender.SeaDrawer.drawFuturPolygon(polygon, color);
        }
    }

    public static IShapeDraw drawLine(Point start, Point end, Color color) {
        if(TempoRender.SeaDrawer != null) {
            return TempoRender.SeaDrawer.drawFuturLine(start, end, color);
        } else return null;
    }

    public static void drawCircle(PositionableCircle circle, Color color) {
        if(TempoRender.SeaDrawer != null) {
            TempoRender.SeaDrawer.drawFuturFilledCircle(circle, color);
        }
    }

    public static void drawShape(PositionableShape<? extends Shape> shape, Color color) {
        if(TempoRender.SeaDrawer != null) {
            TempoRender.SeaDrawer.drawFuturShape(shape, color);
        }
    }

    public static IShapeDraw drawPin(Point position, Color color) {
        if(TempoRender.SeaDrawer != null) {
            TempoRender.SeaDrawer.drawFuturPin(position, color);
        }
        return null;
    }

    public static void waitIfDebugMode(int time) {
        if(TempoRender.SeaDrawer != null) {
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
