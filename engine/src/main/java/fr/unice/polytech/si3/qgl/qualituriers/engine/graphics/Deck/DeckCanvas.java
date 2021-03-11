package fr.unice.polytech.si3.qgl.qualituriers.engine.graphics.Deck;

import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.BoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.SailBoatEntity;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;

import java.awt.*;

public class DeckCanvas extends Canvas {

    private final int width;
    private final int length;

    private float scale = 1;
    private Point offset;

    private Graphics2D g;

    public DeckCanvas(int width, int length) {
        super();
        this.width = width;
        this.length = length;
    }

    private float getScale() {
        return Math.min((float)getWidth() / (float)length, (float)getHeight() / (float)width) * 0.8f;
    }
    private Point getOffset() {
        return new Point((float)getWidth() / 2 - scale * (float)length / 2, (float)getHeight() / 2 - scale * (float)width / 2);
    }

    public void ajustWindow() {
        this.scale = getScale();
        this.offset = getOffset();
        int i = 0;
    }

    public Point getInScreenPosition(Point pt) {
        return pt.scalar(scale).add(offset);
    }

    public void drawEntity(BoatEntity entity) {
        Image img = null;
        switch (entity.getType()) {
            case OAR:
                img = Icons.OAR.getImage();
                break;
            case SAIL:
                if(((SailBoatEntity) entity).isOpened())
                    img = Icons.SAIL_OPEN.getImage();
                else
                    img = Icons.SAIL_CLOSE.getImage();
                break;
            case RUDDER:
                img = Icons.RUDDER.getImage();
                break;
        }
        if(img == null) img = Icons.NONE.getImage();

        var position = getInScreenPosition(entity.getPosition());
        var g = getGraphics();

        g.drawImage(img, (int)position.getX(), (int)position.getY(), (int)scale, (int)scale, null);
    }

    public void clear() {
        g = (Graphics2D)getGraphics();
        //g.clearRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.WHITE);
        g.drawRect(0, 0, getWidth(), getHeight());
    }

    public void drawGrid() {
        var stroke = new BasicStroke(3);
        g.setStroke(stroke);
        g.setColor(Color.gray);

        for(int x = 0; x <= length; x++) {
            var start = getInScreenPosition(new Point(x, 0));
            var end = getInScreenPosition(new Point(x, width));

            g.drawLine((int)start.getX(), (int)start.getY(), (int)end.getX(), (int)end.getY());
        }

        for(int y = 0; y <= width; y++) {
            var start = getInScreenPosition(new Point(0, y));
            var end = getInScreenPosition(new Point(length, y));

            g.drawLine((int)start.getX(), (int)start.getY(), (int)end.getX(), (int)end.getY());
        }
    }

    public void drawSailor(Point pt) {
        g.setColor(Color.PINK);
        var pos = getInScreenPosition(pt);
        g.fillRect((int)pos.getX(), (int)pos.getY(), (int)scale, (int)scale);
    }
}
