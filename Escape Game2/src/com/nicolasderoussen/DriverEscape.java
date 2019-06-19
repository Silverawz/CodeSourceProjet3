package com.nicolasderoussen;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Nicolas DEROUSSEN
 * Version 3 (Huge update for infinite number of combinaison!)
 * -Challenger : done
 * -Defenseur : done
 * -Duel mode : done
 * -Dev mode : done
 */

public class DriverEscape {

private Mode mode;
private String numberofTry;
private String combinaisonNumber;
private String devMode;
private DocumentBuilder documentBuilder;
private boolean confirmValueXml;
private Document document;
final static Logger logger = LogManager.getLogger(DriverEscape.class);

	public Mode getMode() {
		return mode;
	}
	
	public void setMode(Mode mode) {
		this.mode = mode;
	}
	public boolean getConfirmValueXml() {
		return confirmValueXml;
	}
	
	public void setConfirmValueXml(boolean confirmValueXml) {
		this.confirmValueXml = confirmValueXml;
	}

	public Document getDocument() {
		return document;
	}
	
	public void setDocument(Document document) {
		this.document = document;
	}
	
	public String getNumberofTry() {
		return numberofTry;
	}
	
	public void setNumberofTry(String numberofTry) {
		this.numberofTry = numberofTry;
	}
	
	public String getCombinaisonNumber() {
		return combinaisonNumber;
	}
	
	public void setCombinaisonNumber(String combinaisonNumber) {
		this.combinaisonNumber = combinaisonNumber;
	}
	
	public String getDevMode() {
		return devMode;
	}
	
	public void setDevMode(String devMode) {
		this.devMode = devMode;
	}
	
	public DocumentBuilder getDocumentBuilder() {
		return documentBuilder;
	}
	
	public void setDocumentBuilder(DocumentBuilder documentBuilder) {
		this.documentBuilder = documentBuilder;
	}
	
	private void xmlFile(){
		InputStream fileExists = getClass().getResourceAsStream("Config_Escapegame.xml"); //verification if XML file exist
		if(fileExists == null) { // xml not found
			logger.info("File xml == null");
			setConfirmValueXml(false);
		}
		else { // xml found
			logger.info("File xml loaded");
			setConfirmValueXml(true);
		}
		
		if(getConfirmValueXml()) {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			try {
				setDocumentBuilder(documentBuilderFactory.newDocumentBuilder());
				logger.info("DocumentBuilder complete");
			} catch (ParserConfigurationException e) {			
				e.printStackTrace();
				logger.error("Parsing exception" + e.getMessage());
			}
		
			
			try {
				setDocument(getDocumentBuilder().parse(getClass().getResourceAsStream("Config_Escapegame.xml")));
				logger.info("Parsing of the XML file complete");
			} catch (SAXException e) {
				e.printStackTrace();
				logger.error("XML is invalid + " + e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("Input/Output Exception" + e.getMessage());
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				logger.error("InputStream cannot be null" + e.getMessage());
			}
		
			NodeList list = getDocument().getElementsByTagName("escapegame");
				
			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
			
				if (node.getNodeType() == Node.ELEMENT_NODE) {			
					Element element = (Element) node;
					setNumberofTry(element.getElementsByTagName("NumberOfTry").item(0).getTextContent());
					setCombinaisonNumber(element.getElementsByTagName("CombinaisonNumber").item(0).getTextContent());
					setDevMode(element.getElementsByTagName("Devmode").item(0).getTextContent());
				}
			}	
		}
	}
	
	private void start() {
		Instruction instruction = new Instruction(0);
		if(getConfirmValueXml()) {
			setMode(new Mode(getCombinaisonNumber(), getNumberofTry(), getDevMode())); 
		}
		else if (!getConfirmValueXml()) {
			setMode(new Mode()); 
		}
		
		instruction.getInstructionIntro();
		
		try {
			Thread.sleep(150);
			logger.info("Sleep program 0.15sec");
		} catch (InterruptedException e) {
			System.err.println(e.getMessage().toString());
			logger.error("Sleep program 0.15sec +" + e.getMessage());
		}
		
		instruction.getInstructionChoice();
		getMode().getChoiceGameMode();
		getMode().getScanClosed();	
	}
	
	public static void main(String[] args) {
		DriverEscape driverEscape = new DriverEscape();
		try {
			driverEscape.xmlFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		driverEscape.start();
	}
	
}