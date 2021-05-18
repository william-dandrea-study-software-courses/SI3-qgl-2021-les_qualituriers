package fr.unice.polytech.si3.qgl.qualituriers;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Le deck représente le plateau de jeu, dans notre cas, le deck représente la mer
 *
 * @author williamdandrea
 * @author CLODONG Yann
 */
public class Deck {

    private final int width;
    private final int length;

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

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof Deck)) return false;
        var castedObj = (Deck)obj;
        return castedObj.width == width && castedObj.length == length;
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, length);
    }
}
