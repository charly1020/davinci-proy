package com.sabrinalucero.cheetahrun.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.sabrinalucero.cheetahrun.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RunMapFragment extends Fragment implements OnMapReadyCallback, LocationListener, LocationSource {

  private View rootView;
  private MapView mapView; //aqui se captura el layout
  private GoogleMap gMap;
  private static final long MIN_TIME = 400;
  private static final float MIN_DISTANCE = 1000;
  private Timer myTimer;

  private OnLocationChangedListener mListener;
  private LocationManager locationManager;

  //para dibujar en el mapa
  private List<LatLng> points = new ArrayList<>();

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    // Inflate the layout for this fragment
    rootView = inflater.inflate(R.layout.fragment_map, container, false);

    Button runNow = (Button) rootView.findViewById(R.id.runId);
    runNow.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        startRunning();
      }
    });


    FloatingActionButton myFab = (FloatingActionButton) rootView.findViewById(R.id.floatingActionButton);
    myFab.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        checkIfGPSisEnabled();
      }
    });
    locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

    return rootView;
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {

    gMap = googleMap;

    gMap.setLocationSource(this);
    try {
      boolean gpsIsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

      if (gpsIsEnabled) {
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this); //You can also use LocationManager.GPS_PROVIDER and LocationManager.PASSIVE_PROVIDER
        gMap.setLocationSource(this);

      }

    } catch (SecurityException e) {
      System.out.println(e);
    }
  }

  @Override
  public void onPause() {
    super.onPause();


  }

  private void checkIfGPSisEnabled(){
    try {
      boolean gpsIsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

      if (!gpsIsEnabled ){
        showInfoAlert();
      }

    } catch (SecurityException e) {
      System.out.println(e);
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  private void startRunning() {
    Context context = getContext();
    CharSequence text = "Go Go";
    int duration = Toast.LENGTH_SHORT;

    Toast toast = Toast.makeText(context, text, duration);
    toast.show();

    new Timer().schedule(new TimerTask() {
      @Override
      public void run() {
        updateLocation();
      }
    }, 1000, 1);
  }


  private void updateLocation() {
    try {
      //TODO buscar que hace cada parametro dentro de location manager
      locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000L, 20F, this);
    } catch(SecurityException e) {

    }

  }

  //activar gps del dispositivo
  private void showInfoAlert(){
    new AlertDialog.Builder(getContext())
      .setTitle("GPS Signal")
      .setMessage("You don't have GPS signal, Would you like enable the GPS signal now?")
      .setPositiveButton("OK", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
          //El GPS no esta activado, lleva a settings para activarlo
          Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
          startActivity(intent);
        }
      })
      .setNegativeButton("CANCEL", null)
      .show();
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);


    final SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);

  }

  @Override
  public void onLocationChanged(Location location) {

    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
    //vista del mapa, el nro indica la altura desde la que se ve el mapa 1 por ej es a nivel tierra, 5 pais
    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
    gMap.animateCamera(cameraUpdate);

    MarkerOptions mo = new MarkerOptions().position(latLng);
    mo.icon(BitmapDescriptorFactory.fromResource(R.drawable.small_blue));

    gMap.addMarker(mo);

    points.add(latLng);

    float distance = 0;

    //dibujan el recorrido
    if(points.size() > 1) {
      PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);


      for (int z = 0; z < points.size(); z++) {

        if(z!=0) {
          float[] results = new float[1]; ;
          double startLat = points.get(z-1).latitude;
          double startLng = points.get(z-1).longitude;
          double endLat = points.get(z).latitude;
          double endLng = points.get(z).longitude;

          Location.distanceBetween(startLat, startLng, endLat, endLng, results);
          distance += results[0];
        }
        LatLng point = points.get(z);
        options.add(point);
      }
      gMap.addPolyline(options);

    }

    CharSequence text = distance + "mts";

    //probar cambiar que el edit text por un texto que muestre solo los km
    EditText btn = (EditText) rootView.findViewById(R.id.distance);
    btn.setText(text);

  }

  @Override
  public void onStatusChanged(String s, int i, Bundle bundle) {

  }

  @Override
  public void onProviderEnabled(String s) {

  }

  @Override
  public void onProviderDisabled(String s) {

  }

  @Override
  public void activate(OnLocationChangedListener onLocationChangedListener) {
    mListener = onLocationChangedListener;
  }

  @Override
  public void deactivate() {
    mListener = null;
  }



}
