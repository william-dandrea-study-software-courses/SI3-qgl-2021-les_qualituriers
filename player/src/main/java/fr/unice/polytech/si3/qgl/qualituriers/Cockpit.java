package fr.unice.polytech.si3.qgl.qualituriers;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.unice.polytech.si3.qgl.qualituriers.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.boat.Marin;
import fr.unice.polytech.si3.qgl.qualituriers.parser.ParserIn;
import fr.unice.polytech.si3.qgl.regatta.cockpit.ICockpit;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Cockpit implements ICockpit {

	Optional<Boat> boat;
	List<Optional<Marin>> sailors;

	public void initGame(String game) {
		ParserIn parser = new ParserIn();
		try {
			parser.initParser(game);
			boat = parser.createBoat();
			sailors = parser.createSailors();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		//System.out.println("Init game input: " + game);

	}



	public String nextRound(String round) {
		//System.out.println("Next round input: " + round);
		String rendu = "[]";
		try {
			rendu = CockpitIntelligence.premierRendu(sailors);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return rendu;
	}

	@Override
	public List<String> getLogs() {
		return new ArrayList<>();
	}
}

/**
 * FOR THE FIRST RENDU, ON DOIT AVOIR :
 * [
 *     {
 *         "sailorId": 1,
 *         "type": "OAR"
 *     },
 *     {
 *         "sailorId": 2,
 *         "type": "OAR"
 *     }
 * ]
 */
