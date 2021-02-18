package engine.graphics;

import engine.races.Race;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;

import javax.swing.*;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;


public class Renderer {

    private final JFrame frame;
    private final MyCanvas canvas;

    private final Race race;
    private final BoatRenderer boatR;
    private final CheckpointRenderer checkR;
    private final PathRenderer path;

    private final Rectangle2D.Double bb;

    public Renderer(Race race) {
        frame = new JFrame();
        frame.setSize(600, 480);

        canvas = new MyCanvas();
        canvas.setLocation(0, 0);
        canvas.setSize(600, 480);
        canvas.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ajustCanvas();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        frame.add(canvas);

        frame.setVisible(true);

        this.race = race;
        boatR = new BoatRenderer(race);
        checkR = new CheckpointRenderer(race);
        path = new PathRenderer(canvas);


        bb = boatR.getBounds();

        path.addWaypoint(race.getBoat().getPosition().getPoint());

        ajustCanvas();
        //canvas.ajustWindows(java.util.List.of(new Rectangle2D.Double(150, 150, 1, 1), new Rectangle2D.Double(-1, -1, 1, 1)));
    }

    private  void ajustCanvas() {
        canvas.ajustWindows(java.util.List.of(boatR.getBounds(), checkR.getBounds(), path.getBounds()));
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
