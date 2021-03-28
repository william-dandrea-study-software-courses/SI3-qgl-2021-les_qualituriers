package fr.unice.polytech.si3.qgl.qualituriers.utils.shape;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableCircle;

import java.util.Objects;

/**
 * Cette classe représente un element rectangulaire qui pourra être utiliser pour les différents obstacles
 *
 * @author williamdandrea, Alexandre Arcil, CLODONG Yann
 */
public class Rectangle extends PolygonAbstract {

    private final double width;
    private final double height;

    @JsonCreator
    public Rectangle(@JsonProperty("width") double width, @JsonProperty("height") double height,
                     @JsonProperty("orientation") double orientation) {
        super(orientation, new Point[] {
                        new Point(-width / 2, height / 2),
                        new Point(width / 2, height / 2),
                        new Point(width / 2, -height / 2),
                        new Point(-width / 2, -height / 2)
                }, Shapes.RECTANGLE);
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public boolean isIn(Point position) {
        var orr = position.getOrientation() - this.getOrientation();
        var orthoPosition = new Point(orr).scalar(position.length());

        return orthoPosition.getX() >= 0 &&
                orthoPosition.getY() >= 0 &&
                orthoPosition.getX() <= width &&
                orthoPosition.getY() <= height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Rectangle rectangle = (Rectangle) o;
        return Double.compare(rectangle.width, width) == 0 && Double.compare(rectangle.height, height) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), width, height);
    }

    @Override
    public PolygonAbstract scaleFromCenter(double scale) {
        return new Rectangle(width * scale, height * scale, this.getOrientation());
    }

    @Override
    public PositionableCircle getCircumscribed() {
        return new PositionableCircle(new Circle(new Point(width / 2, height / 2).length()), Transform.ZERO);
    }
}
