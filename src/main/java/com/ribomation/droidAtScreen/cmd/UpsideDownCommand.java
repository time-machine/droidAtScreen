package com.ribomation.droidAtScreen.cmd;

import com.ribomation.droidAtScreen.Application;
import com.ribomation.droidAtScreen.cmd.CommandWithTarget;
import com.ribomation.droidAtScreen.gui.DeviceFrame;

/**
 * Flips the device-frame 90 degrees.
 */
public class UpsideDownCommand extends CommandWithTarget<DeviceFrame> {
  public UpsideDownCommand(DeviceFrame deviceFrame) {
    super(deviceFrame);
    updateButton(deviceFrame);
  }

  @Override
  protected void doExecute(Application app, DeviceFrame deviceFrame) {
    deviceFrame.setUpsideDown(!deviceFrame.isUpsideDown());
    updateButton(deviceFrame);
    deviceFrame.repaint();
  }

  protected void updateButton(DeviceFrame deviceFrame) {
    setIcon(deviceFrame.isUpsideDown() ? "downsideup" : "upsidedown");
    setTooltip(String.format("Flips the image upside-down [%s]. " +
        "(Useful for ZTE Blade devices)", deviceFrame.isUpsideDown() ?
            "UpsideDown" : "Normal"));
  }
}
