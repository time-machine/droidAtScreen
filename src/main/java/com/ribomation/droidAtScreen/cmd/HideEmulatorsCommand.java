package com.ribomation.droidAtScreen.cmd;

import com.ribomation.droidAtScreen.gui.DeviceFrame;

/**
 * If emulators should popup or not.
 */
public class HideEmulatorsCommand extends CheckBoxCommand {
  public HideEmulatorsCommand() {
    setLabel("Hide Emulators");
    setTooltip("Do not show emulators automatically");
  }

  @Override
  protected boolean getPreferenceValue() {
    return getApplication().getSettings().isHideEmulators();
  }

  @Override
  protected void setPreferenceValue(boolean hide) {
    getApplication().getSettings().setHideEmulators(hide);
    for (DeviceFrame frame :
        getApplication().getDeviceTableModel().getDevices()) {
      if (frame.getDevice().isEmulator()) frame.setVisible(!hide);
    }
    getApplication().getDeviceTableModel().refresh();
  }
}
