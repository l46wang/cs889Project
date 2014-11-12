package cs889.gui.utility;

import java.util.Random;

import org.apache.log4j.Logger;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;

public class FeatureSelectionLog {
	
	private static String featureSetMsgStart = "The current selected features are: ";
	private static String resultMsgStart = "The results are: ";
	
	
	public static void logResult(Instances experimentedInstances, Logger log4j) throws Exception{
		addExpLogFeatureSet(experimentedInstances, log4j);
		addExpLogResults(experimentedInstances, log4j);
	}
	
	/**
	 * Log the feature set being selected. 
	 * @param experimentedInstances
	 */
	private static void addExpLogFeatureSet(Instances experimentedInstances, Logger log4j){
		//log the selected feature information.
		String curSelectedFeatures = featureSetMsgStart;
		String featureNames = "";
		for(int i = 0; i<experimentedInstances.numAttributes();i++){
			featureNames = featureNames+experimentedInstances.attribute(i).name()+",";
		}
		curSelectedFeatures = curSelectedFeatures+featureNames.substring(0,featureNames.length()-1);
		log4j.debug(curSelectedFeatures);
	}
	
	/**
	 * Log the selected feature set results. 
	 * @param experimentedInstances
	 * @throws Exception 
	 */
	private static void addExpLogResults(Instances experimentedInstances, Logger log4j) throws Exception{
		log4j.debug(FeatureSelectionLog.getEvalOutput(experimentedInstances));
	}
	
	
	/*
	 * 
	 */
	private static String getEvalOutput(Instances ins) throws Exception{
		J48 cls = new J48();
	    //Instances ins = new Instances(new BufferedReader(new FileReader(FeatureSelection.fileName))); 
		ins.setClassIndex(ins.numAttributes() - 1);
	    cls.buildClassifier(ins);
		
	    Evaluation eval = new Evaluation(ins);
	    Random rand = new Random(1);  // using seed = 1
	    
	    eval.crossValidateModel(cls, ins, FeatureSelectionUtil.FOLDS, rand);
	    System.out.println(eval.toClassDetailsString());
	    
	    int _numOfClasses = ins.numClasses();
	    double[][] res = new double[_numOfClasses + 1][7];
	    String[] labels = new String[_numOfClasses +1];
		for(int i = 0; i < _numOfClasses; i++) {
			res[i][0] = eval.truePositiveRate(i);
			res[i][1] = eval.falsePositiveRate(i);
			res[i][2] = eval.trueNegativeRate(i);
			res[i][3] = eval.falseNegativeRate(i);
			res[i][4] = eval.precision(i);
			res[i][5] = eval.recall(i);
			res[i][6] = eval.fMeasure(i);
			labels[i] = ins.classAttribute().value(i);
			
		}
		
		res[_numOfClasses][0] = eval.weightedTruePositiveRate();
		res[_numOfClasses][1] = eval.weightedFalsePositiveRate();
		res[_numOfClasses][2] = eval.weightedTrueNegativeRate();
		res[_numOfClasses][3] = eval.weightedFalseNegativeRate();
		res[_numOfClasses][4] = eval.weightedPrecision();
		res[_numOfClasses][5] = eval.weightedRecall();
		res[_numOfClasses][6] = eval.weightedFMeasure();
		labels[_numOfClasses] = "Weighted Result";
		
		String result = "\n";
		for(int i = 0; i<=_numOfClasses; i++){
			result = result +labels[i]+",";
			for(int j = 0; j<=6; j++){
				result = result+res[i][j]+",";
			}
			result = result.substring(0,result.length()-1)+"\n";
		}
		
		return result;
	}
}
