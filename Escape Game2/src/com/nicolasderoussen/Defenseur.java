package com.nicolasderoussen;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Defenseur {

private ArrayList <Integer> playerCombinaison = new ArrayList <Integer>(); //computer answer with number(s)
private ArrayList <String> playerAnswer = new ArrayList <String>(); //player proposal for computer
private ArrayList <Integer> computerNumber = new ArrayList <Integer>(); //computer answer with number(s)
private boolean win = false; //victory condition
private int tryNumber; private int combinaisonNumberofNumber; //setting with xml
private boolean verificationNumberChoosedConfirmed = false;
private boolean verificationOperationChoosedConfirmed = false;
final static Logger logger = LogManager.getLogger(Defenseur.class);

	public int getTryNumber() {
		return tryNumber;
	}
	
	public void setTryNumber(int tryNumber) {
		this.tryNumber = tryNumber;
	}
	
	public int getCombinaisonNumberofNumber() {
		return combinaisonNumberofNumber;
	}
	
	public void setCombinaisonNumberofNumber(int combinaisonNumberofNumber) {
		this.combinaisonNumberofNumber = combinaisonNumberofNumber;
	}

	//constructor
	public Defenseur(int tryNumber, int combinaisonNumberofNumber) {
		this.tryNumber = tryNumber;
		this.combinaisonNumberofNumber = combinaisonNumberofNumber;
	}

	public boolean getWin() {
		return win;
	}

	public void setWin(boolean mwin) {
		win = mwin;
	}
	
	public void getRdmroll() {
		rdmroll();
	}

	//victory package
	public boolean getVictory() {
		return win;
	}

	public void setVictory(boolean victory) {
		win = victory;
	}
	//

	public void getVictoryTest() {
		victoryTest();
	}

	public void getDefeatComputer() {
		defeatComputer();
	}


	public void getShowNumber() {
		showNumber();
	}

	public void setVerificationNumberChoosedConfirmed(boolean b) {
		this.verificationNumberChoosedConfirmed = b;	
	}
	
	public boolean getVerificationNumberChoosedConfirmed() {
		return verificationNumberChoosedConfirmed;
	}
	
	public boolean getVerificationOperationChoosedConfirmed() {
		return verificationOperationChoosedConfirmed;
	}
	
	public void setVerificationOperationChoosedConfirmed(boolean b) {
		this.verificationOperationChoosedConfirmed = b;	
	}
	
	public void setOperationPlayer(String operationPlayer) {
		verificationOperationChoosedConfirmed(operationPlayer);
	}
	
	//clear all Array
	public void setClearAllArray() {
		computerNumber.clear();
		playerAnswer.clear();
		playerCombinaison.clear();
	}
	
	public void setNumberChoosedByPlayer(String nombre) {		
		playerCombinaison.clear(); // clear arraylist of player answer
		int n = 0;
		if (nombre.length() != getCombinaisonNumberofNumber()) {
			System.out.println("Incorrect input! Your combinaison should matches with " + getCombinaisonNumberofNumber() + " number(s).");
			logger.info("Incorrect input from player, input != " + getCombinaisonNumberofNumber());
			setVerificationNumberChoosedConfirmed(false);
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
					playerCombinaison.add(i, a);
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
	
	


	// show computerNumber entirely
	private void showNumber() {
		if (getVictory() == false) {
			System.out.println("\nFor each of the number(s), help the computer to find your combinaison with + , - and =");
		}
		for (int i = 0; i < computerNumber.size(); i += 0) {
			System.out.print(computerNumber.get(i));
					i++;
		}
	System.out.println();
	}


	// getting the answer of the player (+, - and =) and doing verification and confirmation on it if the answer(s) matches
	private void verificationOperationChoosedConfirmed(String b) { 
		int confirmationEveryNumberMatches = 0; // security for wrong input from player, check if player enter only +, - or =
		int confirmEveryEqualsAreEquals = 0; // if every numbers are fine, the computer number will change
		int confirmAllWork = 0; // end of the loop
		/* confirm that every +, - or = are correct(it's compare the symbol of player with
		playerCombinaison and computerNumber, if everything matches) */
		playerAnswer.clear(); // clear the playerAnswer array
			for(int i = 0; i < b.length(); i += 0) { // first analysis if every char are +, - or =
				if(b.charAt(i) == '+' || b.charAt(i) == '-' || b.charAt(i) == '=') {
					playerAnswer.add(i, Character.toString(b.charAt(i)));
					confirmationEveryNumberMatches++;
				}
				i++;
			}
			if(confirmationEveryNumberMatches == getCombinaisonNumberofNumber()) { //if only +, - or = are answered
				for (int i = 0; i < getCombinaisonNumberofNumber(); i += 0) {
					if(playerCombinaison.get(i).equals(computerNumber.get(i)) && playerAnswer.get(i).equals("=")) {
						confirmEveryEqualsAreEquals++;
					}
					if(playerCombinaison.get(i).compareTo(computerNumber.get(i)) > 0 && playerAnswer.get(i).equals("+")) {
						confirmEveryEqualsAreEquals++;
					}
					if(playerCombinaison.get(i).compareTo(computerNumber.get(i)) < 0  && playerAnswer.get(i).equals("-")) {
						confirmEveryEqualsAreEquals++;
					}
					i++;
				}
			}	
			else if(confirmationEveryNumberMatches != getCombinaisonNumberofNumber()) {
				System.out.println("Incorrect input! Your answer must be only with '+', '-' or '='");
				logger.info("Incorrect input from player, input != '+','-' or '='");
				setVerificationOperationChoosedConfirmed(false);
			}
			if(confirmEveryEqualsAreEquals == getCombinaisonNumberofNumber()) {// if +, - or = are correct
				for (int i = 0; i < getCombinaisonNumberofNumber(); i += 0) {
					if(playerCombinaison.get(i).equals(computerNumber.get(i)) && playerAnswer.get(i).equals("=")) {
						confirmAllWork++;
					}
					if(playerCombinaison.get(i).compareTo(computerNumber.get(i)) > 0 && playerAnswer.get(i).equals("+")) {
						algoMore(i);
						// computerNumber.set(i, computerNumber.get(i) + 1);
						confirmAllWork++;
					}
					if(playerCombinaison.get(i).compareTo(computerNumber.get(i)) < 0  && playerAnswer.get(i).equals("-")) {
						algoLess(i);
						// computerNumber.set(i, computerNumber.get(i) - 1);
						confirmAllWork++;
					}
					i++;
				}
			}
			if (confirmAllWork == getCombinaisonNumberofNumber()) {
				setVerificationOperationChoosedConfirmed(true);
			}
	}



	private void rdmroll() { // set computer number in the arraylist computerNumber depending of CombinaisonNumberofNumber parameter
		computerNumber.clear();
			for (int i = 0; i < getCombinaisonNumberofNumber(); i += 0) {
				computerNumber.add(i, (int)(0 + Math.random() * 9));		
				i++;
		}
	}
	


	//victory condition check up
	private void victoryTest() {
		int a = 0; // confirm if every number(s) matches
		int b = 0; // increment index +1 on playerCombinaison(b) = computerNumber(b)
		int c = 0; // increment index +1 on playerAnswer(c) = correct
		for(int i = 0; i < getCombinaisonNumberofNumber(); i += 0) {
			if (playerCombinaison.get(b).equals(computerNumber.get(b))) {
				b++; a++;
			}
			if (playerAnswer.get(c).equals("=")){
				c++;
			}
			i++;			
		}		
		if(a == getCombinaisonNumberofNumber() && c == getCombinaisonNumberofNumber()) {
			setVictory(true);
			System.out.println("The computer win this one!");	
		}
	}

	private void defeatComputer() { // when try = 0
		System.out.println("The computer has no more trie(s). You win this one!");
	}

	private void algoMore(int i) {	
		if(playerCombinaison.get(i) != null && computerNumber.get(i) != null) {
			int a = playerCombinaison.get(i).intValue();
			int b = computerNumber.get(i).intValue();
			if(a - b == 1 	|| a - b == 2) {
				computerNumber.set(i, computerNumber.get(i) + 1); // if 1 or 2 difference +1
			}
			if(a - b == 3	|| a - b == 4) {
				computerNumber.set(i, computerNumber.get(i) + 2); // if 3 or 4 difference +2
			}		
			if(a - b == 5	|| a - b == 6) {
				computerNumber.set(i, computerNumber.get(i) + 3); // if 5 or 6 difference +3
			}		
			if(a - b == 7 || a - b == 8 || a - b == 9) {
				computerNumber.set(i, computerNumber.get(i) + 4); // if 7, 8 or 9 difference +4
			}	
		}
	}	
	
	private void algoLess(int i) {	
		if(playerCombinaison.get(i) != null && computerNumber.get(i) != null) {
			int a = playerCombinaison.get(i).intValue();
			int b = computerNumber.get(i).intValue();
			if(b - a == 1 	|| b - a == 2) {
				computerNumber.set(i, computerNumber.get(i) - 1); // if 1 or 2 difference -1
			}
			if(b - a == 3	|| b - a == 4) {
				computerNumber.set(i, computerNumber.get(i) - 2); // if 3 or 4 difference -2
			}		
			if(b - a == 5	|| b - a == 6) {
				computerNumber.set(i, computerNumber.get(i) - 3); // if 5 or 6 difference -3
			}		
			if(b - a == 7 || b - a == 8 || b - a == 9) {
				computerNumber.set(i, computerNumber.get(i) - 4); // if 7, 8 or 9 difference -4
			}	
		}
	}	
}