package engine.graphics.Sea;

import engine.races.Race;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Sea {

    private final JFrame frame;
    private final MyCanvas canvas;

    private final Race race;
    private final BoatRenderer boatR;
    private final CheckpointRenderer checkR;
    private final PathRenderer path;

    public Sea(Race race) {
        frame = new JFrame();
        frame.setMaximumSize(new Dimension(600, 480));
        frame.setMinimumSize(new Dimension(600, 480));
        frame.setSize(600, 480);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        canvas = new MyCanvas();
        canvas.setLocation(0, 0);
        canvas.setSize(600, 480);


        canvas.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int delta = e.getWheelRotation();

                if(delta >= 0) {
                    canvas.zoomIn();
                } else {
                    canvas.zoomOut();
                }

                draw();

            }
        });
        canvas.addMouseMotionListener(new MouseMotionListener() {
            private Point start = null;
            @Override
            public void mouseDragged(MouseEvent e) {
                if(start == null) start = new Point(e.getX(), e.getY());

                canvas.setOffset(new Point(e.getX(), e.getY()).substract(start).scalar(-1));
                draw();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                start = null;
            }
        });
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


        path.addWaypoint(race.getBoat().getPosition().getPoint());

        ajustCanvas();
        //canvas.ajustWindows(java.util.List.of(new Rectangle2D.Double(150, 150, 1, 1), new Rectangle2D.Double(-1, -1, 1, 1)));
    }

    private  void ajustCanvas() {
        canvas.ajustWindows(java.util.List.of(boatR.getBounds(), checkR.getBounds()));
        draw();
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