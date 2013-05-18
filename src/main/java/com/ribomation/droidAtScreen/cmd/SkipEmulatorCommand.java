package com.ribomation.droidAtScreen.cmd;

import com.ribomation.droidAtScreen.Application;

public class SkipEmulatorCommand extends CheckBoxCommand {
  public SkipEmulatorCommand() {
    setLabel("Skip Emulators");
    setTooltip("Show new devices immediately");
  }

  @Override
  protected void notifyApplication(Application app, boolean selected) {}

  @Override
  protected boolean getPreferenceValue() {
    return getApplication().getSettings().isHideEmulators();
  }

  @Override
  protected void setPreferenceValue(boolean value) {
    getApplication().getSettings().setHideEmulators(value);
  }
}
