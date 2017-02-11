package ductri.falldetection.svm.predicting;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


import ductri.falldetection.svm.featuring.FeaturingData;
import ductri.falldetection.svm.utils.Utils;
import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;

public class Predicting {
	public String predictFromRawData(double[][] sample, InputStream inputStream) {
//		svm_model model = null;
//		try {
//			//model = svm.svm_load_model(modelFile);
//            model = svm.svm_load_model(new BufferedReader(new InputStreamReader(inputStream)));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		FeaturingData featuringData = new FeaturingData();
		double[] featuring_X = featuringData.getFeature(sample);
		featuring_X = Utils.scale(featuring_X);
        Log.i(ductri.falldetection.utils.Utils.TAG, "---------------------------------------");
        String features = "";
		for (int i=0; i<featuring_X.length; i++) {
            features = features + Double.toString(featuring_X[i]) + ";";
        }
        Log.i(ductri.falldetection.utils.Utils.TAG, features);

//        int m = featuring_X.length;
//		svm_node[] x = new svm_node[m];
//		for(int j=0;j<m;j++)
//		{
//			x[j] = new svm_node();
//			x[j].index = j+1;
//			x[j].value = featuring_X[j];
//		}
//		int v = (int)svm.svm_predict(model,x);
//
////        if (Utils.mapInver[v] != predictFromFeaturingData(featuring_X, inputStream))
////            throw new AssertionError("fail");
//
//		return Utils.mapInver[v];
        return predictFromFeaturingData(featuring_X, inputStream);
	}
	
	public String predictFromFeaturingData(double[] X, String modelFile) {
		svm_model model = null;
		try {
			model = svm.svm_load_model(modelFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		double[] featuring_X = X;
		
		int m = featuring_X.length;
		svm_node[] x = new svm_node[m];
		for(int j=0;j<m;j++)
		{
			x[j] = new svm_node();
			x[j].index = j+1;
			x[j].value = featuring_X[j];
		}
		int v = (int)svm.svm_predict(model,x);
		return Utils.mapInver[v];
	}
	public String predictFromFeaturingData(double[] X, InputStream inputStream) {
		svm_model model = null;
		try {
            model = svm.svm_load_model(new BufferedReader(new InputStreamReader(inputStream)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		double[] featuring_X = X;

		int m = featuring_X.length;
		svm_node[] x = new svm_node[m];
		for(int j=0;j<m;j++)
		{
			x[j] = new svm_node();
			x[j].index = j+1;
			x[j].value = featuring_X[j];
		}
		int v = (int)svm.svm_predict(model,x);
        Log.i(ductri.falldetection.utils.Utils.TAG, "index=" + Integer.toString(v));
		return Utils.mapInver[v];
	}

    public String predictFromFeaturingData(double[] X, svm_model model) {
        double[] featuring_X = X;

        int m = featuring_X.length;
        svm_node[] x = new svm_node[m];
        for(int j=0;j<m;j++)
        {
            x[j] = new svm_node();
            x[j].index = j+1;
            x[j].value = featuring_X[j];
        }
        int v = (int)svm.svm_predict(model, x);
        return Utils.mapInver[v];
    }

	public String[] predictFromFeaturingData(double[][] X, String modelFile) {
		svm_model model = null;
		try {
			model = svm.svm_load_model(modelFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String[] y = new String[X.length];
		for (int i=0; i<X.length; i++) {
			int m = X[i].length;
			svm_node[] x = new svm_node[m];
			for(int j=0;j<m;j++)
			{
				x[j] = new svm_node();
				x[j].index = j+1;
				x[j].value = X[i][j];
			}
			int v = (int)svm.svm_predict(model,x);
			y[i] = Utils.mapInver[v];
		}
		return y;
	}


}
