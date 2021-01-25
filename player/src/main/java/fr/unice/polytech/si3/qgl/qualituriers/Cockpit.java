package fr.unice.polytech.si3.qgl.qualituriers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.unice.polytech.si3.qgl.qualituriers.boat.Boat;
import fr.unice.polytech.si3.qgl.qualituriers.parser.ParserIn;
import fr.unice.polytech.si3.qgl.regatta.cockpit.ICockpit;

public class Cockpit implements ICockpit {

	Boat boat;

	public void initGame(String game) {
		ParserIn parser = new ParserIn();
		try {
			parser.initParser(game);
			boat = parser.createBoat();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println("Init game input: " + game);

	}



	public String nextRound(String round) {
		System.out.println("Next round input: " + round);
		return "[]";
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
 *       "sailorId": 1,
 *       "type": "MOVING",
 *       "xdistance": 1,
 *       "ydistance": 0
 *     },
 *     {
 *         "sailorId": 1,
 *         "type": "OAR"
 *     },
 *     {
 *         "sailorId": 1,
 *         "type": "MOVING",
 *         "xdistance": 0,
 *         "ydistance": 0
 *     },
 *     {
 *         "sailorId": 1,
 *         "type": "OAR"
 *     }
 * ]
 */
