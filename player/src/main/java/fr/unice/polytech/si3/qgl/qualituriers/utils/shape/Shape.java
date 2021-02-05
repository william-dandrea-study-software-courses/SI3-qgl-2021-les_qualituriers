package fr.unice.polytech.si3.qgl.qualituriers.utils.shape;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;

/**
 * Cette classe represente un element commun aux formes des obestacles
 *
 * @author williamdandrea
 */

public class Shape {

    protected String type;

    public Shape(@JsonProperty("type") String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Shape{" +
                "type=" + type +
                '}';
    }

    /**
     * @param position: point dans le reférenciel de la forme
     * @return true si le point est dans la forme, false sinon
     */
    public boolean isIn(Point position) {
        return false;
    }

    public String getType() {
        return type;
    }
}
