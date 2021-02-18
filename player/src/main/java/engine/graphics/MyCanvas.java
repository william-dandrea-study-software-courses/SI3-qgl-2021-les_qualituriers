package engine.graphics;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.util.ArrayList;
import java.util.Objects;

import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.PositionableShape;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Polygon;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shapes;

public class MyCanvas extends Canvas {

    private double scale = 1.8;
    private Point offset = new Point(-300, -200);

    public MyCanvas() {
    }

    private void setCameraPosition(Point pt) {
        offset = new Point(pt.getX() - getWidth() / 2, pt.getY() - getHeight() / 2);
    }

    private int ajustToScale(double n) {
        return (int)(n * scale);
    }

    private Point getScreenPosition(Point pt) {
        return new Point(ajustToScale(pt.getX()), ajustToScale(pt.getY())).substract(offset);
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

        var x = dx / 2;
        var y = dy / 2;

        scale = Math.min(Math.abs(getWidth() / dx), Math.abs(getHeight() / dy)) - 0.2;

        setCameraPosition(new Point(x, y).scalar(scale));
    }

    public void drawPin(Point position, Color color) {
        //setCameraPosition(new Point(1000, -200));
        var g = getGraphics();
        g.setColor(color);
        g.fillOval((int) getScreenPosition(position).getX() - 5, (int) getScreenPosition(position).getY() - 5, 10, 10);
    }

    public void drawCircle(PositionableShape<Circle> circle, Color color) {
        var g = getGraphics();
        g.setColor(color);
        var pos = getScreenPosition(circle.getTransform().getPoint());
        var size = ajustToScale(circle.getShape().getRadius());

        g.fillOval((int)pos.getX() - size, (int)pos.getY() - size, 2 * size, 2 * size);
    }

    public void drawPolygon(PositionableShape<Polygon> polygon, Color color) {
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

    public void drawShape(PositionableShape<Shape> shape, Color color) {
        switch (shape.getShape().getType()) {
            case CIRCLE:
                drawCircle(shape.convertTo(Shapes.CIRCLE), color);
                break;
            case RECTANGLE:
                drawPolygon(shape.convertToPolygon(), color);
                break;
            case POLYGON:
                drawPolygon(shape.convertTo(Shapes.POLYGON), color);
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
