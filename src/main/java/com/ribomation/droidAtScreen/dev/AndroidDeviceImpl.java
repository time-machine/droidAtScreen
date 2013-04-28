package com.ribomation.droidAtScreen.dev;

import com.android.ddmlib.IDevice;
import com.android.ddmlib.RawImage;

import java.awt.image.BufferedImage;

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

  @Override
  public boolean isEmulator() {
    return target.isEmulator();
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

      BufferedImage image = new BufferedImage(screenShot.width,
          screenShot.height, BufferedImage.TYPE_INT_ARGB);
      int pixelIndexIncrement = screenShot.bpp >> 3;

      for (int y = 0, pixelIdx = 0; y < screenShot.height; y++) {
        for (int x = 0; x < screenShot.width; x++,
            pixelIdx += pixelIndexIncrement) {
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
