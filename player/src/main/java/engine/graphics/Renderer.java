package engine.graphics;

import engine.races.Race;

import javax.swing.*;
import java.awt.*;

public class Renderer {

    private final JFrame frame;
    private final MyCanvas canvas;

    private final BoatRenderer boatR;
    private final CheckpointRenderer checkR;

    public Renderer(Race race) {
        frame = new JFrame();
        frame.setSize(600, 480);
        canvas = new MyCanvas();
        canvas.setSize(600, 480);
        canvas.setLocation(0, 0);
        frame.add(canvas);
        frame.setVisible(true);

        boatR = new BoatRenderer(race);
        checkR = new CheckpointRenderer(race);
    }

    private void clear() {
        canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public void draw() {
        clear();
        boatR.render(canvas);
        checkR.draw(canvas);
    }
}
