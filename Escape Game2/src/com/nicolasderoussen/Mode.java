package com.nicolasderoussen;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Mode {

final private char REPLAY_ACTUAL_GAME_MODE = '1', GO_TO_MAIN_MENU = '2';
final private char CHALLENGER = '1', DEFENSEUR = '2', DUEL = '3', EXIT = '4';
private Challenger challenger; private Defenseur defenseur; private Instruction instruction; 
private Scanner scanner;
private int tryNumber; // number of try
private int modeDeJeuActuel; // game mode (1 = challenger, 2 = defenseur, 3 = duel)
private boolean activationEssai = false; // if try are enable = true, if not = false
private boolean onlyOnce = true; // for third mode (duel) not repeating the first explanation
private int devModeActivated = 0; // 0 = off, 1 = on
private int combinaisonNumber; // setting with xml
final static Logger logger = LogManager.getLogger(Mode.class);

	public int getDevModeActivated() {
		return devModeActivated;
	}

	public void setDevModeActivated(int devModeActivated) {
		this.devModeActivated = devModeActivated;
	}

	public boolean getOnlyOnce() {
		return onlyOnce;
	}

	public void setOnlyOnce(boolean onlyOnce) {
		this.onlyOnce = onlyOnce;
	}

	public boolean getActivationEssai() {
		return activationEssai;
	}

	public void setActivationEssai(boolean activationEssai) {
		this.activationEssai = activationEssai;
	}

	public int getModeDeJeuActuel() {
		return modeDeJeuActuel;
	}

	public void setModeDeJeuActuel(int modeDeJeuActuel) {
		this.modeDeJeuActuel = modeDeJeuActuel;
	}

	public void getScanClosed() {
		scanClosed();
	}

	public void getChoiceGameMode() {
		choiceGameMode();
	}

	public int getCombinaisonNumber() {
		return combinaisonNumber;
	}

	public void setCombinaisonNumber(int combinaisonNumber) {
		this.combinaisonNumber = combinaisonNumber;
	}


	//constructor without parameter = infinite try
	public Mode() {
		setActivationEssai(false);
		setCombinaisonNumber(4); // default mode : combinaison size = 4
		System.out.println("File XML not found or invalid, generating default configuration : Combinaison size = 4, Number of try = unlimited");
	}

	//constructor with parameter take from xml file
	public Mode(String combinaisonNumber, String numberOfTry, String devMode) {
		if(!(combinaisonNumber.isEmpty() || numberOfTry.isEmpty() || devMode.isEmpty())) {
			if(isNumeric(combinaisonNumber) && isNumeric(numberOfTry) && isNumeric(devMode)) {
				int combinaisonNumberInt = Integer.parseInt(combinaisonNumber);
				int numberOfTryInt = Integer.parseInt(numberOfTry);
				int devModeInt = Integer.parseInt(devMode);
				if (combinaisonNumberInt == 0 || combinaisonNumberInt < 0) {
					setCombinaisonNumber(4); //  combinaison size = 4
				}

				else if (combinaisonNumberInt != 0 && combinaisonNumberInt > 0) {
					setCombinaisonNumber(combinaisonNumberInt); //  combinaison size = a
				}

				if (numberOfTryInt != 0 && numberOfTryInt > 0) {
					setActivationEssai(true); // try enable
					setTryNumber(numberOfTryInt); // try number = b
				}

				else if (numberOfTryInt == 0 || numberOfTryInt< 0) { // try disable
					setActivationEssai(false);
				}

				if (devModeInt == 1) { // dev mode true
					setDevModeActivated(1);
				}

				else if (devModeInt == 0) { // dev mode false
					setDevModeActivated(0);
				}		
			}
			else {
				System.out.println("Xml file error(s), default mode activated.");
				logger.info("One parameter is incorrect, loading default mode");
				new Mode();
			}		
		}
		else {
			new Mode();
		}
	}

	private static boolean isNumeric(String str) { 
		  try {  
		    Double.parseDouble(str);  
		    return true;
		  } catch(NumberFormatException e){ 
			logger.info("The field " + str + " is not numeric");
		    return false;  
		  }  
		}
	
	public int getTryNumber() {
		return tryNumber;
	}

	public void setTryNumber(int tryNumber) {
		this.tryNumber = tryNumber;
	}

	//replay or back to main menu
	private void choiceReplayOrMainMenu() {
		scanner = new Scanner(System.in);
		for (int i = 0; i != 1; i += 0) {
			char answerPlayer = scanner.next().charAt(0);
			if (answerPlayer == REPLAY_ACTUAL_GAME_MODE) {
				functionRememberMode(getModeDeJeuActuel());
				i++;
			}
			else if (answerPlayer == GO_TO_MAIN_MENU){
				instruction.getInstructionChoice();
				choiceGameMode();
				i++;
			}
			else { // wrong input
				instruction.getInstructionRejouer();
				logger.info("Input is not 1 or 2");
			}
		}
	}

	//game mode choice
	private void choiceGameMode() {
		scanner = new Scanner(System.in);
		defenseur = new Defenseur(getTryNumber(), getCombinaisonNumber());
		challenger = new Challenger(getTryNumber(), getCombinaisonNumber());
		instruction = new Instruction(getCombinaisonNumber()); 
		for(int i = 0; i != 1; i += 0) {
			char chiffreFinal = scanner.next().charAt(0);
			if (chiffreFinal == CHALLENGER){
				instruction.getInstructionChallenger();
				i++;
				challengerMode();
			}
			else if (chiffreFinal == DEFENSEUR){
				instruction.getInstructionDefenseur();
				i++;
				defenseurMode();	
			}
			else if (chiffreFinal == DUEL){
				instruction.getInstructionDuel();
				i++;
				duelModeStart();
				duelMode();
			}
			else if (chiffreFinal == EXIT) {
				instruction.getInstructionEnd();
				scanClosed();
				break;
			}
			else{
				instruction.getInstructionChoice();
			}
		}
	}



	//mode defenseur
	private void defenseurMode() {
		scanner = new Scanner(System.in);
		boolean a = false; // infinite loop for verification of the input
		while(a == false) { // stay false until the player enter a proper input
			String codePlayer = scanner.nextLine(); // taking the player input
			defenseur.setNumberChoosedByPlayer(codePlayer);  // send the player input
			a = defenseur.getVerificationNumberChoosedConfirmed(); // change to true if the player input is correct, else repeat
		}
		defenseur.getRdmroll(); // taking the computer number
		setModeDeJeuActuel(2); // memorizing game (1 = challenger, 2 = defenseur, 3 = duel)
		int nombreEssaiStockage = getTryNumber();
		while(defenseur.getVictory() == false) { // untill victory != true
			if (getActivationEssai() == true) { // if activation try = true
				if (nombreEssaiStockage > 0) { // if atleast 1 try is left
					System.out.println("\\nNumber(s) of trie(s) remaining " + nombreEssaiStockage); // show the remaining tries count
					nombreEssaiStockage -= 1; // remove 1 try
					defenseurSettingLoopGame(); // launch main loop for this mode
				}
				else {
					defenseur.setVictory(true); // end the loop in case of no more try
					defenseur.getDefeatComputer(); // show message defeat
				}
			}
			else {
				defenseurSettingLoopGame(); // if no try parameter then launch main loop for this mode
			}
		}
		defenseur.setVictory(false); // reset for futur game
		instruction.getInstructionRejouer(); // ask player to replay or go main menu
		choiceReplayOrMainMenu(); // choice between replay or main menu
	}

	//mode challenger
	private void challengerMode() {
		challenger.getRdmRoll(); // taking the computer number
		setModeDeJeuActuel(1); // memorizing game (1 = challenger, 2 = defenseur, 3 = duel)
		int nombreEssaiStockage = getTryNumber();
		while(challenger.getVictory() == false) { // untill victory != true
			if (getActivationEssai() == true) {
				if (nombreEssaiStockage > 0) {
					System.out.println("\nNumber(s) of trie(s) remaining " + nombreEssaiStockage); // show the remaining tries count
					nombreEssaiStockage -= 1; // remove 1 try
					challengerSettingLoopGame();
				}
				else {
					challenger.setVictory(true);
					challenger.getDefeatPlayer();
				}
			}
			else {
				challengerSettingLoopGame();
			}
		}
		challenger.setVictory(false);
		instruction.getInstructionRejouer(); // ask player to replay or go main menu
		choiceReplayOrMainMenu(); // choice between replay or main menu
	}


	//mode duel
	private void duelMode() {
		setModeDeJeuActuel(3); // memorizing game mode (1 = challenger, 2 = defenseur, 3 = duel)
		int nombreEssaiStockage = getTryNumber();
		int breakIfNoMoreTries = 0; // break the loop if no tries
		while(challenger.getVictory() == false && defenseur.getWin() == false && breakIfNoMoreTries == 0) {
			if (getActivationEssai() == true) { // if try number have been parameter
				if(nombreEssaiStockage > 0) {
					System.out.println("Number(s) of trie(s) remaining " + nombreEssaiStockage); // show remaining tries number
					nombreEssaiStockage -= 1; // remove 1 try
					firstPartOfDuel(); // start part 1 duel
					if (challenger.getVictory() == false && defenseur.getWin() == false) {
						secondPartOfDuel(); // start part 2 duel
					}
				}
			}
			else if (getActivationEssai() == false){
				firstPartOfDuel(); // start part 1 duel
				if (challenger.getVictory() == false && defenseur.getWin() == false) {
					secondPartOfDuel(); // start part 2 duel
				}
			}
			//if try number = 0 and no one win
			if (getActivationEssai() == true && nombreEssaiStockage == 0 && challenger.getVictory() == false && defenseur.getWin() == false){
				breakIfNoMoreTries = 1; // break the while loop
				System.out.println("No more trie(s) available. Equality!");
			}
		}
		challenger.setVictory(false);
		defenseur.setVictory(false);
		instruction.getInstructionRejouer(); // ask for replay or go main menu
		choiceReplayOrMainMenu(); // choice between replay or main menu
	}





	// initialisation duel mode
	private void duelModeStart() {
		scanner = new Scanner(System.in);
		String combinaisonJoueur = scanner.nextLine();
		defenseur.setNumberChoosedByPlayer(combinaisonJoueur);
		challenger.getRdmRoll(); // taking defensive number for computer
		defenseur.getRdmroll(); // taking offensive number for computer
		setOnlyOnce(true); // if first attempt, ask to write the number, only once
	}

	//***1
	//Duel part 1
	private void firstPartOfDuel() {
		scanner = new Scanner(System.in);
		challenger.getInitialisation(getDevModeActivated()); // check dev mode
		if (getOnlyOnce() == false) { // show the last try if not first attempt
			System.out.println("Your last tentative was : ");
			challenger.getNumberChoosed();
			System.out.println();
			challenger.getComputerAnswer();
			System.out.println();
		}
		else if (getOnlyOnce() == true) { // if first attempt, ask to write the number, only once
			System.out.println("You are first to play! Write " + getCombinaisonNumber() + " number(s) for your first attempt");
			setOnlyOnce(false);
		}
		boolean a = false; // infinite loop to check the player input
		while(a == false) { // stay false until player input is correct
			String string = scanner.nextLine(); // taking player input
			challenger.setNumberChoosed(string); // sending player input
			a = challenger.getVerificationNumberChoosedConfirmed(); // change a = true if the player input is correct
		}
		challenger.getCompareAll(); // compare number between computer and player
		challenger.getResult(); // show result
		challenger.getVictoryTest(); // victory condition verification	
	}

	//***2
	//Duel part 2
	private void secondPartOfDuel() {
		scanner = new Scanner(System.in);
		boolean a = false; // infinite loop to check the player input
		while(a == false) { // stay false until player input is correct
			defenseur.getShowNumber(); // show computer number proposal
			String operationPlayer = scanner.nextLine(); // taking player input (+,- or =)
			defenseur.setOperationPlayer(operationPlayer); // sending player input (+,- or =)
			a = defenseur.getVerificationOperationChoosedConfirmed(); // change a = true if the player input is correct
		}
		defenseur.getVictoryTest(); // victory condition verification
	}

	//main instruction for challenger mode
	private void challengerSettingLoopGame() {
		scanner = new Scanner(System.in);
		challenger.getInitialisation(getDevModeActivated()); // dev mode if enable
		boolean a = false; // infinite loop for input verification
		while(a == false) { // stay false untill input is correct
			String numberOfPlayer = scanner.nextLine(); // taking player input
			challenger.setNumberChoosed(numberOfPlayer); // sending player input
			a = challenger.getVerificationNumberChoosedConfirmed(); // change to true if player input is correct
		}
		challenger.getCompareAll(); // compare number between computer and player
		challenger.getResult(); // show result
		challenger.getVictoryTest(); // victory condition verification
	}

	//***1
	//main instruction for defenseur mode
	private void defenseurSettingLoopGame() {
		scanner = new Scanner(System.in);
		defenseur.setVerificationOperationChoosedConfirmed(false);
		boolean a = false;  // infinite loop for input verification
		while(a == false) { // stay false untill input is correct
			defenseur.getShowNumber();
			String operationPlayer = scanner.nextLine(); // taking player input (-, + or =)
			defenseur.setOperationPlayer(operationPlayer); // sending player input (-, + or =)
			a = defenseur.getVerificationOperationChoosedConfirmed(); // change to true if player input is correct
		}
		defenseur.getVictoryTest(); // victory condition verification
	}


	//parameter of replay (1 = challenger, 2 = defenseur, 3 = duel)
	private void functionRememberMode(int a) {
		if(a == 1) {
			instruction.getInstructionChallenger();
			challengerMode();
		}
		else if(a == 2) {
			instruction.getInstructionDefenseur();
			defenseurMode();
		}
		else if(a == 3) {
			instruction.getInstructionDuel();
			duelModeStart();
			duelMode();
		}
	}


	//close all scanner
	private void scanClosed() {
		if (scanner != null) {
			scanner.close();	
		}
	}
}