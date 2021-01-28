package fr.unice.polytech.si3.qgl.qualituriers;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Le deck represente le plateau de jeu, dans notre cas, le deck represente la mer
 *
 * @author williamdandrea
 */


public class Deck {

    private int width;
    private int length;

    public Deck(@JsonProperty("width") int width,@JsonProperty("length") int length) {
        this.width = width;
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }
}
