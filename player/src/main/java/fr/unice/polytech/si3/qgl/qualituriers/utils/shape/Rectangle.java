package fr.unice.polytech.si3.qgl.qualituriers.utils.shape;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Cette classe représente un element rectangulaire qui pourra être utiliser pour les différents obstacles
 *
 * @author williamdandrea, Alexandre Arcil
 */
public class Rectangle extends Shape {

    private final double width;
    private final double height;
    private final double orientation;

    @JsonCreator
    public Rectangle(@JsonProperty("width") double width, @JsonProperty("height") double height,
                     @JsonProperty("orientation") double orientation) {
        super(Shapes.RECTANGLE);
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
