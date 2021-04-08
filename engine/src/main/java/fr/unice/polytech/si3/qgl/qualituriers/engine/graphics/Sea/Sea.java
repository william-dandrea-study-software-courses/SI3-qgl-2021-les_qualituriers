package fr.unice.polytech.si3.qgl.qualituriers.engine.graphics.Sea;

import fr.unice.polytech.si3.qgl.qualituriers.engine.races.Race;
import fr.unice.polytech.si3.qgl.qualituriers.render.TempoRender;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Sea {

    private final JFrame frame;
    public MyCanvas canvas;

    private final Race race;


    public Sea(Race race) {
        frame = new JFrame();
        frame.setMaximumSize(new Dimension(600, 480));
        frame.setMinimumSize(new Dimension(600, 480));
        frame.setSize(600, 480);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        canvas = new MyCanvas(race);
        canvas.setLocation(0, 0);
        canvas.setSize(600, 480);

        TempoRender.SeaDrawer = canvas;

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

                Point d = new Point(e.getX(), e.getY()).substract(start);
                d = d.scalar(-1);
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
                canvas.ajustCanvas();
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



        canvas.ajustCanvas();
        //canvas.ajustWindows(java.util.List.of(new Rectangle2D.Double(150, 150, 1, 1), new Rectangle2D.Double(-1, -1, 1, 1)));
    }



    private void clear() {
        canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        var g = canvas.getGraphics();
        g.setColor(Color.cyan);
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }



    public void draw() {
        this.canvas.repaint();
        //clear();
        //path.addWaypoint(race.getBoat().getPosition().getPoint(), Point.ZERO);


        //path.draw();
        //this.drawMousePosition();


    }

    //Peut être sympa
    /*private void drawMousePosition() {
        Point mousePos = canvas.getScreenPosition(canvas.getMousePos()); //-> il faudrait la fonction inverse
        canvas.getGraphics().drawString("X: "+mousePos.getX()+" | Y: "+mousePos.getY(), 0, 10);
    }*/



    public void close() {
        frame.dispose();
    }

}
