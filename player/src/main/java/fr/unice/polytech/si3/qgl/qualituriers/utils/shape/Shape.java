package fr.unice.polytech.si3.qgl.qualituriers.utils.shape;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.unice.polytech.si3.qgl.qualituriers.utils.shape.positionable.PositionableCircle;

/**
 * Cette classe represente un element commun aux formes des obestacles
 *
 * @author williamdandrea, Alexandre Arcil, CLODONG Yann
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = Void.class)
@JsonSubTypes({
        @JsonSubTypes.Type(name = "circle", value = Circle.class),
        @JsonSubTypes.Type(name = "rectangle", value = Rectangle.class),
        @JsonSubTypes.Type(name = "polygon", value = Polygon.class)
})
public abstract class Shape {

    protected Shapes type;

    protected Shape(@JsonProperty("type") Shapes type) {
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
    public abstract boolean isIn(Point position);

    /**
     * @return Le cercle circonscrit de la forme (dans le repère de celle-ci)
     */
    public abstract PositionableCircle getCircumscribed();

    @JsonIgnore
    public Shapes getType() {
        return type;
    }

}
