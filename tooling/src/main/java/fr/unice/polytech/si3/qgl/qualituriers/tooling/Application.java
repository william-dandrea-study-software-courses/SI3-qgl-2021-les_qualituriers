package fr.unice.polytech.si3.qgl.qualituriers.tooling;

import fr.unice.polytech.si3.qgl.qualituriers.Cockpit;

public class Application {
	
	public static void main(String [] args) {
		Cockpit cockpit = new Cockpit();
		System.out.println("An instance of my team player: " + cockpit);
		System.out.println("When called, it returns some JSON: " + cockpit.nextRound(""));
	}
}
