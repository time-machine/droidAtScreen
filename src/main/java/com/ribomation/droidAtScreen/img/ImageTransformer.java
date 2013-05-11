package com.ribomation.droidAtScreen.img;

import java.awt.image.BufferedImage;

/**
 * Transforms an image.
 */
public interface ImageTransformer {
  /**
   * Returns a transformed image.
   * @param img input image
   * @return transformed image
   */
  BufferedImage transform(BufferedImage img);
}
