package cs889.gui.utility;

import java.io.IOException;
import java.util.ArrayList;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class FeatureSelectionUtil {
	/**
	 * 
	 * @param org - original instance set
	 * @param selectedAttributeIndices - indices of features that are selected, start from 0. 
	 * @return instances contain only the selected features 
	 * @throws Exception
	 */
	public static final int FOLDS = 10;
	public static Instances getInstance(Instances org, ArrayList<Integer> selectedAttributeIndices) throws Exception{
		
		String deleteString = "";
		boolean first = true;
		for(int i = 0; i<org.numAttributes()-1; i++){
			if(!selectedAttributeIndices.contains(new Integer(i))){
				if(first){
					deleteString = deleteString+(i+1);
					first = false;
				}else{
					deleteString = deleteString+","+(i+1);
				}
			}
		}
	      Remove af = new Remove();
	      af.setAttributeIndices(deleteString.toString());
	      af.setInvertSelection(false);
	      af.setInputFormat(org);
		return Filter.useFilter(org, af);
	}
	
	
	
	public static Instances getAlgorithm1Instance(Instances org, ArrayList<Integer> selectedAttributeIndices) throws Exception{
		
		String deleteString = "";
		boolean first = true;
		for(int i = 0; i<org.numAttributes(); i++){
			if(!selectedAttributeIndices.contains(new Integer(i))){
				if(first){
					deleteString = deleteString+(i+1);
					first = false;
				}else{
					deleteString = deleteString+","+(i+1);
				}
			}
		}
	      Remove af = new Remove();
	      af.setAttributeIndices(deleteString.toString());
	      af.setInvertSelection(false);
	      af.setInputFormat(org);
		return Filter.useFilter(org, af);
	}
	
	public static Instances createInstances() throws IOException{
		Instances instances = new Instances(new java.io.BufferedReader(
				  new java.io.FileReader(FeatureSelection.USER_SELECT_DES)));
		return instances;
	}
	
	/**
	 * Create Instances for the default a1 list based on the given numSelections
	 * @param numSelections
	 * @return
	 * @throws Exception
	 */
	public static Instances createA1Instances(int numSelections) throws Exception{
		Instances instances = new Instances(new java.io.BufferedReader(
				  new java.io.FileReader(FeatureSelection.A1_DES)));
		
		ArrayList<Integer> selectedAttributeIndices =new ArrayList<Integer>();
        for(int i= 0; i<numSelections; i++){
    		selectedAttributeIndices.add(i);
    	  }
        Instances resultInstances = FeatureSelectionUtil.getInstance(instances, selectedAttributeIndices);
		return resultInstances;
	}
	
	/**
	 * Create Instances for the default a2 list based on the given numSelections
	 * @param numSelections
	 * @return
	 * @throws Exception
	 */
	public static Instances createA2Instances(int numSelections) throws Exception{
		Instances instances = new Instances(new java.io.BufferedReader(
				  new java.io.FileReader(FeatureSelection.A2_DES)));
		
		ArrayList<Integer> selectedAttributeIndices =new ArrayList<Integer>();
        for(int i= 0; i<numSelections; i++){
    		selectedAttributeIndices.add(i);
    	  }
        Instances resultInstances = FeatureSelectionUtil.getInstance(instances, selectedAttributeIndices);
		return resultInstances;
	}
	
	
}
