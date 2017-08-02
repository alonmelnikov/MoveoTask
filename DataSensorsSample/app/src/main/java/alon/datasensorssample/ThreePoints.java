package alon.datasensorssample;

import io.realm.RealmObject;

// this  type class is for the Realm DataBase use.
public class ThreePoints extends RealmObject {

   private float _x,_y,_z;


   public float get_x() {
      return _x;
   }

   public float get_y() {
      return _y;
   }

   public float get_z() {
      return _z;
   }

   public void set_x(float _x) {
      this._x = _x;
   }

   public void set_y(float _y) {
      this._y = _y;
   }

   public void set_z(float _z) {
      this._z = _z;
   }
}
