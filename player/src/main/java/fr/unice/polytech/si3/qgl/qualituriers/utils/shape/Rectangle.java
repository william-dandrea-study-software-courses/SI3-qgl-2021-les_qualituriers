package fr.unice.polytech.si3.qgl.qualituriers.utils.shape;

/**
 * Cette classe represente un element rectangulaire qui pourra etre utiliser pour les différents obstacles
 *
 * @author williamdandrea
 */

public class Rectangle extends Shape{

    private double width;
    private double height;
    private double orientation;

    public Rectangle(double width, double height, double orientation) {
        super(Shapes.RECTANGLE);
        this.width = width;
        this.height = height;
        this.orientation = orientation;
    }
}
