package com.ribomation.droidAtScreen.dev;

/**
 * Notification of connected and disconnected devices.
 */
public interface AndroidDeviceListener {
  /**
   * Invoked when a new device is detected.
   * @param dev the new device
   */
  void connected(AndroidDevice dev);

  /**
   * Invoked when a device goes offline.
   * @param dev the defunct device
   */
  void disconnected(AndroidDevice dev);
}
