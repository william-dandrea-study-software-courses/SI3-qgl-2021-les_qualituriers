package fr.unice.polytech.si3.qgl.qualituriers.tooling;

import fr.unice.polytech.si3.qgl.qualituriers.Cockpit;

import java.util.Scanner;

public class Application {
	
	public static void main(String [] args) {
		Cockpit cockpit = new Cockpit();
		cockpit.initGame(new Scanner(System.in).nextLine());
		System.out.println("An instance of my team player: " + cockpit);
		System.out.println("When called, it returns some JSON: " + cockpit.nextRound(""));
	}
}
