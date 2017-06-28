package com.sabrinalucero.cheetahrun.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sabrinalucero.cheetahrun.Utils.Util;
import com.sabrinalucero.cheetahrun.R;
import com.sabrinalucero.cheetahrun.fragments.MapFragment;
import com.sabrinalucero.cheetahrun.fragments.WelcomeFragment;

public class MainActivity extends AppCompatActivity {

  private SharedPreferences prefs;
  private Button btn;

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




    Toast.makeText(this, "Called", Toast.LENGTH_LONG).show();

   // esto para pasar con un boton de main al mapa
    btn = (Button)findViewById(R.id.buttonMap);
    btn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(intent);
      }
    });



  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    //invocar al menu sino no va a aparecer
    getMenuInflater().inflate(R.menu.menu, menu);
    return super.onCreateOptionsMenu(menu);


  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()){
      case R.id.menu_logout:
        logOut();
        return true;
      case R.id.menu_forget_logout:
        Util.removeSharedPreferences(prefs);
        logOut();
        return true;
      case R.id.menu_welcome:

        currentFragment = new WelcomeFragment();

        return true;
      case R.id.menu_map:
        currentFragment = new MapFragment();
        return true;
    }

        changeFragment(currentFragment);
        return super.onOptionsItemSelected(item);

  }

  private void changeFragment(Fragment fragment){
    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
  }

  private void logOut(){

    Intent intent = new Intent(this, LoginActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(intent);
  }


}
