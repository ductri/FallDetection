package ductri.falldetection.svm.featuring;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

import java.util.ArrayList;
import java.util.Arrays;

import ductri.falldetection.svm.utils.Utils;

public class FeaturingData {
	
	public double[] getFeature(double[][] sample) {
		//sample has size 3*128
		
		// Vector feature
		double[] vec = new double[13];
		int vec_index = 0;
		
		// Get a DescriptiveStatistics instance
		DescriptiveStatistics[] stats = new DescriptiveStatistics[3];
		for (int i=0; i<stats.length; i++) {
			stats[i] = new DescriptiveStatistics();
		}
		// Add the data from the array
		for( int i = 0; i < sample.length; i++) {
			for (int j=0; j<sample[i].length; j++) {
				stats[i].addValue(sample[i][j]);
			}
		}
		
		// Get mean
		for (int i=0; i<3; i++) {
			vec[vec_index++] = stats[i].getMean();
		}
		
		// Get standart deviation
		for (int i=0; i<3; i++) {
			vec[vec_index++] = stats[i].getStandardDeviation();
		}
		
		//Get slope
		double[] max = new double[3];
		double[] min = new double[3];
		for (int i=0; i<3; i++) {
			max[i] = stats[i].getMax();
			min[i] = stats[i].getMin();
		}
		double slope = 0;
		for (int i=0; i<3; i++) {
			slope += (max[i] - min[i])*(max[i] - min[i]);
		}
		slope = Math.sqrt(slope);
		vec[vec_index++] = slope;
		
		// Get energy
		FastFourierTransformer fft = new FastFourierTransformer(DftNormalization.STANDARD);
		Complex[][] sample_fft = new Complex[3][];
		for (int i=0; i<3; i++) {
			sample_fft[i] = fft.transform(sample[i], TransformType.FORWARD); 
		}
		double[] energy = new double[3];
		for (int i=0; i<3; i++) {
			for (int j=0; j<sample_fft.length; j++) {
				double r = sample_fft[i][j].getReal();
				energy[i] += r*r;
			}
			energy[i] /= sample_fft.length;
		}
		for (int i=0; i<3; i++) {
			vec[vec_index++] = energy[i];
		}
		
		// Get correlation
		PearsonsCorrelation corr = new PearsonsCorrelation();
        double temp = corr.correlation(sample[0], sample[1]);
        if (Double.isNaN(temp)) {
            vec[vec_index++] = 0;
        } else {
            vec[vec_index++] = temp;
        }
        temp = corr.correlation(sample[2], sample[1]);
        if (Double.isNaN(temp)) {
            vec[vec_index++] = 0;
        } else {
            vec[vec_index++] = temp;
        }
        temp = corr.correlation(sample[2], sample[0]);
        if (Double.isNaN(temp)) {
            vec[vec_index++] = 0;
        } else {
            vec[vec_index++] = temp;
        }
		
		return vec;
	}

	public Dataset featuringAllData(double[][] data, String[] label) {
		ArrayList<double[]> featuring_data = new ArrayList<double[]>();
		ArrayList<String> newLabel = new ArrayList<String>();
		
		double[][] sample;
		for (int i=0; i + 128 < data.length; i+=64) {
			// If all data from i to i+127 have the same label
			if (label[i].equals(label[i+127])) {
				sample = Arrays.copyOfRange(data, i, i+128);
				assert(128 == sample.length);
				sample = Utils.transpose(sample);
				
				// Extract feature
				featuring_data.add(getFeature(sample));
				newLabel.add(label[i]);
			}
		}
		
		Dataset dataset = new Dataset();
		dataset.data = new double[featuring_data.size()][];
		dataset.label = new String[newLabel.size()];
		dataset.label = newLabel.toArray(dataset.label);
		
		for (int i=0; i<dataset.data.length; i++) {
			dataset.data[i] = featuring_data.get(i);
		}
		return dataset;
	}
}
