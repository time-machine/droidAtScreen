package com.ribomation.droidAtScreen.cmd;

import com.ribomation.droidAtScreen.Application;
import com.ribomation.droidAtScreen.dev.AndroidDevice;

import java.awt.image.BufferedImage;

public class ScreenShotCommand extends Command {
  public ScreenShotCommand() {
    setLabel("Save ScreenShot as JPG");
    setTooltip("Takes a screen-shot of the current device and saves it as " +
        "JPG file.");
    setEnabled(false);
  }

  @Override
  protected void doExecute(final Application app) {
    AndroidDevice dev = app.getSelectedDevice();
    if (dev == null) return;

    final BufferedImage screenShot = dev.getScreenShot();
    // TOOD: what's next?
  }
}
