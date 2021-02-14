package fr.unice.polytech.si3.qgl.qualituriers.utils.shape;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;

/**
 * Cette classe représente un element rectangulaire qui pourra être utiliser pour les différents obstacles
 *
 * @author williamdandrea, Alexandre Arcil, CLODONG Yann
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


    public Polygon toPolygon() {
        return new Polygon(orientation, new Point[] {
                new Point(-width / 2, height / 2),
                new Point(width / 2, height / 2),
                new Point(-width / 2, -height / 2),
                new Point(width / 2, -height / 2)
        });
    }

    @Override
    public boolean isIn(Point position) {
        var orr = position.getOrientation() - orientation;
        var orthoPosition = new Point(orr).scalar(position.length());

        return orthoPosition.getX() >= 0 &&
                orthoPosition.getY() >= 0 &&
                orthoPosition.getX() <= width &&
                orthoPosition.getY() <= height;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof Rectangle)) return false;
        var castedObj = (Rectangle)obj;
        return castedObj.width == width && castedObj.height == height && castedObj.orientation == orientation;
    }

    public double getOrientation() {
        return orientation;
    }
}
