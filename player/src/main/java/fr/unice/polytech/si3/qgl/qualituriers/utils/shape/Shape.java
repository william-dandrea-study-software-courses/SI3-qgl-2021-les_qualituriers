package fr.unice.polytech.si3.qgl.qualituriers.utils.shape;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Cette classe represente un element commun aux formes des obestacles
 *
 * @author williamdandrea, Alexandre Arcil
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

    public Shapes getType() {
        return type;
    }
}
