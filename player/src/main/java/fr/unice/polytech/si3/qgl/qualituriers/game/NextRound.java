package fr.unice.polytech.si3.qgl.qualituriers.game;

import fr.unice.polytech.si3.qgl.qualituriers.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.deckentities.Wind;
import fr.unice.polytech.si3.qgl.qualituriers.deckentities.deckvisibleentities.VisibleDeckEntities;

/**
 * Cette classe represente la prochaine action du jeu
 *
 * @author williamdandrea
 */

public class NextRound {

    private Boat ship;
    private Wind wind;
    private VisibleDeckEntities[] visibleEntities;

    public NextRound(Boat ship, Wind wind, VisibleDeckEntities[] visibleEntities) {
        this.ship = ship;
        this.wind = wind;
        this.visibleEntities = visibleEntities;
    }
}
