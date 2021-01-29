package fr.unice.polytech.si3.qgl.qualituriers.render;

import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.game.RoundInfo;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Oar;

import java.util.ArrayList;
import java.util.List;

/**
 * Algo pour le premier rendu : juste faire bouger les rams
 *
 * {
 *      "sailorId": 2,
 *      "type": "OAR"
 * }
 *
 */
public class FirstRender extends Render {

    public FirstRender(GameInfo gameInfo) {
        super(gameInfo);
    }

    @Override
    public Object nextRound(RoundInfo round) {
        List<Oar> sailors = new ArrayList<>();
        for (Marin marin : this.gameInfo.getSailors()) {
            Oar oar = new Oar(marin.getId());
            sailors.add(oar);
        }
        return sailors;
    }

}
