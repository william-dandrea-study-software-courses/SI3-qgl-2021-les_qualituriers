package fr.unice.polytech.si3.qgl.qualituriers.engine.graphics.Sea;

import fr.unice.polytech.si3.qgl.qualituriers.engine.races.Race;
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
    private final ReefRenderer reefR;
    private final StreamRendered streamR;

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
            private Point startDisplayOffset;
            @Override
            public void mouseDragged(MouseEvent e) {
                if(start == null) {
                    startDisplayOffset = canvas.getDisplayOffset();
                    start = new Point(e.getX(), e.getY());
                }

                Point d = new Point(e.getX(), e.getY()).substract(start).scalar(-1);
                d = new Point(d.getX(), d.getY());
                canvas.setOffset(startDisplayOffset.add(d));
                draw();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                start = null;
                startDisplayOffset = null;
            }
        });
        canvas.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                ajustCanvas();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //init start -> calculer diff entre mousedragged et lui -> reinit start
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
        reefR = new ReefRenderer(race);
        streamR = new StreamRendered(race);

        path.addWaypoint(race.getBoat().getPosition().getPoint(), null);

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

        checkR.draw(canvas);
        reefR.draw(canvas);
        streamR.draw(canvas);
        path.draw();
        boatR.render(canvas);
        //this.drawMousePosition();

        if(race.getBoat().getLife() <= 0)
            this.drawDeadMessage();
    }

    //Peut être sympa
    /*private void drawMousePosition() {
        Point mousePos = canvas.getScreenPosition(canvas.getMousePos()); //-> il faudrait la fonction inverse
        canvas.getGraphics().drawString("X: "+mousePos.getX()+" | Y: "+mousePos.getY(), 0, 10);
    }*/

    private void drawDeadMessage() {
        Point position = race.getBoat().getPosition().getPoint();
        var graphics = canvas.getGraphics();
        graphics.setColor(Color.BLACK);
        graphics.drawString("Mort !", (int) canvas.getScreenPosition(position).getX() - 15, (int) canvas.getScreenPosition(position).getY() - 10);
    }

    public void close() {
        frame.dispose();
    }

    public PathRenderer getPath() {
        return path;
    }
}
