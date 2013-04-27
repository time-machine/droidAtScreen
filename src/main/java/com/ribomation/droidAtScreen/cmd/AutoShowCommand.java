package com.ribomation.droidAtScreen.cmd;

import com.ribomation.droidAtScreen.Application;

import javax.swing.*;

public class AutoShowCommand extends CheckBoxCommand {
  public AutoShowCommand() {
    setLabel("Auto Show");
    setTooltip("Show new devices immediately");
  }

  @Override
  protected void notifyApplication(Application app, boolean selected) {
    app.setAutoShow(selected);
  }

  @Override
  protected String getPreferencesKey() {
    return "auto-show";
  }
}
