package com.ribomation.droidAtScreen.cmd;

import com.ribomation.droidAtScreen.Application;
import com.ribomation.droidAtScreen.dev.AndroidDevice;

public class ShowCommand extends Command {
  public ShowCommand() {
    setLabel("Show");
    setTooltip("Shows the current device");
    setEnabled(false);
  }

  @Override
  protected void doExecute(Application app) {
    AndroidDevice selectedDevice = app.getSelectedDevice();
    if (selectedDevice == null) return;

    app.showDevice(selectedDevice);
  }
}
