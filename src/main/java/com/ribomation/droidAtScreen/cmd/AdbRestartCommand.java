package com.ribomation.droidAtScreen.cmd;

import com.ribomation.droidAtScreen.Application;
import com.ribomation.droidAtScreen.gui.StatusBar;

import javax.swing.*;

/**
 * Restarts the ADB server.
 */
public class AdbRestartCommand extends Command {
  public AdbRestartCommand() {
    setLabel("Restart ADB");
    setIcon("sync");
    setMnemonic('R');
    setTooltip("Tries to restart the ADB server. Unplug your device(s) first.");
  }

  @Override
  protected void doExecute(final Application app) {
    final StatusBar statusBar = app.getAppFrame().getStatusBar();
    statusBar.message("Restarting ADB...");

    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        app.disconnectAll();
        boolean succeeded = app.getDeviceManager().restartADB();
        statusBar.message("ADB restart " + (succeeded ? "succeeded" : "failed"));
      }
    });
  }
}
