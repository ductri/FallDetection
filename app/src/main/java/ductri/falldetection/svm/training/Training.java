package ductri.falldetection.svm.training;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import ductri.falldetection.svm.utils.Utils;

public class Training {
	
	public static void main(String[] args) {
		/*
		double[][] sample = Utils.transpose(Utils.get_params_2d(Utils.base_dir + "sample.csv"));
		
		System.out.println(sample.length);
		
		FeaturingData featuringData = new FeaturingData();
		
		double[][] rawData = Utils.get_params_2d(Utils.base_dir + "raw_data.csv");
		String[] label = Utils.getLabel(Utils.base_dir + "label.csv");
		
		Dataset dataset = featuringData.featuringAllData(rawData, label);
		
		double[] mean = {0.306180602542,6.81407565053,0.611456217671,1.96373010828,2.95931325408,2.08626530219,18.7653605795,52646.7293542,401752.098841,46473.1899991,0.0451316168758,0.0136507378484,0.0232433709138};
		double[] max_min = {21.0554326326,22.4814043295,19.9886662417,9.99612669379,11.9869914303,10.0551411852,62.7345531267,634759.664597,783421.569075,576322.559454,1.99985728494,1.99983244425,1.99974152181};
		
		for (int i=0; i<dataset.data.length; i++) {
			for (int j=0; j<dataset.data[i].length; j++) {
				dataset.data[i][j] = (dataset.data[i][j] - mean[j]) / max_min[j];
			}
		}
		
		Utils.write_2d(Utils.base_dir + "featuring_data.csv", dataset.data);
		Utils.writeLabel(Utils.base_dir + "label_featuring_data.csv", dataset.label);
		
		System.out.println(dataset.data.length);
		*/
//
//		double[][] X = Utils.get_params_2d(Utils.base_dir + "featuring_data.csv");
//		String[] y = Utils.getLabel(Utils.base_dir + "label_featuring_data.csv");
//		Utils.saveDatasetWithLabel(X, y, Utils.base_dir + "formated_data.csv");
	}
	
	public void start() {
//		double[][] X = Utils.get_params_2d(Utils.base_dir + "featuring_data.csv");
//		String[] y = Utils.getLabel(Utils.base_dir + "label_featuring_data.csv");
	}
}
