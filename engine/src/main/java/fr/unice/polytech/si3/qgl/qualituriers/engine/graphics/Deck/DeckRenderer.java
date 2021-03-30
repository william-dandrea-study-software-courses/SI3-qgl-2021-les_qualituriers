package fr.unice.polytech.si3.qgl.qualituriers.engine.graphics.Deck;

import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;

import javax.swing.*;

public class DeckRenderer {
    private final JFrame frame;
    private final DeckCanvas canvas;
    private final Boat boat;
    private Marin[] sailors;

    public DeckRenderer(Boat boat, Marin[] sailors) {
        this.boat = boat;
        this.sailors = sailors;
        frame = new JFrame();
        frame.setSize(500, 500);

        canvas = new DeckCanvas(boat.getDeck().getWidth(), boat.getDeck().getLength());
        canvas.setSize(500, 500);
        canvas.setLocation(0, 0);
        canvas.ajustWindow();

        frame.add(canvas);
        //frame.setVisible(true);
    }

    public void setSailor(Marin[] sailors) {
        this.sailors = sailors;
    }

    public void draw() {
        canvas.clear();
        for(var sailor : sailors) {
            canvas.drawSailor(sailor.getPosition());
        }
        for(var entity : boat.getEntities()) {
            canvas.drawEntity(entity);
        }
        canvas.drawGrid();
    }
}
