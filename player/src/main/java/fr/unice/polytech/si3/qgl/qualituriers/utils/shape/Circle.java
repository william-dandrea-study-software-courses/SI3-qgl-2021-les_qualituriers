package fr.unice.polytech.si3.qgl.qualituriers.utils.shape;

/**
 * Cette classe represente un element rond qui pourra etre utiliser pour les différents obstacles
 *
 * @author williamdandrea
 */


public class Circle extends Shape{

    private double radius;

    public Circle(double radius) {
        super(Shapes.CIRCLE);
        this.radius = radius;
    }
}
