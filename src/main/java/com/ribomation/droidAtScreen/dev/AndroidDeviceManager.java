package com.ribomation.droidAtScreen.dev;

import com.android.ddmlib.AndroidDebugBridge;

/**
 * A facade to AndroidDebugBridge.
 */
public class AndroidDeviceManager {
  public AndroidDeviceManager() {
    AndroidDebugBridge.init(false);
    //
  }
}
