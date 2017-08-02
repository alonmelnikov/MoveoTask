package alon.datasensorssample;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import java.io.File;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

// this class  gets the data sensors from DataSample and stores it in the data base and sends it to the  UI Thread
public class DBManagement  {

   private float x,y,z;
    Realm realm = null;
    Handler mUIHandler = null;
  static final int recordsLimit=500;

    void SetHandler(Handler handler) {
        mUIHandler = handler;
    }


    public void setCoordinates(float _x, float _y,float _z){
     x=_x;
     y=_y;
     z=_z;
     insertData();
     Message msg = new Message();
     Bundle b = new Bundle();
     b.putFloat("x",_x);
     b.putFloat("y",_y);
     b.putFloat("z",_z);
     msg.setData(b);
     try {
         mUIHandler.sendMessage(msg);
     }
     catch (Exception e) {
         Log.v("Error", e.toString());
     }
 }
 public void insertData()
 {
     synchronized (this) {
         long count = realm.where(ThreePoints.class).count();
         if (count == recordsLimit) {
             realm.executeTransaction(new Realm.Transaction() {
                 @Override
                 public void execute(Realm realm) {
                     RealmQuery<ThreePoints> query = realm.where(ThreePoints.class);
                     RealmResults<ThreePoints> result = query.findAll();
                     result.first().removeFromRealm();
                 }
             });
         }
         realm.executeTransaction(new Realm.Transaction() {
             @Override
             public void execute(Realm realm) {
                 ThreePoints data = realm.createObject(ThreePoints.class);
                 data.set_x(x);
                 data.set_y(y);
                 data.set_z(z);
             }
         });
     }
 }
    public void InitDataBase() {

        try {
            realm = Realm.getDefaultInstance();
            realm.close();
            Realm.deleteRealm(realm.getConfiguration());
            realm = Realm.getDefaultInstance();
        }
        catch (Exception e)
        {
            e.getMessage();
        }

    }
}
