package com.nicolasderoussen;


public class Instruction {
	private int combinaisonNumberofNumber;
	
	
	public int getCombinaisonNumberofNumber() {
		return combinaisonNumberofNumber;
	}

	public void setCombinaisonNumberofNumber(int combinaisonNumberofNumber) {
		this.combinaisonNumberofNumber = combinaisonNumberofNumber;
	}

	public Instruction(int a) {
		combinaisonNumberofNumber = a;
	}
	
	public void getInstructionIntro() {
		instructionIntro();
	}

	public void getInstructionChoice() {
		instructionChoice();
	}


	public void getInstructionChallenger() {
		instructionChallenger();
	}

	public void getInstructionDefenseur() {
		instructionDefenseur();
	}

	public void getInstructionRejouer() {
		instructionRejouer();
	}

	public void getInstructionDuel() {
		instructionDuel();
	}

	public void getInstructionEnd() {
		instructionEnd();
	}

	private void instructionIntro() {
		System.out.println("Welcome to Escape Game ONLINE !\n");
	}

	private void instructionChoice() {
		System.out.println("Please pick a game mode by writing the corresponding number :");
		System.out.println("1 - Challenger : You have to find the computer's combinaison.");
		System.out.println("2 - Defenseur  : You define the combinaison then the computer try to find it.");
		System.out.println("3 - Duel : You and the computer pick a combinaison then turn after turn, each of you try to find the opponent's one");
		System.out.println("4 - Quit.");
	}

	private void instructionChallenger() {
		System.out.println("Welcome to Challenger ! \n");
		System.out.println("The computer's combinaison has " + getCombinaisonNumberofNumber() + " number(s)");
		System.out.println("If your number is lower, computer will respond with '+', if greater with '-' and if your number is equal with '='");
		System.out.println("Good luck!");
	}

	private void instructionDefenseur() {
		System.out.println("Welcome to Defender !\n");
		System.out.println("Computer is gonna play against you!");
		System.out.println("Before that, enter a combinaison of " + getCombinaisonNumberofNumber() + " number(s).");
	}

	private void instructionRejouer() {
		System.out.println("1 - Replay a game.");
		System.out.println("2 - Go back to main menu.");
	}

	private void instructionDuel() {
		System.out.println("Welcome to Duel !\n");
		System.out.println("You are gonna play attacker and defender at the same time!");
		System.out.println("First, enter a combinaison of " + getCombinaisonNumberofNumber() + " number(s)");
	}

	private void instructionEnd() {
		System.out.println("End of program.");
	}

}