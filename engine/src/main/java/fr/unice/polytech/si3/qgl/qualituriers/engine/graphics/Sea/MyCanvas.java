package fr.unice.polytech.si3.qgl.qualituriers.engine.graphics.Sea;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableCircle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionablePolygon;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableShape;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.util.Objects;

public class MyCanvas extends Canvas {

    private double scale = 1.0;
    private double zoom = 1.0;

    private Point target = new Point(0, 0);

    private Point offset = new Point(-300, -200);
    private Point displayOffset = new Point(0, 0);
    private Point mousePos;

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
        var pts = polygon.getShape().getVertices(polygon.getTransform());
        var ptsX = new int[pts.length];
        var ptsY = new int[pts.length];

        for(int i = 0; i < pts.length; i++) {
            var apt = getScreenPosition(pts[i]);
            ptsX[i] = (int)apt.getX();
            ptsY[i] = (int)apt.getY();
        }

        g.fillPolygon(ptsX, ptsY, pts.length);
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
}