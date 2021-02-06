package fr.unice.polytech.si3.qgl.qualituriers.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.Wind;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.VisibleDeckEntities;
import fr.unice.polytech.si3.qgl.qualituriers.entity.deck.visible.VisibleDeckEntity;

import java.util.Arrays;

/**
 * Cette classe represente la prochaine action du jeu
 *
 * @author williamdandrea
 * @author CLODONG Yann
 */

public class RoundInfo {

    private final Boat ship;
    private final Wind wind;
    private final VisibleDeckEntity[] visibleEntities;

    @JsonCreator
    public RoundInfo(@JsonProperty("ship") Boat ship, @JsonProperty("wind") Wind wind,
                     @JsonProperty("visibleEntities") VisibleDeckEntity[] visibleEntities) {
        this.ship = ship;
        this.wind = wind;
        this.visibleEntities = visibleEntities;
    }

    public Boat getShip() {
        return ship;
    }

    public Wind getWind() {
        return wind;
    }

    public VisibleDeckEntity[] getVisibleEntities() {
        return visibleEntities;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof RoundInfo)) return false;
        var castedObj = (RoundInfo)obj;
        return castedObj.ship.equals(ship) && castedObj.wind.equals(wind) && Arrays.equals(castedObj.visibleEntities, visibleEntities);
    }
}
