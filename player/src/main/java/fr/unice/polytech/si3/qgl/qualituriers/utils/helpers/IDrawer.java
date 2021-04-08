package fr.unice.polytech.si3.qgl.qualituriers.utils.helpers;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableCircle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionablePolygon;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableShape;

import java.awt.*;

public interface IDrawer {

    void drawPolygon(PositionablePolygon polygon, Color color, Graphics g);
    IShapeDraw drawFuturPolygon(PositionablePolygon polygon, Color color);

    void drawLine(fr.unice.polytech.si3.qgl.qualituriers.utils.Point start, Point end, Color color, Graphics g);
    IShapeDraw drawFuturLine(fr.unice.polytech.si3.qgl.qualituriers.utils.Point start, Point end, Color color);

    void drawFilledCircle(PositionableCircle circle, Color color, Graphics g);
    IShapeDraw drawFuturFilledCircle(PositionableCircle circle, Color color);

    void drawCircle(PositionableCircle circle, Color color, Graphics g);

    void drawShape(PositionableShape<? extends Shape> shape, Color color, Graphics g);
    IShapeDraw drawFuturShape(PositionableShape<? extends Shape> shape, Color color);

    void drawPin(Point position, Color color, Graphics g);
    IShapeDraw drawFuturPin(Point position, Color color);

}
