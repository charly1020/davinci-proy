package com.sabrinalucero.cheetahrun.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sabrinalucero.cheetahrun.fragments.FirstFragment;
import com.sabrinalucero.cheetahrun.fragments.RunMapFragment;
import com.sabrinalucero.cheetahrun.fragments.SecondFragment;
import com.sabrinalucero.cheetahrun.fragments.ThirdFragment;

/**
 * Created by Sabrina on 19/07/2017.
 */

public class PageAdapterView extends FragmentStatePagerAdapter {
  private int numberOfTabs;



  public PageAdapterView(FragmentManager fm, int numberOfTabs) {
    super(fm);
    this.numberOfTabs = numberOfTabs;
  }

  @Override
  public Fragment getItem(int position) {

    switch (position){
      case 0: //corresponde a tab1
        //segun donde se haga click se va a renderizar uno u otro
        return new FirstFragment();
      case 1:
        return new RunMapFragment();
      case 2:
        return new SecondFragment();
      case 3:
        return new ThirdFragment();
      default:
        return null;
    }

  }

  //devuelve la cantidad de tabs
  @Override
  public int getCount() {
    return numberOfTabs;
  }
}
