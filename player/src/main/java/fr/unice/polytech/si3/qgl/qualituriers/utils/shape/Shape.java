package fr.unice.polytech.si3.qgl.qualituriers.utils.shape;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

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

    public Shape(@JsonProperty("type") Shapes type) {
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

    @JsonIgnore
    public Shapes getType() {
        return type;
    }


}
