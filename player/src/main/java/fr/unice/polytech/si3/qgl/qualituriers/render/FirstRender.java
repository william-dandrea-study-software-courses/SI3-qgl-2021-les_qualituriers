package fr.unice.polytech.si3.qgl.qualituriers.render;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.qualituriers.entity.boat.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.game.RoundInfo;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Oar;
import fr.unice.polytech.si3.qgl.qualituriers.utils.logger.ILogger;

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

    public FirstRender(GameInfo gameInfo, ILogger logger) {
        super(gameInfo, logger);
    }

    @Override
    public String nextRound(RoundInfo round) throws JsonProcessingException {
        List<Oar> sailors = new ArrayList<>();
        ObjectMapper om = new ObjectMapper();
        // TODO : itérer toutes les rames pour vérifier quel marin peut y accéder en utilisant Moving.canMove()
        for (Marin marin : this.gameInfo.getSailors()) {
            Oar oar = new Oar(marin.getId());
            sailors.add(oar);
        }
        return om.writeValueAsString(sailors);
    }

}
