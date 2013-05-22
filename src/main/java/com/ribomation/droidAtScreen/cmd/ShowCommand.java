package com.ribomation.droidAtScreen.cmd;

import com.ribomation.droidAtScreen.Application;
import com.ribomation.droidAtScreen.gui.DeviceFrame;

@Deprecated
public class ShowCommand extends Command {
  public ShowCommand() {
    setLabel("Show");
    setTooltip("Shows the current device");
    setEnabledOnlyWithDevice(true);
  }

  @Override
  protected void doExecute(Application app) {}
}
