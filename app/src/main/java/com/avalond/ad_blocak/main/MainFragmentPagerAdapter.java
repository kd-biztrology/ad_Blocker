/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Copyright (C) 2016 Julian Andres Klode <jak@jak-linux.org>
 * Copyright (C) 2016 avalond <agonyice0115@gmail.com>
 */
package com.avalond.ad_blocak.main;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Adapter for the pager that holds the fragments of the main activity.
 * @author kevin
 */

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {

  private final ArrayList<Fragment> fragments = new ArrayList<>();


  public MainFragmentPagerAdapter(FragmentManager fm) {
    super(fm);
    fragments.add(new StartFragment());
    fragments.add(new HostsFragment());
    fragments.add(new DNSFragment());
  }


  @Override
  public Fragment getItem(int position) {
    return fragments.get(position);
  }


  @Override
  public int getCount() {
    return fragments.size();
  }
}
