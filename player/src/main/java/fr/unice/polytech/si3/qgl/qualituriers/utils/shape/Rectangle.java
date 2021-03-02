package fr.unice.polytech.si3.qgl.qualituriers.utils.shape;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Transform;

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

    /**
     * Coin en haut à droite du rectangle
     * @return Position du coin
     */
    /*public Point upRight() {
        Transform pos = new Transform(0, 0, this.getOrientation());
        return  pos.getPoint()                          // position
                .add(                               // +
                        pos.up().scalar(height / 2)             // up * h / 2
                                .add(                               // +
                                        pos.right().scalar(width / 2)  ));      // right * w / 2
    }*/

    /**
     * Coin en haut à gauche du rectangle
     * @return Position du coin
     */
    /*public Point upLeft() {
        Transform pos = new Transform(0, 0, this.getOrientation());
        return  pos.getPoint()                          // position
                .add(                               // +
                        pos.up().scalar(height / 2)             // up * h / 2
                                .add(                               // +
                                        pos.left().scalar(width / 2)  ));      // left * w / 2
    }*/

    /**
     * Coin en bas à droite du rectangle
     * @return Position du coin
     */
    /*public Point downRight() {
        Transform pos = new Transform(0, 0, this.getOrientation());
        return  pos.getPoint()                          // position
                .add(                               // +
                        pos.down().scalar(height / 2)             // down * h / 2
                                .add(                               // +
                                        pos.right().scalar(width / 2)  ));      // right * w / 2
    }*/

    /**
     * Coin en bas à gauche du rectangle
     * @return Position du coin
     */
    /*public Point downLeft() {
        Transform pos = new Transform(0, 0, this.getOrientation());
        return  pos.getPoint()                          // position
                .add(                               // +
                        pos.down().scalar(height / 2)             // down * h / 2
                                .add(                               // +
                                        pos.left().scalar(width / 2)  ));      // left * w / 2
    }*/










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

}
