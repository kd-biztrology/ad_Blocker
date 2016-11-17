/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Copyright (C) 2016 Julian Andres Klode <jak@jak-linux.org>
 * Copyright (C) 2016 avalond <agonyice0115@gmail.com>
 */
package com.avalond.ad_blocak.vpn;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootComplete extends BroadcastReceiver {

  @Override
  public void onReceive(Context context, Intent intent) {
    AdVpnService.checkStartVpnOnBoot(context);
  }
}