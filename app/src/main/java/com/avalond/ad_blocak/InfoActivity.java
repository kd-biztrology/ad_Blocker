/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Copyright (C) 2016 Julian Andres Klode <jak@jak-linux.org>
 * Copyright (C) 2016 avalond <agonyice0115@gmail.com>
 */
package com.avalond.ad_blocak;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_info);

    ((TextView) findViewById(R.id.app_version_info))
        .setText(getString(R.string.app_version_info,BuildConfig.VERSION_NAME));
  }
}
