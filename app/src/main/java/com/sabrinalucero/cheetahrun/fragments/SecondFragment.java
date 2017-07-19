package com.sabrinalucero.cheetahrun.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sabrinalucero.cheetahrun.R;


public class SecondFragment extends Fragment {


  public SecondFragment() {
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {


    View view = inflater.inflate(R.layout.fragment_second, container, false);
    return view;
  }
}
