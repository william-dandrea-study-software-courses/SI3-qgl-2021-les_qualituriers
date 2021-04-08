package fr.unice.polytech.si3.qgl.qualituriers.engine.graphics.Sea;

import fr.unice.polytech.si3.qgl.qualituriers.engine.races.Race;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.StreamVisibleDeckEntity;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Polygon;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionablePolygon;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamRendered {

    private static final Color DARK_BLUE = Color.decode("#00008B");
    private final Race race;

    public StreamRendered(Race race) {
        this.race = race;
    }

    public void draw(MyCanvas canvas, Graphics g) {
        for (StreamVisibleDeckEntity stream : this.getStreams()) {
            canvas.drawShape(stream.getPositionableShape(), DARK_BLUE, g);
            this.drawArrowLine(canvas, stream, g);
        }
    }

    private List<StreamVisibleDeckEntity> getStreams() {
        if(this.race.getEntities() == null) return new ArrayList<>();
        return Arrays.stream(this.race.getEntities())
                .filter(entity -> entity instanceof StreamVisibleDeckEntity)
                .map(entity -> (StreamVisibleDeckEntity) entity)
                .collect(Collectors.toList());
    }

    private void drawArrowLine(MyCanvas canvas, StreamVisibleDeckEntity stream, Graphics g) {
        Point point = stream.getPositionableShape().getPoints()[0];
        Point point1 = new Point(stream.getPositionableShape().getTransform().getOrientation())
                .scalar(stream.getStrength() * 20.0).add(point);
        this.drawArrowLine(canvas, (int) point.getX(), (int) point.getY(), (int) point1.getX(), (int) point1.getY() * -1, 2, 2, g);
    }

    /**
     * Source: https://stackoverflow.com/a/27461352
     * Draw an arrow line between two points.
     * @param canvas the graphics component.
     * @param x1 x-position of first point.
     * @param y1 y-position of first point.
     * @param x2 x-position of second point.
     * @param y2 y-position of second point.
     * @param d  the width of the arrow.
     * @param h  the height of the arrow.
     */
    private void drawArrowLine(MyCanvas canvas, int x1, int y1, int x2, int y2, int d, int h, Graphics g) {
        int dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx*dx + dy*dy);
        double xm = D - d, xn = xm, ym = h, yn = -h, x;
        double sin = dy / D, cos = dx / D;

        x = xm*cos - ym*sin + x1;
        ym = xm*sin + ym*cos + y1;
        xm = x;

        x = xn*cos - yn*sin + x1;
        yn = xn*sin + yn*cos + y1;
        xn = x;

        Point[] points = new Point[] {new Point(x2, y2), new Point((int) xm, (int) ym), new Point((int) xn, (int) yn)};

        canvas.drawLine(new Point(x1, y1), new Point(x2, y2), Color.WHITE, g);
        canvas.drawPolygon(new PositionablePolygon(new Polygon(0, points), new Transform(0, 0, 0)), Color.WHITE, g);
    }

}
