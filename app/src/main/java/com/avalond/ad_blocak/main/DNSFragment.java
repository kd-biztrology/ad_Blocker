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
public class DNSFragment extends Fragment {

  public DNSFragment() {
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_dns, container, false);

    RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.dns_entries);

    // use this setting to improve performance if you know that changes
    // in content do not change the layout size of the RecyclerView
    mRecyclerView.setHasFixedSize(true);

    // use a linear layout manager
    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
    mRecyclerView.setLayoutManager(mLayoutManager);

    final ItemRecyclerViewAdapter mAdapter = new ItemRecyclerViewAdapter(
        MainActivity.config.dnsServers.items);
    mRecyclerView.setAdapter(mAdapter);

    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback(mAdapter));
    itemTouchHelper.attachToRecyclerView(mRecyclerView);

    FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.dns_add);
    fab.setOnClickListener(view -> {
      MainActivity main = (MainActivity) getActivity();
      main.editItem(null, item -> {
        MainActivity.config.dnsServers.items.add(item);
        mAdapter.notifyItemInserted(mAdapter.getItemCount() - 1);
        FileHelper.writeSettings(getContext(), MainActivity.config);
      });
    });

    Switch dnsEnabled = (Switch) rootView.findViewById(R.id.dns_enabled);
    dnsEnabled.setChecked(MainActivity.config.dnsServers.enabled);
    dnsEnabled.setOnCheckedChangeListener((buttonView, isChecked) -> {
      MainActivity.config.dnsServers.enabled = isChecked;
      FileHelper.writeSettings(getContext(), MainActivity.config);
    });
    return rootView;
  }
}
