package cs889.gui.test;

import org.apache.log4j.Logger;

import cs889.gui.interactiveFeatureSelection.Algorithm2AttributeSelectionPanel;

public class Log4jTesting {
	public static void main(String[] args){
		Logger log = Logger.getLogger(Log4jTesting.class.getName());
		log.debug("Test");
	}
}
