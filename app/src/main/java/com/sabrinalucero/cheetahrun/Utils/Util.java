package com.sabrinalucero.cheetahrun.Utils;

import android.content.SharedPreferences;

/**
 * Created by Sabrina on 28/06/2017.
 */

public class Util {

  public static String getUserMailPrefs(SharedPreferences preferences){
    //pasamos la misma key con la que se guardo en switch (editor)
    return preferences.getString("email", "");

  }


  public static String getUserPasswordPrefs(SharedPreferences preferences ){
    return preferences.getString("pass", "");
  }

  //elimina las preferencias seteadas en forma individual en lugar de eliminar el archivo globalmente
  public static void removeSharedPreferences(SharedPreferences preferences){
    SharedPreferences.Editor editor = preferences.edit();
    editor.remove("email");
    editor.remove("pass");
    editor.apply();
  }
}
