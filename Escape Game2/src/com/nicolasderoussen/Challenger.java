package com.nicolasderoussen;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Challenger {

	private ArrayList <Integer> numberRdm = new ArrayList <Integer>(); //computer number
	private ArrayList <Integer> numberChoosed = new ArrayList <Integer>(); //player number
	private ArrayList <String> computerAnswer = new ArrayList <String>(); //computer answer with +,- or =
	private int tryNumber; private int combinaisonNumberofNumber;//setting with xml
	private boolean victory = false; //victory condition
	private boolean verificationNumberChoosedConfirmed = false;
	final static Logger logger = LogManager.getLogger(Challenger.class);

	//end the loop or not depending of player answer
	public boolean getVerificationNumberChoosedConfirmed() {
		return verificationNumberChoosedConfirmed;
	}
	
	public void setVerificationNumberChoosedConfirmed(boolean verificationNumberChoosedConfirmed) {
		this.verificationNumberChoosedConfirmed = verificationNumberChoosedConfirmed;
	}

	public int getTryNumber() {
		return tryNumber;
	}

	public void setTryNumber(int tryNumber) {
		this.tryNumber = tryNumber;
	}

	public int getCombinaisonNumberofNumber() {
		return combinaisonNumberofNumber;
	}

	public void setCombinaisonNumberofNumber(int combinaisonNumber) {
		this.combinaisonNumberofNumber = combinaisonNumber;
	}
	
	public boolean getVictory() {
		return victory;
	}

	public void setVictory(boolean victory) {
		this.victory = victory;
	}

	public void getRdmRoll() {
		rdmroll();
	}
	
	public void getDefeatPlayer() {
		defeatPlayer();
	}
	
	public void getCompareAll() {
		compareAll();
	}
	
	public void getResult() {
		result();
	}
	
	public void getVictoryTest() {
		victoryTest();
	}

	public void getInitialisation(int devModeActivated) {
		initialisation(devModeActivated);
	}
	
	public void getNumberChoosed() {
		for (Integer a : numberChoosed) {
			System.out.print(a);
		}		
	}
	
	public void getComputerAnswer() {
		for (String a : computerAnswer) {
			System.out.print(a);
		}		
	}
	// clear all Array
	public void setClearAllArray() {
		numberRdm.clear();
		numberChoosed.clear();
		computerAnswer.clear();
	}
	
	//constructor
	public Challenger(int tryNumber, int combinaisonNumberofNumber) {
		this.tryNumber = tryNumber;
		this.combinaisonNumberofNumber = combinaisonNumberofNumber;
	}
	
	public void setNumberChoosed(String nombre) {		
		numberChoosed.clear(); // clear arraylist of player
		int n = 0;
		if (nombre.length() != getCombinaisonNumberofNumber()) {
			System.out.println("Incorrect input! Your answer should matches with " + getCombinaisonNumberofNumber() + " number(s).");
			setVerificationNumberChoosedConfirmed(false);
			logger.info("Incorrect input from player, input != " + getCombinaisonNumberofNumber());
		}
		else if (nombre.length() == getCombinaisonNumberofNumber()){	
			for (int i = 0; i < nombre.length(); i += 0) {
				char a = nombre.charAt(i);
				if (Character.isDigit(a)) {
					n++;
				}
				i++;	
			}
			if (n == getCombinaisonNumberofNumber()) {	
				for (int i = 0; i < getCombinaisonNumberofNumber(); i += 0) {	
					int a =  Character.getNumericValue(nombre.charAt(i));
					numberChoosed.add(i, a);
					i++;
				}
				setVerificationNumberChoosedConfirmed(true);
			}
			else {
				System.out.println("Incorrect input! Your answer must be only number(s).");
				logger.info("Incorrect input from player, input != " + getCombinaisonNumberofNumber());
				setVerificationNumberChoosedConfirmed(false);
			}	
		}
	}		
	
	//check if dev mode is enable, if yes show the computer number once every loop
	private void initialisation(int a) {
		if (a == 1) {
			checkComputer(); //show computer number for developer
			System.out.println();
		}
	}

	//creating the random number of the computer taking parameter are counted 
	private void rdmroll() { 
		numberRdm.clear();
		for (int i = 0; i < getCombinaisonNumberofNumber(); i += 0) {
			numberRdm.add(i, (int)(0 + Math.random() * 9));		
			i++;
		}
	}
	
	//compare number between computer and player
	private void compareAll() {
		computerAnswer.clear(); // clear arraylist of the computer answer
		for(int i = 0; i < getCombinaisonNumberofNumber(); i += 0) {			
			if(numberChoosed.get(i).equals(numberRdm.get(i))) {
				computerAnswer.add(i, "=");
			}
			else if(numberChoosed.get(i) < (numberRdm.get(i))) {
				computerAnswer.add(i, "+");
			}
			else if(numberChoosed.get(i) > numberRdm.get(i)) {
				computerAnswer.add(i, "-");
			}
			i++;
		}
	}
	
	//dev method, to show the computer number
	private void checkComputer() {
		System.out.print("Computer's combinaison is ");
		for (Integer a : numberRdm) {
			System.out.print(a);
		}
	}

	//show the result of the comparison with + , - or =
	private void result() {
		for (String a : computerAnswer) {
			System.out.print(a);
		}
		System.out.println();
	}
	
	
	//verification of the victory
	private void victoryTest() {
		int a = 0; // for every number that matches with the computer, got +1
		for(int i = 0; i < numberRdm.size(); i += 0) {
			if(numberRdm.get(i).equals(numberChoosed.get(i))) {
				a++;
			}
			if(a == numberRdm.size()){
				setVictory(true);
				System.out.println("\nCongratulations! You win this one.");
			}
			i++;
		}
	}

	//defeat of player
	private void defeatPlayer() {
		System.out.println("\nNo more trie(s) sorry! You lost.");
		System.out.println("The correct answer was : ");
			for (Integer a : numberRdm) {
				System.out.print(a);
			}
	}
}