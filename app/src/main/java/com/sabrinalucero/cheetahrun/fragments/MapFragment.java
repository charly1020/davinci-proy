package com.sabrinalucero.cheetahrun.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sabrinalucero.cheetahrun.R;

public class MapFragment extends Fragment implements OnMapReadyCallback {

  private  View rootView;
  private MapView mapView; //aqui se captura el layout
  private GoogleMap gMap;




  public MapFragment() {
    // Required empty public constructor
  }



  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    // Inflate the layout for this fragment
    rootView = inflater.inflate(R.layout.fragment_map, container, false);
    return rootView;

  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    rootView = rootView.findViewById(R.id.map);
    if (mapView != null){
      mapView.onCreate(null);
      mapView.onResume();
      mapView.getMapAsync(this);
    }
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {

    gMap = googleMap;

    LatLng place = new LatLng(-34.603684,-58.381559);
    gMap.addMarker(new MarkerOptions().position(place).title("Im a marker in BA!"));
    gMap.moveCamera(CameraUpdateFactory.newLatLng(place));
  }
}
