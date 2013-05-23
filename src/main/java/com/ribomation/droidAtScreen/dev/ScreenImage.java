package com.ribomation.droidAtScreen.dev;

import com.android.ddmlib.RawImage;

import java.awt.image.BufferedImage;

/**
 * Wrapper around a Android raw screen image.
 */
public class ScreenImage {
  private RawImage rawImage;

  public ScreenImage(RawImage rawImage) {
    this.rawImage = rawImage;
  }

  public RawImage getRawImage() {
    return rawImage;
  }

  public BufferedImage toBufferedImage() {
    final int W = rawImage.width;
    final int H = rawImage.height;
    BufferedImage image = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
    int bytesPerPixels = rawImage.bpp >> 3;

    for (int y = 0, pxIdx = 0; y < H; y++) {
      for (int x = 0; x < W; x++, pxIdx += bytesPerPixels) {
        image.setRGB(x, y, rawImage.getARGB(pxIdx));
      }
    }

    return image;
  }

  public ScreenImage rotate() {
    rawImage = rawImage.getRotated();
    return this;
  }

  public ScreenImage copy() {
    ScreenImage copy = new ScreenImage(new RawImage());
    copy.rawImage.version = this.rawImage.version;
    copy.rawImage.bpp = this.rawImage.bpp;
    copy.rawImage.size = this.rawImage.size;
    copy.rawImage.width = this.rawImage.width;
    copy.rawImage.height = this.rawImage.height;
    copy.rawImage.red_offset = this.rawImage.red_offset;
    copy.rawImage.red_length = this.rawImage.red_length;
    copy.rawImage.blue_offset = this.rawImage.blue_offset;
    copy.rawImage.blue_length = this.rawImage.blue_length;
    copy.rawImage.green_offset = this.rawImage.green_offset;
    copy.rawImage.green_length = this.rawImage.green_length;
    copy.rawImage.alpha_offset = this.rawImage.alpha_offset;
    copy.rawImage.alpha_length = this.rawImage.alpha_length;
    copy.rawImage.data = this.rawImage.data;
    System.arraycopy(this.rawImage.data, 0, copy.rawImage.data, 0,
        this.rawImage.data.length);
    return copy;
  }

  @Override
  public String toString() {
    return String.format("RawImage[%dx%d, %d bytes, bits/px=%d]",
        rawImage.width, rawImage.height, rawImage.data.length, rawImage.bpp);
  }

  public int getWidth() {
    return rawImage.width;
  }

  public int getHeight() {
    return rawImage.width;
  }
}
