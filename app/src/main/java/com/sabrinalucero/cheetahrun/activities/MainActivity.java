package com.sabrinalucero.cheetahrun.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import com.sabrinalucero.cheetahrun.Utils.Util;
import com.sabrinalucero.cheetahrun.R;
import com.sabrinalucero.cheetahrun.adapters.PageAdapterView;
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

    Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(myToolbar );

    //se añaden los tabs
    TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
    tabLayout.addTab(tabLayout.newTab().setText("Welcome"));
    tabLayout.addTab(tabLayout.newTab().setText("Go Map"));
    tabLayout.addTab(tabLayout.newTab().setText("Works out"));
    //efecto de sombra entre cada tab
    tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


    final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
    PageAdapterView adapter = new PageAdapterView(getSupportFragmentManager(),tabLayout.getTabCount());

    //le pasamos el adaptador al viewpager
    viewPager.setAdapter(adapter);
    //un listener para que cada vez que se cambie, cada vez que cambiemos un tab le avisamos al viewpager que es el encargado de hacerlo.
    viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
        //  Toast.makeText(MainActivity.this, "Selected-->  "+ tab.getText(), Toast.LENGTH_SHORT).show();
        int position = tab.getPosition();
        viewPager.setCurrentItem(position);
      }

      @Override
      public void onTabUnselected(TabLayout.Tab tab) {
        // Toast.makeText(MainActivity.this, "Unselected -->  "+ tab.getText(), Toast.LENGTH_SHORT).show();
      }

      @Override
      public void onTabReselected(TabLayout.Tab tab) {
        //   Toast.makeText(MainActivity.this, "Reselected-->  "+ tab.getText(), Toast.LENGTH_SHORT).show();
      }
    });




    //accedemos al mismo archivo llamandolo del mismo modo
    prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

   /* if (savedInstanceState == null){
      currentFragment = new WelcomeFragment();
      changeFragment(currentFragment);
      
    }*/

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
    Intent intent;
    switch (item.getItemId()){
      case R.id.menu_logout:
        logOut();
        break;
      case R.id.menu_forget_logout:
        Util.removeSharedPreferences(prefs);
        logOut();
        break;
      case R.id.menu_aboutMe:
        intent = new Intent(MainActivity.this, AboutMeActivity.class);
        startActivity(intent);
        return true;
    }

  //  changeFragment(currentFragment);
    return super.onOptionsItemSelected(item);
  }

 // private void changeFragment(Fragment fragment){
 //   getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
 // }

  private void logOut(){

    Intent intent = new Intent(this, LoginActivity.class);
    //flag para no volver al login, tambien se puede hacer con el metodo finish()
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(intent);
  }

}
