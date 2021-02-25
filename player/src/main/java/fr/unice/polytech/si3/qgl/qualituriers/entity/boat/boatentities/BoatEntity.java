package fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;

import java.util.Objects;

/**
 * Cette classe a pour objectif de contenir tout ce qui est commun aux différents types d'entités dans le bateau
 *
 * @author williamdandrea, Alexandre Arcil
 * @author CLODONG Yann
 */

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = Void.class)
@JsonSubTypes({
        @JsonSubTypes.Type(name = "oar", value = OarBoatEntity.class),
        @JsonSubTypes.Type(name = "sail", value = SailBoatEntity.class),
        @JsonSubTypes.Type(name = "rudder", value = RudderBoatEntity.class),
        @JsonSubTypes.Type(name = "watch", value = WatchBoatEntity.class),
        @JsonSubTypes.Type(name = "canon", value = CanonBoatEntity.class)
})
public abstract class BoatEntity {

    @JsonIgnore
    protected BoatEntities type;
    protected int x;
    protected int y;

    protected BoatEntity(@JsonProperty("type") BoatEntities type, @JsonProperty("x") int x, @JsonProperty("y") int y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    @JsonIgnore
    public Point getPosition() {
        return new Point(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @JsonIgnore
    public BoatEntities getType() {
        return type;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof BoatEntity)) return false;
        var castedObj = (BoatEntity)obj;
        return castedObj.type == type && castedObj.x == x && castedObj.y == y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, x, y);
    }

    @Override
    public String toString() {
        return "BoatEntity{" +
                "type=" + type.toString() +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
