package fr.unice.polytech.si3.qgl.qualituriers.utils;

/**
 * @author William D'Andrea
 * @author Yann Clodong
 */
public class Rectangle {

    private double width;
    private double height;
    private double orientation;

    public Rectangle(double width, double height, double orientation) {
        this.width = width;
        this.height = height;
        this.orientation = orientation;
    }

    /**
     * Coin en haut à droite du rectangle
     * @return Position du coin
     */
    public Point upRight() {
        Position pos = new Position(0, 0, orientation);
        return  pos.getPoint()                          // position
                    .add(                               // +
                pos.up().scalar(height / 2)             // up * h / 2
                    .add(                               // +
                pos.right().scalar(width / 2)  ));      // right * w / 2
    }

    /**
     * Coin en haut à gauche du rectangle
     * @return Position du coin
     */
    public Point upLeft() {
        Position pos = new Position(0, 0, orientation);
        return  pos.getPoint()                          // position
                    .add(                               // +
                pos.up().scalar(height / 2)             // up * h / 2
                    .add(                               // +
                pos.left().scalar(width / 2)  ));      // left * w / 2
    }

    /**
     * Coin en bas à droite du rectangle
     * @return Position du coin
     */
    public Point DownRight() {
        Position pos = new Position(0, 0, orientation);
        return  pos.getPoint()                          // position
                    .add(                               // +
                pos.down().scalar(height / 2)             // down * h / 2
                    .add(                               // +
                pos.right().scalar(width / 2)  ));      // right * w / 2
    }

    /**
     * Coin en bas à gauche du rectangle
     * @return Position du coin
     */
    public Point downLeft() {
        Position pos = new Position(0, 0, orientation);
        return  pos.getPoint()                          // position
                    .add(                               // +
                pos.down().scalar(height / 2)             // down * h / 2
                    .add(                               // +
                pos.left().scalar(width / 2)  ));      // left * w / 2
    }
}
