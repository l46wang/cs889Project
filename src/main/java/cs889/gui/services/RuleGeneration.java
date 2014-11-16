package cs889.gui.services;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import weka.classifiers.trees.J48;
import weka.core.Instances;
import cs889.gui.interactiveFeatureSelection.PreselectionPanel;
import cs889.gui.utility.FeatureSelection;
import cs889.gui.utility.FeatureSelectionLog;

public class RuleGeneration {
	public static void createRules() throws Exception{
		J48 cls = new J48();
		String[] options = {"-L", "True"};
		cls.setOptions(options);
	    Instances ins = PreselectionPanel.selectedInstances; 
	     FeatureSelection.log4jUser.debug("Activity: view Rules");
	     FeatureSelectionLog.logResult(ins, FeatureSelection.log4jUser);
	    
		ins.setClassIndex(ins.numAttributes() - 1);
		cls.setBinarySplits(true);
		cls.setMinNumObj(10);
	    cls.buildClassifier(ins);
	    /** The output area for classification results */
	     JTextArea m_OutText = new JTextArea(20, 40);
	     m_OutText.setEditable(false);
	     m_OutText.setText(cls.toString());
	     JScrollPane js = new JScrollPane(m_OutText);
	     
	     final javax.swing.JFrame jf1 = 
	  	       new javax.swing.JFrame("Weka Classifier Rules: J48");
	  	     jf1.setSize(500,400);
	  	     jf1.getContentPane().setLayout(new BorderLayout());
	  	   jf1.addWindowListener(new java.awt.event.WindowAdapter() {
		       public void windowClosing(java.awt.event.WindowEvent e) {
		         jf1.dispose();
		       }
		     });
	  	 jf1.getContentPane().add(js, BorderLayout.CENTER);
	  	jf1.setVisible(true);
		
	}
	
	
	public static void createRulesA1() throws Exception{
		J48 cls = new J48();
		String[] options = {"-L", "True"};
		cls.setOptions(options);
	    Instances ins = PreselectionPanel.a1SelectedInstances; 
	     FeatureSelection.log4jA1.debug("Activity: view Rules");
	     FeatureSelectionLog.logResult(ins, FeatureSelection.log4jA1);
	    
		ins.setClassIndex(ins.numAttributes() - 1);
		cls.setBinarySplits(true);
		cls.setMinNumObj(10);
	    cls.buildClassifier(ins);
	    /** The output area for classification results */
	     JTextArea m_OutText = new JTextArea(20, 40);
	     m_OutText.setEditable(false);
	     m_OutText.setText(cls.toString());
	     JScrollPane js = new JScrollPane(m_OutText);
	     
	     final javax.swing.JFrame jf1 = 
	  	       new javax.swing.JFrame("Weka Classifier Rules: J48");
	  	     jf1.setSize(500,400);
	  	     jf1.getContentPane().setLayout(new BorderLayout());
	  	   jf1.addWindowListener(new java.awt.event.WindowAdapter() {
		       public void windowClosing(java.awt.event.WindowEvent e) {
		         jf1.dispose();
		       }
		     });
	  	 jf1.getContentPane().add(js, BorderLayout.CENTER);
	  	jf1.setVisible(true);
		
	}
	
	
	public static void createRulesA2() throws Exception{
		J48 cls = new J48();
		String[] options = {"-L", "True"};
		cls.setOptions(options);
	    Instances ins = PreselectionPanel.a2SelectedInstances; 
	     FeatureSelection.log4jA2.debug("Activity: view Rules");
	     FeatureSelectionLog.logResult(ins, FeatureSelection.log4jA2);
	    
		ins.setClassIndex(ins.numAttributes() - 1);
		cls.setBinarySplits(true);
		cls.setMinNumObj(10);
	    cls.buildClassifier(ins);
	    /** The output area for classification results */
	     JTextArea m_OutText = new JTextArea(20, 40);
	     m_OutText.setEditable(false);
	     m_OutText.setText(cls.toString());
	     JScrollPane js = new JScrollPane(m_OutText);
	     
	     final javax.swing.JFrame jf1 = 
	  	       new javax.swing.JFrame("Weka Classifier Rules: J48");
	  	     jf1.setSize(500,400);
	  	     jf1.getContentPane().setLayout(new BorderLayout());
	  	   jf1.addWindowListener(new java.awt.event.WindowAdapter() {
		       public void windowClosing(java.awt.event.WindowEvent e) {
		         jf1.dispose();
		       }
		     });
	  	 jf1.getContentPane().add(js, BorderLayout.CENTER);
	  	jf1.setVisible(true);
		
	}
	
	public static void createRulesA3() throws Exception{
		J48 cls = new J48();
		String[] options = {"-L", "True"};
		cls.setOptions(options);
	    Instances ins = PreselectionPanel.a3SelectedInstances; 
	     FeatureSelection.log4jA3.debug("Activity: view Rules");
	     FeatureSelectionLog.logResult(ins, FeatureSelection.log4jA3);
	    
		ins.setClassIndex(ins.numAttributes() - 1);
		cls.setBinarySplits(true);
		cls.setMinNumObj(10);
	    cls.buildClassifier(ins);
	    /** The output area for classification results */
	     JTextArea m_OutText = new JTextArea(20, 40);
	     m_OutText.setEditable(false);
	     m_OutText.setText(cls.toString());
	     JScrollPane js = new JScrollPane(m_OutText);
	     
	     final javax.swing.JFrame jf1 = 
	  	       new javax.swing.JFrame("Weka Classifier Rules: J48");
	  	     jf1.setSize(500,400);
	  	     jf1.getContentPane().setLayout(new BorderLayout());
	  	   jf1.addWindowListener(new java.awt.event.WindowAdapter() {
		       public void windowClosing(java.awt.event.WindowEvent e) {
		         jf1.dispose();
		       }
		     });
	  	 jf1.getContentPane().add(js, BorderLayout.CENTER);
	  	jf1.setVisible(true);
		
	}
}
