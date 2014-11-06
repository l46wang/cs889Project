package cs889.gui.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.BestFirst;
import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.FilteredAttributeEval;
import weka.attributeSelection.GainRatioAttributeEval;
import weka.attributeSelection.GreedyStepwise;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.attributeSelection.ReliefFAttributeEval;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.unsupervised.attribute.Reorder;
import cs889.algorithm.SSD;
import cs889.gui.utility.FeatureSelection;
import cs889.gui.utility.FeatureSelectionUtil;
import weka.filters.Filter;

public class FeatureSetRebuild {
	
	/**
	 * Reorder the initial instances in the given order
	 * @param org
	 * @param order
	 * @return reordered instances
	 * @throws Exception 
	 */
	public static Instances featureReordering(Instances org, ArrayList<Integer> order) throws Exception{
		  Reorder r = new Reorder();
		  String range = "";
		  for (int i : order) {
		    range += (i+1) +",";
		  }
		  range = range+"last";
		  r.setAttributeIndices(range);
		  r.setInputFormat(org);
		  return Filter.useFilter(org, r);
	} 
	
	/**
	 * Save the given instance in a file. 
	 * @param inst
	 * @throws IOException 
	 */
	public static void saveInstancesInFiles(Instances inst, int categrory) throws IOException{
		String fileDesitination = "";
		switch(categrory){
			case FeatureSelection.USER:
				fileDesitination = FeatureSelection.USER_SELECT_DES;
				break;
			case FeatureSelection.A1:
				fileDesitination = FeatureSelection.A1_DES;
				break;
			case FeatureSelection.A2:
				fileDesitination = FeatureSelection.A2_DES;
				break;
			default:
				fileDesitination = FeatureSelection.Test_DES;
				break;
		}
		 BufferedWriter writer = new BufferedWriter(new FileWriter(fileDesitination));
		 writer.write(inst.toString());
		 writer.flush();
		 writer.close();
	}
	
	/**
	 * Algorithm 1 is information gain attribute selection. 
	 * @param inst
	 * @throws Exception
	 */
	public static void reorderFeaturesA1(Instances inst) throws Exception{
		/* Load the iris data set */

		AttributeSelection filter = new AttributeSelection();  // package weka.filters.supervised.attribute!
	 	InfoGainAttributeEval eval = new InfoGainAttributeEval();
	 	Ranker search = new Ranker();
	 	filter.setEvaluator(eval);
	 	filter.setSearch(search);
	 	filter.setInputFormat(inst);
	 	// generate new data
	 	Instances newData = Filter.useFilter(inst, filter);
	 	saveInstancesInFiles(newData, FeatureSelection.A1);
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	public static void reorderUserSelectFeatures() throws Exception{
		Instances userSelection = SSD.generateUserSelection();
		saveInstancesInFiles(userSelection, FeatureSelection.USER);
	}
	
	
	/**
	 * Algorithm 2 is information gain attribute selection. 
	 * @param inst
	 * @throws Exception
	 */
	public static void reorderFeaturesA2(Instances inst) throws Exception{
		/* Load the iris data set */

		AttributeSelection filter = new AttributeSelection();  // package weka.filters.supervised.attribute!
		ReliefFAttributeEval  eval = new ReliefFAttributeEval();
		Ranker search = new Ranker ();
		filter.setEvaluator(eval);
	 	filter.setSearch(search);
	 	filter.setInputFormat(inst);
	 	// generate new data
	 	Instances newData = Filter.useFilter(inst, filter);
	 	saveInstancesInFiles(newData, FeatureSelection.A2);
	}
	
	public static void main(String[] args) throws Exception{
		Instances inst = FeatureSelectionUtil.createInstances();
		FeatureSetRebuild.saveInstancesInFiles(inst, 5);
		
		FeatureSetRebuild.reorderFeaturesA1(inst);
		FeatureSetRebuild.reorderFeaturesA2(inst);
		
		ArrayList<Integer> temp = new ArrayList<Integer>();
		temp.add(1);
		temp.add(2);
		temp.add(3);
		temp.add(4);
		temp.add(5);
		temp.add(6);
		temp.add(7);
		temp.add(8);
		temp.add(0);
		temp.add(9);
		Instances ins = FeatureSetRebuild.featureReordering(inst, temp);
		for(int i = 0; i<ins.numAttributes(); i++){
			System.out.println(inst.attribute(i).name()+" vs "+ins.attribute(i).name());
		}
		
	}
	

}
