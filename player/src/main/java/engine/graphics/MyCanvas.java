package engine.graphics;

import java.awt.*;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;

public class MyCanvas extends Canvas {

    private float scale = 1;
    private Point offset = new Point(300, 200);

    public MyCanvas() {

    }

    private int ajustPos(double n) {
        return (int)(n * scale);
    }

    private Point getPos(Point pt) {
        return this.offset.add(new Point(ajustPos(pt.getX()), ajustPos(pt.getY())));
    }

    public void drawPin(Point position, Color color) {
        getGraphics().setColor(color);
        getGraphics().fillOval((int)getPos(position).getX() - 5, (int)getPos(position).getY() - 5, 10, 10);
    }
}
