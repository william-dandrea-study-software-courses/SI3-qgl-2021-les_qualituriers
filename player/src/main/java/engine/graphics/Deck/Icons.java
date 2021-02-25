package engine.graphics.Deck;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public enum Icons {
    NONE("none.png"),
    OAR("oar.png");

    private String filename;
    Icons(String fileName) {
        this.filename = fileName;
    }

    public Image getImage() {
        try {
            var file = new File("player/src/main/resources/icons/" + filename);
            return new ImageIcon(file.getCanonicalPath()).getImage();
        } catch(IOException filenotfound) {
            throw new RuntimeException("Erreur interne : au moins un fichier de ressource est manquant");
        }
    }
}
