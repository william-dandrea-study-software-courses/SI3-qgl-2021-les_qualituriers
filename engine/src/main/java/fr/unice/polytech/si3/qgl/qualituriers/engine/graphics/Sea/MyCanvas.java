package fr.unice.polytech.si3.qgl.qualituriers.engine.graphics.Sea;

import fr.unice.polytech.si3.qgl.qualituriers.engine.graphics.Arc;
import fr.unice.polytech.si3.qgl.qualituriers.engine.graphics.Sea.futur.*;
import fr.unice.polytech.si3.qgl.qualituriers.engine.races.Race;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.helpers.IDrawer;
import fr.unice.polytech.si3.qgl.qualituriers.utils.helpers.IShapeDraw;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableCircle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionablePolygon;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableShape;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.util.*;
import java.util.List;

public class MyCanvas extends JPanel implements IDrawer {

    private final Race race;
    private double scale = 1.0;
    private double zoom = 1.0;

    private final BoatRenderer boatR;
    private final CheckpointRenderer checkR;
    private final PathRenderer path;
    private final ReefRenderer reefR;
    private final StreamRendered streamR;

    private Point target = new Point(0, 0);

    private Point offset = new Point(-300, -200);
    private Point displayOffset = new Point(0, 0);
    private Point mousePos;

    private final List<FuturDraw> futurDraws;

    private static final Color blue =  new Color(96, 96, 255);

    public MyCanvas(Race race) {
        this.race = race;
        this.mousePos = new Point(0, 0);

        boatR = new BoatRenderer(race);
        checkR = new CheckpointRenderer(race);
        path = new PathRenderer(this);
        reefR = new ReefRenderer(race);
        streamR = new StreamRendered(race);

        path.addWaypoint(race.getBoat().getPosition().getPoint(), null);

        this.setBackground(Color.CYAN);
        futurDraws = new ArrayList<>();
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
        pt = pt.subtract(offset);
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
    }

    public void zoomOut() {
        zoom *= 2;
    }

    public void drawPin(Point position, Color color, Graphics g) {
        //setCameraPosition(new Point(1000, -200));
        g.setColor(color);
        g.fillOval((int) getScreenPosition(position).getX() - 5, (int) getScreenPosition(position).getY() - 5, 10, 10);
    }

    public void drawFilledCircle(PositionableCircle circle, Color color, Graphics g) {
        g.setColor(color);
        var pos = getScreenPosition(circle.getTransform().getPoint());
        var size = ajustToScale(circle.getShape().getRadius());

        g.fillOval((int)pos.getX() - size, (int)pos.getY() - size, 2 * size, 2 * size);
    }

    @Override
    public IShapeDraw drawFuturFilledCircle(PositionableCircle circle, Color color) {
        CircleFuturDraw circleFuturDraw = new CircleFuturDraw(circle, color);
        this.futurDraws.add(circleFuturDraw);
        return circleFuturDraw;
    }

    public void drawCircle(PositionableCircle circle, Color color, Graphics g) {
        g.setColor(color);
        var pos = getScreenPosition(circle.getTransform().getPoint());
        var size = ajustToScale(circle.getShape().getRadius());

        g.drawOval((int)pos.getX() - size, (int)pos.getY() - size, 2 * size, 2 * size);
    }

    public void drawPolygon(PositionablePolygon polygon, Color color, Graphics g) {
        g.setColor(color);
        var pts = polygon.getPoints();
        var ptsX = new int[pts.length];
        var ptsY = new int[pts.length];

        for(int i = 0; i < pts.length; i++) {
            var apt = getScreenPosition(pts[i]);
            ptsX[i] = (int)apt.getX();
            ptsY[i] = (int)apt.getY();
        }

        g.fillPolygon(ptsX, ptsY, pts.length);
        //g.drawPolygon(ptsX, ptsY, pts.length);
    }

    @Override
    public IShapeDraw drawFuturPolygon(PositionablePolygon polygon, Color color) {
        PolygonFuturDraw polygonFuturDraw = new PolygonFuturDraw(polygon, color);
        this.futurDraws.add(polygonFuturDraw);
        return polygonFuturDraw;
    }

    public void drawShape(PositionableShape<? extends Shape> shape, Color color, Graphics g) {
        switch (shape.getShape().getType()) {
            case CIRCLE:
                drawFilledCircle((PositionableCircle) shape, color, g);
                break;
            case RECTANGLE: case POLYGON:
                drawPolygon((PositionablePolygon) shape, color, g);
                break;
        }
    }

    @Override
    public IShapeDraw drawFuturShape(PositionableShape<? extends Shape> shape, Color color) {
        ShapeFuturDraw shapeFuturDraw = new ShapeFuturDraw(shape, color);
        this.futurDraws.add(shapeFuturDraw);
        return shapeFuturDraw;
    }

    @Override
    public IShapeDraw drawFuturPin(Point position, Color color) {
        PinFuturDraw pinFuturDraw = new PinFuturDraw(position, color);
        this.futurDraws.add(pinFuturDraw);
        return pinFuturDraw;
    }

    public void drawLine(Point start, Point end, Color color, Graphics g) {
        g.setColor(color);

        var s = getScreenPosition(start);
        var e = getScreenPosition(end);

        ((Graphics2D)g).setStroke(new BasicStroke(3));

        g.drawLine((int)s.getX(), (int)s.getY(), (int)e.getX(), (int)e.getY());
    }

    @Override
    public IShapeDraw drawFuturLine(Point start, Point end, Color color) {
        LineFuturDraw lineFuturDraw = new LineFuturDraw(start, end, color);
        this.futurDraws.add(lineFuturDraw);
        return lineFuturDraw;
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Iterator<FuturDraw> iterator = this.futurDraws.iterator();
        while (iterator.hasNext()) {
            FuturDraw futurDraw = iterator.next();
            if(futurDraw.isDestroyed())
                iterator.remove();
            else
                futurDraw.draw(this, g);
        }

        checkR.draw(this, g);
        reefR.draw(this, g);
        streamR.draw(this, g);
        //path.draw();
        boatR.render(this, g);

        if(race.getBoat().getLife() <= 0)
            this.drawDeadMessage(g);
    }

    private void drawDeadMessage(Graphics graphics) {
        Point position = race.getBoat().getPosition().getPoint();
        graphics.setColor(Color.BLACK);
        graphics.drawString("Mort !", (int) this.getScreenPosition(position).getX() - 15, (int) this.getScreenPosition(position).getY() - 10);
    }

    public void ajustCanvas() {
        this.ajustWindows(java.util.List.of(boatR.getBounds(), checkR.getBounds()));
    }

    public PathRenderer getPath() {
        return path;
    }

    public void clearFuturDraw() {
        for (FuturDraw futurDraw : this.futurDraws)
            futurDraw.destroy();
    }
}
