package com.ribomation.droidAtScreen.dev;

import com.android.ddmlib.IDevice;
import com.android.ddmlib.RawImage;

import java.awt.image.BufferedImage;
// TODO: -

/**
 * Implementation of {@link com.ribomation.droidAtScreen.dev.AndroidDevice},
 * that hides all Android tool specific invocations.
 */
// TODO: -
public class AndroidDeviceImpl implements AndroidDevice {
  private IDevice target;

  public AndroidDeviceImpl(IDevice target) {
    this.target = target;
  }

  @Override
  public String getName() {
    return target.getSerialNumber();
  }

  @Override
  public boolean isEmulator() {
    return target.isEmulator();
  }

  // TODO: -

  @Override
  public ConnectionState getState() {
    IDevice.DeviceState s = target.getState();
    if (s == IDevice.DeviceState.ONLINE) return ConnectionState.online;
    if (s == IDevice.DeviceState.BOOTLOADER) return ConnectionState.booting;
    if (s == IDevice.DeviceState.OFFLINE) return ConnectionState.offline;
    return ConnectionState.offline;
  }

  @Override
  public BufferedImage getScreenShot() {
    return getScreenShot(false);
  }

  @Override
  public BufferedImage getScreenShot(boolean landscapeMode) {
    try {
      RawImage screenShot = target.getScreenshot();
      if (screenShot == null) return null;
      if (landscapeMode) {
        screenShot = screenShot.getRotated();
      }

      final int H = screenShot.height;
      final int W = screenShot.width;
      BufferedImage image = new BufferedImage(W, H, BufferedImage.TYPE_INT_ARGB);
      int pixelIndexIncrement = screenShot.bpp >> 3;

      for (int y = 0, pixelIdx = 0; y < H; y++) {
        for (int x = 0; x < W; x++, pixelIdx += pixelIndexIncrement) {
          image.setRGB(x, y, screenShot.getARGB(pixelIdx));
        }
      }

      return image;
    }
    catch (Exception e) {
      throw new RuntimeException("Failed to capture device screen shot: " + e);
    }
  }
}
