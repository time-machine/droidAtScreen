package com.ribomation.droidAtScreen.cmd;

import com.ribomation.droidAtScreen.Application;

public class SkipEmulatorCommand extends CheckBoxCommand {
  public SkipEmulatorCommand() {
    setLabel("Skip Emulators");
    setTooltip("Show new devices immediately");
  }

  @Override
  protected void notifyApplication(Application app, boolean selected) {
    app.setSkipEmulator(selected);
  }

  @Override
  protected String getPreferencesKey() {
    return "skip-emulator";
  }
}
