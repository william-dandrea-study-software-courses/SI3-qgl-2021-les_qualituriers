package fr.unice.polytech.si3.qgl.qualituriers.render;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.game.RoundInfo;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;

import java.util.List;

public class RenderTest extends Render {

    public RenderTest(GameInfo gameInfo) {
        super(gameInfo);
    }

    @Override
    public String nextRound(RoundInfo round) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        System.out.println(om.writeValueAsString(round));

        return "[]";
    }
}
