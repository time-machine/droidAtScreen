package com.ribomation.droidAtScreen.cmd;

import com.ribomation.droidAtScreen.Application;

/**
 * Applies one or more transformations of the device image.
 */
public class UpsideDownCommand extends CheckBoxCommand {
  public UpsideDownCommand() {
    setLabel("Upside-Down");
    setTooltip("Flips the image upside-down. Useful for ZTE Blade devices.");
    setIcon("upsidedown");
    setEnabledOnlyWithDevice(true);
  }

  @Override
  protected String getPreferencesKey() {
    return "upside-down";
  }

  @Override
  protected boolean getDefaultValue() {
    return false;
  }

  @Override
  protected void notifyApplication(Application app, boolean upsideDown) {
    app.setUpsideDown(upsideDown);
  }
}
