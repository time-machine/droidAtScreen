package com.ribomation.droidAtScreen.cmd;

import com.ribomation.droidAtScreen.Application;

/**
 * Applies one or more transformations of the device image.
 */
@Deprecated
public class UpsideDownCommand extends CheckBoxCommand {
  public UpsideDownCommand() {
    setLabel("Upside-Down");
    setTooltip("Flips the image upside-down. Useful for ZTE Blade devices.");
    setIcon("upsidedown");
    setEnabledOnlyWithDevice(true);
  }

  @Override
  protected void notifyApplication(Application app, boolean upsideDown) {
    app.setUpsideDown(upsideDown);
  }

  @Override
  protected boolean getPreferenceValue() {
    return getApplication().getSettings().isUpsideDown();
  }

  @Override
  protected void setPreferenceValue(boolean value) {
    getApplication().getSettings().setUpsideDown(value);
  }
}
