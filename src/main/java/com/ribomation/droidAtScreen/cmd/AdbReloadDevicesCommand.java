package com.ribomation.droidAtScreen.cmd;

import com.ribomation.droidAtScreen.Application;

import javax.swing.*;

/**
 * Reloads the devices from ADB.
 */
public class AdbReloadDevicesCommand extends Command {
  public AdbReloadDevicesCommand() {
    setLabel("Reload Devices");
    setIcon("diagram");
    setTooltip("Reloads all devices from ADB.");
  }

  @Override
  protected void doExecute(final Application app) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        app.getDeviceManager().reloadDevices();
        app.getAppFrame().getStatusBar().message("Android devices reloaded");
      }
    });
  }
}
