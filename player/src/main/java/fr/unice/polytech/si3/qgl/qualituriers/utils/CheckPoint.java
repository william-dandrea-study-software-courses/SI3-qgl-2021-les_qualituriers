package fr.unice.polytech.si3.qgl.qualituriers.utils;

/**
 * Cette classe represente un checkpoint auquel le bateau devra arriver pour valider une course
 *
 * @author williamdandrea
 */


public class CheckPoint {

    private Position position;
    private Shapes shapes;

    public CheckPoint(Position position, Shapes shapes) {
        this.position = position;
        this.shapes = shapes;
    }
}
