package fr.unice.polytech.si3.qgl.qualituriers.engine.graphics.Deck;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public enum Icons {

    NONE("none.png"),
    OAR("oar.png"),
    RUDDER("rudder.png"),
    SAIL_CLOSE("sail_close.png"),
    SAIL_OPEN("sail_open.png");

    private final Image img;
    Icons(String fileName) {
        this.img = loadImage(fileName);
    }

    private Image loadImage(String filename) {
        try {
            var file = new File("player/src/main/resources/icons/" + filename);
            return new ImageIcon(file.getCanonicalPath()).getImage();
        } catch(IOException filenotfound) {
            throw new RuntimeException("Erreur interne : au moins un fichier de ressource est manquant");
        }
    }

    public Image getImage() {
        return this.img;
    }

}
