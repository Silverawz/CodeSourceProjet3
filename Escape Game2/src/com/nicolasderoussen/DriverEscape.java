package com.nicolasderoussen;

import java.io.IOException;

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

private String numberofTry;
private String combinaisonNumber;
private String devMode;
private DocumentBuilder documentBuilder;
private Document document;
final static Logger logger = LogManager.getLogger(DriverEscape.class);

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
	
	private void start() {
		Instruction instruction = new Instruction(0);
		Mode mode = new Mode(getCombinaisonNumber(), getNumberofTry(), getDevMode()); 
		instruction.getInstructionIntro();
		try {
			Thread.sleep(150);
			logger.info("Sleep for 0.15 sec is working");
		} catch (InterruptedException e) {
			System.err.println(e.getMessage().toString());
			logger.error("Sleep for 0.15 sec is not working +" + e.getMessage());
		}
		instruction.getInstructionChoice();
		mode.getChoiceGameMode();
		mode.getScanClosed();	
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