package cs889.algorithm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs889.gui.services.FeatureSetRebuild;
import cs889.gui.utility.FeatureSelection;
import cs889.gui.utility.FeatureSelectionUtil;
import weka.core.Instances;

public class SSD {
	/**
	public static final String[] a1Instances = {"A","C","B","E","D" };
	public static final String[] a2Instances = {"A","D","E","C","B" };
	public static final String[] a3Instances = {"B","E","D","A","C" };
	public static final String[] a4Instances = {"C","A","B","E","D" };
	public static final String[] a5Instances = {"C","A","E","B","D" };
	public static final String[] a6Instances = {"C","B","A","D","E" };
	public static final String[] a7Instances = {"D","C","E","B","A" };
	public static final String[] a8Instances = {"E","B","A","D","C" };
	public static final String[] defaultInstances = {"A","B","C","D","E" };
	
	public static final List<String> a1InstancesAL = Arrays.asList(a1Instances);
	public static final List<String> a2InstancesAL = Arrays.asList(a2Instances);
	public static final List<String> a3InstancesAL = Arrays.asList(a3Instances);
	public static final List<String> a4InstancesAL = Arrays.asList(a4Instances);
	public static final List<String> a5InstancesAL = Arrays.asList(a5Instances);
	public static final List<String> a6InstancesAL = Arrays.asList(a6Instances);
	public static final List<String> a7InstancesAL = Arrays.asList(a7Instances);
	public static final List<String> a8InstancesAL = Arrays.asList(a8Instances);
	
	public static final List<String> defaultInstancesAL = Arrays.asList(defaultInstances);
	
	public static final int[] weights = {5, 5, 8, 3, 7, 2, 7, 8};
	
	public static final ArrayList<List<String>> lists = new ArrayList<List<String>>();
	static{
		lists.add(a1InstancesAL);
		lists.add(a2InstancesAL);
		lists.add(a3InstancesAL);
		lists.add(a4InstancesAL);
		lists.add(a5InstancesAL);
		lists.add(a6InstancesAL);
		lists.add(a7InstancesAL);
		lists.add(a8InstancesAL);
	}
	*/
	
	public static Instances defaultIns;
	public static Instances A1Ins;
	public static Instances A2Ins;
	public static Instances A3Ins;
	static {
		try {
			defaultIns = new Instances(new BufferedReader(new FileReader(FeatureSelection.fileName)));
			A1Ins = new Instances(new BufferedReader(new FileReader(FeatureSelection.A1_DES)));
			A2Ins = new Instances(new BufferedReader(new FileReader(FeatureSelection.A2_DES)));
			A3Ins = new Instances(new BufferedReader(new FileReader(FeatureSelection.A3_DES)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public static final ArrayList<String> a1InstancesAL = new ArrayList<String>();
	public static final ArrayList<String> a2InstancesAL = new ArrayList<String>();
	public static final ArrayList<String> a3InstancesAL = new ArrayList<String>();
	public static final ArrayList<String> defaultInstancesAL = new ArrayList<String>();
	
	static{
		for(int i = 0; i<defaultIns.numAttributes()-1; i++){
			a1InstancesAL.add(A1Ins.attribute(i).name());
			a2InstancesAL.add(A2Ins.attribute(i).name());
			a3InstancesAL.add(A3Ins.attribute(i).name());
			defaultInstancesAL.add(defaultIns.attribute(i).name());
		}
	}
	
	public static final ArrayList<List<String>> lists = new ArrayList<List<String>>();
	static{
		lists.add(a1InstancesAL);
		lists.add(a2InstancesAL);
		lists.add(a3InstancesAL);
	}
	
	
	public static int[][] matrix = new int[defaultInstancesAL.size()][defaultInstancesAL.size()];
	/**
	 * Generate the orders in the ABCDE values
	 * @param orders
	 * @return
	 */
	public static int[] generateOrder(List<String> orders){
		int[] resultOrder = new int[orders.size()];
		for(int i = 0; i<resultOrder.length; i++){
			resultOrder[i] = orders.indexOf(defaultInstancesAL.get(i));
		}
		return resultOrder;
	}
	
	/**
	 * Update the matrix according to the given order
	 * @param orders
	 */
	public static void updateMatrix(int[] orders){//, int index){
		//int weight = weights[index];
				
		for(int i = 0; i<orders.length; i++){
			for(int j = i+1; j<orders.length; j++){
				if(orders[i]> orders[j]){
					SSD.matrix[j][i] = SSD.matrix[j][i]+1;// + 1*weight;
				}else{
					SSD.matrix[i][j] = SSD.matrix[i][j]+1;// + 1*weight;
				}
			}
		}
	}
	
	/**
	 * Construct the final matrix
	 */
	public static void constructPairMatrix(){
		for(int i = 0; i<lists.size(); i++){
			List<String> list = lists.get(i);
			SSD.updateMatrix(SSD.generateOrder(list));//, i);
		}
	}
	
	/**
	 * Generate the edge weight
	 * @return
	 */
	public static int[][] generateEdgeWeight(){
		int length = a1InstancesAL.size();
		int[][] result = new int[length][length];
		for(int i = 0; i<length; i++){
			for(int j = 0; j<length; j++){
				if(i!=j){
					if(matrix[i][j]>matrix[j][i])
						result[i][j] = matrix[i][j];
					else
						result[i][j] = 0;
				}
			}
		}
		
		
		for(int i = 0; i<length; i++){
			for(int j = 0; j<length; j++){
				System.out.print(result[i][j]+",");
			}
			System.out.println("");
		}
		System.out.println("========================");
		for(int i = 0; i<length; i++){
			for(int j = 0; j<length; j++){
				if(i!=j){
					for(int k = 0; k<length; k++){
						if(i!=k && j!=k){
							result[j][k] = Math.max(result[j][k], Math.min(result[j][i], result[i][k]));
						}
					}
				}
			}
		}
		
		
		return result;
	}
	
	
	/**
	 * Generate the final attribute order
	 * @param result
	 * @return
	 */
	public static ArrayList<Integer> getFinalOrder(int[][] result){
		ArrayList<Integer> finalOrders = new ArrayList<Integer>();
		int length = a1InstancesAL.size();
		for(int l = 0; l<length; l++){
			boolean[] temp = new boolean[length];
			for(int j = 0; j<length; j++){
				if(finalOrders.contains(new Integer(j))){
					temp[j] = false;
				}else{
					temp[j] = true;
				}
			}
			
			for(int i = 0; i<length; i++){
				if(finalOrders.contains(new Integer(i))){
					continue;
				}
				for(int j = 0; j<length; j++){
					if(finalOrders.contains(new Integer(j))){
						continue;
					}
					if(i!=j){
						if(result[j][i] > result[i][j]){
							temp[i] = false;
						}
					}
				}
			}
			
			for(int i = 0; i<length; i++){
				System.out.print(temp[i]+",");
			}
			System.out.println("");
			for(int i = 0; i<length; i++){
				if(temp[i]){
					finalOrders.add(new Integer(i));
					break;
				}
			}
		}
		return finalOrders;
	}
	
	
	public static Instances generateUserSelection() throws Exception{
		SSD.constructPairMatrix();
		int[][] weights = SSD.generateEdgeWeight();
		ArrayList<Integer> resultList = SSD.getFinalOrder(weights);
		return FeatureSetRebuild.featureReordering(defaultIns, resultList);
	}
	
	public static void main(String[] args){
		int[] orders = SSD.generateOrder(SSD.a1InstancesAL);
		for(int i = 0; i<orders.length; i++)
			System.out.println(orders[i]);
		
		//SSD.updateMatrix(orders);
		SSD.constructPairMatrix();
		for(int i = 0; i<orders.length; i++){
			for(int j = 0; j<orders.length; j++){
				System.out.print(SSD.matrix[i][j]+",");
			}
			System.out.println("");
		}
		
		System.out.println("========================");
		int[][] test = SSD.generateEdgeWeight();
		for(int i = 0; i<orders.length; i++){
			for(int j = 0; j<orders.length; j++){
				System.out.print(test[i][j]+",");
			}
			System.out.println("");
		}
		System.out.println("========================");
		ArrayList<Integer> resultList = SSD.getFinalOrder(test);
		
		for(Integer in: resultList){
			System.out.print(SSD.defaultInstancesAL.get(in)+",");
		}
		System.out.println("");
		Instances ins;
		try {
			ins = SSD.generateUserSelection();
			for(int i = 0; i<ins.numAttributes(); i++){
				System.out.print(ins.attribute(i).name()+",");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
