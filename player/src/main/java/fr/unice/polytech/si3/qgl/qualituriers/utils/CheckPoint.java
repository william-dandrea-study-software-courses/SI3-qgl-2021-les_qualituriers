package fr.unice.polytech.si3.qgl.qualituriers.utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shape;

/**
 * Cette classe represente un checkpoint auquel le bateau devra arriver pour valider une course
 *
 * @author williamdandrea
 */


public class CheckPoint {

    private Transform position;
    private Shape shape;

    public CheckPoint(Transform position, Shape shape) {
        this.position = position;
        this.shape = shape;
    }

    @JsonCreator
    public CheckPoint(@JsonProperty("x") int posX,@JsonProperty("y") int posY,@JsonProperty("orientation") int orientation,@JsonProperty("type") String type){
        this.position = new Transform(posX, posY, orientation);
        switch(type){
            case "rectangle":
                //this.shape = new Shape(Shapes.RECTANGLE);
                //this.shape = new Shapes(1,1,0);
        }
    }

    /*public String getShapesAsString() {
        return shapes.getType();
    }*/

    public CheckPoint(){}


    public Transform getPosition() {
        return position;
    }

    public Shape getShape() {
        return shape;
    }

    @Override
    public String toString() {
        return "CheckPoint{" +
                "position=" + position +
                ", shape=" + shape +
                '}';
    }
}
