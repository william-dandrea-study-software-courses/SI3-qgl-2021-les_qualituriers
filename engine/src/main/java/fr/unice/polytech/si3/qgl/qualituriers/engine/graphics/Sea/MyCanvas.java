package fr.unice.polytech.si3.qgl.qualituriers.engine.graphics.Sea;

import fr.unice.polytech.si3.qgl.qualituriers.engine.graphics.Arc;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.helpers.IDrawer;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableCircle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionablePolygon;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableShape;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.util.Objects;

public class MyCanvas extends Canvas implements IDrawer {

    private double scale = 1.0;
    private double zoom = 1.0;

    private Point target = new Point(0, 0);

    private Point offset = new Point(-300, -200);
    private Point displayOffset = new Point(0, 0);
    private Point mousePos;

    private static final Color blue =  new Color(96, 96, 255);

    public MyCanvas() {
        this.mousePos = new Point(0, 0);
    }

    public void setMousePos(Point mousePos) {
        this.mousePos = mousePos;
    }

    public Point getMousePos() {
        return mousePos;
    }

    private void setCameraPosition(Point pt) {
        offset = pt;
    }

    private int ajustToScale(double n) {
        return (int)(n * scale * zoom);
    }

    public Point getScreenPosition(Point pt) {
        pt = pt.substract(offset);
        pt = new Point(pt.getX(), -pt.getY());
        pt = pt.scalar(zoom * scale);


        return pt.add(new Point(getWidth() / 2.0, getHeight() / 2.0));
    }

    public Point getSeaPosition(Point pt) {
        pt = pt.add(offset);
        pt = new Point(pt.getX(), -pt.getY());
        // 5582 : 2092 -> 183 : 237
        //pt = pt.add(offset);
        //pt = pt.scalar()
        return pt;
    }

    public void setOffset(Point offset) {
        offset = new Point(offset.getX(), offset.getY());
        this.offset = offset.scalar(1 / (zoom * scale));
    }

    public void moveOffsetOf(Point delta) {
        this.displayOffset = this.displayOffset.add(delta);
    }

    public double getTotalZoom() {
        return zoom * scale;
    }

    public Point getDisplayOffset() {
        return offset.scalar(zoom * scale);
    }

    public void ajustWindows(java.util.List<Rectangle2D> bounds) {
        double minx = bounds.stream().filter(Objects::nonNull).map(RectangularShape::getX).reduce(0., (Double old, Double neo) -> {
            if(neo < old) return neo;
            else return old;
        });
        double miny = bounds.stream().filter(Objects::nonNull).map(RectangularShape::getY).reduce(0., (Double old, Double neo) -> {
            if(neo < old) return neo;
            else return old;
        });
        double maxx = bounds.stream().filter(Objects::nonNull).map(r -> r.getX() + r.getWidth()).reduce(0., (Double old, Double neo) -> {
            if(neo > old) return neo;
            else return old;
        });
        double maxy = bounds.stream().filter(Objects::nonNull).map(r -> r.getY() + r.getHeight()).reduce(0., (Double old, Double neo) -> {
            if(neo > old) return neo;
            else return old;
        });

        var dx = maxx - minx;
        var dy = maxy - miny;

        var x = (minx + maxx) / 2;
        var y = (miny + maxy) / 2;

        scale = Math.min(Math.abs(getWidth() / dx), Math.abs(getHeight() / dy));

        setCameraPosition(new Point(x, y));
    }

    public void zoomIn() {
        zoom /= 2;
        //offset = offset.substract(new Point(getWidth() / 2, getHeight() / 2).scalar(zoom));
    }

    public void zoomOut() {
        zoom *= 2;
    }

    public void drawPin(Point position, Color color) {
        //setCameraPosition(new Point(1000, -200));
        var g = getGraphics();
        g.setColor(color);
        g.fillOval((int) getScreenPosition(position).getX() - 5, (int) getScreenPosition(position).getY() - 5, 10, 10);
    }

    public void drawCircle(PositionableCircle circle, Color color) {
        var g = getGraphics();
        g.setColor(color);
        var pos = getScreenPosition(circle.getTransform().getPoint());
        var size = ajustToScale(circle.getShape().getRadius());

        g.fillOval((int)pos.getX() - size, (int)pos.getY() - size, 2 * size, 2 * size);
    }

    public void drawPolygon(PositionablePolygon polygon, Color color) {
        var g = getGraphics();
        g.setColor(color);
        var pts = polygon.getPoints();
        var ptsX = new int[pts.length];
        var ptsY = new int[pts.length];

        for(int i = 0; i < pts.length; i++) {
            var apt = getScreenPosition(pts[i]);
            ptsX[i] = (int)apt.getX();
            ptsY[i] = (int)apt.getY();
        }

        g.drawPolygon(ptsX, ptsY, pts.length);
    }

    public void drawShape(PositionableShape<? extends Shape> shape, Color color) {
        switch (shape.getShape().getType()) {
            case CIRCLE:
                drawCircle((PositionableCircle) shape, color);
                break;
            case RECTANGLE: case POLYGON:
                drawPolygon((PositionablePolygon) shape, color);
                break;
        }
    }

    public void drawLine(Point start, Point end, Color color) {
        var g = (Graphics2D)getGraphics();
        g.setColor(color);

        var s = getScreenPosition(start);
        var e = getScreenPosition(end);

        g.setStroke(new BasicStroke(3));

        g.drawLine((int)s.getX(), (int)s.getY(), (int)e.getX(), (int)e.getY());
    }

    public void drawArc(Arc arc) {
        var g = (Graphics2D)getGraphics();
        Point point1 = this.getScreenPosition(arc.getPoint1());
        Point point2 = this.getScreenPosition(arc.getPoint2());
        Point point3 = this.getScreenPosition(arc.getPoint3());
        double radius = this.getRadius(point1, point2, point3);
        Arc2D.Double arc2D = new Arc2D.Double();
        arc2D.setArcByTangent(new Point2D.Double(point1.getX(), point1.getY()), new Point2D.Double(point2.getX(), point2.getY()),
                new Point2D.Double(point3.getX(), point3.getY()), radius);
        g.setColor(blue);
        g.draw(arc2D);
    }

    private double getRadius(Point point1, Point point2, Point point3) {
        double x1 = point1.getX();
        double x2 = point2.getX();
        double x3 = point3.getX();
        double y1 = point1.getY();
        double y2 = point2.getY();
        double y3 = point3.getY();
        double a = (x3*x3 - x2*x2) / (y3 - y2);
        double b = (x1*x1 - x2*x2) / (y1 - y2);
        double c = 2*((x3 - x2) / (y3 - y2));
        double d = 2*((x1 - x2) / (y1 - y2));
        double x = (a - b + y3 - y1) / (c - d);
        double y = -2 * x * ((x1 - x2) / (y1 - y2)) + b + y1 + y2;
        y /= 2;
        return Math.sqrt(Math.pow(x1 - x, 2) + Math.pow(y1 - y, 2));
    }
}
