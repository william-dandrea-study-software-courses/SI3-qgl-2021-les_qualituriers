package fr.unice.polytech.si3.qgl.qualituriers.utils.shape;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Cette classe represente un element rectangulaire qui pourra etre utiliser pour les différents obstacles
 *
 * @author williamdandrea
 */


public class Rectangle extends Shape{

    private double width;
    private double height;
    private double orientation;

    public Rectangle(@JsonProperty("width")double width, @JsonProperty("height")double height, @JsonProperty("orientation")double orientation) {
        super(Shapes.RECTANGLE.getType());
        this.width = width;
        this.height = height;
        this.orientation = orientation;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getOrientation() {
        return orientation;
    }
}
