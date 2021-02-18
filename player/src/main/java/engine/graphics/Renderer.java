package engine.graphics;

import engine.races.Race;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Renderer {

    private boolean isFirstDraw = true;
    private final JFrame frame;
    private final MyCanvas canvas;

    private final Race race;
    private final BoatRenderer boatR;
    private final CheckpointRenderer checkR;
    private final PathRenderer path;

    public Renderer(Race race) {
        frame = new JFrame();
        frame.setSize(600, 480);
        canvas = new MyCanvas();
        canvas.setSize(600, 480);
        canvas.setLocation(0, 0);
        frame.add(canvas);
        frame.setVisible(true);

        this.race = race;
        boatR = new BoatRenderer(race);
        checkR = new CheckpointRenderer(race);
        path = new PathRenderer(canvas);

        canvas.ajustWindows(java.util.List.of(boatR.getBounds(), checkR.getBounds()));

        //canvas.ajustWindows(java.util.List.of(new Rectangle2D.Double(150, 150, 1, 1), new Rectangle2D.Double(-1, -1, 1, 1)));
    }

    private void clear() {
        canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        var g = canvas.getGraphics();
        g.setColor(Color.cyan);
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public void draw() {
        clear();
        path.addWaypoint(race.getBoat().getPosition().getPoint());
        checkR.draw(canvas);
        path.draw();
        boatR.render(canvas);
    }

    public void close() {
        frame.dispose();
    }
}
