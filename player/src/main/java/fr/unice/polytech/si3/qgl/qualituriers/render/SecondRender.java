package fr.unice.polytech.si3.qgl.qualituriers.render;

import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.game.RoundInfo;

/**
 * @author Alexandre Arcil
 * • Un seul point de passage
 * • Le point de passage ne sera pas en face de vous : il va falloir apprendre à tourner
 * • Votre bateau sera plus grand: 4 cases de long pour 2 cases de large
 * • Votre bateau contiendra 6 rames
 * • Vos 4 marins ne seront pas forcement placés sur des rames au début de la partie : déplacez les avec l'action MOVING.
 */
public class SecondRender extends Render {

    public SecondRender(GameInfo gameInfo) {
        super(gameInfo);
    }

    @Override
    public Object nextRound(RoundInfo round) {
        return null;
    }

}
