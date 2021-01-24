package fr.unice.polytech.si3.qgl.qualituriers.utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Shapes;

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

    @JsonCreator
    public CheckPoint(@JsonProperty("x") int posX,@JsonProperty("y") int posY,@JsonProperty("orientation") int orientation,@JsonProperty("type") String type){
        this.position = new Position(posX, posY, orientation);
        switch(type){
            case "rectangle":
                this.shapes = new Shapes(1,1,0);
        }
    }

    public String getShapesAsString() {
        return shapes.getType();
    }

    public CheckPoint(){}
}
