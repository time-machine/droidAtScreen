package com.ribomation.droidAtScreen.dev;

import com.ribomation.droidAtScreen.Application;
import com.ribomation.droidAtScreen.cmd.CommandWithTarget;
import com.ribomation.droidAtScreen.gui.DeviceFrame;

/**
 * Flips the device-frame 90 degrees.
 */
public class UpsideDownCommand extends CommandWithTarget<DeviceFrame> {
  public UpsideDownCommand(DeviceFrame deviceFrame) {
    super(deviceFrame);
    setIcon("upsidedown");
    setTooltip("Flips the image upside-down. Useful for ZTE Blade devices.");
    updateButton(deviceFrame);
  }

  @Override
  protected void doExecute(Application app, DeviceFrame deviceFrame) {
    deviceFrame.setUpsideDown(!deviceFrame.isUpsideDown());
    deviceFrame.validate();
  }

  protected void updateButton(DeviceFrame deviceFrame) {}
}
