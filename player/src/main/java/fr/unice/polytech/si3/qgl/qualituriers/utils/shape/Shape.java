package fr.unice.polytech.si3.qgl.qualituriers.utils.shape;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Cette classe represente un element commun aux formes des obestacles
 *
 * @author williamdandrea
 */

public class Shape {

    protected String type;

    public Shape(@JsonProperty("type") String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Shape{" +
                "type=" + type +
                '}';
    }

    public String getType() {
        return type;
    }
}
