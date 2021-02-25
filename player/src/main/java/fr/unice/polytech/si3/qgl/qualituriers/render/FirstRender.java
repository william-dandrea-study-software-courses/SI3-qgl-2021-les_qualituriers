package fr.unice.polytech.si3.qgl.qualituriers.render;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.boatentities.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.game.RoundInfo;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
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
    public List<Action> nextRound(RoundInfo round) throws JsonProcessingException {
        List<Action> sailors = new ArrayList<>();
        // TODO : itérer toutes les rames pour vérifier quel marin peut y accéder en utilisant Moving.canMove()
        for (Marin marin : this.gameInfo.getSailors()) {
            Oar oar = new Oar(marin.getId());
            sailors.add(oar);
        }
        return sailors;
    }

}
