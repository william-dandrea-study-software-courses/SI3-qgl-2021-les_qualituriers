package fr.unice.polytech.si3.qgl.qualituriers;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;

/**
 * Le deck represente le plateau de jeu, dans notre cas, le deck represente la mer
 *
 * @author williamdandrea
 * @author CLODONG Yann
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

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof Deck)) return false;
        var castedObj = (Deck)obj;
        return castedObj.width == width && castedObj.length == length;
    }
}
