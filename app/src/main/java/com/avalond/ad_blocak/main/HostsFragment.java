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

import com.avalond.ad_blocak.FileHelper;
import com.avalond.ad_blocak.MainActivity;
import com.avalond.ad_blocak.R;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

/**
 * @author kevin
 */
public class HostsFragment extends Fragment {

  public HostsFragment() {

  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    View rootView = inflater.inflate(R.layout.fragment_hosts, container, false);

    RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.host_entries);

    mRecyclerView.setHasFixedSize(true);

    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
    mRecyclerView.setLayoutManager(mLayoutManager);

    final ItemRecyclerViewAdapter mAdapter =
        new ItemRecyclerViewAdapter(MainActivity.config.hosts.items);
    mRecyclerView.setAdapter(mAdapter);

    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback(mAdapter));
    itemTouchHelper.attachToRecyclerView(mRecyclerView);

    FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.host_add);
    fab.setOnClickListener(view -> {

      final MainActivity main = (MainActivity) getActivity();
      main.editItem(null, item -> {

        MainActivity.config.hosts.items.add(item);
        mAdapter.notifyItemInserted(mAdapter.getItemCount() - 1);
        FileHelper.writeSettings(getContext(), MainActivity.config);
      });
    });

    Switch hostEnabled = (Switch) rootView.findViewById(R.id.host_enabled);
    hostEnabled.setChecked(MainActivity.config.hosts.enabled);
    hostEnabled.setOnCheckedChangeListener((buttonView, isChecked) -> {

      MainActivity.config.hosts.enabled = isChecked;
      FileHelper.writeSettings(getContext(), MainActivity.config);
    });

    return rootView;
  }
}
