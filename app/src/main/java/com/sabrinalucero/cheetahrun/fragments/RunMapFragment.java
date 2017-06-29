package com.sabrinalucero.cheetahrun.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sabrinalucero.cheetahrun.R;

public class RunMapFragment extends Fragment implements OnMapReadyCallback, LocationListener {

  private View rootView;
  private MapView mapView; //aqui se captura el layout
  private GoogleMap gMap;
  private LocationManager locationManager;
  private static final long MIN_TIME = 400;
  private static final float MIN_DISTANCE = 1000;


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    // Inflate the layout for this fragment
    rootView = inflater.inflate(R.layout.fragment_map, container, false);


    FloatingActionButton myFab = (FloatingActionButton) rootView.findViewById(R.id.floatingActionButton);
    myFab.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        checkIfGPSisEnabled();
      }
    });

    return rootView;
  }

  private void checkIfGPSisEnabled(){
    try {
      int gpsSignal = Settings.Secure.getInt(getActivity().getContentResolver(),Settings.Secure.LOCATION_MODE);

      if (gpsSignal == 0 ){
        showInfoAlert();
      } else {
        LatLng place = new LatLng(-34.603684,-58.381559);

        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place, 12.0f));

      }
    } catch (Settings.SettingNotFoundException e) {
      e.printStackTrace();
    }
  }

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
  public void onMapReady(GoogleMap googleMap) {

    gMap = googleMap;
    LatLng place = new LatLng(-34.603684,-58.381559);
    gMap.addMarker(new MarkerOptions().position(place).title("Im a marker in BA!"));
    gMap.moveCamera(CameraUpdateFactory.newLatLng(place));

    locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
    try {
      int gpsSignal = Settings.Secure.getInt(getActivity().getContentResolver(),Settings.Secure.LOCATION_MODE);

      if (gpsSignal == 0 ) {
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this); //You can also use LocationManager.GPS_PROVIDER and LocationManager.PASSIVE_PROVIDER
      }
    } catch (SecurityException e) {

    } catch (Settings.SettingNotFoundException e){

    }
  }

  @Override
  public void onLocationChanged(Location location) {
    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
    gMap.animateCamera(cameraUpdate);
    locationManager.removeUpdates(this);
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
}
