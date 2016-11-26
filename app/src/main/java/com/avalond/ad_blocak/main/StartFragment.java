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

import static android.app.Activity.RESULT_OK;

import com.avalond.ad_blocak.Configuration;
import com.avalond.ad_blocak.FileHelper;
import com.avalond.ad_blocak.MainActivity;
import com.avalond.ad_blocak.R;
import com.avalond.ad_blocak.vpn.AdVpnService;
import com.avalond.ad_blocak.vpn.Command;
import java.io.File;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.VpnService;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

/**
 * @author kevin
 */

public class StartFragment extends Fragment {
  public static final int REQUEST_START_VPN = 1;
  private static final String TAG = "StartFragment";


  public StartFragment() {
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_start, container, false);
    Switch switchOnBoot = (Switch) rootView.findViewById(R.id.switch_onboot);

    ImageView view = (ImageView) rootView.findViewById(R.id.start_button);

    TextView stateText = (TextView) rootView.findViewById(R.id.state_textview);
    stateText.setText(getString(AdVpnService.vpnStatusToTextId(AdVpnService.vpnStatus)));

    view.setOnLongClickListener(v -> {
      if (AdVpnService.vpnStatus != AdVpnService.VPN_STATUS_STOPPED) {
        Log.i(TAG, "Attempting to disconnect");

        Intent intent = new Intent(getActivity(), AdVpnService.class);
        intent.putExtra("COMMAND", Command.STOP.ordinal());
        getActivity().startService(intent);
      } else {
        checkHostsFilesAndStartService();
      }
      return true;
    });

    switchOnBoot.setChecked(MainActivity.config.autoStart);
    switchOnBoot.setOnCheckedChangeListener((buttonView, isChecked) -> {
      MainActivity.config.autoStart = isChecked;
      FileHelper.writeSettings(getContext(), MainActivity.config);
    });

    return rootView;
  }


  private void checkHostsFilesAndStartService() {
    if (!areHostsFilesExistant()) {
      new AlertDialog.Builder(getActivity())
          .setIcon(R.drawable.ic_warning)
          .setTitle(R.string.missing_hosts_files_title)
          .setMessage(R.string.missing_hosts_files_message)
          .setNegativeButton(R.string.button_no, (dialog, which) -> {
                          /* Do nothing */
          })
          .setPositiveButton(R.string.button_yes, (dialog, which) -> startService())
          .show();
      return;
    }
    startService();
  }


  private void startService() {
    Log.i(TAG, "Attempting to connect");
    Intent intent = VpnService.prepare(getContext());
    if (intent != null) {
      startActivityForResult(intent, REQUEST_START_VPN);
    } else {
      onActivityResult(REQUEST_START_VPN, RESULT_OK, null);
    }
  }


  /**
   * Check if all configured hosts files exist.
   *
   * @return true if all host files exist or no host files were configured.
   */
  private boolean areHostsFilesExistant() {
    if (!MainActivity.config.hosts.enabled) {
      return true;
    }
    for (Configuration.Item item : MainActivity.config.hosts.items) {
      File file = FileHelper.getItemFile(getContext(), item);
      if (item.state != Configuration.Item.STATE_IGNORE && file != null) {
        if (!file.exists()) {
          return false;
        }
      }
    }
    return true;
  }


  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    Log.d(TAG, "onActivityResult: Received result=" + resultCode + " for request=" + requestCode);
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_START_VPN && resultCode == RESULT_OK) {
      Log.d("MainActivity", "onActivityResult: Starting service");
      Intent intent = new Intent(getContext(), AdVpnService.class);
      intent.putExtra("COMMAND", Command.START.ordinal());
      intent.putExtra("NOTIFICATION_INTENT",
          PendingIntent.getActivity(getContext(), 0,
              new Intent(getContext(), MainActivity.class), 0));
      getContext().startService(intent);
    }
  }
}
