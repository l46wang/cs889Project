package cs889.gui.utility;

import org.apache.log4j.Logger;

import cs889.gui.interactiveFeatureSelection.Algorithm1AttributeSelectionPanel;
import cs889.gui.interactiveFeatureSelection.Algorithm2AttributeSelectionPanel;
import cs889.gui.interactiveFeatureSelection.Algorithm3AttributeSelectionPanel;
import cs889.gui.interactiveFeatureSelection.AttributeSelectionPanel;

public interface FeatureSelection {
	
	public static final String fileName = "./data/census_income.arff";
	public static final String a1FileName = "F:/Study/Steve/Master/CS 889/project/finalVersion/src/main/java/cs889/InputFiles/census_income.arff";
	public static final String a2FileName = "F:/Study/Steve/Master/CS 889/project/finalVersion/src/main/java/cs889/InputFiles/census_income.arff";
	public static final String a3FileName = "F:/Study/Steve/Master/CS 889/project/finalVersion/src/main/java/cs889/InputFiles/census_income.arff";
	
	//Define the file destinations
	public static final String USER_SELECT_DES = "./data/Usercensus_income.arff";
	public static final String A1_DES = "./data/A1census_income.arff";
	public static final String A2_DES = "./data/A2census_income.arff";
	public static final String A3_DES = "./data/A3census_income.arff";
	public static final String Test_DES = "./data/Testcensus_income.arff";
	
	//Define categories
	public static final int USER = 0;
	public static final int A1 = 1;
	public static final int A2 = 2;
	public static final int A3 = 3;
	
	//Define the Logger file
	public static Logger log4jUser = Logger.getLogger(AttributeSelectionPanel.class.getName());
	public static Logger log4jA1 = Logger.getLogger(Algorithm1AttributeSelectionPanel.class.getName());
	public static Logger log4jA2 = Logger.getLogger(Algorithm2AttributeSelectionPanel.class.getName());
	public static Logger log4jA3 = Logger.getLogger(Algorithm3AttributeSelectionPanel.class.getName());
	
	public static Logger log4j = Logger.getLogger(FeatureSelection.class.getName());

}
