package fr.unice.polytech.si3.qgl.qualituriers.utils;

/**
 * @author William D'Andrea
 * @author Yann Clodong
 */
public class Position {
    private double x;
    private double y;
    private double orientation;

    public Position(double x, double y, double orientation) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
    }

    public Position(Point position, double orientation) {
        this.x = position.getX();
        this.y = position.getY();
        this.orientation = orientation;
    }

    /**
     * @return Vecteur droite de cette position
     */
    public Point right() {
        return new Point(orientation);
    }

    /**
     * @return Vecteur haut de cette position
     */
    public Point up() {
        return new Point(orientation + Math.PI / 2);
    }

    /**
     * @return Vecteur bas de cette position
     */
    public Point down() {
        return up().scalar(-1);
    }

    /**
     * @return Vecteur gauche de cette position
     */
    public Point left() {
        return right().scalar(-1);
    }

    /**
     * Location
     * @return location
     */
    public Point getPoint() {
        return new Point(x, y);
    }

    /**
     * Direction que pointe la position
     * @return Direction
     */
    public Point direction() {
        return new Point(orientation);
    }

    /**
     * Translation
     * @param direction: Direction (normé) dans laquelle déplacer cette position
     * @return La position translatée
     */
    public Position translate(Point direction) {
        return new Position(direction.add(getPoint()), orientation);
    }

    /**
     * Rotation
     * @param deltaAngle: angle
     * @return Position qui a tourner de deltaAngle
     */
    public Position rotate(double deltaAngle) {
        return new Position(x, y, orientation + deltaAngle);
    }

    /**
     * Rotation nécessaire pour que l'orientation pointe la location précisé<br>
     * <u>ex:</u>  La Position caractérise l'état du bateau. l'angle retourner sera la déviation de cap que le bateau devra effectué pour rejoindre le point location.
     * @param location: l'emplacement a pointer
     * @return L'angle
     */
    public double getAngleToSee(Point location) {
        var dir = location.substract(getPoint());
        return direction().angleWith(dir);
    }
}
