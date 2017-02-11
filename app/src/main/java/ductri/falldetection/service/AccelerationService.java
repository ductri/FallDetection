package ductri.falldetection.service;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.Toast;

import ductri.falldetection.analysis.Detector;
import ductri.falldetection.utils.Utils;

/**
 * Created by ductr on 12/21/2016.
 */
public class AccelerationService extends android.app.Service{
    private Looper mServiceLooper;
    private Detector detecterService;
    private AccelerationListener accelerationListener;
    //ResultReceiver resultReceiver;
    @Override
    public void onCreate() {
        // Start up the thread running the service.  Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block.  We also make it
        // background priority so CPU-intensive work will not disrupt our UI.

        Log.i(Utils.TAG, "service is created");

        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        // Get the HandlerThread's Looper and use it for our Handler
        mServiceLooper = thread.getLooper();

        SensorManager sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
//        Sensor acceleration = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        detecterService = new Detector(mServiceLooper, sensorManager, this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
        //resultReceiver = intent.getParcelableExtra("receiver");

        Log.i(Utils.TAG, "service is onStartcommand");


        Message msg = detecterService.obtainMessage();
        msg.arg1 = startId;
        detecterService.sendMessage(msg);

        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "service destroy", Toast.LENGTH_SHORT).show();
        Log.i(Utils.TAG, "Service is destroyed");
        detecterService.unregisterListener();
        super.onDestroy();
    }

}
