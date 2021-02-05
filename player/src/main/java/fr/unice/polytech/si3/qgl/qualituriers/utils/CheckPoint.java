package fr.unice.polytech.si3.qgl.qualituriers.utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Circle;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Polygon;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.Rectangle;
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
    public CheckPoint(@JsonProperty("x") int posX,@JsonProperty("y") int posY,@JsonProperty("orientation") int orientation,@JsonProperty("shape") JsonNode shapeNode) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.position = new Transform(posX, posY, orientation);
        switch(shapeNode.get("type").asText()){
            case "rectangle":
                this.shape = om.treeToValue(shapeNode, Rectangle.class);
                break;
            case "circle":
                this.shape = om.treeToValue(shapeNode, Circle.class);
                break;
                //this.shape = new Shapes(1,1,0);
            case "polygon":
                this.shape = om.treeToValue(shapeNode, Polygon.class);
                break;
                //this.shape = new Shapes(1,1,0);
            default:
                break;
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
