package com.ribomation.droidAtScreen.dev;

import com.ribomation.droidAtScreen.Application;
import com.ribomation.droidAtScreen.cmd.CommandWithTarget;
import com.ribomation.droidAtScreen.gui.DeviceFrame;

public class RecordingCommand extends CommandWithTarget<DeviceFrame> {
  public RecordingCommand(DeviceFrame deviceFrame) {
    super(deviceFrame);
  }

  @Override
  protected void doExecute(Application app, DeviceFrame deviceFrame) {}

  protected void updateButton(DeviceFrame deviceFrame) {}
}
