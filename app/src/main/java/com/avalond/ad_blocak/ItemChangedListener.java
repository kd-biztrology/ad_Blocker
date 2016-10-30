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

/**
 * A callback for returns from the {@link ItemActivity}.
 * The method {@link #onItemChanged(Configuration.Item)} will be called with a new item as
 * returned by the ItemActivity.
 */
public interface ItemChangedListener {
    void onItemChanged(Configuration.Item item);
}
