package com.ribomation.droidAtScreen.img;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 * Handles portrait and landscape mode.
 */
public class OrientationImageTransformer implements ImageTransformer {
  private RenderingHints hints = new RenderingHints(
      RenderingHints.KEY_INTERPOLATION,
      RenderingHints.VALUE_INTERPOLATION_BICUBIC);
  private boolean portrait = true;

  public OrientationImageTransformer() {
    setPortrait(true);
  }

  public OrientationImageTransformer(boolean portrait) {
    setPortrait(portrait);
  }

  public boolean isPortrait() {
    return portrait;
  }

  public void setPortrait(boolean portrait) {
    this.portrait = portrait;
  }

  @Override
  public BufferedImage transform(BufferedImage img) {
    int w = img.getWidth(), h = img.getHeight();
    AffineTransform tx = createTX(w, h);
    AffineTransformOp op = new AffineTransformOp(tx, hints);
    return op.filter(img, new BufferedImage(h, w, img.getType()));
  }

  private AffineTransform createTX(int w, int h) {
    if (isPortrait()) return new AffineTransform();

    AffineTransform tx = new AffineTransform();
    AffineTransform rotate = AffineTransform.getQuadrantRotateInstance(3, w / 2,
        h / 2);
    AffineTransform move = AffineTransform.getTranslateInstance(w / 4, w / 4);
    tx.concatenate(rotate);
    tx.concatenate(move);
    return tx;
  }
}
