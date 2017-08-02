package alon.datasensorssample;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Color;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends Activity{

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // get the bundle and extract data by key
            Bundle b = msg.getData();
            float x = b.getFloat("x");
            float y = b.getFloat("y");
            float z = b.getFloat("z");
         Log.i("xxxxx:",Float.toString(x));
            Log.i("yyyyy:",Float.toString(y));
            Log.i("zzzzz:",Double.toString(z));
            if (z >= 0){
               if ( x<0&& y<500) {
                   getWindow().getDecorView().setBackgroundColor(Color.BLUE);
               }
             else if (y>450|| y<10) {
                   getWindow().getDecorView().setBackgroundColor(Color.GREEN);
               }
                   else if (y<0 && x<0)
                   {
                       getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                   }
           else if (x>0 && y>0)
               {
                   getWindow().getDecorView().setBackgroundColor(Color.GREEN);
               }

            }
            else if( z<0) {
                if (x>500){
                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                }
                getWindow().getDecorView().setBackgroundColor(Color.RED);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfiguration);
        int display_mode = getResources().getConfiguration().orientation;

        if (display_mode == Configuration.ORIENTATION_PORTRAIT)
       Log.i("yo","yo");
        setContentView(R.layout.activity_main);
        SensorManager sensorManager= (SensorManager)getSystemService(SENSOR_SERVICE);
        DataSample dataSample= new DataSample(sensorManager, savedInstanceState);
        DBManagement dbMan = new DBManagement();
        dbMan.InitDataBase();
        dbMan.SetHandler(mHandler);
        dataSample.SetDB(dbMan);
        dataSample.run();


    }
}
