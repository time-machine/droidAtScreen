package com.ribomation.droidAtScreen.dev;

import com.android.ddmlib.IDevice;

/**
 * Implementation of {@link com.ribomation.droidAtScreen.dev.AndroidDevice},
 * that hides all Android tool specific invocations.
 */
public class AndroidDeviceImpl implements AndroidDevice {
  private IDevice target;

  public AndroidDeviceImpl(IDevice target) {
    this.target = target;
  }

  @Override
  public String getName() {
    return target.getSerialNumber();
  }
}
