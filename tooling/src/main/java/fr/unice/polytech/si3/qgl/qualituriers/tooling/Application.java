package fr.unice.polytech.si3.qgl.qualituriers.tooling;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.qualituriers.Cockpit;
import fr.unice.polytech.si3.qgl.qualituriers.utils.Point;

import java.io.IOException;
import java.util.Scanner;

public class Application {
	
	public static void main(String [] args) throws IOException{
		run();
	}

	public static void run() throws IOException {
		Cockpit cockpit = new Cockpit();
		cockpit.initGame(new Scanner(System.in).nextLine());
		System.out.println("An instance of my team player: " + cockpit);
		System.out.println("When called, it returns some JSON: " + cockpit.nextRound(new Scanner(System.in).nextLine()));
		System.out.println(cockpit.gameInfo.getShip().getTransform());
	}

	public static void runTest() throws IOException{
		Cockpit cockpit = new Cockpit();
		cockpit.initGame(new Scanner(System.in).nextLine());
		System.out.println("An instance of my team player: " + cockpit);

		System.out.println("When called, it returns some JSON: ");
		String temp = new Scanner(System.in).nextLine();
		cockpit.nextRound(temp);
		double boatX = 0;
		double boatY = 0;
		while(cockpit.gameInfo.getShip().getTransform().getPoint() != cockpit.render.getCheckpoints()[0].getPosition().getPoint()) {
			String pos = "\"position\":{\"x\":" + boatX +",\"y\":" + boatY + ",\"orientation\":0}";
			String newRound = "{\"ship\":{\"type\":\"ship\",\"life\":100," + pos + ",\"name\":\"Les copaings d'abord!\",\"deck\":{\"width\":3,\"length\":6},\"entities\":[{\"x\":1,\"y\":0,\"type\":\"oar\"},{\"x\":1,\"y\":2,\"type\":\"oar\"},{\"x\":3,\"y\":0,\"type\":\"oar\"},{\"x\":3,\"y\":2,\"type\":\"oar\"},{\"x\":4,\"y\":0,\"type\":\"oar\"},{\"x\":4,\"y\":2,\"type\":\"oar\"},{\"x\":2,\"y\":1,\"type\":\"sail\",\"openned\":false},{\"x\":5,\"y\":0,\"type\":\"rudder\"}],\"shape\":{\"type\":\"rectangle\",\"width\":3,\"height\":6,\"orientation\":0}},\"visibleEntities\":[{\"type\":\"stream\",\"position\":{\"x\":500,\"y\":0,\"orientation\":0},\"shape\":{\"type\":\"rectangle\",\"width\":50,\"height\":500,\"orientation\":0},\"strength\":40}],\"wind\":{\"orientation\":0,\"strength\":110}}";
			System.out.println("When called, it returns some JSON: " + cockpit.nextRound(newRound));
			boatX = cockpit.gameInfo.getShip().getTransform().getPoint().getX();
			boatY = cockpit.gameInfo.getShip().getTransform().getPoint().getY();
		}
	}
}
