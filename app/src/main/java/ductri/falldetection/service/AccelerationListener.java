package ductri.falldetection.service;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

/**
 * Created by ductr on 12/21/2016.
 */
public class AccelerationListener implements SensorEventListener {

    private AccelerationHandler accelerationHandler;

    public AccelerationListener(AccelerationHandler accelerationHandler) {
        this.accelerationHandler = accelerationHandler;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        accelerationHandler.process(event.values);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
