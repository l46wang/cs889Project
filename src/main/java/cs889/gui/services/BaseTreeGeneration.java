package cs889.gui.services;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.gui.treevisualizer.PlaceNode2;
import weka.gui.treevisualizer.TreeVisualizer;
import cs889.gui.interactiveFeatureSelection.PreselectionPanel;
import cs889.gui.utility.FeatureSelection;
import cs889.gui.utility.FeatureSelectionLog;
import cs889.gui.utility.FeatureSelectionUtil;

public class BaseTreeGeneration {
	public static void generateTreeA2() throws Exception{
		J48 cls = new J48();
	    Instances ins = PreselectionPanel.a2SelectedInstances;
		ins.setClassIndex(ins.numAttributes() - 1);
		cls.setBinarySplits(true);
		cls.setMinNumObj(10);
	     cls.buildClassifier(ins);
	     
	     FeatureSelection.log4jA2.debug("Activity: view Tree");
	     FeatureSelectionLog.logResult(ins, FeatureSelection.log4jA2);
	     
	     // display classifier
	     final javax.swing.JFrame jf = 
	       new javax.swing.JFrame("Weka Classifier Tree Visualizer: J48");
	     jf.setSize(500,400);
	     jf.getContentPane().setLayout(new BorderLayout());
	     TreeVisualizer tv = new TreeVisualizer(null,
	         cls.graph(),
	         new PlaceNode2());
	     jf.getContentPane().add(tv, BorderLayout.CENTER);
	     jf.addWindowListener(new java.awt.event.WindowAdapter() {
	       public void windowClosing(java.awt.event.WindowEvent e) {
	         jf.dispose();
	       }
	     });
	 
	     jf.setVisible(true);
	     tv.fitToScreen();
	}
	
	public static void generateTreeA3() throws Exception{
		J48 cls = new J48();
	    Instances ins = PreselectionPanel.a3SelectedInstances;
	 
		ins.setClassIndex(ins.numAttributes() - 1);
		cls.setBinarySplits(true);
		cls.setMinNumObj(10);
	     cls.buildClassifier(ins);
	 
	     FeatureSelection.log4jA3.debug("Activity: view Tree");
	     FeatureSelectionLog.logResult(ins, FeatureSelection.log4jA3);
	     
	     
	     
	     // display classifier
	     final javax.swing.JFrame jf = 
	       new javax.swing.JFrame("Weka Classifier Tree Visualizer: J48");
	     jf.setSize(500,400);
	     jf.getContentPane().setLayout(new BorderLayout());
	     TreeVisualizer tv = new TreeVisualizer(null,
	         cls.graph(),
	         new PlaceNode2());
	     jf.getContentPane().add(tv, BorderLayout.CENTER);
	     jf.addWindowListener(new java.awt.event.WindowAdapter() {
	       public void windowClosing(java.awt.event.WindowEvent e) {
	         jf.dispose();
	       }
	     });
	 
	     jf.setVisible(true);
	     tv.fitToScreen();
	}
	
	public static void generateTreeA1() throws Exception{
		J48 cls = new J48();
	    Instances ins = PreselectionPanel.a1SelectedInstances;
	    System.out.println(ins.numAttributes());
	    for(int i = 0; i<ins.numAttributes(); i++){
	    	System.out.println(ins.attribute(i).name());
	    }
		ins.setClassIndex(ins.numAttributes() - 1);
		cls.setBinarySplits(true);
		cls.setMinNumObj(10);
	     cls.buildClassifier(ins);
	 
	     FeatureSelection.log4jA1.debug("Activity: view Tree");
	     FeatureSelectionLog.logResult(ins, FeatureSelection.log4jA1);
	     
	     // display classifier
	     final javax.swing.JFrame jf = 
	       new javax.swing.JFrame("Weka Classifier Tree Visualizer: J48");
	     jf.setSize(500,400);
	     jf.getContentPane().setLayout(new BorderLayout());
	     TreeVisualizer tv = new TreeVisualizer(null,
	         cls.graph(),
	         new PlaceNode2());
	     jf.getContentPane().add(tv, BorderLayout.CENTER);
	     jf.addWindowListener(new java.awt.event.WindowAdapter() {
	       public void windowClosing(java.awt.event.WindowEvent e) {
	         jf.dispose();
	       }
	     });
	 
	     jf.setVisible(true);
	     tv.fitToScreen();
	}
	

	
	public static void generateTree() throws Exception{
		J48 cls = new J48();
		String[] options = {"-L", "True"};
		cls.setOptions(options);
	    Instances ins = PreselectionPanel.selectedInstances; 
	     FeatureSelection.log4jUser.debug("Activity: view Tree");
	     FeatureSelectionLog.logResult(ins, FeatureSelection.log4jUser);
	    
		ins.setClassIndex(ins.numAttributes() - 1);
		cls.setBinarySplits(true);
		cls.setMinNumObj(10);
	     cls.buildClassifier(ins);
	     
	     System.out.println("====================================");
	     System.out.println(cls.toString());
	     System.out.println("====================================");
	     // display classifier
	     final javax.swing.JFrame jf = 
	       new javax.swing.JFrame("Weka Classifier Tree Visualizer: J48");
	     jf.setSize(500,400);
	     jf.getContentPane().setLayout(new BorderLayout());
	     System.out.println(cls.graph());
	     
	     System.out.println("-----------------");
	     
//	     System.out.println(PlaceNode2());
	     TreeVisualizer tv = new TreeVisualizer(null,
	         cls.graph(),
	         new PlaceNode2());
	     jf.getContentPane().add(tv, BorderLayout.CENTER);
	     jf.addWindowListener(new java.awt.event.WindowAdapter() {
	       public void windowClosing(java.awt.event.WindowEvent e) {
	         jf.dispose();
	       }
	     });
	 
	     jf.setVisible(true);
	     tv.fitToScreen();
	     
	}
	
	
}
