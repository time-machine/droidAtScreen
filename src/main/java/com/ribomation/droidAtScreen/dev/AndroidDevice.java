package com.ribomation.droidAtScreen.dev;

/**
 * Facade/wrapper around an Android device.
 */
public interface AndroidDevice {
  /**
   * Returns its device name.
   * @return its name
   */
  String getName();

  /**
   * Returns true if it's an emulator, i.e., not a physical device.
   * @return true if not physical device
   */
  boolean isEmulator();
}
