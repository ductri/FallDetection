package ductri.falldetection.analysis;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import ductri.falldetection.activity.DangerActivity;
import ductri.falldetection.activity.StatusActivity;
import ductri.falldetection.service.AccelerationHandler;
import ductri.falldetection.service.AccelerationListener;
import ductri.falldetection.svm.predicting.Predicting;
import ductri.falldetection.utils.Utils;
import libsvm.svm;

/**
 * Created by ductr on 12/21/2016.
 */
public class Detector extends Handler implements AccelerationHandler{

    // Receive input in real-time
    private AccelerationListener accelerationListener;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Context context;
    private ArrayList<float[]> samples;
    private static final int MAX_SAMPLE = 128;
    private Predicting predictor;
    InputStream svmModelInputStream;


    public Detector(Looper looper, SensorManager sensorManager, Context context) {
        super(looper);
        this.accelerationListener = new AccelerationListener(this);
        this.sensorManager = sensorManager;
        this.accelerometer = this.sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); //TODO check loi ko co sensor
        this.context = context;
        this.samples = new ArrayList<float[]>(MAX_SAMPLE);
        this.predictor = new Predicting();

    }

    @Override
    public void handleMessage(Message msg) {
        Log.i(Utils.TAG, "start getting data from sensor");
        sensorManager.registerListener(accelerationListener, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);

    }

    public void unregisterListener() {
        Log.i(Utils.TAG, "stop getting data from sensor");
        sensorManager.unregisterListener(accelerationListener, accelerometer);
    }

    @Override
    public void process(float[] values) {
        samples.add(values);
        Log.i(Utils.TAG, Integer.toString(samples.size()));
        if (samples.size() == MAX_SAMPLE) {
            Log.i(Utils.TAG, "start analyzing");
            analyze();
        }
    }

    private void showStatus(String status) {
        Intent intent = new Intent(context, DangerActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        unregisterListener();
        Toast.makeText(context, "Status: " + status, Toast.LENGTH_SHORT).show();
    }

    private void analyze() {

        double[][] sample = new double[MAX_SAMPLE][];
        for (int i=0; i<samples.size(); i++) {
            float[] temp = samples.get(i);
            sample[i] = new double[temp.length];
            for (int j=0; j<sample[i].length; j++) {
                sample[i][j] = temp[j];
                Log.d(Utils.TAG, Double.toString(temp[j]));
            }
        }
        // DO something with sample
        sample = ductri.falldetection.svm.utils.Utils.transpose(sample);

        // This InputStream is closed after using by some unknown functions in SVM package
        try {
            svmModelInputStream = this.context.getAssets().open("SVM_model.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String label = predictor.predictFromRawData(sample, svmModelInputStream);
//        double[] X = {-0.0023747330326924906,-0.307091946533915,0.4966125288402548,-0.19644910158051007,-0.24687706429818787,-0.20748244741314428,-0.2991232047449368,-0.08237497754780211,-0.5127610617149455,0.9717021215219115,-0.022567418793163554,-0.006825940787014011,-0.011623187627149948};
//        String label = predictor.predictFromFeaturingData(X, svmModelInputStream);
        Log.i(Utils.TAG, "label = " + label);

        if (label.equals("FALL")) {
            showStatus(label);
        }

        samples.clear();
    }
}
