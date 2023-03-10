package fr.unice.polytech.si3.qgl.qualituriers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.game.RoundInfo;
import fr.unice.polytech.si3.qgl.qualituriers.render.RenderTest;
import fr.unice.polytech.si3.qgl.qualituriers.render.SecondRender;
import fr.unice.polytech.si3.qgl.qualituriers.render.ThirdRender;
import fr.unice.polytech.si3.qgl.regatta.cockpit.ICockpit;

import java.util.ArrayList;
import java.util.List;

public class Cockpit implements ICockpit {

	ThirdRender render;
	private ObjectMapper om;

	public void initGame(String game) {
		//System.out.println("Game : " + game);
		this.om = new ObjectMapper();
		this.om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			GameInfo gameInfo = om.readValue(game, GameInfo.class);

			this.render = new ThirdRender(gameInfo);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	public String nextRound(String round) {
		//System.out.println("Round : " + round);
		if(this.render != null) {
			try {
				RoundInfo roundInfo = om.readValue(round, RoundInfo.class);
				String next = this.render.nextRound(roundInfo);
				if (next != null)
					return next;
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return "[]";
	}

	@Override
	public List<String> getLogs() {
		return new ArrayList<>();
	}

}
