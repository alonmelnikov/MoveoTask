package alon.datasensorssample;

import android.app.Application;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.util.LogPrinter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.Executor;

// this class is responsible to listen to SensorChanged evens and to pass the new data about the sensor to the DataBase class.
public class DataSample implements  Runnable{

    private  DBManagement mDbManagement;
    SensorManager mSensorManager;
    Sensor accelerometerSensor;
    boolean accelerometerPresent;

   private SensorEventListener accelerometerListener;

    public void SetDB(DBManagement dbManagement)
    {
        mDbManagement = dbManagement;
    }

    public  DataSample(SensorManager sensorManager,Bundle savedInstanceState) {

    mSensorManager = sensorManager;
        accelerometerListener = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor arg0, int arg1) {

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            float z = Math.round(Math.toDegrees(event.values[2]));
            float x = Math.round(Math.toDegrees(event.values[0]));
            float y = Math.round(Math.toDegrees(event.values[1]));
            mDbManagement.setCoordinates(x,y,z);

        }  };
}
    @Override
    public void run() {

        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if(sensorList.size() > 0){
            accelerometerPresent=true;
            accelerometerSensor = sensorList.get(0);
        }
         else{
            accelerometerPresent=false;
             Log.i("Tehnical isuue:","your device do not support any sensor");
        }
        onResume();

    }


    protected void onResume() {


        if (accelerometerPresent) {
           mSensorManager.registerListener(accelerometerListener, accelerometerSensor, 300000);


            }
    }
    protected void onStop() {
        if(accelerometerPresent){
         mSensorManager.unregisterListener(accelerometerListener);
        }
    }


}
