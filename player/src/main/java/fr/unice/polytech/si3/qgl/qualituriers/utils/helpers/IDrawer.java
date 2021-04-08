package fr.unice.polytech.si3.qgl.qualituriers.utils.helpers;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableCircle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionablePolygon;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableShape;

import java.awt.*;

public interface IDrawer {
    void drawPolygon(PositionablePolygon polygon, Color color);
    void drawLine(fr.unice.polytech.si3.qgl.qualituriers.utils.Point start, Point end, Color color);
    void drawFilledCircle(PositionableCircle circle, Color color);
    void drawCircle(PositionableCircle circle, Color color);
    void drawShape(PositionableShape<? extends Shape> shape, Color color);
    void drawPin(Point position, Color color);
}
