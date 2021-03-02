package fr.unice.polytech.si3.qgl.qualituriers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.qualituriers.game.GameInfo;
import fr.unice.polytech.si3.qgl.qualituriers.game.RoundInfo;
import fr.unice.polytech.si3.qgl.qualituriers.render.ThirdRender;
import fr.unice.polytech.si3.qgl.qualituriers.utils.action.Action;
import fr.unice.polytech.si3.qgl.qualituriers.utils.logger.CockpitLogger;
import fr.unice.polytech.si3.qgl.regatta.cockpit.ICockpit;

import java.util.List;

public class Cockpit implements ICockpit {

	ThirdRender render;
	private ObjectMapper om;
	private CockpitLogger logger;

	public void initGame(String game) {
		this.logger = new CockpitLogger();
		this.om = new ObjectMapper();
		this.om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		this.logger.log("Levez l'encre !");
		try {
			GameInfo gameInfo = om.readValue(game, GameInfo.class);
			this.render = new ThirdRender(gameInfo, this.logger);
			this.logger.log("Encre levée capitaine !");
		} catch (JsonProcessingException e) {
			this.logger.log(e.toString());
		}
	}

	public String nextRound(String round) {
		this.logger.log("Que faisons-nous maintenant capitaine ?");
		if(this.render != null) {
			try {
				RoundInfo roundInfo = om.readValue(round, RoundInfo.class);
				List<Action> actions = this.render.nextRound(roundInfo);
				if (actions != null)
					return om.writeValueAsString(actions);
			} catch (JsonProcessingException e) {
				this.logger.log(e.toString());
			}
		}
		this.logger.log("Reposez-vous.");
		return "[]";
	}

	@Override
	public List<String> getLogs() {
		return this.logger.getMessages();
	}

}
