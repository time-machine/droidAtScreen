package com.ribomation.droidAtScreen.dev;

import java.awt.image.BufferedImage;

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

  /**
   * Captures and returns a screen-shot from the device.
   * @return a new screen shot
   * @throws RuntimeException if it failed
   */
  BufferedImage getScreenShot();

  /**
   * Captures and returns a screen-shot from the device.
   * @param landscapeMode true if the image should flipped to landscape mode
   * @return a new screen shot
   * @throws RuntimeException if it failed
   */
  BufferedImage getScreenShot(boolean landscapeMode);
}
