package com.sabrinalucero.cheetahrun.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import com.sabrinalucero.cheetahrun.Utils.Util;
import com.sabrinalucero.cheetahrun.R;
import com.sabrinalucero.cheetahrun.fragments.RunMapFragment;
import com.sabrinalucero.cheetahrun.fragments.WelcomeFragment;

public class MainActivity extends AppCompatActivity {

  private SharedPreferences prefs;
  private Button btn;
  private int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
  private int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION;



  Fragment currentFragment;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //accedemos al mismo archivo llamandolo del mismo modo
    prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

    if (savedInstanceState == null){
      currentFragment = new WelcomeFragment();
      changeFragment(currentFragment);
      
    }

    // Here, thisActivity is the current activity
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      System.out.println("usted no tiene permisos");
      ActivityCompat.requestPermissions(this,
              new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
              MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
    }

    if( ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(this,
              new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
              MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
    }

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    //invocar al menu sino no va a aparecer
    getMenuInflater().inflate(R.menu.menu, menu);
    return super.onCreateOptionsMenu(menu);
  }

  //menu toolbar
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()){
      case R.id.menu_logout:
        logOut();
        break;
      case R.id.menu_forget_logout:
        Util.removeSharedPreferences(prefs);
        logOut();
        break;
      case R.id.menu_welcome:

        currentFragment = new WelcomeFragment();

        break;
      case R.id.menu_map:
        currentFragment = new RunMapFragment();
        break;
    }

    changeFragment(currentFragment);
    return super.onOptionsItemSelected(item);
  }

  private void changeFragment(Fragment fragment){
    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
  }

  private void logOut(){

    Intent intent = new Intent(this, LoginActivity.class);
    //flag para no volver al login, tambien se puede hacer con el metodo finish()
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(intent);
  }

}
