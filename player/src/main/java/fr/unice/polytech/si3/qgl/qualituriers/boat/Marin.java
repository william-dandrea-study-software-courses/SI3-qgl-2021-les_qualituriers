package fr.unice.polytech.si3.qgl.qualituriers.boat;

/**
 * Cette classe represente un marin qui pourra donner des ordres a différents protagonistes present sur le bateau
 *
 * @author williamdandrea
 */
public class Marin {

    private int id;
    private int x;
    private int y;
    private String name;

    public Marin(int id, int x, int y, String name) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public int getId() {
        return id;
    }
}
