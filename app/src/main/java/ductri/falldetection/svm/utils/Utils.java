package ductri.falldetection.svm.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Utils {
	//"F:\\code\\java\\FallDetection\\resources\\";

    public static Map<String, Integer> map = new HashMap<>();
	public static Map<String, String> dict = null;
    public static Map<String, String> initDict() {
        if (dict == null) {
            dict = new HashMap<String, String>();
            dict.put("STD", "Standing");
            dict.put("WAL", "Walking");
            dict.put("JOG", "Jogging");
            dict.put("JUM", "Jumping");
            dict.put("STU", "Stairs up");
            dict.put("STN", "Stairs down");
            dict.put("SCH", "Sit chair");
            dict.put("CSI", "Car-step in");
            dict.put("CSO", "Car-step out");
            dict.put("FOL", "Falling: Forward-lying");
            dict.put("FKL", "Falling: Front-knees-lying");
            dict.put("BSC", "Falling: Back-sitting-chair");
            dict.put("SDL", "Falling: Sideward-lying");
        }
        return dict;
    }
	//public static String[] mapInver = {"STD", "WAL", "JOG", "JUM", "STU", "STN", "SCH", "CSI", "CSO", "FOL", "FKL", "BSC", "SDL"};
	public static String[] mapInver = {"NOT FALL", "FALL"};
	
	public static double[][] get_params_2d(InputStream inputStream) {
		ArrayList<double[]> params = new ArrayList<>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(inputStream));

			String sCurrentLine;
			int index = 0;
			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println("index = " + (index++));
				String[] items = sCurrentLine.split(",");
				double[] row = new double[items.length];
				for (int i=0; i<items.length; i++) {
					row[i] = Double.parseDouble(items[i]);
				}
				params.add(row);
			}
			
			int noRows = params.size();
			int noColums = params.get(0).length;
			double[][] result = new double[noRows][noColums];
			for (int i=0; i<noRows; i++) {
				result[i] = params.get(i); 
			}
			
			return result;

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
		return null;
	}
	
	double[] get_params_1d(InputStream inputStream) {
		double[] params = null;
        BufferedReader br = null;
        try {
			br = new BufferedReader(new InputStreamReader(inputStream));

			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				String[] items = sCurrentLine.split(",");
				params = new double[items.length];
				for (int i=0; i<items.length; i++) {
					params[i] = Double.parseDouble(items[i]);
				}
			}
			
			return params;

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
		return null;
	}
	
	public static double[][] transpose(double[][] matrix) {
		double[][] newMatrix = new double[matrix[0].length][matrix.length];
		
		for (int i=0; i<matrix.length; i++) {
			for (int j=0; j<matrix[0].length; j++) {
				newMatrix[j][i] = matrix[i][j];
			}
		}
		
		return newMatrix;
	}
	
	public static String[] getLabel(InputStream inputStream) {
		ArrayList<String> params = new ArrayList<String>();
        BufferedReader br = null;
        try {
			br = new BufferedReader(new InputStreamReader(inputStream));

			String sCurrentLine;
			int index = 0;
			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(index++);
				params.add(sCurrentLine);
			}
			String[] result = new String[params.size()];
			result = params.toArray(result);
			return result;

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
		return null;
	}
	
	public static void write_2d(String url, double[][] data) {
		try {

			FileWriter fw = new FileWriter(url);
			BufferedWriter bw = new BufferedWriter(fw);

			int index = 0;
			
			for (int i=0; i<data.length; i++) {
				for (int j=0; j<data[i].length - 1; j++) {
					bw.write(Double.toString(data[i][j]) + ",");
				}
				bw.write(Double.toString(data[i][data[i].length-1]) + "\n");
			}
			System.out.println("index = " + (index++));
			
			bw.close();
			fw.close();
		} catch (IOException e) {

			e.printStackTrace();

		}
	}
	
	public static void writeLabel(String url, String[] lb) {
		try {

			FileWriter fw = new FileWriter(url);
			BufferedWriter bw = new BufferedWriter(fw);

			int index = 0;
			
			for (int i=0; i<lb.length; i++) {
				bw.write(lb[i] + "\n");
			}
			System.out.println("index = " + (index++));
			
			bw.close();
			fw.close();
		} catch (IOException e) {

			e.printStackTrace();

		}
	}
	
	public static void saveDatasetWithLabel(double[][] X, String[] y, String url) {
		try {
			
			FileWriter fw = new FileWriter(url);
			BufferedWriter bw = new BufferedWriter(fw);

			int index = 0;
			assert(X.length == y.length);
			
			map.put("STD", 0);
			map.put("WAL", 1);
			map.put("JOG", 2);
			map.put("JUM", 3);
			map.put("STU", 4);
			map.put("STN", 5);
			map.put("SCH", 6);
			map.put("CSI", 7);
			map.put("CSO", 8);
			map.put("FOL", 9);
			map.put("FKL", 10);
			map.put("BSC", 11);
			map.put("SDL", 12);
			
			for (int i=0; i<X.length; i++) {
				StringBuilder builder = new StringBuilder();
				builder.append(map.get(y[i]).toString());
				for (int j=0; j<X[i].length; j++) {
					builder.append(" ");
					builder.append(Integer.toString(j+1));
					builder.append(":");
					builder.append(X[i][j]);
				}
				builder.append("\n");
				bw.write(builder.toString());
				System.out.println("index = " + (index++));
			}
			
			bw.close();
			fw.close();
		} catch (IOException e) {

			e.printStackTrace();

		}
	}
	public static double[] scale(double[] data) {
		double[] mean = {0.306180602542,6.81407565053,0.611456217671,1.96373010828,2.95931325408,2.08626530219,18.7653605795,52646.7293542,401752.098841,46473.1899991,0.0451316168758,0.0136507378484,0.0232433709138};
		double[] max_min = {21.0554326326,22.4814043295,19.9886662417,9.99612669379,11.9869914303,10.0551411852,62.7345531267,634759.664597,783421.569075,576322.559454,1.99985728494,1.99983244425,1.99974152181};
        for (int i=0; i<data.length; i++) {
            data[i] = (data[i] - mean[i]) / max_min[i];
        }
		return data;
	}
}
