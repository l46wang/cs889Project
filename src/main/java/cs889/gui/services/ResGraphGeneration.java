package cs889.gui.services;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;

import com.googlecode.charts4j.*;

import cs889.gui.interactiveFeatureSelection.PreselectionPanel;
import cs889.gui.utility.FeatureSelection;
import cs889.gui.utility.FeatureSelectionUtil;
import static com.googlecode.charts4j.Color.*;
import static com.googlecode.charts4j.UrlUtil.normalize;

public class ResGraphGeneration {

	private static Instances ins = null;
	
	/**
	 * Adding wrapper call function
	 * @param inst
	 * @throws Exception
	 */
	public static void evalOutput(int fSetIndicator) throws Exception{
		switch(fSetIndicator){
			case FeatureSelection.USER:
				ins = PreselectionPanel.selectedInstances;
				break;
			case FeatureSelection.A1:
				ins = PreselectionPanel.a1SelectedInstances;
				break;
			case FeatureSelection.A2:
				ins = PreselectionPanel.a2SelectedInstances;
				break;
//			case FeatureSelection.A3:
//				ins = PreselectionPanel.a1SelectedInstances
			default: break;
		}
		
		evalOutput();
	}
	
	public static void evalOutput() throws Exception{
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
	    String[] labels = new String[_numOfClasses];
		for(int i = 0; i < _numOfClasses; i++) {
			res[i][0] = eval.truePositiveRate(i);
			res[i][1] = eval.falsePositiveRate(i);
			res[i][2] = eval.precision(i);
			res[i][3] = eval.recall(i);
			res[i][4] = eval.fMeasure(i);
			res[i][5] = eval.areaUnderROC(i);
			res[i][6] = res[i][0];
			
			labels[i] = ins.classAttribute().value(i);
			
		}
		
		res[_numOfClasses][0] = eval.weightedTruePositiveRate();
		res[_numOfClasses][1] = eval.weightedFalsePositiveRate();
		res[_numOfClasses][2] = eval.weightedPrecision();
		res[_numOfClasses][3] = eval.weightedRecall();
		res[_numOfClasses][4] = eval.weightedFMeasure();
		res[_numOfClasses][5] = eval.weightedAreaUnderROC();
		res[_numOfClasses][6] = res[_numOfClasses][0];
		
		String pic = drawTheResult(res, labels);
		System.out.println(pic);
		display(pic);
		
	}
	
	private static void display(String url) {
		
                try {
                   
                    URL _url = new URL(url);
                    BufferedImage image = ImageIO.read(_url);
                    JLabel label = new JLabel(new ImageIcon(image));
                    JFrame f = new JFrame();
                    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    f.getContentPane().add(label);
                    f.pack();
                    f.setLocation(200, 200);
                    f.setVisible(true);
                } catch (Exception exp) {
                    exp.printStackTrace();
                }
		
	}
	
	 public static ArrayList<Color> colors = new ArrayList<Color>();
	 static {
		 
		 colors.add(Color.AQUA);
		 colors.add(Color.RED);
		 colors.add(Color.BLACK);
		 colors.add(Color.ROYALBLUE);
		 colors.add(Color.HONEYDEW);
	 }
	
	 public static String drawTheResult(double[][] res, String[] labels) {
	        // EXAMPLE CODE START
		 	ArrayList<RadarPlot> plot = new ArrayList<RadarPlot>();
		 	for(int i = 0; i < res.length; i++) {
		 		String legend = "";
		 		if(i == res.length - 1) {
		 			legend = "Weighted Avg.";
		 		}
		 		else {
		 			legend = labels[i];
		 		}
		 		RadarPlot _plot = Plots.newRadarPlot(DataUtil.scaleWithinRange(0.0, 1.0, res[i]), colors.get(i), legend);
		 		_plot.addShapeMarkers(Shape.SQUARE, colors.get(i), 12);
		        _plot.addShapeMarkers(Shape.SQUARE, WHITE, 8);
		        _plot.setColor(colors.get(i));
		        _plot.setLineStyle(LineStyle.newLineStyle(4, 1, 0));
		        plot.add(_plot);
		 	}
	        RadarChart chart = GCharts.newRadarChart(plot);
	        chart.setTitle("Result based on current features", BLACK, 20);
	        chart.setSize(500, 600);
	        RadialAxisLabels radialAxisLabels = AxisLabelsFactory.newRadialAxisLabels("TP Rate", "FP Rate", "Precision", "Recall", "F-Measure", "ROC Area");
	        radialAxisLabels.setRadialAxisStyle(BLACK, 12);
	        chart.addRadialAxisLabels(radialAxisLabels);
	        AxisLabels contrentricAxisLabels = AxisLabelsFactory.newNumericAxisLabels(Arrays.asList(0, 0.2, 0.4, 0.6, 0.8, 1.0));
	        contrentricAxisLabels.setAxisStyle(AxisStyle.newAxisStyle(BLACK, 12, AxisTextAlignment.RIGHT));
	        chart.addConcentricAxisLabels(contrentricAxisLabels);
	        String url = chart.toURLString();
	        // EXAMPLE CODE END. Use this url string in your web or
	        // Internet application.
	        return url;
	    }
	

}
